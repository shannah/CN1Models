/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import java.util.Objects;

/**
 *
 * @author shannah
 */
public class PropertyUtil {
    public static Object getRawProperty(Entity entity, Property prop) {
        if (entity.properties != null) {
            return entity.properties.get(prop);
        }
        return null;
    }
    
    public static void setRawProperty(Entity entity, Property prop, Object value) {
        if (value != null && !prop.getContentType().getRepresentationClass().isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Property "+prop+" of type "+prop.getContentType().getRepresentationClass()+" is not assignable by value "+value+" of type "+value.getClass());
        }
        if (entity.properties == null) {
            if (entity.getEntityType() != null) {
                if (!entity.getEntityType().contains(prop)) {
                    throw new IllegalArgumentException("Entity type "+entity.getEntityType()+" does not contain property "+prop);
                }
                entity.initProperties();
            }
        }
        if (entity.getEntityType() != null) {
            if (!entity.getEntityType().contains(prop)) {
                throw new IllegalArgumentException("Entity type "+entity.getEntityType()+" does not contain property "+prop);
            }
            Object existing = entity.properties.get(prop);
            if (!Objects.equals(existing, value)) {
                entity.properties.put(prop, value);
                entity.setChangedInternal();
                entity.firePropertyChangeEvent(new PropertyChangeEvent(entity, prop, existing, value));
            }
        } else {
            throw new IllegalArgumentException("Cannot set raw property on entity because it has no entity type assigned.");
        }
        
    }
}
