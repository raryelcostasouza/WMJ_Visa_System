/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppFileNames;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.Init;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.control.CtrForm;
import org.watmarpjan.visaManager.model.EntryUpdate90DayNotice;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPane90DayNotice extends AbstractChildPaneController implements IFormMonasticProfile
{

    @FXML
    private DatePicker dpNext90DayNotice;

    @FXML
    private TableView<EntryUpdate90DayNotice> tvDueNotice90Day;

    @FXML
    private TableColumn tcSelected;

    @FXML
    private DatePicker dpValidDatesNoticeOnline;

    @FXML
    private TextField tfPassportNumber;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfGivenName;
    @FXML
    private TextField tfMiddleName;
    @FXML
    private DatePicker dpDateOfBirth;
    @FXML
    private TextField tfNationality;

    @FXML
    private TextField tfArrivalTm6Number;
    @FXML
    private DatePicker dpArrivalDate;
    @FXML
    private RadioButton rbTravelBy;

    @FXML
    private TextField tfBuildingName;
    @FXML
    private TextField tfAddrNumber;
    @FXML
    private TextField tfAddrSoiRoad;
    @FXML
    private TextField tfAddrStateProvince;
    @FXML
    private TextField tfAddrCityAmphur;
    @FXML
    private TextField tfAddrDistrictTambon;

    @FXML
    private TextField tfReceiptNumber;

    @FXML
    private DatePicker dpReceiptDate;

    @FXML
    private TextField tfReceiptFilePath;

    @FXML
    private RadioButton rbReceiptPending;

    @FXML
    private RadioButton rbReceiptApproved;

    @FXML
    private ToggleGroup tgReceiptStatus;

    @FXML
    private TableView<EntryReceiptOnlineNotice> tvReceipts;

    private ArrayList<TextField> alTextFields;

    @Override
    public void init()
    {
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNext90DayNotice);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpDateOfBirth);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpArrivalDate);

        tcSelected.setCellValueFactory(new PropertyValueFactory<EntryUpdate90DayNotice, Boolean>("selected"));
        final Callback<TableColumn<EntryUpdate90DayNotice, Boolean>, TableCell<EntryUpdate90DayNotice, Boolean>> defaultCellFactory = CheckBoxTableCell.forTableColumn(tcSelected);
        tcSelected.setCellFactory(CheckBoxTableCell.forTableColumn(tcSelected));

        tvDueNotice90Day.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("profileNickname"));
        tvDueNotice90Day.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("strDueDate"));

        alTextFields = new ArrayList<>();
        alTextFields.add(tfPassportNumber);
        alTextFields.add(tfSurname);
        alTextFields.add(tfGivenName);
        alTextFields.add(tfMiddleName);
        alTextFields.add(tfNationality);

        alTextFields.add(tfArrivalTm6Number);

        alTextFields.add(tfBuildingName);
        alTextFields.add(tfAddrStateProvince);
        alTextFields.add(tfAddrCityAmphur);
        alTextFields.add(tfAddrDistrictTambon);
        alTextFields.add(tfAddrSoiRoad);
        alTextFields.add(tfAddrNumber);
    }

    @FXML
    public void actionLinkThaiImmigration(ActionEvent ae)
    {
        Init.HOST_SERVICES.showDocument("https://extranet.immigration.go.th/fn90online");
    }

    public void fillData()
    {
        ArrayList<EntryUpdate90DayNotice> al;

        al = ctrGUIMain.getCtrMain().getCtrProfile().loadListUpdate90DayNotice();

        tvDueNotice90Day.getItems().clear();
        tvDueNotice90Day.getItems().addAll(al);

        dpNext90DayNotice.setValue(LocalDate.now().plusDays(90));
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        Monastery monastery;

        fillData();

        //dpValidDatesNoticeOnline.setValue(Util.convertDateToLocalDate(p.getNext90dayNotice()));
        tfPassportNumber.setText(p.getPassportNumber());
        tfSurname.setText(p.getLastName());
        tfMiddleName.setText(p.getMiddleName());
        tfGivenName.setText(p.getMonasticName());
        dpDateOfBirth.setValue(Util.convertDateToLocalDate(p.getBirthDate()));
        tfNationality.setText(p.getNationality());

        tfArrivalTm6Number.setText(p.getArrivalCardNumber());
        dpArrivalDate.setValue(Util.convertDateToLocalDate(p.getArrivalLastEntryDate()));
        rbTravelBy.setText(p.getArrivalTravelBy());

        if (p.getMonasteryResidingAt() != null)
        {
            monastery = p.getMonasteryResidingAt();
            tfBuildingName.setText(monastery.getMonasteryName());
            tfAddrNumber.setText(monastery.getAddrNumber());
            tfAddrSoiRoad.setText(monastery.getAddrRoad());
            tfAddrStateProvince.setText(monastery.getAddrJangwat());
            tfAddrCityAmphur.setText(monastery.getAddrAmpher());
            tfAddrDistrictTambon.setText(monastery.getAddrTambon());
        }

        for (TextField tf : alTextFields)
        {
            if (tf.getText() != null)
            {
                tf.setText(tf.getText().toUpperCase(Locale.ENGLISH));
            }
        }

    }

    @FXML
    void actionPreviewForm90day(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrForm().fillForm(AppFiles.getFormTM47Notice90Day(), p, CtrForm.OPTION_PREVIEW_FORM);
    }

    @FXML
    void actionPrintForm(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrForm().fillForm(AppFiles.getFormTM47Notice90Day(), p, CtrForm.OPTION_PRINT_FORM);
    }

    @FXML
    void actionUpdate90Day(ActionEvent ae)
    {
        MonasticProfile p;
        LocalDate ldNextNotice;
        Date dNextNotice;

        for (EntryUpdate90DayNotice e : tvDueNotice90Day.getItems())
        {
            if (e.isSelected())
            {
                ldNextNotice = dpNext90DayNotice.getValue();
                dNextNotice = org.watmarpjan.visaManager.util.Util.convertLocalDateToDate(ldNextNotice);

                p = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileByID(e.getIdProfile());
                p.setNext90DayNotice(dNextNotice);
                ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            }
        }
        fillData();
    }

    @FXML
    void actionButtonSelectReceiptPDF(ActionEvent ae)
    {
        File fReceipt;
        fReceipt = CtrFileOperation.selectFile("Select Receipt PDF File", CtrFileOperation.FILE_CHOOSER_TYPE_PDF);

        if (fReceipt != null)
        {
            tfReceiptFilePath.setText(fReceipt.toString());
        }
    }

    @FXML
    void actionButtonAddReceipt(ActionEvent ae)
    {
        File fReceipt, destFile;
        MonasticProfile p;
        String receiptStatus;
        int opStatus;

        if (validateFieldsReceipt())
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            fReceipt = new File(tfReceiptFilePath.getText());
            if (rbReceiptApproved.isSelected())
            {
                receiptStatus = AppFileNames.RECEIPT_STATUS_APPROVED;
            } else
            {
                receiptStatus = AppFileNames.RECEIPT_STATUS_PENDING;
            }
            destFile = AppFiles.getReceiptOnline90d(p.getNickname(), tfReceiptNumber.getText(), dpReceiptDate.getValue(), receiptStatus);
            System.out.println(destFile.toString());
            opStatus = CtrFileOperation.copyOperation(fReceipt, destFile);
            if (opStatus == 0)
            {
                clearAddReceiptFields();
                CtrAlertDialog.infoDialog("Receipt saved successfully", "The receipt was successfully saved.");
            }
        } else
        {
            CtrAlertDialog.warningDialog("Please fill out all fields before adding a receipt.");
        }

    }

    private void clearAddReceiptFields()
    {
        tfReceiptNumber.setText("");
        dpReceiptDate.setValue(null);
        rbReceiptApproved.setSelected(false);
        rbReceiptPending.setSelected(false);
        tfReceiptFilePath.setText("");
    }

    private boolean validateFieldsReceipt()
    {
        return ((!tfReceiptNumber.getText().isEmpty())
                && (dpReceiptDate.getValue() != null)
                && (rbReceiptApproved.isSelected() || rbReceiptPending.isSelected())
                && (!tfReceiptFilePath.getText().isEmpty()));
    }
}
