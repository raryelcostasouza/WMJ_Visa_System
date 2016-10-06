/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import org.watmarpjan.visaManager.model.hibernate.Profile;

/**
 *
 * @author WMJuser01
 */
public class ProfileUtil 
{
    public static String getTitle(Profile p)
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
            return "นาด";
        }
    }
    
    public static String getFullName(Profile p)
    {
        String strFullName;

        strFullName = p.getName();
        if (p.getMiddleName() != null)
        {
            strFullName += " " + p.getMiddleName();
        }

        strFullName += " " + p.getLastName();

        return strFullName;
    }
    
    public static String getOrdinationType(Profile p)
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
    
    public static String getStrOrdinationDate(Profile p)
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
    
    public static int getAge(Date birthDate)
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
}
