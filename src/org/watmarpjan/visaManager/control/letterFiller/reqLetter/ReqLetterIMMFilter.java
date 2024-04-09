/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.reqLetter;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public abstract class ReqLetterIMMFilter extends ReqLetterFiller
{

    public ReqLetterIMMFilter(File fLetter, MonasticProfile p)
    {
        super(fLetter, p);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException, NullPointerException
    {
        super.fillLetter();

        fillField("«fullName»");
        fillField("«visaType»");
        fillField("«titleTH»");
        fillField("«arrivalLastEntryDateThai»");
        fillField("«visaExpiryDateThai»");

    }

}
