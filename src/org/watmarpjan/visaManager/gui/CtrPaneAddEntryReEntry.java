/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
import org.watmarpjan.visaManager.model.hibernate.Profile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneAddEntryReEntry extends AbstractFormSelectExtraScan implements IFormMonasticProfile
{

    @FXML
    private TextField tfTM6Number;

    @FXML
    private DatePicker dpLastEntry;

    @FXML
    private TextField tfTravelFrom;

    @FXML
    private ComboBox<String> cbTravelBy;

    @FXML
    private TextField tfPortOfEntry;

    @FXML
    private Button bArchive;
    @FXML
    private Button bRegister;

    @FXML
    private Button bSelectDepartureCard;

    @FXML
    private Button bSelectArriveStamp;

    @FXML
    private ImageView ivDepartureCardScan;

    private File fSelectedDepartureCard;

    @Override
    public void init()
    {
        cbTravelBy.getItems().addAll(AppConstants.LIST_TRAVEL_BY);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpLastEntry);
    }

    @Override
    public void fillData(Profile p)
    {
        PassportScan psArriveStamp;

        psArriveStamp = ctrGUIMain.getCtrMain().getCtrPassportScan().getScanArriveStamp(p.getIdprofile());
        super.fillData(psArriveStamp);

        tfTM6Number.setText(p.getArrivalCardNumber());
        dpLastEntry.setValue(Util.convertDateToLocalDate(p.getArrivalLastEntryDate()));
        tfTravelFrom.setText(p.getArrivalTravelFrom());
        cbTravelBy.setValue(p.getArrivalTravelBy());
        tfPortOfEntry.setText(p.getArrivalPortOfEntry());
        loadIMGScan(p);

        if (p.getArrivalCardNumber() != null)
        {
            //blocks edition and enables archive button
            bArchive.setDisable(false);
            bSelectArriveStamp.setDisable(true);
            bSelectDepartureCard.setDisable(true);
            bRegister.setDisable(true);

            tfTM6Number.setEditable(false);
            dpLastEntry.setDisable(true);
            tfTravelFrom.setEditable(false);
            cbTravelBy.setDisable(true);
            tfPortOfEntry.setEditable(false);
        } else
        {
            bArchive.setDisable(true);
            bRegister.setDisable(false);
            bSelectArriveStamp.setDisable(false);
            bSelectDepartureCard.setDisable(false);

            tfTM6Number.setEditable(true);
            dpLastEntry.setDisable(false);
            tfTravelFrom.setEditable(true);
            cbTravelBy.setDisable(false);
            tfPortOfEntry.setEditable(true);
        }

    }

    @FXML
    void actionArchive(ActionEvent ae)
    {
        boolean confirmation;
        Profile p;
        int operationStatus1, operationStatus2, operationStatus3;
        File fScanDepartureCard;
        File fScanArriveStamp;
        File fScanAfterUpdate;
        PassportScan psArriveStamp;

        confirmation = CtrAlertDialog.confirmationDialog("Archive", "The arrival information will be cleared and the scans archived. \nDo you want to continue?");
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            psArriveStamp = ctrGUIMain.getCtrMain().getCtrPassportScan().getScanArriveStamp(p.getIdprofile());

            fScanDepartureCard = AppFiles.getScanDepartureCard(p.getNickname());
            fScanArriveStamp = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psArriveStamp);

            /*
             * For proper consistency 2 operations need to be successful
             * 1 - move the scan file to the archive
             * 2 - update the Database information
             *
             * Implementation:
             * 1) Copy the scan file to the archive
             * 2) If the copy is successful, then update the DB info
             * 3) If the db update is successful then deletes the original files
             *
             */
            //first copy the scan file to the archive
            operationStatus1 = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, fScanDepartureCard);
            operationStatus2 = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, fScanArriveStamp);
            if (operationStatus1 == 0 && operationStatus2 == 0)
            {
                //if successful clear the entry info
                p.setArrivalCardNumber(null);
                p.setArrivalLastEntryDate(null);
                p.setArrivalTravelFrom(null);
                p.setArrivalTravelBy(null);
                p.setArrivalPortOfEntry(null);

                //if the scan file is shared with other scans
                //need to remove the flag for contentArriveStamp
                if (psArriveStamp.isContentLastVisaExt() || psArriveStamp.isContentVisaScan())
                {
                    psArriveStamp.setContentArriveStamp(false);
                    //DB update
                    operationStatus3 = ctrGUIMain.getCtrMain().getCtrPassportScan().updateAndRemoveScan(null);
                } else
                {
                    //DB update
                    operationStatus3 = ctrGUIMain.getCtrMain().getCtrPassportScan().updateAndRemoveScan(psArriveStamp);
                }

                //if the DB update is successful 
                if (operationStatus3 == 0)
                {
                    //deletes the original departure card scan file
                    CtrFileOperation.deleteFile(fScanDepartureCard);

                    //if the arrive stamp scan does not include the visa or extension scan
                    if (!psArriveStamp.isContentLastVisaExt() && !psArriveStamp.isContentVisaScan())
                    {
                        //it can be deleted as well
                        CtrFileOperation.deleteFile(fScanArriveStamp);
                    } else
                    {
                        //if it is shared with other scans, need to rename the file
                        //removing the suffix -ArriveStamp from the file
                        fScanAfterUpdate = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psArriveStamp);
                        CtrFileOperation.renameFile(fScanArriveStamp, fScanAfterUpdate);
                    }
                    CtrAlertDialog.infoDialog("Archived successfully", "The previous departure scan was archived successfully.");

                    fillData(p);
                }
            }

        }
    }

    @FXML
    void actionSelectFile(ActionEvent ae)
    {
        fSelectedDepartureCard = CtrFileOperation.selectFile("Select Departure Card Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);
        if (fSelectedDepartureCard != null)
        {
            ImgUtil.loadImageView(ivDepartureCardScan, ImgUtil.IMG_TYPE_PASSPORT, fSelectedDepartureCard);
        }
    }

    @FXML
    void actionRegister(ActionEvent ae)
    {
        Profile p;
        PassportScan psArriveStamp;
        int operationStatus1_1, operationStatus1_0, operationStatus2;

        if (validateFields() && fSelectedDepartureCard != null && resultSelectScan != null)
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

            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            operationStatus1_0 = CtrFileOperation.copyOperation(fSelectedDepartureCard, AppFiles.getScanDepartureCard(p.getNickname()));
            operationStatus1_1 = super.processSelectedScan(p, SCAN_TYPE_ARRIVE_STAMP);
            psArriveStamp = super.getPassportScan();

            if ((operationStatus1_0 == 0) && (operationStatus1_1 == 0))
            {
                //if the scan processing is sucessful
                p.setArrivalCardNumber(tfTM6Number.getText());
                p.setArrivalLastEntryDate(Util.convertLocalDateToDate(dpLastEntry.getValue()));
                p.setArrivalTravelFrom(tfTravelFrom.getText());
                p.setArrivalTravelBy(cbTravelBy.getValue());
                p.setArrivalPortOfEntry(tfPortOfEntry.getText());

                operationStatus2 = ctrGUIMain.getCtrMain().getCtrProfile().addEntryReentry(p, psArriveStamp);

                //if the DB update is successful
                if (operationStatus2 == 0)
                {
                    CtrAlertDialog.infoDialog("Entry/Re-entry registered", "The entry/re-entry information was successfully registered.");
                    fSelectedDepartureCard = null;
                    resultSelectScan = null;
                    ctrGUIMain.getCtrMain().getCtrProfile().refreshProfile(p);
                    fillData(p);
                } else
                {
                    //if the DB update fails has to delete the scan file
                    CtrFileOperation.deleteFile(AppFiles.getScanDepartureCard(p.getNickname()));
                    super.undoProcessingSelectedScan(p);
                }
            }
        } else
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
    void actionIMGClicked(MouseEvent me)
    {
        Profile p;
        File fImg;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        fImg = AppFiles.getScanDepartureCard(p.getNickname());
        ImgUtil.openClickedIMG(fImg);
    }

    private void loadIMGScan(Profile p)
    {
        File fDepartureCard;
        File fArriveStamp;
        PassportScan psArriveStamp;

        fDepartureCard = AppFiles.getScanDepartureCard(p.getNickname());
        psArriveStamp = ctrGUIMain.getCtrMain().getCtrPassportScan().getScanArriveStamp(p.getIdprofile());
        fArriveStamp = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psArriveStamp);

        ImgUtil.loadImageView(ivDepartureCardScan, ImgUtil.IMG_TYPE_PASSPORT, fDepartureCard);
        ImgUtil.loadImageView(ivPreview, ImgUtil.IMG_TYPE_PASSPORT, fArriveStamp);
    }
}
