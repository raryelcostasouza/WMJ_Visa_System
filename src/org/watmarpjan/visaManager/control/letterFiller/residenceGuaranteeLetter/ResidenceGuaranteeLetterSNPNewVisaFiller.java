/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class ResidenceGuaranteeLetterSNPNewVisaFiller extends ResidenceGuaranteeLetterSNPFiller
{

    public ResidenceGuaranteeLetterSNPNewVisaFiller(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTResidenceGuaranteeLetterSNPNewVisa(p.getMonasteryResidingAt()), p, objCtrConfigFiles);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException, NullPointerException
    {
        super.fillLetter();
        
        fillField("«visaType»");
        
    }

}
