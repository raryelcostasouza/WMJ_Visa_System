/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.watmarpjan.visaManager.gui.panel.CtrPaneEditSave;

/**
 *
 * @author WMJ_user
 */
public class CtrFieldChangeListener implements ChangeListener<Object>
{

    private final CtrPaneEditSave ctrEditSave;
    private boolean unsavedChangesCurrentForm;

    public CtrFieldChangeListener(CtrPaneEditSave ctrEditSave)
    {
        this.ctrEditSave = ctrEditSave;
        this.unsavedChangesCurrentForm = false;
    }

    public boolean hasUnsavedChanges()
    {
        return unsavedChangesCurrentForm;
    }

    public void resetUnsavedChanges()
    {
        this.unsavedChangesCurrentForm = false;
        ctrEditSave.disableSaveButton();
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue)
    {
        if (!ctrEditSave.getLockStatus() && !unsavedChangesCurrentForm)
        {
            unsavedChangesCurrentForm = true;
            ctrEditSave.enableSaveButton();
        }
    }

    public void registerChangeListener(ArrayList alFields)
    {
        for (Object field : alFields)
        {
            if (field instanceof TextField)
            {
                ((TextField) field).textProperty().addListener(this);
            } else if (field instanceof ComboBox)
            {
                ComboBox<String> cbStr;
                cbStr = (ComboBox<String>) field;
                cbStr.valueProperty().addListener(this);
                cbStr.getEditor().textProperty().addListener(this);
            } else if (field instanceof DatePicker)
            {
                ((DatePicker) field).valueProperty().addListener(this);
            } else if (field instanceof TextArea)
            {
                ((TextArea) field).textProperty().addListener(this);
            } else if (field instanceof ToggleGroup)
            {
                ((ToggleGroup) field).selectedToggleProperty().addListener(this);
            }
        }
    }

}
