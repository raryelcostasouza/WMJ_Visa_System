/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneController;
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
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.BlockMonasticSelection;
import org.watmarpjan.visaManager.model.EntryPrintoutTM30;
import org.watmarpjan.visaManager.model.hibernate.PrintoutTm30;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneTM30NotifResidence extends AChildPaneController
{

//    @FXML
//    private Button bArchive;

    @FXML
    private TableView<EntryPrintoutTM30> tvSavedNotifications;

    @FXML
    private TableColumn<EntryPrintoutTM30, String> tcOpenPDF;
    
    @FXML
    private TableColumn<EntryPrintoutTM30, String> tcRemovePrintout;
    
    @FXML
    private TreeView<String> tvMonastics;

    @FXML
    private DatePicker dpNotification;

    @FXML
    private TextField tfPathPDF;

    private BlockMonasticSelection bMonasticSelection;
    private File fSelected;

    @Override
    public void init()
    {
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNotification);
        tvMonastics.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        bMonasticSelection = new BlockMonasticSelection(tvMonastics);
        
        tvSavedNotifications.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("pNotifDate"));
        tvSavedNotifications.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("pListMonasticNickname"));
        
        tcOpenPDF.setCellValueFactory(new PropertyValueFactory<>(""));
        tcRemovePrintout.setCellValueFactory(new PropertyValueFactory<>(""));

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
                                        clickedEntry = getTableView().getItems().get(getIndex());
                                        CtrFileOperation.openPDFOnDefaultProgram(AppFiles.getPrintoutTM30(clickedEntry.getPrintoutTM30()));
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        
         Callback<TableColumn<EntryPrintoutTM30, String>, TableCell<EntryPrintoutTM30, String>> removePrintoutCellFactory =
                new Callback<TableColumn<EntryPrintoutTM30, String>, TableCell<EntryPrintoutTM30, String>>()
        {
            @Override
            public TableCell call(final TableColumn<EntryPrintoutTM30, String> param)
            {
                final TableCell<EntryPrintoutTM30, String> cell = new TableCell<EntryPrintoutTM30, String>()
                {

                    final Button btn = new Button("");
                    final ImageView ivRemoveIcon = new ImageView(AppPaths.getPathToIconSubfolder().resolve("remove.png").toUri().toString());

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

                            btn.setGraphic(ivRemoveIcon);
                            btn.setOnAction((ActionEvent event)
                                    -> 
                                    {
                                        EntryPrintoutTM30 clickedEntry;
                                        clickedEntry = getTableView().getItems().get(getIndex());
                                        actionRemovePrintout(event, clickedEntry);
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
        tcRemovePrintout.setCellFactory(removePrintoutCellFactory);
    }

    public void fillData()
    {
        loadTableNotifResidence();
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(bMonasticSelection);

        dpNotification.setValue(null);
        tfPathPDF.setText("");

        tvMonastics.getSelectionModel().clearSelection();
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
        int opStatus2, opStatus1, auxIndex;
        Integer nMaxAuxIndex;

        ldNotifDate = dpNotification.getValue();

        if (validateFields())
        {
            //if there is more than one printout on the same date
            //they will have different auxIndex values
            nMaxAuxIndex = ctrGUIMain.getCtrMain().getCtrPrintoutTM30().getMaxAuxIndexPrintoutForNotifDate(ldNotifDate);
            
            //if there is no entry registered for the selected notif date
            if (nMaxAuxIndex == null)
            {
                auxIndex = 0;
            }
            else
            {
                auxIndex = nMaxAuxIndex +1;
            }
            
            opStatus1 = CtrFileOperation.copyOperation(fSelected, AppFiles.getPrintoutTM30(ldNotifDate, auxIndex));
            //if the file copy was successfull
            if (opStatus1 == 0)
            {
                opStatus2 = ctrGUIMain.getCtrMain().getCtrPrintoutTM30().addNewPrintout(ldNotifDate, getNicknameSelectedMonastics(), auxIndex);
                //if the DB update was successfull
                if (opStatus2 == 0)
                {
                    CtrAlertDialog.infoDialog("TM30 Registered", "The TM30 Form was added successfully.");
                    fillData();
                } else
                {
                    CtrFileOperation.deleteFile(AppFiles.getPrintoutTM30(ldNotifDate,auxIndex));
                }
            }
        }
        else
        {
            CtrAlertDialog.warningDialog("Fill out all fields before adding a new Printout.");
        }
    }

    @FXML
    void actionRemovePrintout(ActionEvent ae, EntryPrintoutTM30 objEntryTM30)
    {
        String msg;
        boolean confirmation;
        int opStatusDB, opStatusArchive;
        PrintoutTm30 objTM30;
    
         msg = "Are you sure that you want to remove the following TM30 Printout entry?\n"
                 + "(Note: The printout file will be archived)\n "
                + "Notification Date: " + objEntryTM30.getPNotifDate() + "\n"
                + "Monastics: " + objEntryTM30.getPListMonasticNickname();

        confirmation = CtrAlertDialog.confirmationDialog("Confirmation", msg);
        if (confirmation)
        {
            objTM30 = objEntryTM30.getPrintoutTM30();
            opStatusDB = ctrGUIMain.getCtrMain().getCtrPrintoutTM30().removePrintout(objTM30);
            if (opStatusDB == 0)
            {
                tvSavedNotifications.getItems().remove(objEntryTM30);
                opStatusArchive = CtrFileOperation.archivePrintoutTM30(AppFiles.getPrintoutTM30(objTM30));
                if (opStatusArchive == 0)
                {
                    CtrAlertDialog.infoDialog("Archived successfully", "The TM30 Printout was archived successfully.");
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
        for (CheckBoxTreeItem<String> cbMonasticItem : bMonasticSelection.getListCheckBoxMonastics())
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
