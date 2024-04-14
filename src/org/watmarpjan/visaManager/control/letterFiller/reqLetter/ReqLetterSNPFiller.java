/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.reqLetter;

import java.io.File;
import java.time.LocalDate;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public abstract class ReqLetterSNPFiller extends ReqLetterFiller
{

    public ReqLetterSNPFiller(File fLetter, MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(fLetter, p, objCtrConfigFiles);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException, NullPointerException
    {
        super.fillLetter();
       
        fillField("«name»");
        fillField("«middleName»");
        fillField("«lastName»");
        fillField("«ethnicity»");
        fillField("«passportCountry»");

        fillField("«arrivalLastEntryDateDay»");
        fillField("«arrivalLastEntryDateMonthThai»");
        fillField("«arrivalLastEntryDateYear»");

        fillField("«WatResidingAtThai»");
        fillField("«WatResidingAtThaiaddrTambon»");
        fillField("«WatResidingAtThaiaddrAmpher»");
        fillField("«WatResidingAtThaiaddrJangwat»");

    }

}
