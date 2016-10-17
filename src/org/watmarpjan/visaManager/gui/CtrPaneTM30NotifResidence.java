/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.EntryTM30GroupByDate;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
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
    private TableView<EntryTM30GroupByDate> tvSavedNotifications;

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

        listItemTMonastics = new ArrayList<>();
        tvMonastics.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        tvSavedNotifications.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("pNotifDate"));
        tvSavedNotifications.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("pListMonasticNickname"));

        tvSavedNotifications.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EntryTM30GroupByDate>()
        {
            @Override
            public void changed(ObservableValue<? extends EntryTM30GroupByDate> observable, EntryTM30GroupByDate oldValue, EntryTM30GroupByDate newValue)
            {
                if (newValue != null)
                {
                    bArchive.setDisable(false);
                } else
                {
                    bArchive.setDisable(true);
                }
            }
        }
        );
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNotification);
    }

    public void fillData()
    {
        loadTableNotifResidence();
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

    private void loadTableNotifResidence()
    {
        ArrayList<EntryTM30GroupByDate> listEntryTM30GroupByDate;

        listEntryTM30GroupByDate = ctrGUIMain.getCtrMain().getCtrProfile().loadListTM30GroupByDate();

        tvSavedNotifications.getItems().clear();
        tvSavedNotifications.getItems().addAll(listEntryTM30GroupByDate);
    }

    @FXML
    void actionArchive(ActionEvent ae)
    {
        EntryTM30GroupByDate objTableEntry;

        objTableEntry = tvSavedNotifications.getSelectionModel().getSelectedItem();

    }

    @FXML
    void actionAddNew(ActionEvent ae)
    {
        LocalDate ldNotifDate;
        TreeItem<String> newNotif;
        TreeItem<String> monasticNode;
        int opStatus2, opStatus1;
        MonasticProfile mp;

        ldNotifDate = dpNotification.getValue();

        if (ldNotifDate != null)
        {
            opStatus1 = CtrFileOperation.copyOperation(fSelected, AppFiles.getPrintoutTM30(ldNotifDate));
            //if the file copy was successfull
            if (opStatus1 == 0)
            {
                for (String nickname : getNicknameSelectedMonastics())
                {
                    mp = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileByNickName(nickname);
                    mp.setTm30NotifDate(Util.convertLocalDateToDate(ldNotifDate));
                }
                opStatus2 = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(null);
                //if the DB update was successfull
                if (opStatus2 == 0)
                {
                    CtrAlertDialog.infoDialog("TM30 Registered", "The TM30 Form was added successfully.");
                    fillData();
                } else
                {
                    CtrFileOperation.deleteFile(AppFiles.getPrintoutTM30(ldNotifDate));
                }
            }
        }
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
