/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel.stampedPage;

import java.io.File;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.gui.panel.CtrPanePassport;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.stampedPage.input.InfoFileScanStampedPage;
import org.watmarpjan.visaManager.model.stampedPage.output.InfoGenericScanStampedPage;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author WMJ_user
 */
public class CtrModuleGenericScanStampedPage
{

    @FXML
    private Button bSelectFile;

    @FXML
    private Button bArchive;

    @FXML
    private TextField tfLeftPage;

    @FXML
    private TextField tfRightPage;

    @FXML
    private TitledPane tpMain;

    @FXML
    private ImageView ivScan;

    protected CtrPanePassport objCtrPanePassport;
    private File fScan;
    protected File fSelectedButUnsaved;

    //original data loaded from file
    //used to compare for changes and need of renaming
    protected InfoFileScanStampedPage objInfoScanLoaded;

    public CtrModuleGenericScanStampedPage()
    {

    }

    public CtrModuleGenericScanStampedPage(Button pBSelectFile, Button pBArchive, TextField pTFLeftPageNumber, TextField pTFRightPageNumber, ImageView pIVScan, CtrPanePassport pObjCtrPassport)
    {
        this.objCtrPanePassport = pObjCtrPassport;
        
        bSelectFile = pBSelectFile;
        bArchive = pBArchive;

        tfLeftPage = pTFLeftPageNumber;
        tfRightPage = pTFRightPageNumber;
        ivScan = pIVScan;
    }

    public void init(CtrPanePassport pCtrPanePassport, int index)
    {
        fScan = null;
        fSelectedButUnsaved = null;
        objInfoScanLoaded = null;
        objCtrPanePassport = pCtrPanePassport;

        tpMain.setText("Stamped Page Scan " + index);

        //listener to filter out invalid characters from left page field
        tfLeftPage.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue != null)
                {
                    tfLeftPage.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });

        //listener to fill the right page automatically
        tfLeftPage.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                int parsedPageNumberValue;
                if (newValue != null)
                {
                    try
                    {
                        parsedPageNumberValue = parseInt(tfLeftPage.getText());
                        tfRightPage.setText(parsedPageNumberValue + 1 + "");

                    }
                    catch (NumberFormatException nfe)
                    {
                        tfRightPage.setText("");
                    }
                }
            }
        });

        GUIUtil.loadImageView(ivScan, GUIUtil.IMG_TYPE_PASSPORT, null);
        if (objCtrPanePassport.getCtrGUIMain().getPaneEditSaveController().getLockStatus())
        {
            actionLockEdit();
        }
        else
        {
            actionUnlockEdit();
        }
    }

    @FXML
    public void actionIMGClicked(MouseEvent me)
    {
        File f2Open;
        if (fSelectedButUnsaved != null)
        {
            f2Open = fSelectedButUnsaved;
        }
        else
        {
            f2Open = fScan;
        }
        CtrFileOperation.openFileOnDefaultProgram(f2Open);
    }

    public Button getbSelectFile()
    {
        return bSelectFile;
    }

    public Button getbArchive()
    {
        return bArchive;
    }

    public TextField getTfPLeftPageNumber()
    {
        return tfLeftPage;
    }
    
    public TextField getTfPRightPageNumber()
    {
        return tfRightPage;
    }

    public void clearData()
    {
        objInfoScanLoaded = null;
        fScan = null;
        tfLeftPage.setText("");
        tfRightPage.setText("");
    }

    public void resetLockStatus()
    {
        tfLeftPage.setEditable(false);
        bSelectFile.setDisable(true);
        bArchive.setDisable(true);
    }

    public void actionUnlockEdit()
    {
        if (fScan != null)
        {
            bArchive.setDisable(false);
        }
        else
        {
            bSelectFile.setDisable(false);
            tfLeftPage.setEditable(true);
        }

    }

    public void actionLockEdit()
    {
        if (fScan != null)
        {
            bArchive.setDisable(true);
        }
        else
        {
            bSelectFile.setDisable(true);
        }

    }

    public void fillData(InfoFileScanStampedPage objPS)
    {
        if (objPS != null)
        {
            objInfoScanLoaded = objPS;
            fScan = objPS.getFileScan();

            GUIUtil.loadImageView(ivScan, GUIUtil.IMG_TYPE_PASSPORT, fScan);

            getTfPLeftPageNumber().setText(objPS.getLeftPageNumber() + "");
        }
        else
        {
            GUIUtil.loadImageView(ivScan, GUIUtil.IMG_TYPE_PASSPORT, null);
            fScan = null;
        }

    }

    @FXML
    void actionArchiveExtraScan(ActionEvent ae)
    {
        MonasticProfile p;
        boolean confirmation;

        p = objCtrPanePassport.getCtrGUIMain().getCtrPaneSelection().getSelectedProfile();

        confirmation = CtrAlertDialog.confirmationDialog("Archive passport scan", "This passport scan will be archived. \nDo you want to continue?");
        if (confirmation)
        {
            CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, fScan);
            objCtrPanePassport.fillDataScans();
        }
        
