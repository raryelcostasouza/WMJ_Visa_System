/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import java.io.File;
import java.time.LocalDate;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import static org.watmarpjan.visaManager.control.CtrLetterODF.searchNReplace;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.MonasteryUtil;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public abstract class VisaChangeOrNewReqLetterFiller extends ODTLetterFiller
{

    public VisaChangeOrNewReqLetterFiller(File fLetter, MonasticProfile p)
    {
        super(fLetter, p);
    }

    
    @Override
    public void fillLetter(MonasticProfile p) throws InvalidNavigationException
    {
        String strLastEntry, monasteryAddr;
        LocalDate ldVisaExpiry;
        
        
        searchNReplace(objTD, "«titleTH2»", ProfileUtil.getTitleTH2(p));
        searchNReplace(objTD, "«fullName»", ProfileUtil.getFullName(p));
        searchNReplace(objTD, "«nationality»", p.getNationality());
        searchNReplace(objTD, "«passportNumber»", p.getPassportNumber());
        searchNReplace(objTD, "«visaType»", p.getVisaType());

        searchNReplace(objTD, "«titleTH»", ProfileUtil.getTitleTH(p));
        searchNReplace(objTD, "«ordinationTypeThai»", ProfileUtil.getOrdinationType(p));

        strLastEntry = Util.toStringThaiDateFormat(p.getArrivalLastEntryDate());
        searchNReplace(objTD, "«arrivalLastEntryDateThai»", strLastEntry);

        monasteryAddr = MonasteryUtil.getStringWatAddrFull(p.getMonasteryResidingAt(), false, true);
        searchNReplace(objTD, "«WatResidingAtThai_addrTambon_addrAmpher_addrJangwat»", ProfileUtil.getOrdinationType(p));

        ldVisaExpiry = Util.convertDateToLocalDate(p.getVisaExpiryDate());
        searchNReplace(objTD, "«visaExpiryDateThai»", Util.toStringThaiDateFormat(ldVisaExpiry));
    }
    
}
