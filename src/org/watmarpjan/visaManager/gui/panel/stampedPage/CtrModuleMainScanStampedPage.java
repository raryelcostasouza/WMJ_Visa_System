/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel.stampedPage;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import org.watmarpjan.visaManager.gui.panel.stampedPage.CtrModuleGenericScanStampedPage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.gui.panel.CtrPanePassport;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.stampedPage.output.InfoMainScanStampedPage;
import org.watmarpjan.visaManager.model.stampedPage.input.InfoFileScanStampedPage;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author adhipanyo
 */
public class CtrModuleMainScanStampedPage extends CtrModuleGenericScanStampedPage
{

    private final RadioButton rbArriveStamp;
    private final RadioButton rbVisa;
    private final RadioButton rbLastVisaExt;

    //used for managing the control of the components for the main scans (arrive stamp, visa and visa extension)
    public CtrModuleMainScanStampedPage(Button bSelectFile, Button bArchive, 
                                        TextField tfLeftPageNumber, TextField tfRightPageNumber, 
                                        RadioButton rbArriveStamp, RadioButton rbVisa, RadioButton rbLastVisaExt, 
                                        ImageView ivScan, CtrPanePassport objCtrPassport)
    {
        super(bSelectFile, bArchive, tfLeftPageNumber, tfRightPageNumber, ivScan, objCtrPassport);

        this.rbArriveStamp = rbArriveStamp;
        this.rbVisa = rbVisa;
        this.rbLastVisaExt = rbLastVisaExt;
    }

    @Override
    public void resetLockStatus()
    {
        super.resetLockStatus();

        rbArriveStamp.setDisable(true);
        rbVisa.setDisable(true);
        rbLastVisaExt.setDisable(true);

        rbArriveStamp.setSelected(false);
        rbVisa.setSelected(false);
        rbLastVisaExt.setSelected(false);
    }

    public void actionUnlockEdit()
    {
        super.actionUnlockEdit();

        rbArriveStamp.setDisable(false);
        rbLastVisaExt.setDisable(false);
        rbVisa.setDisable(false);
    }

    public void actionLockEdit()
    {
        super.actionLockEdit();

        rbArriveStamp.setDisable(true);
        rbLastVisaExt.setDisable(true);
        rbVisa.setDisable(true);
    }

    public RadioButton getRbArriveStamp()
    {
        return rbArriveStamp;
    }

    public RadioButton getRbVisa()
    {
        return rbVisa;
    }

    public RadioButton getRbLastVisaExt()
    {
        return rbLastVisaExt;
    }

    public void fillData(InfoFileScanStampedPage objPS, boolean lockStatus)
    {
        super.fillData(objPS, lockStatus);
        if (objPS != null)
        {

            if (objPS.containsScanArriveStamp())
            {
                getRbArriveStamp().setSelected(true);
            }
            if (objPS.containsScanVisa())
            {
                getRbVisa().setSelected(true);
            }
            if (objPS.containsScanLastVisaExt())
            {
                getRbLastVisaExt().setSelected(true);
            }
        }

    }

    public void clearData()
    {
        super.clearData();

        getRbArriveStamp().setSelected(false);
        getRbLastVisaExt().setSelected(false);
        getRbVisa().setSelected(false);
    }

    protected boolean validateScanInfo(ArrayList<CtrModuleGenericScanStampedPage> listCtrModules)
    {
        //if the page number is not empty and at least one of the options is selected
        //returns true
        boolean statusValid;
        boolean validationPageNumber;
        boolean scanTypeSelected;

        if (fSelectedButUnsaved != null)
        {
            validationPageNumber = super.validateScanInfo(listCtrModules);

            scanTypeSelected = (getRbArriveStamp().isSelected()
                    || getRbLastVisaExt().isSelected()
                    || getRbVisa().isSelected());

            statusValid = validationPageNumber && scanTypeSelected;
            //if the scan type or the page number is empty
            if (!scanTypeSelected)
            {
                CtrAlertDialog.errorDialog("Please select the main scan category (Arrive Stamp, Visa or Visa Extension)");
            }

            return statusValid;
        }
        //if no selected scan it should be considered valid... no action needed
        return true;

    }

