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
public class TaskExtendNonImmVisaGeneric extends TaskExtendTouristVisaNotExtended
{

    private SimpleStringProperty prawat;
    private SimpleStringProperty samnakput;

    public TaskExtendNonImmVisaGeneric(String profileNickname, Date dDueDate, Monastery monasteryResidingAt, String passportKeptAt, String visaType)
    {
        super(profileNickname, dDueDate, monasteryResidingAt, passportKeptAt, visaType);
        
        if (visaType.contains("Privilege"))
        {
            setPrawat("N/A");
            setSamnakput("Thai Elite");
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
