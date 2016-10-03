/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.ResultDialogSelectScan.AbstractResultDialogSelectScan;
import org.watmarpjan.visaManager.model.ResultDialogSelectScan.ResultExistingScan;
import org.watmarpjan.visaManager.model.ResultDialogSelectScan.ResultNewFileScan;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
import org.watmarpjan.visaManager.model.hibernate.Profile;

/**
 *
 * @author WMJ_user
 */
public abstract class AbstractFormSelectExtraScan extends AbstractChildPaneController
{

    protected AbstractResultDialogSelectScan resultSelectScan;
    @FXML
    protected TextField tfPsptPageNumber;
    @FXML
    protected ImageView ivPreview;

    private PassportScan psSelected;
    private File fExistingScan;
    private File fRenamedScan;
    private File fNewScan;

    public static final int SCAN_TYPE_VISA_EXT = 0;
    public static final int SCAN_TYPE_VISA = 1;
    public static final int SCAN_TYPE_ARRIVE_STAMP = 2;

    @FXML
    public void actionSelectScan(ActionEvent ae)
    {
        Optional<AbstractResultDialogSelectScan> optionSelected = ctrGUIMain.actionShowDialogSelectScan();
        PassportScan psExistingScan;
        File fNewScan, fExistingScan;
        Profile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        if (optionSelected.isPresent())
        {
            resultSelectScan = optionSelected.get();

            if (optionSelected.get() instanceof ResultNewFileScan)
            {
                fNewScan = ((ResultNewFileScan) optionSelected.get()).getFileNewScan();
                ImgUtil.loadImageView(ivPreview, ImgUtil.IMG_TYPE_PASSPORT, fNewScan);
                tfPsptPageNumber.setEditable(true);
            } else
            {
                psExistingScan = ((ResultExistingScan) optionSelected.get()).getExistingPassportScan();
                fExistingScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psExistingScan);
                ImgUtil.loadImageView(ivPreview, ImgUtil.IMG_TYPE_PASSPORT, fExistingScan);

                tfPsptPageNumber.setText(psExistingScan.getPageNumber() + "");
                tfPsptPageNumber.setEditable(false);
            }

        }
    }

    protected int processSelectedScan(Profile p, int scanType)
    {
        int pageNumber, operationStatus;

        if (resultSelectScan instanceof ResultNewFileScan)
        {
            //if the scan is not an existing one... creates a new passportscan
            pageNumber = Integer.parseInt(tfPsptPageNumber.getText());

            switch (scanType)
            {
                case SCAN_TYPE_VISA_EXT:
                    psSelected = new PassportScan(p, pageNumber, false, false, true);
                    break;
                case SCAN_TYPE_VISA:
                    psSelected = new PassportScan(p, pageNumber, false, true, false);
                    break;
                case SCAN_TYPE_ARRIVE_STAMP:
                    psSelected = new PassportScan(p, pageNumber, true, false, false);
                    break;
            }

            fNewScan = ((ResultNewFileScan) resultSelectScan).getFileNewScan();
            operationStatus = CtrFileOperation.copyOperation(fNewScan, AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psSelected));
        } else
        {
            //updates the existing passport scan contents to include the selected scan
            psSelected = ((ResultExistingScan) resultSelectScan).getExistingPassportScan();

            //scan filename before
            fExistingScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psSelected);

            switch (scanType)
            {
                case SCAN_TYPE_VISA_EXT:
                    psSelected.setContentLastVisaExt(true);
                    break;
                case SCAN_TYPE_VISA:
                    psSelected.setContentVisaScan(true);
                    break;
                case SCAN_TYPE_ARRIVE_STAMP:
                    psSelected.setContentArriveStamp(true);
                    break;
            }

            //scan filename after update
            fRenamedScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psSelected);
            operationStatus = CtrFileOperation.renameFile(fExistingScan, fRenamedScan);
        }

        return operationStatus;
    }

    protected void undoProcessingSelectedScan(Profile p)
    {
        if (resultSelectScan instanceof ResultNewFileScan)
        {
            //if the DB update fails has to delete the new scan file
            CtrFileOperation.deleteFile(AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psSelected));
        } else
        {
            //if the db update fails need to rename back the scan file
            CtrFileOperation.renameFile(fRenamedScan, fExistingScan);
        }
    }

    protected void fillData(PassportScan ps)
    {
        if (ps != null)
        {
            tfPsptPageNumber.setText(ps.getPageNumber() + "");
            tfPsptPageNumber.setEditable(false);
        } else
        {
            tfPsptPageNumber.setText("");
            tfPsptPageNumber.setEditable(true);
        }
    }

    protected PassportScan getPassportScan()
    {
        return psSelected;
    }
}
