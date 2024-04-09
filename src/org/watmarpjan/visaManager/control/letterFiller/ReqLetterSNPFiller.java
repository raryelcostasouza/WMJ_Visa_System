/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import java.io.File;
import java.time.LocalDate;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public abstract class ReqLetterSNPFiller extends VisaChangeOrNewReqLetterFiller
{

    public ReqLetterSNPFiller(File fLetter, MonasticProfile p)
    {
        super(fLetter, p);
    }
    
    @Override
    public void fillLetter(MonasticProfile p) throws InvalidNavigationException,NullPointerException
    {
        super.fillLetter(p);
        LocalDate ldArrivalLastEntry;
        Monastery mResidence;
        
        searchNReplace(objTD, "«name»", p.getMonasticName());
        searchNReplace(objTD, "«middleName»", p.getMiddleName());
        searchNReplace(objTD, "«lastName»", p.getLastName());
        searchNReplace(objTD, "«ethnicity»", p.getEthnicity());
        searchNReplace(objTD, "«passportCountry»", p.getPassportCountry());
        
        ldArrivalLastEntry = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
        searchNReplace(objTD, "«arrivalLastEntryDateDay»", ldArrivalLastEntry.getDayOfMonth() +"");
        searchNReplace(objTD, "«arrivalLastEntryDateMonthThai»", Util.convertMonthToThaiLang(ldArrivalLastEntry));
        searchNReplace(objTD, "«arrivalLastEntryDateYear»", Util.convertYearToThai(ldArrivalLastEntry.getYear()).toString());
        
        mResidence = p.getMonasteryResidingAt();
        searchNReplace(objTD, "«WatResidingAtThai»", mResidence.getMonasteryName());
        searchNReplace(objTD, "«WatResidingAtThaiaddrTambon»", mResidence.getAddrTambon());
        searchNReplace(objTD, "«WatResidingAtThaiaddrAmpher»", mResidence.getAddrAmpher());
        searchNReplace(objTD, "«WatResidingAtThaiaddrJangwat»", mResidence.getAddrJangwat());
             
    }
    
}
