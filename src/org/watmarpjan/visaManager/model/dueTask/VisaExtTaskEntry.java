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
 * @author WMJ_user
 */
public class VisaExtTaskEntry extends EntryDueTask
{

    private final SimpleStringProperty prawat;
    private final SimpleStringProperty samnakput;
    private final SimpleStringProperty immigration;
    
    public VisaExtTaskEntry(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        LocalDate ldPrawat, ldSamnakput, ldImmigration;

        ldPrawat = ldDueDate.minusMonths(6);
        ldSamnakput = ldDueDate.minusMonths(3);
        ldImmigration = ldDueDate.minusMonths(1);
        
        prawat = new SimpleStringProperty(ldPrawat.format(Util.DEFAULT_DATE_FORMAT));
        samnakput = new SimpleStringProperty(ldSamnakput.format(Util.DEFAULT_DATE_FORMAT));
        immigration = new SimpleStringProperty(ldImmigration.format(Util.DEFAULT_DATE_FORMAT));
    }

    public String getPrawat()
    {
        return prawat.get();
    }

    public String getSamnakput()
    {
        return samnakput.get();
    }

    public String getImmigration()
    {
        return immigration.get();
    }
}
