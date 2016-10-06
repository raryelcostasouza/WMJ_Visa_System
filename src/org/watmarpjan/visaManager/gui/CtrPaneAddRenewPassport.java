/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
import org.watmarpjan.visaManager.model.hibernate.Profile;
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
    private TextField tfpassportIssuedAt;
    @FXML
    private DatePicker dpPassportExpiryDate;
    @FXML
    private DatePicker dpPassportIssueDate;
    
    @FXML
    private Button bPassportScan;
    @FXML
    private Button bArchive;
    @FXML
    private Button bRegister;
    
    @FXML
    private ImageView ivPassportScan;
    
    private File fScanSelected;
    
    @Override
    public void fillData(Profile p)
    {
        tfpassportNumber.setText(p.getPassportNumber());
        tfpassportIssuedAt.setText(p.getPassportIssuedAt());
        dpPassportExpiryDate.setValue(Util.convertDateToLocalDate(p.getPassportExpiryDate()));
        dpPassportIssueDate.setValue(Util.convertDateToLocalDate(p.getPassportIssueDate()));
        loadIMGScan(p.getNickname(), p.getPassportNumber());

        //if there is a passport registered already
        if (p.getPassportNumber() != null)
        {
            //blocks edition and enables archive button
            bArchive.setDisable(false);
            bPassportScan.setDisable(true);
            bRegister.setDisable(true);
            
            tfpassportNumber.setEditable(false);
            tfpassportIssuedAt.setEditable(false);
            dpPassportExpiryDate.setDisable(true);
            dpPassportIssueDate.setDisable(true);
        } else
        {
            //unlocks edition and enables select scan button
            bArchive.setDisable(true);
            bPassportScan.setDisable(false);
            bRegister.setDisable(false);
            
            tfpassportNumber.setEditable(true);
            tfpassportIssuedAt.setEditable(true);
            dpPassportExpiryDate.setDisable(false);
            dpPassportIssueDate.setDisable(false);
        }
    }
    
    @FXML
    void actionSelectFile(ActionEvent ae)
    {
        fScanSelected = CtrFileOperation.selectFile("Select Passport Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);
        if (fScanSelected != null)
        {
            ImgUtil.loadImageView(ivPassportScan, ImgUtil.IMG_TYPE_PASSPORT, fScanSelected);
        }
    }
    
    private void loadIMGScan(String nickName, String passportNumber)
    {
        File fPassportScan;
        
        fPassportScan = AppFiles.getScanPassportFirstPage(nickName, passportNumber);
        ImgUtil.loadImageView(ivPassportScan, ImgUtil.IMG_TYPE_PASSPORT, fPassportScan);
    }
    
    @FXML
    void actionIMGClicked(MouseEvent me)
    {
        Profile p;
        File fImg;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        
        fImg = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
        ImgUtil.openClickedIMG(fImg);
    }
    
    @FXML
    void actionArchive(ActionEvent ae)
    {
        boolean confirmation;
        Profile p;
        ArrayList<File> alFilePassportScans;
        int operationStatus1, operationStatus2;
        boolean error = false;

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
        confirmation = CtrAlertDialog.confirmationDialog("Archive", "The passport data (number, issue location, expiry date) will be cleared and all scans related to the current passport will be archived as well. \nDo you want to continue?");
        
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            //adds all passport scans to a list
            alFilePassportScans = new ArrayList<>();
            alFilePassportScans.add(AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()));
            for (Iterator<PassportScan> it = p.getPassportScanSet().iterator(); it.hasNext();)
            {
                PassportScan ps = it.next();
                alFilePassportScans.add(AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps));
            }

            //archive all passport scans on a loop
            for (File f2Archive : alFilePassportScans)
            {
                operationStatus1 = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, f2Archive);
                if (operationStatus1 == -1)
                {
                    error = true;
                }
            }

            //if all the archiving operation works correctly
            if (!error)
            {
                //clear the passport info
                p.setPassportNumber(null);
                p.setPassportIssuedAt(null);
                p.setPassportExpiryDate(null);
                p.setPassportIssueDate(null);

                //op1Status = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
                //update DB operation
                operationStatus2 = ctrGUIMain.getCtrMain().getCtrPassportScan().removeExtraScans(p.getPassportScanSet());
                if (operationStatus2 == 0)
                {
                    //if the DB update is successful can delete the scan files
                    for (File fScan : alFilePassportScans)
                    {
                        CtrFileOperation.deleteFile(fScan);
                    }
                    ctrGUIMain.getCtrMain().getCtrProfile().refreshProfile(p);
                    fillData(p);
                    CtrAlertDialog.infoDialog("Archived successfully", "The previous passport was archived successfully.");
                }
            }
        }
    }
    
    @FXML
    void actionRegister(ActionEvent ae)
    {
        Profile p;
        int operationStatus1, operationStatus2;
        
        File fDestination;

        //if all the information is filled out
        if (validateFields() && fScanSelected != null)
        {
            /*
             * For proper consistency 2 operations need to be successful
             * 1 - copy the scan file to the app folder
             * 2 - update the Database information
             *
             * Implementation:
             * 1) Copy the scan file
             * 2) If the copy is successful, then update the DB info
             * 3) If the db update fails delete the file inside the app folder
             *
             */
            
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
            fDestination = AppFiles.getScanPassportFirstPage(p.getNickname(), tfpassportNumber.getText());
            operationStatus1 = CtrFileOperation.copyOperation(fScanSelected, fDestination);

            //if the file was successfully copied saves the data as well
            if (operationStatus1 == 0)
            {
                p.setPassportNumber(tfpassportNumber.getText());
                p.setPassportIssuedAt(tfpassportIssuedAt.getText());
                p.setPassportIssueDate(Util.convertLocalDateToDate(dpPassportIssueDate.getValue()));
                p.setPassportExpiryDate(Util.convertLocalDateToDate(dpPassportExpiryDate.getValue()));
                operationStatus2 = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);

                //if the DB update was successful
                if (operationStatus2 == 0)
                {
                    CtrAlertDialog.infoDialog("Passport Added/Renewed", "The passport data was sucessfully updated.");
                    fScanSelected = null;
                    fillData(p);
                } else
                {
                    //if the DB update fails has to delete the scan file
                    CtrFileOperation.deleteFile(AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()));
                }
            }
            
        } else
        {
            CtrAlertDialog.errorDialog("Please fill out the ALL passport information before registering.");
        }
    }
    
    private boolean validateFields()
    {
        return ((dpPassportExpiryDate.getValue() != null)
                && (dpPassportIssueDate.getValue() != null)
                && (!tfpassportNumber.getText().isEmpty())
                && (!tfpassportIssuedAt.getText().isEmpty()));
    }
}
