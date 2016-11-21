/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

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

    public static String getTitle(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "พระภิกษุ";
        }
        else if (p.getSamaneraOrdDate() != null)
        {
            return "สามเณร";
        }
        else
        {
            return "นาค";
        }
    }

    public static String getFullName(MonasticProfile p)
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

    public static String getStrOrdinationDate(MonasticProfile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return Util.toStringThaiDateFormat(p.getBhikkhuOrdDate());
        }
        else if (p.getSamaneraOrdDate() != null)
        {
            return Util.toStringThaiDateFormat(p.getSamaneraOrdDate());
        }
        else
        {
            return null;
        }
    }

    public static String getStrAge(Date birthDate)
    {
        LocalDate ldBirth, ldToday;
        Period age;

        if (birthDate != null)
        {
            ldToday = LocalDate.now();
            ldBirth = Util.convertDateToLocalDate(birthDate);
            age = Period.between(ldBirth, ldToday);
            return age.getYears() + "";
        }
        else
        {
            return "";
        }

    }
}
