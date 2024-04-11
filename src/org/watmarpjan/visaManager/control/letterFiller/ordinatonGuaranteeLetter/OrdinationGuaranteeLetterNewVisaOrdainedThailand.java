/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter;

import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class OrdinationGuaranteeLetterNewVisaOrdainedThailand extends OrdinationGuaranteeLetterFiller
{
    
    public OrdinationGuaranteeLetterNewVisaOrdainedThailand(MonasticProfile p)
    {
        super(AppFiles.getODTOrdinationGuaranteeLetter(p, "NewVisaThailand"),p);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException
    {
        super.fillLetter(); 
        
        fillField("«visaType»");
        fillField("«WatOrdainedAtThaiaddrTambon»");
    }
    
    
    
}
