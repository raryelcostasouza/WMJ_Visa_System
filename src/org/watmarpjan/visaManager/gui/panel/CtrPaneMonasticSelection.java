/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneController;
import java.io.File;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 * .
 *
 * @author WMJ_user
 */
public class CtrPaneMonasticSelection extends AChildPaneController
{

    @FXML
    private ComboBox<String> cbSelectedMonastic;

    @FXML
    private CheckBox cbShowOnlyActive;

    @FXML
    private ImageView ivProfile;

    private Integer IDSelectedProfile;

    private String previousSelection;

    private String newSelection;

    @Override
    public void init()
    {
        MonasticProfile firstProfile;

        previousSelection = null;
        newSelection = null;
        fillNicknameList();
        firstProfile = ctrGUIMain.getCtrMain().getCtrProfile().loadFirstProfile(cbShowOnlyActive.isSelected());

        if (firstProfile != null)
        {
            cbSelectedMonastic.setValue(firstProfile.getNickname());
            IDSelectedProfile = firstProfile.getIdProfile();
            loadIMGProfile(firstProfile);
        }
        else
        {
            cbSelectedMonastic.setValue(null);
            IDSelectedProfile = -1;
        }
    }

    public void fillNicknameList()
    {
        ArrayList<String> nickNameList;

        nickNameList = ctrGUIMain.getCtrMain().getCtrProfile().loadNicknameList(cbShowOnlyActive.isSelected());

        // if the nickname list is not initialized
        if (cbSelectedMonastic.getItems() != null)
        {
            //clear the list
            cbSelectedMonastic.getItems().clear();
        }
        else
        {
            //initialize the list
            cbSelectedMonastic.setItems(FXCollections.observableArrayList());
        }
        //fill with the refreshed list
        cbSelectedMonastic.getItems().addAll(nickNameList);
    }

    public void reloadNicknameList(String selectedNickname)
    {
        System.out.println("list reaload");
        fillNicknameList();

        setSelectedProfileByNickname(selectedNickname);
        //cbSelectedMonastic.setValue(selectedNickname);

        //loadIMGProfile(selectedNickname);
    }

    public void removeProfileFromList(String nickname)
    {
        int indexRemoved;
        String item2SelectAfterRemove;

        indexRemoved = cbSelectedMonastic.getItems().indexOf(nickname);
        
        //if removes the first item selects automatically the next one to avoid null field
        if (indexRemoved == 0)
        {
            item2SelectAfterRemove = cbSelectedMonastic.getItems().get(1);
            cbSelectedMonastic.setValue(item2SelectAfterRemove);
        }
        cbSelectedMonastic.getItems().remove(nickname);
    }

    public Integer getIDSelectedProfile()
    {
        return IDSelectedProfile;

    }

    public MonasticProfile getSelectedProfile()
    {
        if (IDSelectedProfile != -1)
        {
            return ctrGUIMain.getCtrMain().getCtrProfile().loadByID(IDSelectedProfile);
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

    public void reloadCurrentProfile()
    {
        MonasticProfile p;

        p = getSelectedProfile();
        cbSelectedMonastic.setValue(null);
        cbSelectedMonastic.setValue(p.getNickname());
    }

    @FXML
    void listenerCbSelectedMonastic(ActionEvent ae)
    {
        MonasticProfile p;

        //uses newSelection as a flag to avoid recursion
        
        //if there is no selected value change operation undergoing
        //and the current value is not null
        if (newSelection == null && cbSelectedMonastic.getValue() != null)
        {
            //flag selected value change operation setting newSelection
            newSelection = cbSelectedMonastic.getValue();

            //if there was no previous selection or 
            //the new selection is different than the previous one
            if (previousSelection == null || previousSelection != newSelection)
            {
                //go ahead and switch profiles and lock
                if (ctrGUIMain.getCurrentEditableGUIFormController() != null)
                {
                    ctrGUIMain.getPaneEditSaveController().actionLock();
                }

                //load profile info
                p = ctrGUIMain.getCtrMain().getCtrProfile().loadByNickName(newSelection);
                loadIMGProfile(p);
                IDSelectedProfile = p.getIdProfile();
                ctrGUIMain.fillMonasticProfileData();
                
                //adjust flag variables
                previousSelection = newSelection;
            }
            
            //after operation finish reset the newSelection flag
            //to enable further selection change operations
            newSelection = null;
                
        }
    }

    public void lockCBMonasticSelection()
    {
        cbSelectedMonastic.setDisable(true);
    }

    public void unlockCBMonasticSelection()
    {
        cbSelectedMonastic.setDisable(false);
    }

    private void loadIMGProfile(MonasticProfile p)
    {
        File fIMG;
        if (p != null)
        {
            fIMG = AppFiles.getProfilePhoto(p.getNickname());
        }
        else
        {
            fIMG = null;
        }
        GUIUtil.loadImageView(ivProfile, GUIUtil.IMG_TYPE_PROFILE, fIMG);
    }

    @FXML
    void listenerCBShowOnlyActive(ActionEvent ae)
    {
        MonasticProfile selectedProfile;
        MonasticProfile firstProfile;

        selectedProfile = ctrGUIMain.getCtrMain().getCtrProfile().loadByID(IDSelectedProfile);

        /*
         * if the currently selected profile is inactive and the option for
         * showing only active profiles is selected it will disappear from the
         * list so it is necessary to change the selected profile
         */
        if (cbShowOnlyActive.isSelected() && selectedProfile.getStatus().equals("INACTIVE"))
        {
            //one choice is to select to the first profile on the DB
            firstProfile = ctrGUIMain.getCtrMain().getCtrProfile().loadFirstProfile(cbShowOnlyActive.isSelected());
            //if there is at least one active profile on the DB
            if (firstProfile != null)
            {
                IDSelectedProfile = firstProfile.getIdProfile();
                fillNicknameList();
                cbSelectedMonastic.setValue(firstProfile.getNickname());
            }
            else
            {
                IDSelectedProfile = -1;
                cbSelectedMonastic.setValue(null);
            }

        }
        else
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
        }
        else
        {
            return false;
        }
    }

    public boolean isSet2ShowOnlyActive()
    {
        return cbShowOnlyActive.isSelected();
    }
    
}
