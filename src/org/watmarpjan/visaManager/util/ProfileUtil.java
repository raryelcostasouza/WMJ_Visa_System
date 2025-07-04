/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ThaiBuddhistDate;
import java.util.Date;
import java.util.HashMap;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.model.ParsedVassaDates;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;

/**
 *
 * @author WMJuser01
 */
public class ProfileUtil
{

    public static String getShortenedBirthWeekDay(Date birthDate)
    {
        LocalDate ldBirth;

        if (birthDate != null)
        {
            ldBirth = Util.convertDateToLocalDate(birthDate);
            return (String) ldBirth.getDayOfWeek().toString().subSequence(0, 3);
        }
        return "";
    }

    public static String getTitleTH(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "พระ";
        }
        else if (p.getSamaneraOrdDate() != null)
        {
            return "สามเณร";
        }
        else
        {
            return "Mr.";
        }
    }

    public static String getTitleTH2(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "พระภิกษุ";
        }
        else
        {
            return "สามเณร";
        }
    }

    public static String getTitleEN(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "Phra";
        }
        else if (p.getSamaneraOrdDate() != null)
        {
            return "Samanera";
        }
        else
        {
            return "Mr.";
        }
    }

    public static String getTitleEN2(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "Bhikkhu";
        }
        else
        {
            return "Samanera";
        }
    }

    private static String getFullNameDefault(MonasticProfile p)
    {
        String strFullName;

        strFullName = p.getMonasticName();
        if (p.getMiddleName() != null)
        {
            strFullName += " " + p.getMiddleName();
        }

        strFullName += " " + p.getLastName();

        return strFullName;
    }

    private static String getFullNameChinese(MonasticProfile p)
    {
        String strFullName;

        strFullName = p.getLastName();
        strFullName += " " + p.getMonasticName();

        if (p.getMiddleName() != null)
        {
            strFullName += " " + p.getMiddleName();
        }

        return strFullName;
    }

    public static String getFullName(MonasticProfile p)
    {
        String strFullName;

        if ((p.getNameOrder() == null) || p.getNameOrder().equals(AppConstants.NAME_ORDER_DEFAULT))
        {
            return ProfileUtil.getFullNameDefault(p);
        }
        //Chinese full name order (Last Name + First Name + Middle Name)
        else
        {
            return ProfileUtil.getFullNameChinese(p);
        }
    }

    public static String getOrdinationType(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "อุปสมบท";
        }
        else if (p.getSamaneraOrdDate() != null)
        {
            return "บรรพชา";
        }
        else
        {
            return "";
        }
    }

    public static String getStrOrdinationDatePrawat(MonasticProfile p)
    {
        LocalDate ldOrdDate;
        if (p.getBhikkhuOrdDate() != null)
        {
            ldOrdDate = Util.convertDateToLocalDate(p.getBhikkhuOrdDate());
        }
        else if (p.getSamaneraOrdDate() != null)
        {
            ldOrdDate = Util.convertDateToLocalDate(p.getSamaneraOrdDate());
        }
        else
        {
            ldOrdDate = null;
        }

        if (ldOrdDate != null)
        {
            return Util.toStringThaiDateFormatMonthText(ldOrdDate);
        }
        else
        {
            return null;
        }
    }

    public static String getStrOrdinationDate(MonasticProfile p)
    {
        LocalDate ldOrdDate;
        ThaiBuddhistDate tbd;
        ldOrdDate = getOrdinationDate(p);

        tbd = ThaiBuddhistDate.from(ldOrdDate);
        return tbd.format(Util.DEFAULT_DATE_FORMAT);
    }

    public static LocalDate getOrdinationDate(MonasticProfile p)
    {
        LocalDate ldOrdDate;

        if (p.getBhikkhuOrdDate() != null)
        {
            ldOrdDate = Util.convertDateToLocalDate(p.getBhikkhuOrdDate());
        }
        else if (p.getSamaneraOrdDate() != null)
        {
            ldOrdDate = Util.convertDateToLocalDate(p.getSamaneraOrdDate());
        }
        else
        {
            ldOrdDate = null;
        }

        return ldOrdDate;
    }

    public static String getStrAge(Date birthDate)
    {
        return getAge(birthDate) + "";
    }

    private static int getAge(Date birthDate)
    {
        LocalDate ldBirth, ldToday;
        Period age;

        if (birthDate != null)
        {
            ldToday = LocalDate.now();
            ldBirth = Util.convertDateToLocalDate(birthDate);
            age = Period.between(ldBirth, ldToday);
            return age.getYears();
        }
        else
        {
            return 0;
        }
    }

    public static String getStrAgeThai(MonasticProfile p)
    {
        int age;
        age = getAge(p.getBirthDate());
        return Util.convertNumberToThaiDigits(age);
    }

    public static LocalDate getVisaOrExtExpiryDate(MonasticProfile p)
    {
        LocalDate ldExtExpiry, ldLatestExt = null;

        //if the visa has been extended
        if (p.getVisaExtensionSet() != null && !p.getVisaExtensionSet().isEmpty())
        {
            //looks for the latest extension expiry date
            for (VisaExtension vext : p.getVisaExtensionSet())
            {
                ldExtExpiry = Util.convertDateToLocalDate(vext.getExpiryDate());
                if (ldLatestExt == null || ldExtExpiry.getYear() > ldLatestExt.getYear())
                {
                    ldLatestExt = ldExtExpiry;
                }
            }
        }
        //otherwise get the original visa expiry date
        else
        {
            ldLatestExt = Util.convertDateToLocalDate(p.getVisaExpiryDate());
        }

        return ldLatestExt;
    }

    public static boolean isOrdainedInThailand(MonasticProfile p)
    {
        return p.getMonasteryOrdainedAt().getAddrCountry().equals(AppConstants.COUNTRY_THAILAND);
    }

    public static boolean isResidingInThailand(MonasticProfile p)
    {
        return p.getMonasteryResidingAt().getAddrCountry().equals(AppConstants.COUNTRY_THAILAND);
    }

    public static boolean hasVisaExemption(MonasticProfile p)
    {
        for (String visaTypeExemption : AppConstants.LIST_VISA_EXEMPTIONS)
        {
            if (p.getVisaType().equals(visaTypeExemption))
            {
                return true;
            }
        }
        return false;

    }

    public static String getPaliNameBhikkhuThai(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "ฉายา " + p.getPaliNameThai();
        }
        return null;
    }

    public static int getVassaCount(MonasticProfile p, HashMap<Integer, ParsedVassaDates> dictVassaDates)
    {
        int currentYear, vassaCount, ordainedYear;
        LocalDate ordDate;

        ordDate = getOrdinationDate(p);
        if (ordDate != null)
        {
            currentYear = LocalDate.now().getYear();
            ordainedYear = ordDate.getYear();

            vassaCount = currentYear - ordainedYear + 1;

            System.out.println("Initial Count: " + vassaCount);
            if (hasOrdainedAfterVassaEnd(ordDate, dictVassaDates))
            {
                System.out.println("Has ordained After Vassa End");
                vassaCount -= 1;
            }

            if (isCurrentDateBeforeVassaEnd(dictVassaDates))
            {
                System.out.println("Current Date Before Vassa End");
                vassaCount -= 1;
            }
            return vassaCount;
        }

        return 0;

    }

    public static int getVassaCountLetter(MonasticProfile p, HashMap<Integer, ParsedVassaDates> dictVassaDates)
    {
        int vassaCount;

        vassaCount = getVassaCount(p, dictVassaDates);
        if (p.getVassaCountAdjust() != null)
        {
            return vassaCount + p.getVassaCountAdjust();
        }
        return vassaCount;
    }

    private static boolean hasOrdainedAfterVassaEnd(LocalDate ordDate, HashMap<Integer, ParsedVassaDates> dictVassaDates)
    {
        LocalDate vassaEndDateOnOrdainedYear;

        System.out.println(ordDate.toString());
        System.out.println(dictVassaDates.get(ordDate.getYear()));
        vassaEndDateOnOrdainedYear = dictVassaDates.get(ordDate.getYear()).getVassaEndDate();

        return (ordDate.compareTo(vassaEndDateOnOrdainedYear) > 0);

    }

    private static boolean isCurrentDateBeforeVassaEnd(HashMap<Integer, ParsedVassaDates> dictVassaDates)
    {
        LocalDate vassaStartCurrentYear, today;

        today = LocalDate.now();
        vassaStartCurrentYear = dictVassaDates.get(today.getYear()).getVassaEndDate();
        return today.compareTo(vassaStartCurrentYear) < 0;
    }

    //returns if for a given year we are before, during or after vassa
    private static String getVassaStatus(LocalDate date2Check, HashMap<Integer, ParsedVassaDates> dictVassaDates)
    {
        ParsedVassaDates vassa;

        vassa = dictVassaDates.get(date2Check.getYear());

        //if date before vassa start on that year
        if (date2Check.compareTo(vassa.getVassaStartDate()) < 0)
        {
            return "Before vassa";
        }
        else if (date2Check.compareTo(vassa.getVassaEndDate()) > 0)
        {
            return "After vassa";
        }
        return "During vassa";
    }

    public static String getVassaStatusOrdainedYear(MonasticProfile p, HashMap<Integer, ParsedVassaDates> dictVassaDates)
    {
        LocalDate ordDate = getOrdinationDate(p);
        if (ordDate != null)
        {
            return getVassaStatus(ordDate, dictVassaDates);
        }
        return "";
    }

    public static String getVassaStatusCurrentYear(HashMap<Integer, ParsedVassaDates> dictVassaDates)
    {
        return getVassaStatus(LocalDate.now(), dictVassaDates);
    }

}
