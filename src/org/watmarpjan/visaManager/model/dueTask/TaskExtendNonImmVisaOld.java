/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author WMJ_user
 */
public class TaskExtendNonImmVisaOld extends TaskExtendNonImmVisaGeneric
{
    //used when the visa has ALREADY been EXTENDED.
    public TaskExtendNonImmVisaOld(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        LocalDate ldPrawat, ldSamnakput;

        ldPrawat = ldDueDate.minusMonths(6);
        ldSamnakput = ldDueDate.minusMonths(3);
        
        setPrawat(ldPrawat);
        setSamnakput(ldSamnakput);
    }
}
