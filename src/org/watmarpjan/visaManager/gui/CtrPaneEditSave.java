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

    @Override
    public void init()
    {
        Path pIconLock, pIconUnlock, pIconSave;
        ImageView ivSaveButton;

        pIconLock = AppPaths.getPathToIconSubfolder().resolve("lock.png");
        pIconUnlock = AppPaths.getPathToIconSubfolder().resolve("unlock.png");
        pIconSave = AppPaths.getPathToIconSubfolder().resolve("save.png");

        ivLock = new ImageView(pIconLock.toFile().toURI().toString());
        ivUnlock = new ImageView(pIconUnlock.toFile().toURI().toString());
        ivSaveButton = new ImageView(pIconSave.toFile().toURI().toString());

        bLockUnlock.setGraphic(ivLock);
        bSave.setGraphic(ivSaveButton);
        lockStatus = true;
    }

    @FXML
    void actionLockUnlockButton(ActionEvent ae)
    {
        if (lockStatus && !ctrGUIMain.getCurrentEditableGUIFormController().isSelectionEmpty())
        {
            actionUnlock();
        } else
        {
            actionLock();
        }
    }

    public void actionLock()
    {
        lockStatus = true;
        bLockUnlock.setGraphic(ivLock);
        ctrGUIMain.getCurrentEditableGUIFormController().actionLockEdit();
    }

    private void actionUnlock()
    {
        lockStatus = false;
        bLockUnlock.setGraphic(ivUnlock);
        ctrGUIMain.getCurrentEditableGUIFormController().actionUnlockEdit();
    }

    @FXML
    void actionSave(ActionEvent ae)
    {
        ctrGUIMain.getCurrentEditableGUIFormController().actionSave();
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

}
