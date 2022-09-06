/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.formFiller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public class PrawatVisaExtFiller extends PrawatFiller
{

    public PrawatVisaExtFiller(CtrMain ctrMain, MonasticProfile p)
    {
        super(ctrMain, p);
    }

    @Override
    protected void fillFormData(MonasticProfile p) throws IOException
    {
        super.fillFormData(p);
        
        if (p.getVisaExtensionSet() != null)
        {
            fillPrawatExtensionsDetails(acroForm, p);
        }
        
        fillVisaExpiryDateDesired();
    }
    
    private void fillPrawatExtensionsDetails(PDAcroForm acroForm, MonasticProfile p) throws IOException
    {
        String countString, periodString;
        LocalDate ldExtensionExpiry;
        int count = 1;

        //sorts the list of extensions according to expiry date
        List<VisaExtension> listExt = p.getVisaExtensionSet().stream().collect(Collectors.toList());
        listExt.sort((VisaExtension o1, VisaExtension o2) -> o1.getExpiryDate().compareTo(o2.getExpiryDate()) //order by expiry date
        );

        //print all the extensions periods in order of expiry date
        for (VisaExtension vext : listExt)
        {   
            if (count < 10)
            {
                countString = "0"+count;
            }
            else if (count == 10)
            {
                countString = ""+count;
            }
            //because of the 10 extensions fields on the pdf just fill the details up to the 10th
            else
            {
                break;
            }

            ldExtensionExpiry = Util.convertDateToLocalDate(vext.getExpiryDate());
            periodString = Util.toStringThaiDateFormatMonthText(ldExtensionExpiry.minusYears(1)) + " - " + Util.toStringThaiDateFormatMonthText(ldExtensionExpiry);
            acroForm.getField("visaExtension"+countString+"Thai").setValue(periodString);
            count++;
        }
    }
    
    private void fillVisaExpiryDateDesired() throws IOException
    {   
        if (this.ldVisaExpiry != null)
        {
            super.fillVisaExpiryDateDesired(ldVisaExpiry.plusYears(1));
        }
    }
}
