/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class TaskNotice90D extends EntryDueTask
{

    private final SimpleBooleanProperty onlineNoticeAccepted;
    private final SimpleStringProperty firstDay;
    private final SimpleStringProperty lastDayOnline;
    private final SimpleStringProperty lastDayOffice;

    public TaskNotice90D(String profileNickname, Date dDueDate, Boolean isOnlineNoticeAccepted, String nicknameMonasteryResidingAt, String passportKeptAt)
    {
        super(profileNickname, dDueDate, nicknameMonasteryResidingAt, passportKeptAt);
        LocalDate ldFirstDay, ldLastDayOnline, ldLastDayOffice;

        //adds the * mark indicating that the 90 day online is accepted
        if (isOnlineNoticeAccepted == null || !isOnlineNoticeAccepted)
        {
            onlineNoticeAccepted = new SimpleBooleanProperty(false);
        }
        else
        {
            onlineNoticeAccepted = new SimpleBooleanProperty(true);
        }

        ldFirstDay = ldDueDate.minusDays(14);
        ldLastDayOnline = ldDueDate.minusDays(7);
        ldLastDayOffice = ldDueDate.plusDays(7);
        firstDay = new SimpleStringProperty(ldFirstDay.format(Util.DEFAULT_DATE_FORMAT));
        lastDayOnline = new SimpleStringProperty(ldLastDayOnline.format(Util.DEFAULT_DATE_FORMAT));
        lastDayOffice = new SimpleStringProperty(ldLastDayOffice.format(Util.DEFAULT_DATE_FORMAT));
    }

    public String getFirstDay()
    {
        return firstDay.get();
    }

    public String getLastDayOnline()
    {
        return lastDayOnline.get();
    }

    public String getLastDayOffice()
    {
        return lastDayOffice.get();
    }

    public Boolean getOnlineNoticeAccepted()
    {
        return onlineNoticeAccepted.get();
    }
    
    public BooleanProperty onlineNoticeAcceptedProperty()
    {
        return onlineNoticeAccepted;
    }

}
