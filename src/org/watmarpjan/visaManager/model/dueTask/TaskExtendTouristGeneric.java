/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

public class TaskExtendTouristGeneric extends EntryDueTask
{
    protected SimpleStringProperty immigration;

    public TaskExtendTouristGeneric(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
    }

    public String getImmigration()
    {
        return immigration.get();
    }
}
