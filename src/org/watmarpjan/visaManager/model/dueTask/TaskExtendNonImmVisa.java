/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author pm.dell
 */
public class TaskExtendNonImmVisa extends TaskExtendTouristVisaNotExtended
{

    private SimpleStringProperty prawat;
    private SimpleStringProperty samnakput;

    public TaskExtendNonImmVisa(String profileNickname, Date dDueDate, Monastery monasteryResidingAt, String passportKeptAt)
    {
        super(profileNickname, dDueDate, monasteryResidingAt, passportKeptAt);
        LocalDate ldPrawat, ldSamnakput;
        
        ldPrawat = ldDueDate.minusMonths(3);
        ldSamnakput = ldDueDate.minusDays(45);
        
        setPrawat(ldPrawat);
        setSamnakput(ldSamnakput);
    }

    public String getPrawat()
    {
        return prawat.get();
    }

    protected void setPrawat(LocalDate ldPrawat)
    {
        this.prawat = new SimpleStringProperty(ldPrawat.format(Util.DEFAULT_DATE_FORMAT));
    }

    public String getSamnakput()
    {
        return samnakput.get();
    }

    protected void setSamnakput(LocalDate ldSNP)
    {
        this.samnakput = new SimpleStringProperty(ldSNP.format(Util.DEFAULT_DATE_FORMAT));
    }
}
