/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.time.LocalDate;
import java.util.Date;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class TaskExtendNonImmVisaOld extends TaskExtendNonImmVisaGeneric
{
    //used when the visa has ALREADY been EXTENDED.
    public TaskExtendNonImmVisaOld(String profileNickname, Date dDueDate, Monastery monasteryResidingAt, String passportKeptAt, String visaType)
    {
        super(profileNickname, dDueDate, monasteryResidingAt, passportKeptAt, visaType);
        LocalDate ldPrawat, ldSamnakput;
        String strColumnPrawat, strColumnSNP;

        if (!visaType.contains("Privilege"))
        {
            strColumnPrawat = ldDueDate.minusMonths(6).format(Util.DEFAULT_DATE_FORMAT);
            strColumnSNP = ldDueDate.minusMonths(3).format(Util.DEFAULT_DATE_FORMAT);
            
            setPrawat(strColumnPrawat);
            setSamnakput(strColumnSNP);
        }
    }
}
