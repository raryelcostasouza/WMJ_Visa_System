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
 * @author pm.dell
 */
public class TaskExtendVisaNew extends TaskExtendVisaGeneric
{
    //used when the visa has NOT been EXTENDED YET.
    public TaskExtendVisaNew(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        LocalDate ldPrawat, ldSamnakput;

        ldPrawat = ldDueDate.minusMonths(3);
        ldSamnakput = ldDueDate.minusMonths(2);
        
        setPrawat(ldPrawat);
        setSamnakput(ldSamnakput);
    }
}
