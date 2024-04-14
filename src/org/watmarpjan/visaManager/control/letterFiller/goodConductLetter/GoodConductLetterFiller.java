/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.goodConductLetter;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterSNPFiller;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public abstract class GoodConductLetterFiller extends ResidenceGuaranteeLetterSNPFiller
{
    
    public GoodConductLetterFiller(File fTemplate,MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(fTemplate, p, objCtrConfigFiles);
    }
    
    @Override
    public void fillLetter() throws InvalidNavigationException
    {
        super.fillLetter();
        fillField( "«titleTH»");
    }
    
}
