/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

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

    public TaskExtendNonImmVisa(String profileNickname, Date dDueDate, Monastery monasteryResidingAt, String passportKeptAt, String visaType)
    {
        super(profileNickname, dDueDate, monasteryResidingAt, passportKeptAt, visaType);
     
        if (visaType.equals("Privilege Entry"))
        {
            setPrawat("N/A");
            setSamnakput("Thai Elite");
        }
        else
        {
            setPrawat(ldDueDate.minusMonths(3).format(Util.DEFAULT_DATE_FORMAT));
            setSamnakput(ldDueDate.minusDays(45).format(Util.DEFAULT_DATE_FORMAT));
        }
    }

    public String getPrawat()
    {
        return prawat.get();
    }

    protected void setPrawat(String strColumnPrawat)
    {
       this.prawat = new SimpleStringProperty(strColumnPrawat);
    }

    public String getSamnakput()
    {
        return samnakput.get();
    }

    protected void setSamnakput(String strColumnSNP)
    {
        this.samnakput = new SimpleStringProperty(strColumnSNP);
    }
}
