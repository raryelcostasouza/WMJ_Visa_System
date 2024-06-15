/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.reqLetter;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.control.letterFiller.ODTLetterFiller;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public abstract class ReqLetterFiller extends ODTLetterFiller
{

    public ReqLetterFiller(File fLetter, MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(fLetter, p, objCtrConfigFiles);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException, NullPointerException
    {
        fillField("«titleTH2»");
        fillField("«nationality»");
        fillField("«passportNumber»");

        fillField("«ordinationTypeThai»");

        fillField("«WatResidingAtThai_addrTambon_addrAmpher_addrJangwat»");

        fillField("«ordinationDateDayThai»");
        fillField("«ordinationDateMonthThai»");
        fillField("«ordinationDateYearThai»");

        fillField("«WatOrdainedAtThai_addrTambon_addrAmpher_addrJangwat_addrCountry»");
    }

}
