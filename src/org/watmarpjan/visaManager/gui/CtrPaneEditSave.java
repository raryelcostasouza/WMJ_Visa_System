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
        Path pIconLock, pIconUnlock;

        pIconLock = AppPaths.getPathToIconSubfolder().resolve("lock.png");
        pIconUnlock = AppPaths.getPathToIconSubfolder().resolve("unlock.png");

        ivLock = new ImageView(new File(pIconLock.toString()).toURI().toString());
        ivUnlock = new ImageView(new File(pIconUnlock.toString()).toURI().toString());

        bLockUnlock.setGraphic(ivLock);
        lockStatus = true;
    }

    @FXML
    void actionLockUnlockButton(ActionEvent ae)
    {
        if (lockStatus)
        {
            lockStatus = false;
            bLockUnlock.setGraphic(ivUnlock);
            ctrGUIMain.getCurrentEditableGUIFormController().actionUnlockEdit();
            bSave.setDisable(false);
        } else
        {
            lockStatus = true;
            bLockUnlock.setGraphic(ivLock);
            ctrGUIMain.getCurrentEditableGUIFormController().actionLockEdit();
            bSave.setDisable(true);
        }
    }

    @FXML
    void actionSave(ActionEvent ae)
    {
        ctrGUIMain.getCurrentEditableGUIFormController().actionSave();
    }

    public boolean getLockStatus()
    {
        return lockStatus;
    }

}
