/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import ca.weblite.tools.ddd.Property.Description;
import ca.weblite.tools.ddd.Property.Label;



/**
 *
 * @author shannah
 */
public class WidgetDescriptor {
    private Property property;
    private AttributeSet attributes = new AttributeSet();
    
    public WidgetDescriptor(){
    
    }

    void setProperty(Property prop) {
        this.property = prop;
    }
    
    public WidgetDescriptor(Property prop) {
        this.property = prop;
    }
    
    public void setAttributes(Attribute... atts) {
        attributes.setAttributes(atts);
    }
    
    public <T extends Attribute> T getAttribute(Class<T> type) {
        return attributes.getAttribute(type);
    }

    public Type getWidgetType() {
        return attributes.getAttribute(Type.class);
    }
    
    public Label getLabel() {
        return attributes.getAttribute(Label.class);
    }
    
    public Description getDescription() {
        return attributes.getAttribute(Description.class);
    }
    
    public static class Type extends Attribute<Class> {
        
        public Type(Class value) {
            super(value);
        }
        
    }
    
    public AttributeSet getAllAttributes() {
        
        AttributeSet out = new AttributeSet();
        if (property != null) {
            out.addAll(property.getAttributes());
        }
        
        out.addAll(attributes);
        return out;
    }
    
    
}
