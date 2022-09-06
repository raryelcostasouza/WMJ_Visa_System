/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.formFiller;

import java.io.IOException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class TM86Filler extends TM8XFiller
{

    public TM86Filler(CtrMain ctrMain, MonasticProfile p)
    {
        super(ctrMain, 
                AppFiles.getFormTM86VisaChange(p.getMonasteryResidingAt().
                                                getMonasteryNickname()),
                p);
    }

    @Override
    protected void fillFormData(MonasticProfile p) throws IOException
    {
        super.fillFormData(p);
        
         acroForm.getField("visaType").setValue(p.getVisaType());
    }
    
    
    
}
