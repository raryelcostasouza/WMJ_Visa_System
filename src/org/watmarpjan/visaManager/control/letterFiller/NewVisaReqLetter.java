/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import java.io.File;
import java.time.LocalDate;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.MonasteryUtil;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public class NewVisaReqLetter extends VisaChangeOrNewReqLetterFiller
{
    public NewVisaReqLetter(File fLetter, MonasticProfile p)
    {
        super(fLetter, p);
    }
    
    @Override
    public void fillLetter(MonasticProfile p) throws InvalidNavigationException
    {
        super.fillLetter(p);
        String strMOrdainedAt;
        LocalDate ldOrdDate;
        
        ldOrdDate = ProfileUtil.getOrdinationDate(p);
        searchNReplace(objTD, "«ordinationDateDayThai»", ldOrdDate.getDayOfMonth() + "");
        searchNReplace(objTD, "«ordinationDateMonthThai»", Util.convertMonthToThaiLang(ldOrdDate));
        searchNReplace(objTD, "«ordinationDateYearThai»", Util.convertYearToThai(ldOrdDate.getYear()) + "");
        
        strMOrdainedAt = MonasteryUtil.getStringWatAddrFull(p.getMonasteryOrdainedAt(), true, true);
        searchNReplace(objTD, "«WatOrdainedAtThai_addrTambon_addrAmpher_addrJangwat_addrCountry»", strMOrdainedAt);

    }
}
