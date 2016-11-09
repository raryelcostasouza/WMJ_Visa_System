/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

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
import org.watmarpjan.visaManager.control.CtrForm;
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
    private TextField tfTravelFrom;

    @FXML
    private ComboBox<String> cbTravelBy;

    @FXML
    private TextField tfPortOfEntry;

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
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        if (p != null)
        {
            tfTM6Number.setText(p.getArrivalCardNumber());
            dpLastEntry.setValue(Util.convertDateToLocalDate(p.getArrivalLastEntryDate()));
            tfTravelFrom.setText(p.getArrivalTravelFrom());
            cbTravelBy.setValue(p.getArrivalTravelBy());
            tfPortOfEntry.setText(p.getArrivalPortOfEntry());

            if (p.getArrivalCardNumber() != null)
            {
                //blocks edition and enables archive button
                bClear.setDisable(false);
                bRegister.setDisable(true);

                tfTM6Number.setEditable(false);
                dpLastEntry.setDisable(true);
                tfTravelFrom.setEditable(false);
                cbTravelBy.setDisable(true);
                tfPortOfEntry.setEditable(false);
            }
            else
            {
                bClear.setDisable(true);
                bRegister.setDisable(false);

                tfTM6Number.setEditable(true);
                dpLastEntry.setDisable(false);
                tfTravelFrom.setEditable(true);
                cbTravelBy.setDisable(false);
                tfPortOfEntry.setEditable(true);
            }
        }
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
                CtrAlertDialog.infoDialog("Cleared successfully", "The previous arrival info scan was cleared.");
                fillData(p);
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
            p.setArrivalTravelFrom(tfTravelFrom.getText());
            p.setArrivalTravelBy(cbTravelBy.getValue());
            p.setArrivalPortOfEntry(tfPortOfEntry.getText());

            opStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            if (opStatus == 0)
            {
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
                && (!tfPortOfEntry.getText().isEmpty())
                && (!tfTravelFrom.getText().isEmpty())
                && (cbTravelBy.getValue() != null);
    }

    @FXML
    void actionPreviewFormTM8Reentry(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrForm().fillForm(AppFiles.getFormTM8Reentry(), p, CtrForm.OPTION_PREVIEW_FORM, cbReentryTogetherExtension.isSelected());
    }

    @FXML
    void actionPrintFormTM8Reentry(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrForm().fillForm(AppFiles.getFormTM8Reentry(), p, CtrForm.OPTION_PRINT_FORM, cbReentryTogetherExtension.isSelected());
    }

}
