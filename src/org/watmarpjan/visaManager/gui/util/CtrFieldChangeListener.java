/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.watmarpjan.visaManager.gui.panel.CtrGUIMain;
import org.watmarpjan.visaManager.gui.panel.CtrPaneEditSave;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerCBSelectableEntity;

/**
 *
 * @author WMJ_user
 */
public class CtrFieldChangeListener implements ChangeListener<Object>
{

    private final CtrPaneEditSave ctrEditSave;
    private final CtrGUIMain ctrGUIMain;
    private boolean unsavedChangesCurrentForm;

    public CtrFieldChangeListener(CtrPaneEditSave ctrEditSave, CtrGUIMain ctrGUIMain)
    {
        this.ctrGUIMain = ctrGUIMain;
        this.ctrEditSave = ctrEditSave;
        this.unsavedChangesCurrentForm = false;
    }

    public boolean hasUnsavedChanges()
    {
        return unsavedChangesCurrentForm;
    }

    public void setHasUnsavedChanges()
    {
        this.unsavedChangesCurrentForm = true;
        ctrEditSave.enableSaveButton();

        if (ctrGUIMain.getCurrentPaneController() instanceof AChildPaneControllerCBSelectableEntity)
        {
            AChildPaneControllerCBSelectableEntity currentPane = (AChildPaneControllerCBSelectableEntity) ctrGUIMain.getCurrentPaneController();
            currentPane.lockCBSelectionEntity();
        }
    }

    public void resetUnsavedChanges()
    {
        this.unsavedChangesCurrentForm = false;
        ctrEditSave.disableSaveButton();

        if (ctrGUIMain.getCurrentPaneController() instanceof AChildPaneControllerCBSelectableEntity)
        {
            AChildPaneControllerCBSelectableEntity currentPane = (AChildPaneControllerCBSelectableEntity) ctrGUIMain.getCurrentPaneController();
            currentPane.unlockCBSelectionEntity();
        }
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue)
    {
        if (!ctrEditSave.getLockStatus() && !unsavedChangesCurrentForm)
        {
            setHasUnsavedChanges();

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
            }
            else if (field instanceof ComboBox)
            {
                ComboBox<String> cbStr;
                cbStr = (ComboBox<String>) field;
                cbStr.valueProperty().addListener(this);
                cbStr.getEditor().textProperty().addListener(this);
            }
            else if (field instanceof DatePicker)
            {
                ((DatePicker) field).valueProperty().addListener(this);
            }
            else if (field instanceof TextArea)
            {
                ((TextArea) field).textProperty().addListener(this);
            }
            else if (field instanceof ToggleGroup)
            {
                ((ToggleGroup) field).selectedToggleProperty().addListener(this);
            }
            else if (field instanceof CheckBox)
            {
                ((CheckBox) field).selectedProperty().addListener(this);
            }
            else if (field instanceof Spinner)
            {
                ((Spinner<Integer>) field).valueProperty().addListener(this);
            }
        }
    }
}
