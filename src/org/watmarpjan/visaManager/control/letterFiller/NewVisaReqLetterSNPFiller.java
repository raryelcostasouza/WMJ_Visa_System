/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import java.time.LocalDate;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import static org.watmarpjan.visaManager.control.CtrLetterODF.searchNReplace;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.MonasteryUtil;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public class NewVisaReqLetterSNPFiller extends NewVisaReqLetter
{

    public NewVisaReqLetterSNPFiller(MonasticProfile p)
    {
        super(AppFiles.getODTNewVisaReqLetterSNP(p.getMonasteryResidingAt()), p);
    }
    
    
    
}
