/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import java.io.File;
import java.time.LocalDate;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public abstract class ReqLetterIMMFilter extends VisaChangeOrNewReqLetterFiller
{
    
    public ReqLetterIMMFilter(File fLetter, MonasticProfile p)
    {
        super(fLetter, p);
    }

    @Override
    public void fillLetter(MonasticProfile p) throws InvalidNavigationException,NullPointerException
    {
        super.fillLetter(p);
        String strLastEntry;
        LocalDate ldVisaExpiry;
                
        searchNReplace(objTD, "«fullName»", ProfileUtil.getFullName(p));
        searchNReplace(objTD, "«visaType»", p.getVisaType());

        searchNReplace(objTD, "«titleTH»", ProfileUtil.getTitleTH(p));
        
        strLastEntry = Util.toStringThaiDateFormatMonthText(p.getArrivalLastEntryDate());
        searchNReplace(objTD, "«arrivalLastEntryDateThai»", strLastEntry);
        
        ldVisaExpiry =  ProfileUtil.getVisaOrExtExpiryDate(p);
        searchNReplace(objTD, "«visaExpiryDateThai»", Util.toStringThaiDateFormatMonthText(ldVisaExpiry));

    }
    
   
    
}
