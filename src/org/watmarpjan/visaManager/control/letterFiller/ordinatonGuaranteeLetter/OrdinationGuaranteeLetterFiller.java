/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.control.letterFiller.ODTLetterFiller;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public abstract class OrdinationGuaranteeLetterFiller extends ODTLetterFiller
{
    public OrdinationGuaranteeLetterFiller(File fLetter, MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(fLetter, p, objCtrConfigFiles);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException
    {
        //need to use thai numbers
        fillField("«titleTH2»");
        fillField("«name»");

        fillField("«middleName»");

        fillField("«lastName»");
        fillField("«paliNameThai»");

        fillField("«age»");
        fillField("«vassa»");
        fillField("«ethnicity»");
        fillField("«nationality»");
        fillField("«passportNumber»");
        fillField("«passportCountry»");

        fillField("«ordinationTypeThai»");

        fillField("«ordinationDateDay»");
        fillField("«ordinationDateMonthThai»");
        fillField("«ordinationDateYear»");

        fillField("«WatOrdainedAtThai»");
        fillField("«WatOrdainedAtThaiaddrAmpher»");
        fillField("«WatOrdainedAtThaiaddrJangwat»");
        fillField("«preceptorName»");
    }
}
