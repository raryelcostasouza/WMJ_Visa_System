/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneController;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneAddRenewPassport extends AChildPaneController implements IFormMonasticProfile
{

    @FXML
    private TextField tfpassportNumber;
    @FXML
    private ComboBox<String> cbPassportCountry;
    @FXML
    private ComboBox<String> cbPassportIssuedAt;
    @FXML
    private DatePicker dpPassportExpiryDate;
    @FXML
    private DatePicker dpPassportIssueDate;

    @FXML
    private Button bClear;
    @FXML
    private Button bRegister;

    @Override
    public void init()
    {
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpPassportIssueDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpPassportExpiryDate);

        tfpassportNumber.textProperty().addListener((observable, oldValue, newValue)
                ->
        {
            if (newValue != null)
            {
                String filteredString;
                //removes any non-word characters
                filteredString = newValue.replaceAll("\\W", "");
                tfpassportNumber.setText(filteredString.toUpperCase());
            }
        });
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        GUIUtil.loadContentComboboxGeneric(cbPassportCountry, ctrGUIMain.getCtrMain().loadListCountry());
        GUIUtil.loadContentComboboxGeneric(cbPassportIssuedAt, ctrGUIMain.getCtrMain().getCtrProfile().loadListPassportIssuedAt());

        if (p != null)
        {
            tfpassportNumber.setText(p.getPassportNumber());
            cbPassportCountry.setValue(p.getPassportCountry());
            cbPassportIssuedAt.setValue(p.getPassportIssuedAt());
            dpPassportExpiryDate.setValue(Util.convertDateToLocalDate(p.getPassportExpiryDate()));
            dpPassportIssueDate.setValue(Util.convertDateToLocalDate(p.getPassportIssueDate()));

            //if there is a passport registered already
            if (p.getPassportNumber() != null)
            {
                //blocks edition and enables archive button
                bClear.setDisable(false);
                bRegister.setDisable(true);

                tfpassportNumber.setEditable(false);
                cbPassportCountry.setDisable(true);
                cbPassportIssuedAt.setDisable(true);
                dpPassportExpiryDate.setDisable(true);
                dpPassportIssueDate.setDisable(true);
            }
            else
            {
                //unlocks edition and enables select scan button
                bClear.setDisable(true);
                bRegister.setDisable(false);

                tfpassportNumber.setEditable(true);
                cbPassportCountry.setDisable(false);
                cbPassportIssuedAt.setDisable(false);
                dpPassportExpiryDate.setDisable(false);
                dpPassportIssueDate.setDisable(false);
            }
        }
    }

    @FXML
    void actionClearPassportData(ActionEvent ae)
    {
        boolean confirmation;
        MonasticProfile p;
        int opStatus;

        confirmation = CtrAlertDialog.confirmationDialog("Clear passport data", "The passport data (number, issue location, expiry date) will be cleared and all passport scans will be archived. \nDo you want to continue?");

        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            //archives the scan files
            CtrFileOperation.archiveAllPassportScans(p);

            p.setPassportNumber(null);
            p.setPassportCountry(null);
            p.setPassportIssuedAt(null);
            p.setPassportExpiryDate(null);
            p.setPassportIssueDate(null);

            opStatus = ctrGUIMain.getCtrMain().getCtrProfile().update(p);
            if (opStatus == 0)
            {
                fillData(p);
                CtrAlertDialog.infoDialog("Cleared successfully", "The previous passport info was cleared successfully.");
            }
        }
    }

    @FXML
    void actionRegister(ActionEvent ae)
    {
        MonasticProfile p;
        int operationStatus;

        //if all the information is filled out
        if (validateFields())
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            p.setPassportNumber(tfpassportNumber.getText());
            p.setPassportCountry(cbPassportCountry.getValue());
            p.setPassportIssuedAt(cbPassportIssuedAt.getValue());
            p.setPassportIssueDate(Util.convertLocalDateToDate(dpPassportIssueDate.getValue()));
            p.setPassportExpiryDate(Util.convertLocalDateToDate(dpPassportExpiryDate.getValue()));
            operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().update(p);

            if (operationStatus == 0)
            {
                fillData(p);
                CtrAlertDialog.infoDialog("Passport Added/Renewed", "The passport data was sucessfully updated.");
            }
        }
        else
        {
            CtrAlertDialog.errorDialog("Please fill out the ALL passport information before registering.");
        }
    }

    private boolean validateFields()
    {
        return ((dpPassportExpiryDate.getValue() != null)
                && (dpPassportIssueDate.getValue() != null)
                && (!tfpassportNumber.getText().isEmpty())
                && (cbPassportCountry.getValue() != null)
                && (cbPassportIssuedAt.getValue() != null));
    }
}
