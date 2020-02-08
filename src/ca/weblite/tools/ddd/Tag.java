/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import ca.weblite.tools.ddd.Property.Name;

/**
 *
 * @author shannah
 */
public class Tag extends Attribute<Name> {
    
    public Tag(Name value) {
        super(value);
    }
    
    public Tag(String str) {
        this(new Name(str));
    }
    
    public Tag() {
        this(new Name(""));
    }
    
    
}
