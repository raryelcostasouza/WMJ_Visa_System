/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import org.watmarpjan.visaManager.gui.CtrPaneEditSave;

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
    }

    @Override
    public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue)
    {
        if (!ctrEditSave.getLockStatus() && !unsavedChangesCurrentForm)
        {
            System.out.println("oldValue: " + oldValue + " | newValue: " + newValue);
            unsavedChangesCurrentForm = true;
        }
    }

}