//        MonasticProfile p;
//        File fScan2Archive;
//
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        if (ae.getSource().equals(bArchive1))
//        {
//            fScan2Archive = objEPS1.getFileScan();
//        } else if (ae.getSource().equals(bArchive2))
//        {
//            fScan2Archive = objEPS2.getFileScan();
//        } else
//        {
//            fScan2Archive = objEPS3.getFileScan();
//        }
//
//        CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, fScan2Archive);
//
//        fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
//        loadIMGPreviews(p);
    }

    @FXML
    void actionChooseExtraScan(ActionEvent ae)
    {
        MonasticProfile p;

        p = objCtrPanePassport.getCtrGUIMain().getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans
        if (p.getPassportNumber() != null)
        {
            fSelectedButUnsaved = CtrFileOperation.selectFile("Extra Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);
            if (fSelectedButUnsaved != null)
            {
                objCtrPanePassport.getCtrGUIMain().getCtrFieldChangeListener().setHasUnsavedChanges();
                GUIUtil.loadImageView(ivScan, GUIUtil.IMG_TYPE_PASSPORT, fSelectedButUnsaved);
                //do a tab refresh to get the height adjusted according to img size
                objCtrPanePassport.switch2Tab(1);
            }
        }
        else
        {
            CtrAlertDialog.errorDialog(objCtrPanePassport.ERROR_NO_PASSPORT_REGISTERED);
        }
    }

    protected boolean validateScanInfo(ArrayList<CtrModuleGenericScanStampedPage> listCtrModules)
    {
        //if the page number is not empty and is a valid number returns true
        boolean isValid;

        isValid = true;
        //if page number is empty
        if (getTfPLeftPageNumber().getText().isEmpty())
        {
            CtrAlertDialog.errorDialog("Please fill all the fields for Stamped Page Scan.");
            isValid = false;
        }
        else
        {
            //test if the page number input is valid
            isValid = validatePageNumber(listCtrModules);
        }

        return isValid;
    }

    public boolean saveScan(ArrayList<CtrModuleGenericScanStampedPage> listCtrModules)
    {
        MonasticProfile p;
        int ret;
        File fDestination;
        boolean errorHappened;
        InfoGenericScanStampedPage objInfoScan;
        String strLeftPageNumber;

        //only need to try to save if there is a file chosen to be added on the module
        if (fSelectedButUnsaved != null)
        {
            //if the page number information is not empty and valid number
            if (validateScanInfo(listCtrModules))
            {
                p = objCtrPanePassport.getCtrGUIMain().getCtrPaneSelection().getSelectedProfile();

                strLeftPageNumber = getTfPLeftPageNumber().getText();
                objInfoScan = new InfoGenericScanStampedPage(strLeftPageNumber);
                fDestination = AppFiles.generateFileNameGenericStampedPageScan(p.getNickname(), p.getPassportNumber(), objInfoScan);

                ret = CtrFileOperation.copyOperation(fSelectedButUnsaved, fDestination);

                //if the operation was unsuccessful
                if (ret == -1)
                {
                    CtrAlertDialog.errorDialog("Unable to copy the file for Extra Scan " + fSelectedButUnsaved.getName() + ".");
                    errorHappened = true;
                }
                //if no error on copying the files
                else
                {
                    errorHappened = false;
                }
            }
            //if page number validation error happened
            else
            {
                errorHappened = true;
            }
        }
        //if not the case no error happened ... it is just the case that nothing need to be done
        else
        {
            errorHappened = false;
        }

        return errorHappened;
    }

    private boolean validatePageNumber(ArrayList<CtrModuleGenericScanStampedPage> listCtrModules)
    {
        int currentScanPageNumber, otherScanLeftPageNumber, otherScanRightPageNumber;
        try
        {
            currentScanPageNumber = Integer.parseInt(getTfPLeftPageNumber().getText());

            //if the page number of the new scan is repeated somewhere else refuses the operation
            //check if the page number is repeated on other scan
            for (CtrModuleGenericScanStampedPage otherCtrModule : listCtrModules)
            {
                //check to avoid comparing the pagenumber with the same scan
                ///and with other scans with empty data
                if (this != otherCtrModule && !otherCtrModule.getTfPLeftPageNumber().getText().isEmpty())
                {
                    //compares the current left page number with the left and right page number of other scans
                    otherScanLeftPageNumber = Integer.parseInt(otherCtrModule.getTfPLeftPageNumber().getText());
                    otherScanRightPageNumber = Integer.parseInt(otherCtrModule.getTfPRightPageNumber().getText());
                    if ((currentScanPageNumber == otherScanLeftPageNumber)
                            || (currentScanPageNumber == otherScanRightPageNumber)
                            || (currentScanPageNumber == (otherScanLeftPageNumber -1)))
                    {
                        CtrAlertDialog.errorDialog("The page number for the newly added scan already exists at another scan.");
                        return false;
                    }
                }

            }
            return true;
        }
        //if not a valid number it will return false
        catch (NumberFormatException nfe)
        {
            CtrAlertDialog.errorDialog("Invalid page number.");
            return false;
        }

    }

    public void setFileScanSelectedButUnsaved(File fSelectedButUnsaved)
    {
        this.fSelectedButUnsaved = fSelectedButUnsaved;
    }

    public File getFileScanSelectedButUnsaved()
    {
        return fSelectedButUnsaved;
    }

    public File getFileScan()
    {
        return fScan;
    }

}
