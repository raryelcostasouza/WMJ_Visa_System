/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.Tm30NotificationResidence;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneTM30NotifResidence extends AbstractChildPaneController
{
    
    @FXML
    private Button bArchive;
    
    @FXML
    private Button bSelectPDF;
    
    @FXML
    private TreeView<String> tvSavedNotifications;
    
    @FXML
    private TreeView<String> tvMonastics;
    
    @FXML
    private DatePicker dpNotification;
    
    @FXML
    private TextField tfPathPDF;
    
    private ArrayList<CheckBoxTreeItem<String>> listItemTMonastics;
    private File fSelected;
    
    @Override
    public void init()
    {
        TreeItem<String> rootSavedNotif;
        
        listItemTMonastics = new ArrayList<>();
        tvMonastics.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        
        rootSavedNotif = new TreeItem<>("Saved Residence Notifications");
        tvSavedNotifications.setRoot(rootSavedNotif);
        tvSavedNotifications.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue)
            {
                if (newValue != null && !newValue.isLeaf())
                {
                    bArchive.setDisable(false);
                } else
                {
                    bArchive.setDisable(true);
                }
                
            }
        });
        
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNotification);
    }
    
    public void fillData()
    {
        loadSavedNotificationTree();
        loadMonasticTree();
        
        dpNotification.setValue(null);
        tfPathPDF.setText("");
        
        tvMonastics.getSelectionModel().clearSelection();
    }
    
    private void loadMonasticTree()
    {
        ArrayList<String> monasticNickNameList;
        
        monasticNickNameList = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileNicknameList(true);
        TreeItem<String> rootItem = new TreeItem<>("Monastics");
        listItemTMonastics.clear();
        for (String nickname : monasticNickNameList)
        {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(nickname);
            listItemTMonastics.add(item);
        }
        rootItem.getChildren().addAll(listItemTMonastics);
        tvMonastics.setRoot(rootItem);
        
    }
    
    private void loadSavedNotificationTree()
    {
        TreeItem<String> rootItem, formItem, monasticItem;
        ArrayList<Tm30NotificationResidence> listFormTM30;
        LocalDate ldNotifDate;
        
        tvSavedNotifications.getRoot().getChildren().clear();
        listFormTM30 = ctrGUIMain.getCtrMain().getCtrFormTM30().loadAll();
        
        for (Tm30NotificationResidence form : listFormTM30)
        {
            ldNotifDate = Util.convertDateToLocalDate(form.getNotificationDate());
            formItem = new TreeItem<>(ldNotifDate.format(Util.DEFAULT_DATE_FORMAT));
            if (form.getMonasticProfileSet() != null)
            {
                for (Iterator<MonasticProfile> iterator = form.getMonasticProfileSet().iterator(); iterator.hasNext();)
                {
                    monasticItem = new TreeItem<>(iterator.next().getNickname());
                    formItem.getChildren().add(monasticItem);
                }
            }
            
            tvSavedNotifications.getRoot().getChildren().add(formItem);
        }
    }
    
    @FXML
    void actionAddNew(ActionEvent ae)
    {
        LocalDate ldNotifDate;
        TreeItem<String> newNotif;
        TreeItem<String> monasticNode;
        Tm30NotificationResidence objFormTM30;
        int opStatus2, opStatus1;
        
        ldNotifDate = dpNotification.getValue();
        
        if (ldNotifDate != null)
        {
            objFormTM30 = new Tm30NotificationResidence();
            objFormTM30.setNotificationDate(Util.convertLocalDateToDate(ldNotifDate));
            
            opStatus1 = CtrFileOperation.copyOperation(fSelected, AppFiles.getPrintoutTM30(ldNotifDate));
            //if the file copy was successfull
            if (opStatus1 == 0)
            {
                opStatus2 = ctrGUIMain.getCtrMain().getCtrFormTM30().create(objFormTM30, getNicknameSelectedMonastics());
                //if the DB update was successfull
                if (opStatus2 == 0)
                {
                    CtrAlertDialog.infoDialog("TM30 Registered", "The TM30 Form was added successfully.");
                } else
                {
                    CtrFileOperation.deleteFile(AppFiles.getPrintoutTM30(ldNotifDate));
                }
            }
            
            {
//                newNotif = new TreeItem<>(ldNotifDate.format(Util.DEFAULT_DATE_FORMAT));
//                for (String nickName : getNicknameSelectedMonastics())
//                {
//                    monasticNode = new TreeItem<>(nickName);
//                    newNotif.getChildren().add(monasticNode);
//                }
//                tvSavedNotifications.getRoot().getChildren().add(newNotif);
            }
        }
        fillData();
        
    }
    
    @FXML
    void actionSelectFile(ActionEvent ae)
    {
        fSelected = CtrFileOperation.selectFile("Select TM30 File", CtrFileOperation.FILE_CHOOSER_TYPE_PDF);
        if (fSelected != null)
        {
            tfPathPDF.setText(fSelected.getAbsolutePath().toString());
        }
    }
    
    private ArrayList<String> getNicknameSelectedMonastics()
    {
        ArrayList<String> listSelectedMonastics;
        
        listSelectedMonastics = new ArrayList<>();
        for (CheckBoxTreeItem<String> cbMonasticItem : listItemTMonastics)
        {
            if (cbMonasticItem.isSelected())
            {
                listSelectedMonastics.add(cbMonasticItem.getValue());
            }
        }
        return listSelectedMonastics;
    }
}
