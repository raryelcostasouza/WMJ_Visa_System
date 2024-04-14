/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.dhammaPracticeInstituteLetter;

import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter.ResidenceGuaranteeLetterSNPFiller;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class DhammaPracticeInsLettertVisaChangeFiller extends ResidenceGuaranteeLetterSNPFiller
{
    public DhammaPracticeInsLettertVisaChangeFiller(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTDhammaPracticeInstGuaranteeLetterVisaChange(p.getMonasteryResidingAt()), p, objCtrConfigFiles);
    }
    
}
