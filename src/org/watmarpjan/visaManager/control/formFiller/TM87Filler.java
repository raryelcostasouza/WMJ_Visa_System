/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.formFiller;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class TM87Filler extends TM8XFiller
{

    public TM87Filler(CtrMain ctrMain, MonasticProfile p)
    {
        super(ctrMain, 
                AppFiles.getFormTM87NewVisa(p.getMonasteryResidingAt().
                                                getMonasteryNickname()),
                p);
    }

    @Override
    protected void fillFormData(MonasticProfile p) throws IOException
    {
        super.fillFormData(p); 
        ArrayList<PDTextField> alThaiFields;
        
        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("visaTypeThai"));
        
        adjustFontThaiField(alThaiFields);
        acroForm.getField("visaTypeThai").setValue(p.getVisaType());
    }
    
    
    
}
