/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import static ca.weblite.tools.ddd.ContentType.BooleanType;

/**
 *
 * @author shannah
 */
public class BooleanProperty extends AbstractProperty<Boolean> {
    public BooleanProperty() {
        super(BooleanType);
    }
}
