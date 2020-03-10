/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.model.hibernate.Monastery;

public class TaskExtendTouristGeneric extends EntryDueTask
{
    protected SimpleStringProperty immigration;

    public TaskExtendTouristGeneric(String profileNickname, Date dDueDate, Monastery monasteryResidingAt, String passportKeptAt)
    {
        super(profileNickname, dDueDate, monasteryResidingAt.getMonasteryNickname(), passportKeptAt);
    }

    public String getImmigration()
    {
        return immigration.get();
    }
}
