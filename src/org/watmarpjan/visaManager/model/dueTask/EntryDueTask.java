/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public abstract class EntryDueTask implements Comparable<EntryDueTask>
{

    private final SimpleStringProperty profileNickname;
    private final SimpleStringProperty strDueDate;
    private final SimpleStringProperty weekDayDueDate;
    private final SimpleStringProperty remainingTime;
    
    protected LocalDate ldDueDate;

    public EntryDueTask(String profileNickname, Date dueDate)
    {
        Period pRemainingTime;

        this.profileNickname = new SimpleStringProperty(profileNickname);
        ldDueDate = org.watmarpjan.visaManager.util.Util.convertDateToLocalDate(dueDate);
        this.strDueDate = new SimpleStringProperty(ldDueDate.format(Util.DEFAULT_DATE_FORMAT));
        this.weekDayDueDate = new SimpleStringProperty((String) ldDueDate.getDayOfWeek().toString().subSequence(0, 3));

        pRemainingTime = Period.between(LocalDate.now(), ldDueDate);
        this.remainingTime = new SimpleStringProperty(remainingTimeToString(pRemainingTime));

    }

    private String remainingTimeToString(Period remainingTime)
    {
        String str = "";

        if (remainingTime.getYears() != 0)
        {
            str += remainingTime.getYears() + " year(s) ";
        } else if (remainingTime.getMonths() != 0)
        {
            str += remainingTime.getMonths() + " month(s) ";
        }

        if (remainingTime.getDays() != 0)
        {
            str += remainingTime.getDays() + " day(s)";
        }
        return str;
    }

    public String getProfileNickname()
    {
        return profileNickname.get();
    }
    
    protected void setProfileNickname(String pNewNickname)
    {
        profileNickname.set(pNewNickname);
    }

    public String getDueDate()
    {
        return strDueDate.get();
    }

    public String getWeekDayDueDate()
    {
        return weekDayDueDate.get();
    }

    public String getRemainingTime()
    {
        return remainingTime.get();
    }

    public LocalDate getLdDueDate()
    {
        return ldDueDate;
    }
    
    @Override
    public int compareTo(EntryDueTask objEntryDueTask)
    {
        if (objEntryDueTask == null)
        {
            return 1;
        }
        else if (this.getDueDate().compareTo(objEntryDueTask.getDueDate()) > 0)
        {
            return 1;
        }
        else if (this.getDueDate().compareTo(objEntryDueTask.getDueDate()) < 0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }

}
