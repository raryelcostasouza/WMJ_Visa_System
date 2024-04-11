/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.residenceGuaranteeLetter;

import java.io.File;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class ResidenceGuaranteeLetterSNPVisaChangeFiller extends ResidenceGuaranteeLetterSNPFiller
{
    
    public ResidenceGuaranteeLetterSNPVisaChangeFiller(MonasticProfile p)
    {
        super(AppFiles.getODTResidenceGuaranteeLetterSNPVisaChange(p.getMonasteryResidingAt()), p);
    }
    
}
