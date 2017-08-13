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
 * @author KroobaHariel
 */
public class TaskExtendTouristVisaNotExtended extends TaskExtendTouristGeneric
{

    public TaskExtendTouristVisaNotExtended(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        LocalDate ldImmigration;

        ldImmigration = ldDueDate.minusMonths(1);
        immigration = new SimpleStringProperty(ldImmigration.format(Util.DEFAULT_DATE_FORMAT));
    }

}
