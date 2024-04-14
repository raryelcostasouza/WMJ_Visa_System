/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control;

import java.time.LocalDate;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.MonasteryUtil;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public class Form2AppFieldMapper
{

    public static String getProfileField(MonasticProfile p, String fieldName, CtrConfigFiles objCtrConfigFiles)
    {
        switch (fieldName)
        {
            case "«titleTH»":
                return ProfileUtil.getTitleTH(p);

            case "«titleTH2»":
                return ProfileUtil.getTitleTH2(p);
            case "«name»":
                return p.getMonasticName();
            case "«fullName»":
                return ProfileUtil.getFullName(p);
            case "«middleName»":
                return (p.getMiddleName() == null) ? "" : p.getMiddleName();
            case "«lastName»":
                return p.getLastName();
            case "«paliNameThai»":
                return (p.getBhikkhuOrdDate() == null) ? "" : ProfileUtil.getPaliNameBhikkhuThai(p);
            case "«age»":
                return ProfileUtil.getStrAge(p.getBirthDate());
            case "«ethnicity»":
                return p.getEthnicity();
            case "«nationality»":
                return p.getNationality();
            case "«birthPlace»":
                return p.getBirthPlace();

            case "«birthCountry»":
                return p.getBirthCountry();

            case "«nameFather»":
                return p.getFatherName();

            case "«nameMother»":
                return p.getMotherName();

            case "«birthDateDayThaiDigits»":
                LocalDate ldBd1 = Util.convertDateToLocalDate(p.getBirthDate());
                return Util.convertDayToThaiDigits(ldBd1);
            case "«birthDateMonthThai»":
                LocalDate ldBd2 = Util.convertDateToLocalDate(p.getBirthDate());
                return Util.convertMonthToThaiLang(ldBd2);
            case "«birthDateYearThaiDigits»":
                LocalDate ldBd3 = Util.convertDateToLocalDate(p.getBirthDate());
                return Util.convertYearToThaiDigits(ldBd3);

            case "«passportCountry»":
                return p.getPassportCountry();
            case "«passportNumber»":
                return p.getPassportNumber();

            case "«visaType»":
                return p.getVisaType();
            case "«arrivalLastEntryDateThai»":
                return Util.toStringThaiDateFormatMonthText(p.getArrivalLastEntryDate());

            case "«arrivalLastEntryDateDay»":
                return Util.convertDateToLocalDate(p.getArrivalLastEntryDate()).getDayOfMonth() + "";
            case "«arrivalLastEntryDateMonthThai»":
                LocalDate ld1 = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
                return Util.convertMonthToThaiLang(ld1);
            case "«arrivalLastEntryDateYear»":
                LocalDate ld2 = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
                return Util.convertYearToThai(ld2.getYear()).toString();

            case "«visaExpiryDateThai»":
                LocalDate ldVisaExpiry;
                ldVisaExpiry = ProfileUtil.getVisaOrExtExpiryDate(p);
                return Util.toStringThaiDateFormatMonthText(ldVisaExpiry);

            case "«ordinationTypeThai»":
                return ProfileUtil.getOrdinationType(p);

            case "«ordinationDateDay»":
                return (ProfileUtil.getOrdinationDate(p)== null) ? null : ProfileUtil.getOrdinationDate(p).getDayOfMonth() + "";
            case "«ordinationDateMonthThai»":
                LocalDate ldOd = ProfileUtil.getOrdinationDate(p);
                return Util.convertMonthToThaiLang(ldOd);
            case "«ordinationDateYear»":
                LocalDate ldOd2 = ProfileUtil.getOrdinationDate(p);
                return Util.convertYearToThai(ldOd2.getYear()).toString();

            case "«ordinationDateDayThai»":
                LocalDate ldOd3 = ProfileUtil.getOrdinationDate(p);
                return Util.convertDayToThaiDigits(ldOd3);
            case "«ordinationDateYearThai»":
                LocalDate ldOd4 = ProfileUtil.getOrdinationDate(p);
                return Util.convertYearToThaiDigits(ldOd4);

            case "«preceptorName»":
                return (p.getUpajjhaya() == null) ? null : p.getUpajjhaya().getUpajjhayaName();
            case "«vassa»":
                return ProfileUtil.getVassaCount(p,objCtrConfigFiles.getConfigVassaDates().getDictVassaDates());
                
            //Monastery related fields
            case "«WatResidingAtThai_addrTambon_addrAmpher_addrJangwat»":
                Monastery m1 = p.getMonasteryResidingAt();
                return (m1 == null) ? null : MonasteryUtil.getStringWatAddrFull(m1, false, true);
            case "«WatResidingAtThai»":
                Monastery m2 = p.getMonasteryResidingAt();
                return (m2 == null) ? null : m2.getMonasteryName();
            case "«WatResidingAtThaiaddrTambon»":
                Monastery m3 = p.getMonasteryResidingAt();
                return (m3 == null) ? null : m3.getAddrTambon();
            case "«WatResidingAtThaiaddrAmpher»":
                Monastery m4 = p.getMonasteryResidingAt();
                return (m4 == null) ? null : m4.getAddrAmpher();
            case "«WatResidingAtThaiaddrJangwat»":
                Monastery m5 = p.getMonasteryResidingAt();
                return (m5 == null) ? null : m5.getAddrJangwat();
            case "«WatResidingAtThaiaddrCountry»":
                Monastery m6 = p.getMonasteryResidingAt();
                return (m6 == null) ? null : m6.getAddrCountry();

            case "«WatOrdainedAtThai_addrTambon_addrAmpher_addrJangwat_addrCountry»":
                Monastery m7 = p.getMonasteryOrdainedAt();
                return (m7 == null) ? null : MonasteryUtil.getStringWatAddrFull(m7, true, true);

            case "«WatOrdainedAtThai»":
                Monastery m8 = p.getMonasteryOrdainedAt();
                return (m8 == null) ? null : p.getMonasteryOrdainedAt().getMonasteryName();
            case "«WatOrdainedAtThaiaddrTambon»":
                Monastery m9 = p.getMonasteryOrdainedAt();
                return (m9 == null) ? null : m9.getAddrTambon();
            case "«WatOrdainedAtThaiaddrAmpher»":
                Monastery m10 = p.getMonasteryOrdainedAt();
                return (m10 == null) ? null : m10.getAddrAmpher();
            case "«WatOrdainedAtThaiaddrJangwat»":
                Monastery m11 = p.getMonasteryOrdainedAt();
                return (m11 == null) ? null : m11.getAddrJangwat();
            case "«WatOrdainedAtThaiaddrCountry»":
                Monastery m12 = p.getMonasteryOrdainedAt();
                return (m12 == null) ? null : m12.getAddrCountry();
            default:
                return fieldName + ": FIELD_NOT_MAPPED_TO_APP_DATA";
        }

    }
}
