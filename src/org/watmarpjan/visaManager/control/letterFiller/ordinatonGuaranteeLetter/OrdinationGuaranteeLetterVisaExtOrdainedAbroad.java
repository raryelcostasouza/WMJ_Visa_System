/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter;

import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class OrdinationGuaranteeLetterVisaExtOrdainedAbroad extends OrdinationGuaranteeLetterFiller
{
    
    public OrdinationGuaranteeLetterVisaExtOrdainedAbroad(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTOrdinationGuaranteeLetter(p, "VisaExtension"), p,  objCtrConfigFiles);
    }
    
    @Override
    public void fillLetter() throws InvalidNavigationException
    {
        super.fillLetter();
        
        //from VisaChangeOrdainedAbroad
        fillField("«WatOrdainedAtThaiaddrCountry»");
        
        fillField("«visaType»");
    }
    
}
