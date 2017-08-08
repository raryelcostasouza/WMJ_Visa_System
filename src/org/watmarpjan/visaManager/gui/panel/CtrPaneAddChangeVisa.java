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
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerExportPDF;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.hibernate.Embassy;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;
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

    private void initTabLetters(String[] letterList)
    {
        GUIUtil.loadContentComboboxGeneric(cbEmbassy, ctrGUIMain.getCtrMain().getCtrEmbassy().loadList());
        cbLetterType.getItems().clear();
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
                initTabLetters(AppConstants.LIST_LETTER_MONASTIC);
            }
            else
            {
                initTabLetters(AppConstants.LIST_LETTER_LAYPERSON);
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
                case "Layperson Abroad - Embassy) TH":
                    break;

                case "Monastic Abroad - Embassy":
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    vbExtraFields.getChildren().add(gpDeparture);
                    break;
                case "Monastic Abroad - Embassy EN":
                    bpLetterFields.setCenter(tbExtraFields);
                    vbExtraFields.getChildren().clear();
                    vbExtraFields.getChildren().add(gpEmbassy);
                    break;
                case "Monastic Abroad - Samnak Put":
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
        String strLetterSelected, strSelectedEmbassy;
        String[][] data2CSV = new String[2][27];
        MonasticProfile p;
        Embassy e;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        strLetterSelected = cbLetterType.getValue();
        strSelectedEmbassy = cbEmbassy.getValue();
        e = ctrGUIMain.getCtrMain().getCtrEmbassy().loadByName(strSelectedEmbassy);

        data2CSV[0][0] = "titleEnglish";
        data2CSV[1][0] = ProfileUtil.getTitleEN(p);

        data2CSV[0][1] = "titleThai";
        data2CSV[1][1] = ProfileUtil.getTitleTH2(p);

        data2CSV[0][2] = "OrdinationTypeThai";
        data2CSV[1][2] = ProfileUtil.getOrdinationType(p);

        data2CSV[0][3] = "OrdinationStatusThai";
        data2CSV[1][3] = ProfileUtil.getTitleTH(p);

        data2CSV[0][4] = "OrdinationStatusEnglish";
        data2CSV[1][4] = ProfileUtil.getTitleEN2(p);

        data2CSV[0][5] = "name";
        data2CSV[1][5] = p.getMonasticName();

        data2CSV[0][6] = "middleName";
        data2CSV[1][6] = p.getMiddleName();

        data2CSV[0][7] = "lastName";
        data2CSV[1][7] = p.getLastName();

        data2CSV[0][8] = "paliNameEnglish";
        data2CSV[1][8] = p.getPaliNameEnglish();

        data2CSV[0][9] = "nationality";
        data2CSV[1][9] = p.getNationality();

        data2CSV[0][10] = "passportNumber";
        data2CSV[1][10] = p.getPassportNumber();

        data2CSV[0][11] = "addressLine1";
        data2CSV[1][11] = tfMonasticAddr1.getText();

        data2CSV[0][12] = "addressLine2";
        data2CSV[1][12] = tfMonasticAddr2.getText();

        data2CSV[0][13] = "addressLine3";
        data2CSV[1][13] = tfMonasticAddr3.getText();

        data2CSV[0][14] = "addressLine4";
        data2CSV[1][14] = tfMonasticAddr4.getText();

        data2CSV[0][15] = "contactEmail";
        data2CSV[1][15] = p.getEmail();

        data2CSV[0][16] = "contactPhone";
        data2CSV[1][16] = tfMonasticPhoneAbroad.getText();

        data2CSV[0][17] = "nameEmbassyThai";
        data2CSV[1][17] = e.getNameTh();

        data2CSV[0][18] = "nameEmbassyEnglish";
        data2CSV[1][18] = e.getNameEn();

        data2CSV[0][19] = "countryEmbassyThai";
        data2CSV[1][19] = e.getCountry();

        data2CSV[0][20] = "addressEmbassyLine1";
        data2CSV[1][20] = e.getAddressLine1();

        data2CSV[0][21] = "addressEmbassyLine2";
        data2CSV[1][21] = e.getAddressLine2();

        data2CSV[0][22] = "addressEmbassyLine3";
        data2CSV[1][22] = e.getAddressLine3();

        data2CSV[0][23] = "addressEmbassyLine4";
        data2CSV[1][23] = e.getAddressLine4();

        data2CSV[0][24] = "departureDateThai";
        data2CSV[1][24] = dpDepartureDateFromThai.getValue().toString();

        data2CSV[0][25] = "firstArrivalDateThai";
        data2CSV[1][25] = p.getFirstEntryDate().toString();

        data2CSV[0][26] = "destinationPath";
        data2CSV[1][26] = AppPaths.getPathToProfileLetters(p.getNickname()).toAbsolutePath().toString();

        CtrFileOperation.generateLetter(strLetterSelected, p, data2CSV);
    }
//    @FXML
//    void actionPrintFormTM86VisaChange(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormTM86VisaChange(), p, CtrPDF.OPTION_PRINT_FORM, false);
//    }
}
