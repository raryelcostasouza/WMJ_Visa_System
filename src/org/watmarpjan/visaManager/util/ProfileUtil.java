/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
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
            return "นาด";
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

    public static LocalDate getLastExtensionExpiryDate(MonasticProfile p)
    {
        ArrayList<VisaExtension> listExt;
        VisaExtension lastExt;
                
        if ((p.getVisaExtensionSet() != null) && (!p.getVisaExtensionSet().isEmpty()))
        {
            listExt = new ArrayList<>();
            listExt.addAll(p.getVisaExtensionSet());
            lastExt = listExt.get(listExt.size() - 1);
            return Util.convertDateToLocalDate(lastExt.getExpiryDate());
        }
        return null;
    }

    public static LocalDate getVisaExpiryDateDesired(MonasticProfile p)
    {
        LocalDate ldVisaExpiry, ldExtensionExpiry, ldNewExpiryDate;
        Date dVisaExpiry;
        VisaExtension lastExt;

        dVisaExpiry = p.getVisaExpiryDate();
        if (dVisaExpiry != null)
        {
            ldVisaExpiry = Util.convertDateToLocalDate(dVisaExpiry);

            //if there are visa extensions 
            if ((p.getVisaExtensionSet() != null) && (!p.getVisaExtensionSet().isEmpty()))
            {
                ArrayList<VisaExtension> listExt = new ArrayList<>();
                listExt.addAll(p.getVisaExtensionSet());
                lastExt = listExt.get(listExt.size() - 1);
                ldExtensionExpiry = Util.convertDateToLocalDate(lastExt.getExpiryDate());

                //the desired expiry date is one year from the last extension
                ldNewExpiryDate = ldExtensionExpiry.plusYears(1);

            }
            else
            {
                //if there are no extensions
                //the desired expiry date is one year from the visa expiry
                ldNewExpiryDate = ldVisaExpiry.plusYears(1);

            }
            return ldNewExpiryDate;
        }
        return null;

    }
}
