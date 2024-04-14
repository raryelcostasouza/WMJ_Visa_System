/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller.ordinatonGuaranteeLetter;

import java.io.File;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrConfigFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class OrdinationGuaranteeLetterNewVisaOrdainedAbroad extends OrdinationGuaranteeLetterFiller
{
    
    public OrdinationGuaranteeLetterNewVisaOrdainedAbroad(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTOrdinationGuaranteeLetter(p, "NewVisaThailand"),p, objCtrConfigFiles);
    }
    
   @Override
    public void fillLetter() throws InvalidNavigationException
    {
        super.fillLetter(); 
        
        fillField("«visaType»");
        fillField("«WatOrdainedAtThaiaddrCountry»");
    }
}
