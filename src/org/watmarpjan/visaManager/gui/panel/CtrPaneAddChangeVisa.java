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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerExportPDF;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneAddChangeVisa extends AChildPaneControllerExportPDF implements IFormMonasticProfile
{

    @FXML
    private TextField tfVisaNumber;

    @FXML
    private ComboBox<String> cbVisaType;

    @FXML
    private DatePicker dpVisaExpiryDate;

    @FXML
    private DatePicker dpNext90DayNotice;

    @FXML
    private Button bClear;
    @FXML
    private Button bRegister;

    @FXML
    private ComboBox<String> cbLetterType;

    @FXML
    private DatePicker dpDepartureDateFromThai;

    @FXML
    private ComboBox<String> cbEmbassy;

    @FXML
    private TextField tfMonasticPhoneAbroad;
    
    @FXML
    private TextField tfMonasticAddr1;
    
    @FXML
    private TextField tfMonasticAddr2;
    
    @FXML
    private TextField tfMonasticAddr3;
    
    @FXML
    private TextField tfMonasticAddr4;

    @FXML
    private BorderPane bpLetterFields;

    @FXML
    private TitledPane tbExtraFields;

    @FXML
    private VBox vbExtraFields;

    @FXML
    private GridPane gpDeparture;

    @FXML
    private GridPane gpEmbassy;

    @FXML
    private TitledPane tbMonasticAddr;

    @FXML
    private GridPane gpMonasticPhone;

    @Override
    public void init()
    {
        super.init();
        cbVisaType.getItems().addAll(AppConstants.LIST_VISA_TYPES);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNext90DayNotice);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpVisaExpiryDate);

        tfVisaNumber.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue != null)
            {
                tfVisaNumber.setText(newValue.toUpperCase());
            }
        });
    }

    private void clearFieldsVisaLetters()
    {
        cbLetterType.setValue(null);
        dpDepartureDateFromThai.setValue(null);
        cbEmbassy.setValue(null);
        tfMonasticAddr1.setText("");
        tfMonasticAddr2.setText("");
        tfMonasticAddr3.setText("");
        tfMonasticAddr4.setText("");
        tfMonasticPhoneAbroad.setText("");

        bpLetterFields.setCenter(null);
    }

    private void loadContentsCBLetter(String[] letterList)
    {
        cbLetterType.getItems().addAll(letterList);
    }

    @FXML
    void actionClear(ActionEvent ae)
    {
        int opStatus;
        boolean confirmation;
        MonasticProfile p;

        confirmation = CtrAlertDialog.confirmationDialog("Clear", "The visa information (and its extensions) will be cleared. \nDo you want to continue?");
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            opStatus = ctrGUIMain.getCtrMain().getCtrVisa().clearVisaInfoForProfile(p);
            if (opStatus == 0)
            {
                fillData(p);
                CtrAlertDialog.infoDialog("Cleared successfully", "The previous visa information was cleared successfully.");
            }
        }
    }

    @FXML
    void actionRegister(ActionEvent ae)
    {
        MonasticProfile p;
        int operationStatus;

        if (validateFields())
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            //if the scan copy is sucessful
            p.setVisaNumber(tfVisaNumber.getText());
            p.setVisaType(cbVisaType.getValue());
            p.setVisaExpiryDate(Util.convertLocalDateToDate(dpVisaExpiryDate.getValue()));
            p.setNext90DayNotice(Util.convertLocalDateToDate(dpNext90DayNotice.getValue()));
            operationStatus = ctrGUIMain.getCtrMain().getCtrVisa().addNewVisaForProfile(p);

            //if the DB update is successful
            if (operationStatus == 0)
            {
                CtrAlertDialog.infoDialog("Visa info registered", "The new visa information was successfully registered.");
                fillData(p);
            }

        }
        else
        {
            CtrAlertDialog.errorDialog("Please fill out ALL the visa fields before registering");
        }
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        clearFieldsVisaLetters();
        if (p != null)
        {
            if (p.getSamaneraOrdDate() != null || p.getBhikkhuOrdDate() != null)
            {
                loadContentsCBLetter(AppConstants.LIST_LETTER_MONASTIC);
            }
            else
            {
                loadContentsCBLetter(AppConstants.LIST_LETTER_LAYPERSON);
            }

            tfVisaNumber.setText(p.getVisaNumber());
            cbVisaType.setValue(p.getVisaType());
            dpVisaExpiryDate.setValue(Util.convertDateToLocalDate(p.getVisaExpiryDate()));
            dpNext90DayNotice.setValue(Util.convertDateToLocalDate(p.getNext90DayNotice()));

            if (p.getVisaNumber() != null)
            {
                bClear.setDisable(false);
                bRegister.setDisable(true);

                tfVisaNumber.setEditable(false);
                cbVisaType.setDisable(true);
                dpVisaExpiryDate.setDisable(true);
                dpNext90DayNotice.setDisable(true);

            }
            else
            {
                bClear.setDisable(true);
                bRegister.setDisable(false);

                tfVisaNumber.setEditable(true);
                cbVisaType.setDisable(false);
                dpVisaExpiryDate.setDisable(false);
                dpNext90DayNotice.setDisable(false);
            }
        }
    }

    private boolean validateFields()
    {
        return ((!tfVisaNumber.getText().isEmpty())
                && (dpVisaExpiryDate.getValue() != null)
                && (dpNext90DayNotice.getValue() != null)
                && (cbVisaType.getValue() != null));
    }

    @FXML
    void actionPreviewFormTM86VisaChange(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM86VisaChange(), p, CtrPDF.OPTION_PREVIEW_FORM, false);
    }

    @FXML
    void listenerCBLetter(ActionEvent ae)
    {
        String strLetterSelected;

        strLetterSelected = cbLetterType.getValue();

        if (strLetterSelected != null)
        {
            switch (strLetterSelected)
            {
                 case "Layperson Abroad (Embassy) TH":
                     break;
                
                case "Monastic Abroad (Embassy) TH":
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    vbExtraFields.getChildren().add(gpDeparture);
                    break;
                case "Monastic Abroad (Embassy) EN":
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    break;
                case "Monastic Abroad (Samnak Put)":
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    vbExtraFields.getChildren().add(tbMonasticAddr);
                    vbExtraFields.getChildren().add(gpMonasticPhone);
                    vbExtraFields.getChildren().add(gpDeparture);
                    break;
            }
        }
    }

    @FXML
    void actionGenerateLetter(ActionEvent ae)
    {

    }
//    @FXML
//    void actionPrintFormTM86VisaChange(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM86VisaChange(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
}
