/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import ca.weblite.tools.ddd.Attribute;
import ca.weblite.tools.ddd.ContentType;
import ca.weblite.tools.ddd.Entity;
import ca.weblite.tools.ddd.Property;
import com.codename1.ui.Component;

/**
 *
 * @author shannah
 */
public class DataTransformer extends Attribute {
    
    public DataTransformer() {
        super(null);
    }
    
    
    
    public boolean supports(ContentType source, ContentType target) {
        return false;
    }
    
    public <T,V> V transform(ContentType<T> source, ContentType<V> target, T data) {
        throw new IllegalArgumentException("Cannot transform data "+data+" from "+source+" to "+target);
    }
    
    
}
