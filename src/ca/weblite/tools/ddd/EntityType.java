/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;


import ca.weblite.tools.ddd.Property.Description;
import ca.weblite.tools.ddd.Property.Label;
import ca.weblite.tools.ddd.Property.Widget;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author shannah
 */
public class EntityType implements Iterable<Property> {
    private EntityType superType;
    //private Map<String,Property> properties = new HashMap<>();
    private final Set<Property> propertiesSet = new LinkedHashSet<>();
    private static Map<Class<? extends EntityType>, EntityType> types = new HashMap<>();
    
    public static EntityType getEntityType(Class<? extends EntityType> type) {
        EntityType t = types.get(type);
        if (t == null) {
            try {
                t = (EntityType)type.newInstance();
                types.put(type, t);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return t;
    }
    
    public void addProperty(Property property) {
        propertiesSet.add(property);
    }
    
    public void addAllProperties(Property... properties) {
        for (Property p : properties) {
            addProperty(p);
        }
    }
    
    public boolean removeProperty(Property property) {
        if (propertiesSet.contains(property)) {
            propertiesSet.remove(property);
            return true;
        }
        return false;
    }

    @Override
    public Iterator<Property> iterator() {
        return propertiesSet.iterator();
    }

    
    public boolean contains(Property property) {
        return propertiesSet.contains(property);
    }
    
    
    public StringProperty string(Attribute... atts) {
        StringProperty out = new StringProperty();
        out.setAttributes(atts);
        propertiesSet.add(out);
        return out;
    }
    
    public IntProperty Integer() {
        IntProperty p = new IntProperty();
        propertiesSet.add(p);
        
        return p;
     
    }
    
    public DoubleProperty Double() {
        DoubleProperty d = new DoubleProperty();
        propertiesSet.add(d);
        return d;
    }
    
    public BooleanProperty Boolean() {
        BooleanProperty b = new BooleanProperty();
        propertiesSet.add(b);
        return b;
    }
    
    public <T extends EntityList> ListProperty<T> compose(Class<T> type, Attribute... atts) {
        ListProperty p = new ListProperty(type);
        p.setAttributes(atts);
        propertiesSet.add(p);
        return p;
    }
    
    public <T extends EntityList> ListProperty<T> list(Class<T> type, Attribute... atts) {
        return compose(type, atts);
    }
    
    
    public static Label label(String label) {
        return new Label(label);
    }
    
    public static Description description(String description) {
        return new Description(description);
    }
    
    public static Widget widget(Attribute... atts) {
        WidgetDescriptor desc = new WidgetDescriptor();
        desc.setAttributes(atts);
        return new Widget(desc);
    }
    
    public static Tags tags(Tag... atts) {
        return new Tags(atts);
    }
    

    private boolean frozen;
    void freeze() {
        if (frozen) {
            return;
        }
        frozen = true;
        for (Property p : propertiesSet) {
            p.freeze();
        }
    }
}
