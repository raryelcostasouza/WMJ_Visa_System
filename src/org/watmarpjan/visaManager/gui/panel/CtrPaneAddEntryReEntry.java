/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneAddEntryReEntry extends AbstractChildPaneController implements IFormMonasticProfile
{

    @FXML
    private TextField tfTM6Number;
    @FXML
    private CheckBox cbReentryTogetherExtension;
    @FXML
    private DatePicker dpLastEntry;

    @FXML
    private ComboBox<String> cbTravelFrom;

    @FXML
    private ComboBox<String> cbTravelBy;

    @FXML
    private ComboBox<String> cbPortOfEntry;

    @FXML
    private Button bClear;
    @FXML
    private Button bRegister;

    @FXML
    private Button bPreview;

    @FXML
    private Button bPrint;

    @Override
    public void init()
    {
        bPreview.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bPrint.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));

        cbTravelBy.getItems().addAll(AppConstants.LIST_TRAVEL_BY);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpLastEntry);

        tfTM6Number.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                tfTM6Number.setText(newValue.toUpperCase());
            }
        });
    }

    @Override
    public void fillData(MonasticProfile p
    )
    {
        loadContentsCBPortOfEntry();
        loadContentsCBTravelFrom();

        if (p != null)
        {
            tfTM6Number.setText(p.getArrivalCardNumber());
            dpLastEntry.setValue(Util.convertDateToLocalDate(p.getArrivalLastEntryDate()));
            cbTravelFrom.setValue(p.getArrivalTravelFrom());
            cbTravelBy.setValue(p.getArrivalTravelBy());
            cbPortOfEntry.setValue(p.getArrivalPortOfEntry());

            if (p.getArrivalCardNumber() != null)
            {
                //blocks edition and enables archive button
                bClear.setDisable(false);
                bRegister.setDisable(true);

                tfTM6Number.setEditable(false);
                dpLastEntry.setDisable(true);
                cbTravelFrom.setDisable(true);
                cbTravelBy.setDisable(true);
                cbPortOfEntry.setDisable(true);
            }
            else
            {
                bClear.setDisable(true);
                bRegister.setDisable(false);

                tfTM6Number.setEditable(true);
                dpLastEntry.setDisable(false);
                cbTravelFrom.setDisable(false);
                cbTravelBy.setDisable(false);
                cbPortOfEntry.setDisable(false);
            }
        }
    }

    private void loadContentsCBPortOfEntry()
    {
        ArrayList<String> alPortOfEntry;

        alPortOfEntry = ctrGUIMain.getCtrMain().getCtrProfile().loadListPortOfEntry();

        cbPortOfEntry.getItems().clear();
        cbPortOfEntry.getItems().addAll(alPortOfEntry);
    }

    private void loadContentsCBTravelFrom()
    {
        ArrayList<String> alTravelFrom;

        alTravelFrom = ctrGUIMain.getCtrMain().getCtrProfile().loadListTravelFrom();

        cbTravelFrom.getItems().clear();
        cbTravelFrom.getItems().addAll(alTravelFrom);
    }

    @FXML
    void actionClear(ActionEvent ae)
    {
        boolean confirmation;
        MonasticProfile p;
        int operationStatus;

        confirmation = CtrAlertDialog.confirmationDialog("Clear", "The arrival information will be cleared. \nDo you want to continue?");
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            p.setArrivalCardNumber(null);
            p.setArrivalLastEntryDate(null);
            p.setArrivalTravelFrom(null);
            p.setArrivalTravelBy(null);
            p.setArrivalPortOfEntry(null);

            operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            if (operationStatus == 0)
            {
                fillData(p);
                CtrAlertDialog.infoDialog("Cleared successfully", "The previous arrival info scan was cleared.");
            }
        }
    }

    @FXML
    void actionRegister(ActionEvent ae)
    {
        MonasticProfile p;
        int opStatus;

        if (validateFields())
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            p.setArrivalCardNumber(tfTM6Number.getText());
            p.setArrivalLastEntryDate(Util.convertLocalDateToDate(dpLastEntry.getValue()));
            p.setArrivalTravelFrom(cbTravelFrom.getValue());
            p.setArrivalTravelBy(cbTravelBy.getValue());
            p.setArrivalPortOfEntry(cbPortOfEntry.getValue());

            opStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            if (opStatus == 0)
            {
                fillData(p);
                CtrAlertDialog.infoDialog("Entry/Re-entry registered", "The entry/re-entry info was registered successfully.");
            }
        }
        else
        {
            CtrAlertDialog.errorDialog("Please fill out ALL the entry/re-entry fields before registering");
        }
    }

    private boolean validateFields()
    {
        return (!tfTM6Number.getText().isEmpty())
                && (dpLastEntry.getValue() != null)
                && (cbPortOfEntry.getValue() != null)
                && (cbTravelFrom.getValue() != null)
                && (cbTravelBy.getValue() != null);
    }

    @FXML
    void actionPreviewFormTM8Reentry(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM8Reentry(), p, CtrPDF.OPTION_PREVIEW_FORM, cbReentryTogetherExtension.isSelected());
    }

    @FXML
    void actionPrintFormTM8Reentry(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM8Reentry(), p, CtrPDF.OPTION_PRINT_FORM, cbReentryTogetherExtension.isSelected());
    }

}
