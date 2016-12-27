/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author pm.dell
 */
public class TaskExtendVisaGeneric extends EntryDueTask
{

    private SimpleStringProperty prawat;
    private SimpleStringProperty samnakput;
    private SimpleStringProperty immigration;

    public TaskExtendVisaGeneric(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        LocalDate ldImmigration;

        ldImmigration = ldDueDate.minusMonths(1);
        immigration = new SimpleStringProperty(ldImmigration.format(Util.DEFAULT_DATE_FORMAT));
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

    public String getImmigration()
    {
        return immigration.get();
    }
}
