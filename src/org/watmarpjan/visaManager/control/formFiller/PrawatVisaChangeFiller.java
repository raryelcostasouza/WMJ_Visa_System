/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.formFiller;

import java.io.IOException;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author raryel
 */
public class PrawatVisaChangeFiller extends PrawatFiller
{

    public PrawatVisaChangeFiller(CtrMain ctrMain, MonasticProfile p)
    {
        super(ctrMain, p);
    }
    
    @Override
    protected void fillFormData(MonasticProfile p) throws IOException
    {
        super.fillFormData(p);
        
        fillVisaExpiryDateDesired();
    }
    
    private void fillVisaExpiryDateDesired() throws IOException
    {   
        if (this.ldVisaExpiry != null)
        {
            super.fillVisaExpiryDateDesired(ldVisaExpiry.plusDays(90));
        }
    }
    
}
