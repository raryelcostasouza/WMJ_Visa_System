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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.EntryDueTask;
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
    private TableColumn<EntryTM30GroupByDate, String> tcOpenPDF;

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
        ImageView ivPDFIcon;

        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNotification);
        listItemTMonastics = new ArrayList<>();
        tvMonastics.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        tvSavedNotifications.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("pNotifDate"));
        tvSavedNotifications.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("pListMonasticNickname"));
        tcOpenPDF.setCellValueFactory(new PropertyValueFactory<>(""));

        tvSavedNotifications.setColumnResizePolicy((param) -> true);
        Callback<TableColumn<EntryTM30GroupByDate, String>, TableCell<EntryTM30GroupByDate, String>> openPDFCellFactory =
                new Callback<TableColumn<EntryTM30GroupByDate, String>, TableCell<EntryTM30GroupByDate, String>>()
        {
            @Override
            public TableCell call(final TableColumn<EntryTM30GroupByDate, String> param)
            {
                final TableCell<EntryTM30GroupByDate, String> cell = new TableCell<EntryTM30GroupByDate, String>()
                {

                    final Button btn = new Button("");
                    final ImageView ivPDFIcon = new ImageView(AppPaths.getPathToIconSubfolder().resolve("pdf.png").toUri().toString());

                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        } else
                        {

                            btn.setGraphic(ivPDFIcon);
                            btn.setOnAction((ActionEvent event)
                                    -> 
                                    {
                                        EntryTM30GroupByDate clickedEntry;

                                        clickedEntry = getTableView().getItems().get(getIndex());
                                        CtrFileOperation.openPDFOnDefaultProgram(AppFiles.getPrintoutTM30(clickedEntry.getaLdNotifDate()));
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        tcOpenPDF.setCellFactory(openPDFCellFactory);

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
        ArrayList<LocalDate> listNotifDateSavedTM30;

        //loads the list of the notif dates for all saved files of TM30 from disk
        listNotifDateSavedTM30 = CtrFileOperation.getListNotifDateFilesTM30();

        //loads the entries of notif dates and the associated monastics from DB
        listEntryTM30GroupByDate = ctrGUIMain.getCtrMain().getCtrProfile().loadListTM30GroupByDate();

        for (LocalDate ldSavedFileTM30 : listNotifDateSavedTM30)
        {
            //if there is a TM30 that is not currently associated to any monastic
            //add a table entry as well
            //compares the entry date of the fileList on the disk with the entry dates from the DB
            if (!listEntryTM30GroupByDate.contains(new EntryTM30GroupByDate(ldSavedFileTM30)))
            {
                listEntryTM30GroupByDate.add(new EntryTM30GroupByDate(ldSavedFileTM30));
            }
        }

        tvSavedNotifications.getItems().clear();
        tvSavedNotifications.getItems().addAll(listEntryTM30GroupByDate);
    }

    @FXML
    void actionAddNew(ActionEvent ae)
    {
        LocalDate ldNotifDate;
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
