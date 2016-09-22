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

    public Notice90DayTaskEntry(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        LocalDate ldBeginProcessing;

        ldBeginProcessing = ldDueDate.minusDays(14);
        beginProcessingBy = new SimpleStringProperty(ldBeginProcessing.format(Util.DEFAULT_DATE_FORMAT));
    }

}