    public boolean saveScan(MonasticProfile p, ArrayList<CtrModuleGenericScanStampedPage> listCtrModules)
    {
        boolean error;
        if (validateScanInfo(listCtrModules))
        {
            //if a new scan file was selected
            if (fSelectedButUnsaved != null)
            {
                return addNewScan(p);
            }
            //if there was no scan added need to check if there was change on the selected scan types
            //if so need to rename
            else if ((getFileScan()!= null) && checkIfNeedRenameScanFile())
            {
                return renameExtraScan(p);
            }
            error = false;
            return error;
        }
        error = true;
        //if there were validation errors
        return error;

    }

    private boolean addNewScan(MonasticProfile p)
    {
        InfoMainScanStampedPage objPS;
        int ret;
        boolean stateScanArriveStamp;
        boolean stateScanVisa;
        boolean stateScanLastVisaExt;
        File fDestination, fSelected;
        String strLeftPageNumber;

        fSelected = getFileScanSelectedButUnsaved();
        strLeftPageNumber = getTfPLeftPageNumber().getText();
        stateScanArriveStamp = getRbArriveStamp().isSelected();
        stateScanVisa = getRbVisa().isSelected();
        stateScanLastVisaExt = getRbLastVisaExt().isSelected();

        objPS = new InfoMainScanStampedPage(strLeftPageNumber, stateScanArriveStamp, stateScanVisa, stateScanLastVisaExt);
        fDestination = AppFiles.generateFileNameMain3StampedPageScan(p.getNickname(), p.getPassportNumber(), objPS);

        ret = CtrFileOperation.copyOperation(fSelected, fDestination);
        //if the operation was unsuccessful
        if (ret == -1)
        {
            CtrAlertDialog.errorDialog("Unable to copy the file for Extra Scan " + fSelected.getName() + ".");
            return true;
        }

        return false;
    }

    private boolean checkIfNeedRenameScanFile()
    {
        boolean stateScanArriveStamp;
        boolean stateScanVisa;
        boolean stateScanLastVisaExt;

        stateScanArriveStamp = getRbArriveStamp().isSelected();
        stateScanVisa = getRbVisa().isSelected();
        stateScanLastVisaExt = getRbLastVisaExt().isSelected();
        //if there was any change on the radio buttons for the scan type
        //when compared with the originally loaded info

        return (stateScanArriveStamp != objInfoScanLoaded.containsScanArriveStamp())
                || (stateScanVisa != objInfoScanLoaded.containsScanVisa())
                || (stateScanLastVisaExt != objInfoScanLoaded.containsScanLastVisaExt());
    }

    private boolean renameExtraScan(MonasticProfile p)
    {
        boolean stateScanArriveStamp;
        boolean stateScanVisa;
        boolean stateScanLastVisaExt;
        File fScanUpdated;
        int ret;
        String strLeftPageNumber;

        stateScanArriveStamp = getRbArriveStamp().isSelected();
        stateScanVisa = getRbVisa().isSelected();
        stateScanLastVisaExt = getRbLastVisaExt().isSelected();

        //if there was any change on the radio buttons for the scan type
        //save the changes by updating the name of the scan file
        strLeftPageNumber = objInfoScanLoaded.getLeftPageNumber()+"";
        InfoMainScanStampedPage objPSNew = new InfoMainScanStampedPage(strLeftPageNumber, stateScanArriveStamp, stateScanVisa, stateScanLastVisaExt);
        fScanUpdated = AppFiles.generateFileNameMain3StampedPageScan(p.getNickname(), p.getPassportNumber(), objPSNew);

        ret = CtrFileOperation.renameFile(objInfoScanLoaded.getFileScan(), fScanUpdated);
        if (ret == -1)
        {
            CtrAlertDialog.errorDialog("Unable to rename the extra scan file.");
            return true;
        }

        return false;
    }
    
    public void archiveScan(MonasticProfile p )
    {
        boolean confirmation;
        
        confirmation = CtrAlertDialog.confirmationDialog("Archive passport scan", "This passport scan will be archived. \nDo you want to continue?");
        if (confirmation)
        {
            CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, getFileScan());
            objCtrPanePassport.fillDataScans();
        }
        
    }

}
