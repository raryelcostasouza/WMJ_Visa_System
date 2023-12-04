/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.dueTask;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.model.hibernate.Monastery;

/**
 *
 * @author KroobaHariel
 */
public class TaskExtendTouristVisaExtended extends TaskExtendTouristGeneric
{
    public TaskExtendTouristVisaExtended(String profileNickname, Date dDueDate, Monastery monasteryResidingAt, String passportKeptAt, String visaType)
    {
        super(profileNickname, dDueDate, monasteryResidingAt, passportKeptAt, visaType);
        immigration = new SimpleStringProperty("Already Extended");
    }
}
