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
public class PassportRenewTaskEntry extends EntryDueTask
{
    private SimpleStringProperty beginProcessingBy;
    
    public PassportRenewTaskEntry(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);

        LocalDate ldBeginProcessing;

        ldBeginProcessing = ldDueDate.minusDays(30);
        beginProcessingBy = new SimpleStringProperty(ldBeginProcessing.format(Util.DEFAULT_DATE_FORMAT));
    }
    
    public String getBeginProcessingBy()
    {
        return beginProcessingBy.get();
    }

}
