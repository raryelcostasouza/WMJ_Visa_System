/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class VisaChangeReqLetterIMMFiller extends VisaChangeOrNewReqLetterFiller
{

    public VisaChangeReqLetterIMMFiller(MonasticProfile p)
    {
        super(AppFiles.getODTVisaChangeReqLetterIMM(p.getMonasteryResidingAt()), p);
    }    
}
