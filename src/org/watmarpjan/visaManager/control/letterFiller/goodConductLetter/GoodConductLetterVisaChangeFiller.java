/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.goodConductLetter;

import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class GoodConductLetterVisaChangeFiller extends GoodConductLetterFiller
{
    
    public GoodConductLetterVisaChangeFiller(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTGoodConductGuaranteeLetter(p.getMonasteryResidingAt(),"VisaChange"), p, objCtrConfigFiles);
    }
    
}
