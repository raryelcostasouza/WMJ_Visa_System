/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.watmarpjan.visaManager.model.EntryPrintoutTM30;
import org.watmarpjan.visaManager.model.hibernate.PrintoutTm30;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneTM30NotifResidence extends AbstractChildPaneController
{

//    @FXML
//    private Button bArchive;

    @FXML
    private TableView<EntryPrintoutTM30> tvSavedNotifications;

    @FXML
    private TableColumn<EntryPrintoutTM30, String> tcOpenPDF;

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
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNotification);
        listItemTMonastics = new ArrayList<>();
        tvMonastics.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        tvSavedNotifications.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("pNotifDate"));
        tvSavedNotifications.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("pListMonasticNickname"));
        tcOpenPDF.setCellValueFactory(new PropertyValueFactory<>(""));

        tvSavedNotifications.setColumnResizePolicy((param) -> true);
        Callback<TableColumn<EntryPrintoutTM30, String>, TableCell<EntryPrintoutTM30, String>> openPDFCellFactory =
                new Callback<TableColumn<EntryPrintoutTM30, String>, TableCell<EntryPrintoutTM30, String>>()
        {
            @Override
            public TableCell call(final TableColumn<EntryPrintoutTM30, String> param)
            {
                final TableCell<EntryPrintoutTM30, String> cell = new TableCell<EntryPrintoutTM30, String>()
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
                                        EntryPrintoutTM30 clickedEntry;
                                        LocalDate ldNotif;
                                        
                                        clickedEntry = getTableView().getItems().get(getIndex());
                                        ldNotif = Util.convertDateToLocalDate(clickedEntry.getPrintoutTM30().getNotifDate());
                                        CtrFileOperation.openPDFOnDefaultProgram(AppFiles.getPrintoutTM30(ldNotif));
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
        ArrayList<PrintoutTm30> listPrintoutTM30;
        ArrayList<EntryPrintoutTM30> listEntryTM30;
        EntryPrintoutTM30 objEntryTM30;
        
        listPrintoutTM30 = ctrGUIMain.getCtrMain().getCtrPrintoutTM30().loadListPrintoutTM30();
        listEntryTM30 = new ArrayList<>();
        for (PrintoutTm30 objTM30 : listPrintoutTM30)
        {
            ctrGUIMain.getCtrMain().getCtrPrintoutTM30().refresh(objTM30);
            objEntryTM30 = new EntryPrintoutTM30(objTM30);
            listEntryTM30.add(objEntryTM30);
        }

        tvSavedNotifications.getItems().clear();
        tvSavedNotifications.getItems().addAll(listEntryTM30);
    }

    @FXML
    void actionAddNew(ActionEvent ae)
    {
        LocalDate ldNotifDate;
        int opStatus2, opStatus1;

        ldNotifDate = dpNotification.getValue();

        if (validateFields())
        {
            opStatus1 = CtrFileOperation.copyOperation(fSelected, AppFiles.getPrintoutTM30(ldNotifDate));
            //if the file copy was successfull
            if (opStatus1 == 0)
            {
                opStatus2 = ctrGUIMain.getCtrMain().getCtrPrintoutTM30().addNewPrintout(ldNotifDate, getNicknameSelectedMonastics());
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
        else
        {
            CtrAlertDialog.warningDialog("Fill out all fields before adding a new Printout.");
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
    
    private boolean validateFields()
    {
        return (fSelected!= null) && 
                (dpNotification.getValue() != null);
    }
}
