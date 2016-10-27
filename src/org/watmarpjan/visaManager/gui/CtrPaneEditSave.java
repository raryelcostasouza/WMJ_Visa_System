/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppPaths;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneEditSave extends AbstractChildPaneController
{

    private boolean lockStatus;
    private ImageView ivLock, ivUnlock;

    @FXML
    private Button bLockUnlock;

    @FXML
    private Button bSave;

    @FXML
    private Button bAddNew;

    @Override
    public void init()
    {
        Path pIconLock, pIconUnlock, pIconSave, pIconAdd;
        ImageView ivSaveButton, ivAddButton;

        pIconLock = AppPaths.getPathToIconSubfolder().resolve("lock.png");
        pIconUnlock = AppPaths.getPathToIconSubfolder().resolve("unlock.png");
        pIconSave = AppPaths.getPathToIconSubfolder().resolve("save.png");
        pIconAdd = AppPaths.getPathToIconSubfolder().resolve("add.png");

        ivLock = new ImageView(pIconLock.toFile().toURI().toString());
        ivUnlock = new ImageView(pIconUnlock.toFile().toURI().toString());
        ivSaveButton = new ImageView(pIconSave.toFile().toURI().toString());
        ivAddButton = new ImageView(pIconAdd.toFile().toURI().toString());

        bLockUnlock.setGraphic(ivLock);
        bSave.setGraphic(ivSaveButton);
        bAddNew.setGraphic(ivAddButton);

        lockStatus = true;
    }

    @FXML
    void actionLockUnlockButton(ActionEvent ae)
    {
        if (lockStatus && !ctrGUIMain.getCurrentEditableGUIFormController().isSelectionEmpty())
        {
            actionUnlock();
        } else if (lockStatus && ctrGUIMain.getCurrentEditableGUIFormController().isSelectionEmpty())
        {
            actionUnlockAddNewButton();
        } else
        {
            actionLock();
        }
    }

    public void actionLock()
    {
        lockStatus = true;
        bLockUnlock.setGraphic(ivLock);
        bAddNew.setDisable(true);
        ctrGUIMain.getCurrentEditableGUIFormController().actionLockEdit();
    }

    private void actionUnlock()
    {
        lockStatus = false;
        bLockUnlock.setGraphic(ivUnlock);
        bAddNew.setDisable(false);
        ctrGUIMain.getCurrentEditableGUIFormController().actionUnlockEdit();
    }

    private void actionUnlockAddNewButton()
    {
        lockStatus = false;
        bLockUnlock.setGraphic(ivUnlock);
        bAddNew.setDisable(false);
    }

    @FXML
    void actionSave(ActionEvent ae)
    {
        ctrGUIMain.getCurrentEditableGUIFormController().actionSave();
    }
    
    @FXML
    void actionAddNew(ActionEvent ae)
    {
        IEditableGUIForm ctrEditForm;
        
        ctrEditForm = ctrGUIMain.getCurrentEditableGUIFormController();
        if (ctrEditForm instanceof ICreateEditGUIForm)
        {
            ((ICreateEditGUIForm) ctrEditForm).actionAddNew();
        }
    }

    public void enableSaveButton()
    {
        bSave.setDisable(false);
    }

    public void disableSaveButton()
    {
        bSave.setDisable(true);
    }

    public boolean getLockStatus()
    {
        return lockStatus;
    }

    public void setVisible_ButtonAddNew(boolean newStatus)
    {
        bAddNew.setVisible(newStatus);
    }

}
