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
public interface Thing {
    public static final Tag additionalType = new Tag(),
            alternateName = new Tag(),
            description = new Tag(),
            disambiguatingDescription = new Tag(),
            identifier = new Tag(),
            image = new Tag(),
            mainEntityOfPage = new Tag(),
            name = new Tag(),
            potentialAction = new Tag(),
            sameAs = new Tag(),
            subjectOf = new Tag(),
            url = new Tag();
            
}
