/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.reqLetter;

import org.watmarpjan.visaManager.control.letterFiller.reqLetter.ReqLetterIMMFilter;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class NewVisaReqLetterIMMFiller extends ReqLetterIMMFilter
{
    public NewVisaReqLetterIMMFiller(MonasticProfile p)
    {
        super(AppFiles.getODTNewVisaReqLetterIMM(p.getMonasteryResidingAt()), p);
    }
}
