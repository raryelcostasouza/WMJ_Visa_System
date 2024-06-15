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
public class OrdinationGuaranteeLetterVisaChangeOrdainedAbroad extends OrdinationGuaranteeLetterFiller
{

    public OrdinationGuaranteeLetterVisaChangeOrdainedAbroad(MonasticProfile p, CtrConfigFiles objCtrConfigFiles)
    {
        super(AppFiles.getODTOrdinationGuaranteeLetter(p, "VisaChange"), p, objCtrConfigFiles);
    }

    @Override
    public void fillLetter() throws InvalidNavigationException
    {
        super.fillLetter();
        fillField("«WatOrdainedAtThaiaddrCountry»");
    }
}
