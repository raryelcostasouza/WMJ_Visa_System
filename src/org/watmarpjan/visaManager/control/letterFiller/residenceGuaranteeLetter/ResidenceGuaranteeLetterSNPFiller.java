/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.control.letterFiller.ODTLetterFiller;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;

/**
 *
 * @author raryel
 */
public abstract class ResidenceGuaranteeLetterSNPFiller extends ODTLetterFiller
{
    public ResidenceGuaranteeLetterSNPFiller(File fTemplate, MonasticProfile p, CtrConfigFiles objCtrConfigFiles )
    {
        super(fTemplate, p, objCtrConfigFiles);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException, NullPointerException
    {
        fillField( "«titleTH2»");
        
        fillField( "«name»");
        fillField( "«middleName»");
        fillField( "«lastName»");
        
        fillField( "«paliNameThai»");
        fillField( "«age»");
        fillField( "«vassa»");
        fillField( "«ethnicity»");
        fillField( "«nationality»");
        fillField( "«passportCountry»");
        fillField( "«passportNumber»");
        
        fillField( "«WatResidingAtThai»");
        
        if (ProfileUtil.isOrdainedInThailand(monastic))
        {
            fillField( "«WatResidingAtThaiaddrTambon»");
        }
        else
        {
            fillField( "«WatResidingAtThaiaddrCountry»");
        }
        
        fillField( "«WatResidingAtThaiaddrAmpher»");
        fillField( "«WatResidingAtThaiaddrJangwat»");
    }
    
}
