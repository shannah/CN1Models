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
public class EntityListProperty<T extends EntityList> extends EntityProperty<T> {
    
    public EntityListProperty(Class<T> cls) {
        super(cls);
    }
    
}
