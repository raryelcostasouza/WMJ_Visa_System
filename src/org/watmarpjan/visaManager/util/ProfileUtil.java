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
import org.watmarpjan.visaManager.model.hibernate.Profile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;

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
        } else if (p.getSamaneraOrdDate() != null)
        {
            return "สามเณร";
        } else
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
        } else if (p.getSamaneraOrdDate() != null)
        {
            return "บรรพชา";
        } else
        {
            return "";
        }
    }

    public static String getStrOrdinationDate(Profile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return Util.toStringThaiDateFormat(p.getBhikkhuOrdDate());
        } else if (p.getSamaneraOrdDate() != null)
        {
            return Util.toStringThaiDateFormat(p.getSamaneraOrdDate());
        } else
        {
            return null;
        }
    }

    public static int getAge(Profile p)
    {
        LocalDate ldBirth, ldToday;
        Period age;

        if (p.getBirthDate() != null)
        {
            ldToday = LocalDate.now();
            ldBirth = Util.convertDateToLocalDate(p.getBirthDate());
            age = Period.between(ldBirth, ldToday);
            return age.getYears();
        } else
        {
            return 0;
        }

    }

    public static LocalDate getVisaExpiryDateDesired(Profile p)
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

            } else
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
