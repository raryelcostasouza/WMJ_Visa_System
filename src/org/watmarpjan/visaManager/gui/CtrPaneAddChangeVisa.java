/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
public class CtrPaneAddChangeVisa extends AbstractFormSelectExtraScan implements IFormMonasticProfile
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
    private Button bSelectScan;
    @FXML
    private Button bArchive;
    @FXML
    private Button bRegister;

    @Override
    public void init()
    {
        cbVisaType.getItems().addAll(AppConstants.LIST_VISA_TYPES);
    }

    @FXML
    void actionArchive(ActionEvent ae)
    {
        int opStatus1, opStatus2, opStatus3;
        boolean confirmation;
        Profile p;
        File fScanVisa, fScanLastVisaExt, fScanAfterUpdate;
        PassportScan psVisaPage, psLastVisaExt;
        ArrayList<PassportScan> listPassportScanToDelete;

        confirmation = CtrAlertDialog.confirmationDialog("Archive", "The visa information (and its extensions) will be cleared and the scans archived. \nDo you want to continue?");
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            psVisaPage = ctrGUIMain.getCtrMain().getCtrPassportScan().getScanVisa(p.getIdprofile());
            psLastVisaExt = ctrGUIMain.getCtrMain().getCtrPassportScan().getScanLastVisaExt(p.getIdprofile());

            fScanVisa = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psVisaPage);
            fScanLastVisaExt = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psLastVisaExt);

            /*
             * For proper consistency 2 operations need to be successful
             * 1 - move the scan files to the archive
             * 2 - update the Database information
             *
             * Implementation:
             * 1) Copy the scan file to the archive
             * 2) If the copy is successful, then update the DB info
             * 3) If the db update is successful then deletes the original scan file
             *
             */
            //first copy the scan file to the archive
            opStatus1 = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, fScanVisa);

            //if there is a last visa extension scan file, and if it is not in the same file of the visa page scan
            if ((psLastVisaExt != null) && !psLastVisaExt.isContentVisaScan())
            {
                //archive the last visa ext scan as well
                opStatus2 = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, fScanLastVisaExt);
            } else
            {
                opStatus2 = 0;
            }

            //if the archiving operation is successfull
            if ((opStatus1 == 0) && (opStatus2 == 0))
            {
                listPassportScanToDelete = new ArrayList<>();

                //if the visa page scan contains ONLY the visa scan
                if (psVisaPage.isContentVisaScan()
                        && (!psVisaPage.isContentArriveStamp())
                        && (!psVisaPage.isContentLastVisaExt()))
                {
                    //need to delete the original scan file
                    listPassportScanToDelete.add(psVisaPage);
                }

                //if there is a last visa extension scan
                if (psLastVisaExt != null)
                {
                    listPassportScanToDelete.add(psLastVisaExt);
                }

                //clear the visa info and all visa extensions under it
                opStatus3 = ctrGUIMain.getCtrMain().getCtrVisa().clearVisaInfoForProfile(p, listPassportScanToDelete, psVisaPage);

                //if the update of the entry info is successful 
                //deletes the original scan file
                if (opStatus3 == 0)
                {
                    //if the visa page is on the list to be deleted
                    //scenario where the visa page scan contains ONLY the visa scan
                    if (listPassportScanToDelete.contains(psVisaPage))
                    {
                        CtrFileOperation.deleteFile(fScanVisa);
                    } else
                    {
                        //otherwise need to rename the scan file
                        fScanAfterUpdate = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psVisaPage);
                        CtrFileOperation.renameFile(fScanVisa, fScanAfterUpdate);
                    }

                    //if there is a last visa extension scan
                    if (fScanLastVisaExt != null)
                    {
                        CtrFileOperation.deleteFile(fScanLastVisaExt);
                    }

                    //refresh the profile because the passportScanList and visaExtensionList was updated
                    ctrGUIMain.getCtrMain().getCtrProfile().refreshProfile(p);
                    fillData(p);
                    CtrAlertDialog.infoDialog("Archived successfully", "The previous departure scan was archived successfully.");
                }

            }
        }
    }

    @FXML
    void actionRegister(ActionEvent ae)
    {
        Profile p;
        PassportScan psVisaScan;
        int operationStatus1, operationStatus2;

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

            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            operationStatus1 = super.processSelectedScan(p, SCAN_TYPE_VISA);
            psVisaScan = super.getPassportScan();

            if (operationStatus1 == 0)
            {
                //if the scan copy is sucessful
                p.setVisaNumber(tfVisaNumber.getText());
                p.setVisaType(cbVisaType.getValue());
                p.setVisaExpiryDate(Util.convertLocalDateToDate(dpVisaExpiryDate.getValue()));
                p.setNext90DayNotice(Util.convertLocalDateToDate(dpNext90DayNotice.getValue()));
                operationStatus2 = ctrGUIMain.getCtrMain().getCtrVisa().addNewVisaForProfile(p, psVisaScan);

                //if the DB update is successful
                if (operationStatus2 == 0)
                {
                    CtrAlertDialog.infoDialog("Visa info registered", "The new visa information was successfully registered.");
                    resultSelectScan = null;
                    fillData(p);
                } else
                {
                    //if the DB update fails has to undo the Operation 1
                    super.undoProcessingSelectedScan(p);
                }
            }
        } else
        {
            CtrAlertDialog.errorDialog("Please fill out ALL the visa fields before registering");
        }
    }

    @Override
    public void fillData(Profile p)
    {
        PassportScan psVisaScan;

        psVisaScan = ctrGUIMain.getCtrMain().getCtrPassportScan().getScanVisa(p.getIdprofile());
        super.fillData(psVisaScan);

        tfVisaNumber.setText(p.getVisaNumber());
        cbVisaType.setValue(p.getVisaType());
        dpVisaExpiryDate.setValue(Util.convertDateToLocalDate(p.getVisaExpiryDate()));
        dpNext90DayNotice.setValue(Util.convertDateToLocalDate(p.getNext90DayNotice()));

        loadIMGScan(p.getNickname(), p.getPassportNumber(), psVisaScan);

        if (p.getVisaNumber() != null)
        {
            bArchive.setDisable(false);
            bSelectScan.setDisable(true);
            bRegister.setDisable(true);

            tfVisaNumber.setEditable(false);
            cbVisaType.setDisable(true);
            dpVisaExpiryDate.setDisable(true);
            dpNext90DayNotice.setDisable(true);
            tfPsptPageNumber.setEditable(false);

        } else
        {
            bArchive.setDisable(true);
            bSelectScan.setDisable(false);
            bRegister.setDisable(false);

            tfVisaNumber.setEditable(true);
            cbVisaType.setDisable(false);
            dpVisaExpiryDate.setDisable(false);
            dpNext90DayNotice.setDisable(false);
            tfPsptPageNumber.setEditable(true);
        }
    }

    private void loadIMGScan(String nickName, String passportNumber, PassportScan psVisaScan)
    {
        File fVisaScan;

        fVisaScan = AppFiles.getExtraScan(nickName, passportNumber, psVisaScan);
        ImgUtil.loadImageView(ivPreview, ImgUtil.IMG_TYPE_PASSPORT, fVisaScan);
    }

    private boolean validateFields()
    {
        return ((!tfVisaNumber.getText().isEmpty())
                && (dpVisaExpiryDate.getValue() != null)
                && (dpNext90DayNotice.getValue() != null)
                && (cbVisaType.getValue() != null)
                && (!tfPsptPageNumber.getText().isEmpty()));
    }

}
