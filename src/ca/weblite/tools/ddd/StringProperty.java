/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd;

import static ca.weblite.tools.ddd.ContentType.Text;

/**
 *
 * @author shannah
 */
public class StringProperty extends AbstractProperty<String> {
    public StringProperty() {
        super(Text);
    }
}
