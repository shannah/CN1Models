/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

/**
 *
 * @author shannah
 */
public class ListProperty<T extends EntityList> extends EntityProperty<T> {
    
    public ListProperty(Class<T> cls) {
        super(cls);
    }

    @Override
    public void setValue(Entity entity, T value) {
        T existing = getValue(entity);
        if (existing != null) {
            entity.getAggregate().remove(existing);
        }
        
        if (value != null && value.getAggregate() != entity.getAggregate()) {
            value.getAggregate().remove(value);
        }
        super.setValue(entity, value);
        if (value != null) {
            entity.getAggregate().add(value);
        }
        
    }

    
    
}
