/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.weblite.tools.ddd.schemas;

import ca.weblite.tools.ddd.Tag;

/**
 *
 * @author shannah
 */
public interface ListRowItem extends Thing {
    public static final Tag line1 = new Tag();
    public static final Tag line2 = new Tag();
    public static final Tag icon = new Tag();
}
