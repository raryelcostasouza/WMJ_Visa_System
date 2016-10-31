/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class Notice90DayTaskEntry extends EntryDueTask
{
    private SimpleStringProperty firstDay;
    private final SimpleStringProperty lastDayOnline;
    
    public Notice90DayTaskEntry(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        LocalDate ldFirstDay, ldLastDayOnline;

        ldFirstDay = ldDueDate.minusDays(14);
        ldLastDayOnline =  ldDueDate.minusDays(7);
        firstDay = new SimpleStringProperty(ldFirstDay.format(Util.DEFAULT_DATE_FORMAT));
        lastDayOnline = new SimpleStringProperty(ldLastDayOnline.format(Util.DEFAULT_DATE_FORMAT));
    }
    
    public String getFirstDay()
    {
        return firstDay.get();
    }
    
    public String getLastDayOnline()
    {
        return lastDayOnline.get();
    }

}
