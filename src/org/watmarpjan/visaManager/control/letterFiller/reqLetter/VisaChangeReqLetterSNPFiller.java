/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.reqLetter;

import org.watmarpjan.visaManager.control.letterFiller.reqLetter.ReqLetterSNPFiller;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class VisaChangeReqLetterSNPFiller extends ReqLetterSNPFiller
{
    public VisaChangeReqLetterSNPFiller(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTVisaChangeReqLetterSNP(p.getMonasteryResidingAt()), p, objCtrConfigFiles);
    }
}
