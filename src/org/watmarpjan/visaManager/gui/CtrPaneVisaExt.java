/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.time.LocalDate;
import org.watmarpjan.visaManager.model.EntryVisaExt;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
import org.watmarpjan.visaManager.model.hibernate.Profile;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneVisaExt extends AbstractFormSelectExtraScan implements IFormMonasticProfile
{

    @FXML
    private TextField tfParentVisaNumber;

    @FXML
    private TableView<EntryVisaExt> tvExtensions;

    @FXML
    private TextField tfExtNumber;

    @FXML
    private DatePicker dpExpiryDate;
    @FXML
    private Button bSelectScan;
    @FXML
    private Button bRegister;

//    private AbstractResultDialogSelectScan resultSelectScan;
    @Override
    public void init()
    {
        TableColumn<EntryVisaExt, String> tc;

        //code for inserting a remove button on the last column
        Callback<TableColumn<EntryVisaExt, String>, TableCell<EntryVisaExt, String>> actionCellFactory =
                new Callback<TableColumn<EntryVisaExt, String>, TableCell<EntryVisaExt, String>>()
        {
            @Override
            public TableCell call(final TableColumn<EntryVisaExt, String> param)
            {
                final TableCell<EntryVisaExt, String> cell = new TableCell<EntryVisaExt, String>()
                {

                    final Button btn = new Button("Remove");

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
                            btn.setOnAction(new EventHandler<ActionEvent>()
                            {
                                @Override
                                public void handle(ActionEvent event)
                                {
                                    actionRemoveVisaExt(event, getIndex());
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        tvExtensions.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("extNumber"));
        tvExtensions.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        tc = (TableColumn<EntryVisaExt, String>) tvExtensions.getColumns().get(2);

        tc.setCellValueFactory(new PropertyValueFactory<>("action"));
        tc.setCellFactory(actionCellFactory);
    }

    @Override
    public void fillData(Profile p)
    {
        ArrayList<EntryVisaExt> alVisaExtensions;
        LocalDate ldVisaExpiry;

        ImgUtil.loadImageView(ivPreview, ImgUtil.IMG_TYPE_PASSPORT, AppFiles.getDefaultIMG(ImgUtil.IMG_TYPE_PASSPORT));
        if (p.getVisaNumber() != null)
        {
            tfParentVisaNumber.setText(p.getVisaNumber());

            tfExtNumber.setEditable(true);
            dpExpiryDate.setDisable(false);

            bSelectScan.setDisable(false);
            tfPsptPageNumber.setEditable(true);
            bRegister.setDisable(false);
        } else
        {
            tfParentVisaNumber.setText("");
            tfExtNumber.setEditable(false);
            dpExpiryDate.setDisable(true);

            bSelectScan.setDisable(true);
            tfPsptPageNumber.setEditable(false);
            bRegister.setDisable(true);
        }

        tfExtNumber.setText("");
        tfPsptPageNumber.setText("");

        alVisaExtensions = ctrGUIMain.getCtrMain().getCtrVisa().loadListExtensions(p.getIdprofile());
        tvExtensions.getItems().clear();
        tvExtensions.getItems().addAll(alVisaExtensions);

        //pre-set the expiry date for the extension as 1 year after the original visa expiry date
        if (p.getVisaExpiryDate() != null)
        {
            ldVisaExpiry = Util.convertDateToLocalDate(p.getVisaExpiryDate());
            dpExpiryDate.setValue(ldVisaExpiry.plusYears(1));
        } else
        {
            dpExpiryDate.setValue(null);
        }

    }

    @FXML
    void actionRegisterVisaExt(ActionEvent ae)
    {
        Profile p;
        VisaExtension vExt;
        int operationStatus1, operationStatus2;
        PassportScan psExtScan;

        //TODO test this function
        if (validateFields() && resultSelectScan != null)
        {
            /*
             * For proper consistency 2 operations need to be successful
             * 1.1 - If the user select a new scan file
             * need to copy it to the app folder
             * OR
             * 1.2 - If the user select an existing scan file
             * need to rename the scan file
             *
             * 2 - update the Database information
             *
             * Implementation:
             * 1) Process the scan file
             * 2) If the copy is successful, then update the DB info
             * 3) If the db update fails undo the operation 1
             *
             */

            p = ctrGUIMain.getPaneSelectionController().getSelectedProfile();
            operationStatus1 = super.processSelectedScan(p, AbstractFormSelectExtraScan.SCAN_TYPE_VISA_EXT);
            psExtScan = super.getPassportScan();

            //if the copy/rename operation was successfull
            if (operationStatus1 == 0)
            {
                //if the scan copy is sucessful
                vExt = new VisaExtension();
                vExt.setExtNumber(tfExtNumber.getText());
                vExt.setExpiryDate(Util.convertLocalDateToDate(dpExpiryDate.getValue()));

                vExt.setProfile(p);

                operationStatus2 = ctrGUIMain.getCtrMain().getCtrVisa().addVisaExt(vExt, psExtScan);

                //if the DB update is successful
                if (operationStatus2 == 0)
                {
                    CtrAlertDialog.infoDialog("Extension info registered", "The visa extension was successfully registered.");
                    resultSelectScan = null;
                    ctrGUIMain.getCtrMain().getCtrProfile().refreshProfile(p);
                    fillData(p);
                } else
                {
                    //if the DB update fails has to undo the Operation 1
                    super.undoProcessingSelectedScan(p);
                }
            }
        } else
        {
            CtrAlertDialog.errorDialog("Please fill out ALL the visa extension fields before registering");
        }
    }

    private void actionRemoveVisaExt(ActionEvent ae, int selectedRowIndex)
    {
        EntryVisaExt selectedEntry;
        VisaExtension vExt;
        int operationStatus;
        boolean confirmation;
        String msg;

        selectedEntry = tvExtensions.getItems().get(selectedRowIndex);
        vExt = ctrGUIMain.getCtrMain().getCtrVisa().loadVisaExtensionByNumber(selectedEntry.getExtNumber());

        msg = "Are you sure that you want to remove the following visa extension?\n"
                + "Extension Number: " + selectedEntry.getExtNumber() + "\n"
                + "Expiry Date: " + selectedEntry.getLdExpiryDate().toString();

        confirmation = CtrAlertDialog.confirmationDialog("Confirmation", msg);
        if (confirmation)
        {
            operationStatus = ctrGUIMain.getCtrMain().getCtrVisa().removeVisaExt(vExt);

            if (operationStatus == 0)
            {
                tvExtensions.getItems().remove(selectedEntry);
            }
        }
    }

    public boolean validateFields()
    {
        return ((tfExtNumber.getText() != null)
                && (dpExpiryDate.getValue() != null)
                && (tfPsptPageNumber.getText() != null));
    }

}
