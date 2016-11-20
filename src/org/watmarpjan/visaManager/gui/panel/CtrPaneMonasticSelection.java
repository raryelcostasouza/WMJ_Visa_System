/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 * .
 *
 * @author WMJ_user
 */
public class CtrPaneMonasticSelection extends AbstractChildPaneController
{

    @FXML
    private ComboBox<String> cbSelectedMonastic;

    @FXML
    private CheckBox cbShowOnlyActive;

    private Integer IDSelectedProfile;

    @Override
    public void init()
    {
        MonasticProfile firstProfile;

        fillNicknameList();
        firstProfile = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileByIndex(0);

        if (firstProfile != null)
        {
            cbSelectedMonastic.setValue(firstProfile.getNickname());
            IDSelectedProfile = firstProfile.getIdProfile();
        } else
        {
            cbSelectedMonastic.setValue(null);
            IDSelectedProfile = -1;
        }
    }

    public void fillNicknameList()
    {
        ArrayList<String> nickNameList;

        nickNameList = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileNicknameList(cbShowOnlyActive.isSelected());

        // if the nickname list is not initialized
        if (cbSelectedMonastic.getItems() != null)
        {
            //clear the list
            cbSelectedMonastic.getItems().clear();
        } else
        {
            //initialize the list
            cbSelectedMonastic.setItems(FXCollections.observableArrayList());
        }
        //fill with the refreshed list
        cbSelectedMonastic.getItems().addAll(nickNameList);
    }

    public void reloadNicknameList(String selectedNickname)
    {
        fillNicknameList();
        cbSelectedMonastic.setValue(selectedNickname);
    }

    public Integer getIDSelectedProfile()
    {
        return IDSelectedProfile;

    }

    public MonasticProfile getSelectedProfile()
    {
        if (IDSelectedProfile != -1)
        {
            return ctrGUIMain.getCtrMain().getCtrProfile().loadProfileByID(IDSelectedProfile);
        }
        return null;

    }
    
    public void setSelectedProfileByNickname(String nickname)
    {
        if (cbSelectedMonastic.getItems().contains(nickname))
        {
            cbSelectedMonastic.setValue(nickname);
        }
    }

    @FXML
    void listenerCbSelectedMonastic(ActionEvent ae)
    {
        String selectedNickname = cbSelectedMonastic.getValue();
        MonasticProfile p;
        if (selectedNickname != null)
        {
            if (ctrGUIMain.getCurrentEditableGUIFormController() != null)
            {
                ctrGUIMain.getPaneEditSaveController().actionLock();
            }

            p = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileByNickName(selectedNickname);
            IDSelectedProfile = p.getIdProfile();
            ctrGUIMain.fillMonasticProfileData();
        }
    }

    @FXML
    void listenerCBShowOnlyActive(ActionEvent ae)
    {
        MonasticProfile selectedProfile;
        MonasticProfile firstProfile;

        selectedProfile = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileByID(IDSelectedProfile);

        /*
         * if the currently selected profile is inactive and the option for
         * showing only active profiles is selected it will disappear from the
         * list so it is necessary to change the selected profile
         */
        if (cbShowOnlyActive.isSelected() && selectedProfile.getStatus().equals("INACTIVE"))
        {
            //one choice is to select to the first profile on the DB
            firstProfile = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileByIndex(0);
            //if there is at least one active profile on the DB
            if (firstProfile != null)
            {
                IDSelectedProfile = firstProfile.getIdProfile();
                fillNicknameList();
                cbSelectedMonastic.setValue(firstProfile.getNickname());
            } else
            {
                IDSelectedProfile = -1;
                cbSelectedMonastic.setValue(null);
            }

        } else
        {
            /*
             * otherwise, just reloads the nickname list keeping the currently
             * selected profile
             */
            fillNicknameList();
            cbSelectedMonastic.setValue(selectedProfile.getNickname());
        }
    }

    public boolean isSelectionEmpty()
    {
        if (cbSelectedMonastic.getValue() == null)
        {
            return true;
        } else
        {
            return false;
        }
    }
}
