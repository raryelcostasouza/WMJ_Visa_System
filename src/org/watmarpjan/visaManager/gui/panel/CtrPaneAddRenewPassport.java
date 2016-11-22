/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneAddRenewPassport extends AbstractChildPaneController implements IFormMonasticProfile
{

    @FXML
    private TextField tfpassportNumber;
    @FXML
    private TextField tfpassportCountry;
    @FXML
    private TextField tfpassportIssuedAt;
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
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        if (p != null)
        {
            tfpassportNumber.setText(p.getPassportNumber());
            tfpassportCountry.setText(p.getPassportCountry());
            tfpassportIssuedAt.setText(p.getPassportIssuedAt());
            dpPassportExpiryDate.setValue(Util.convertDateToLocalDate(p.getPassportExpiryDate()));
            dpPassportIssueDate.setValue(Util.convertDateToLocalDate(p.getPassportIssueDate()));

            //if there is a passport registered already
            if (p.getPassportNumber() != null)
            {
                //blocks edition and enables archive button
                bClear.setDisable(false);
                bRegister.setDisable(true);

                tfpassportNumber.setEditable(false);
                tfpassportCountry.setEditable(false);
                tfpassportIssuedAt.setEditable(false);
                dpPassportExpiryDate.setDisable(true);
                dpPassportIssueDate.setDisable(true);
            }
            else
            {
                //unlocks edition and enables select scan button
                bClear.setDisable(true);
                bRegister.setDisable(false);

                tfpassportNumber.setEditable(true);
                tfpassportCountry.setEditable(true);
                tfpassportIssuedAt.setEditable(true);
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

            //archive passport scans
            if (p.getPassportScanSet() != null)
            {
                //archives the scan files
                CtrFileOperation.archiveAllPassportScans(p);
                //clears the DB entries for the Extra Scans
                ctrGUIMain.getCtrMain().getCtrPassportScan().removeExtraScans(p.getPassportScanSet());
            }

            p.setPassportNumber(null);
            p.setPassportCountry(null);
            p.setPassportIssuedAt(null);
            p.setPassportExpiryDate(null);
            p.setPassportIssueDate(null);

            opStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            if (opStatus == 0)
            {
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
            p.setPassportCountry(tfpassportCountry.getText());
            p.setPassportIssuedAt(tfpassportIssuedAt.getText());
            p.setPassportIssueDate(Util.convertLocalDateToDate(dpPassportIssueDate.getValue()));
            p.setPassportExpiryDate(Util.convertLocalDateToDate(dpPassportExpiryDate.getValue()));
            operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);

            if (operationStatus == 0)
            {
                CtrAlertDialog.infoDialog("Passport Added/Renewed", "The passport data was sucessfully updated.");
                fillData(p);
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
                && (!tfpassportCountry.getText().isEmpty())
                && (!tfpassportIssuedAt.getText().isEmpty()));
    }
}
