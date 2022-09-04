/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.letterFiller;

import java.time.LocalDate;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public class OrdinationGuaranteeLetterFiller extends ODTLetterFiller
{

    public OrdinationGuaranteeLetterFiller(MonasticProfile p)
    {
        super(AppFiles.getODTOrdinationGuaranteeLetter(p.getMonasteryResidingAt()), p);
    }
    
    @Override
    public void fillLetter(MonasticProfile p) throws InvalidNavigationException
    {
        LocalDate ldOrdDate, ldBirthDate;

        //need to use thai numbers
        searchNReplace(objTD, "«titleTH2»", ProfileUtil.getTitleTH2(p));
        searchNReplace(objTD, "«name»", p.getMonasticName());
        searchNReplace(objTD, "«middleName»", p.getMiddleName());
        searchNReplace(objTD, "«paliNameThai»", p.getPaliNameThai());
        searchNReplace(objTD, "«lastName»", p.getLastName());

        ldBirthDate = Util.convertDateToLocalDate(p.getBirthDate());
        searchNReplace(objTD, "«birthDateDayThaiDigits»", Util.convertDayToThaiDigits(ldBirthDate));
        searchNReplace(objTD, "«birthDateMonthThai»",Util.convertMonthToThaiLang(ldBirthDate));
        searchNReplace(objTD, "«birthDateYearThaiDigits»",Util.convertYearToThaiDigits(ldBirthDate));

        searchNReplace(objTD, "«birthPlace»", p.getBirthPlace());
        searchNReplace(objTD, "«birthCountry»", p.getBirthCountry());

        searchNReplace(objTD, "«nameFather»", p.getFatherName());
        searchNReplace(objTD, "«nameMother»", p.getMotherName());

        searchNReplace(objTD, "«ordinationTypeThai»", ProfileUtil.getOrdinationType(p));
        ldOrdDate = ProfileUtil.getOrdinationDate(p);
        searchNReplace(objTD, "«ordinationDateDayThaiDigits»", Util.convertDayToThaiDigits(ldOrdDate));
        searchNReplace(objTD, "«ordinationDateMonthThai»", Util.convertMonthToThaiLang(ldOrdDate));
        searchNReplace(objTD, "«ordinationDateYearThaiDigits»", Util.convertYearToThaiDigits(ldOrdDate));

        searchNReplace(objTD, "«WatOrdainedAtThai»", p.getMonasteryOrdainedAt().getMonasteryName());
        searchNReplace(objTD, "«WatResidingAtThai»", p.getMonasteryResidingAt().getMonasteryName());
        searchNReplace(objTD, "«WatResidingAtThaiaddrTambon»", p.getMonasteryResidingAt().getAddrTambon());
        searchNReplace(objTD, "«WatResidingAtThaiaddrAmpher»", p.getMonasteryResidingAt().getAddrAmpher());
        searchNReplace(objTD, "«WatResidingAtThaiaddrJangwat»", p.getMonasteryResidingAt().getAddrJangwat());

    }
    
}
