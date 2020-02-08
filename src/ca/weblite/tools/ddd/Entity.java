/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import com.codename1.ui.CN;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.EventDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Observable;
import java.util.Set;

/**
 *
 * @author shannah
 */
public class Entity extends Observable implements java.io.Serializable {
    Map<Object,Object> properties;
    private EntityType entityType;
    private Map<Property,Set<ActionListener>> propertyChangeListenersMap;
    private EventDispatcher propertyChangeListeners;
    
    
    public void addPropertyChangeListener(Property property, ActionListener<PropertyChangeEvent>  l)  {
        if (propertyChangeListenersMap == null) {
            propertyChangeListenersMap = new HashMap<>();
        }
        Set<ActionListener> propertyListenerSet = propertyChangeListenersMap.get(property);
        if (propertyListenerSet == null) {
            propertyListenerSet = new LinkedHashSet<>();
            propertyChangeListenersMap.put(property, propertyListenerSet);
        }
        propertyListenerSet.add(l);
    }
    
    public void removePropertyChangeListener(Property property, ActionListener<PropertyChangeEvent> l) {
        if (propertyChangeListenersMap == null) {
            return;
        }
        Set<ActionListener> propertyListenerSet = propertyChangeListenersMap.get(property);
        if (propertyListenerSet == null) {
            return;
        }
        propertyListenerSet.remove(l);
    }
    
    public void addPropertyChangeListener(ActionListener<PropertyChangeEvent> l) {
        if (propertyChangeListeners == null) {
            propertyChangeListeners = new EventDispatcher();
        }
        propertyChangeListeners.addListener(l);
    }
    
    public void removePropertyChangeListener(ActionListener<PropertyChangeEvent> l) {
        if (propertyChangeListeners == null) {
            return;
        }
        propertyChangeListeners.removeListener(l);
    }
    
    protected void firePropertyChangeEvent(PropertyChangeEvent pce) {
        if (!CN.isEdt()) {
            CN.callSerially(()->firePropertyChangeEvent(pce));
            return;
        }
        if (propertyChangeListenersMap != null) {
            Set<ActionListener> listeners = propertyChangeListenersMap.get(pce.getProperty());
            if (listeners != null) {
                ArrayList<ActionListener> toSend = new ArrayList<>(listeners);
                for (ActionListener l : toSend) {
                    l.actionPerformed(pce);
                    if (pce.isConsumed()) {
                        return;
                    }
                }
            }
        }
        if (propertyChangeListeners != null) {
            propertyChangeListeners.fireActionEvent(pce);
        }
    }
   
    void initProperties() {
        if (properties == null) {
            properties = new HashMap<>();
        }
    }
    
    public void setEntityType(EntityType entityType) {
        entityType.freeze();
        this.entityType = entityType;
    }
    
    public EntityType getEntityType() {
        return entityType;
    }
   
    /**
     * @return the aggregate
     */
    public Aggregate getAggregate() {
        if (aggregate == null) {
            // Avoid NPEs
            aggregate = new Aggregate(this);
        }
        return aggregate;
    }
    
    void setAggregate(Aggregate aggregate) {
        this.aggregate = aggregate;
    }

    private transient Aggregate aggregate;
    
    @Override
    protected synchronized void clearChanged() {
        super.clearChanged(); 
    }
    
    public Object get(Object key) {
        if (properties == null) {
            return null;
        }
        if (key instanceof Property) {
            Property prop = (Property)properties.get(key);
            return prop.getValue(this);
        }
        return properties.get(key);
    }
    
    public void set(Object key, Object value) {
        if (properties == null) {
            if (entityType == null) {
                throw new IllegalArgumentException("No entity type.  Cannot set property");
            }
            initProperties();
        }
        if (key instanceof Property) {
            ((Property)key).setValue(this, value);
            return;
        }
        Object existing = properties.get(key);
        if (!Objects.equals(existing, value)) {
            properties.put(key, value);
            
            setChanged();
        }
    }
    
    public <T> T get(Property<T> prop) {
        if (entityType == null) {
            throw new IllegalArgumentException("Attempt to get property from entity with no entity type assigned");
        }
        if (!entityType.contains(prop)) {
            throw new IllegalArgumentException("Entity type "+entityType+" does not contain property "+prop);
        }
        if (properties == null) {
            return null;
        }
        
        return prop.getValue(this);
    }
    
    
    void setChangedInternal() {
        super.setChanged();
    }
    
    
    
}
