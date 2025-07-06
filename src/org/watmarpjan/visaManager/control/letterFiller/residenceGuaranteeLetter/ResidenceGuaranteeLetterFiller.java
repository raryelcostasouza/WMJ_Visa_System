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

/**
 *
 * @author raryel
 */
public abstract class ResidenceGuaranteeLetterFiller extends ODTLetterFiller
{

    public ResidenceGuaranteeLetterFiller(File fLetter, MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(fLetter, p, objCtrConfigFiles);
    }

    
    @Override
    public void fillLetter() throws InvalidNavigationException
    {
        fillField( "«titleTH»");
        fillField( "«fullName»");
        fillField( "«nationality»");
        fillField( "«age»");
        fillField( "«passportNumber»");
        fillField( "«visaType»");
        
        fillField( "«WatResidingAtThai_addrTambon_addrAmpher_addrJangwat»");
    }
    
}
