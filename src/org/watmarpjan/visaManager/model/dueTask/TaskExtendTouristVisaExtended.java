/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author KroobaHariel
 */
public class TaskExtendTouristVisaExtended extends TaskExtendTouristGeneric
{
    public TaskExtendTouristVisaExtended(String profileNickname, Date dDueDate)
    {
        super(profileNickname, dDueDate);
        immigration = new SimpleStringProperty("Already Extended");
    }
}
