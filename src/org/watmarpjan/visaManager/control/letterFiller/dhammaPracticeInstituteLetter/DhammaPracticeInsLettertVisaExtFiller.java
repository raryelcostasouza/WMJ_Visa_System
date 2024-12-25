/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.dhammaPracticeInstituteLetter;

import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterSNPFiller;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class DhammaPracticeInsLettertVisaExtFiller extends ResidenceGuaranteeLetterSNPFiller
{
    public DhammaPracticeInsLettertVisaExtFiller(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTDhammaPracticeInstGuaranteeLetterSNP(p.getMonasteryResidingAt(),"VisaExtension"), p, objCtrConfigFiles);
    }
    
    @Override
    public void fillLetter() throws InvalidNavigationException
    {
        super.fillLetter();
        fillField( "«visaType»");
    }
    
}
