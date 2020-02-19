/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.io.File;
import static java.lang.Integer.parseInt;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.eps.InfoPassportScanStampedPage;
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
    private ImageView ivScan;
    
    private boolean scanContent;
    
    private CtrPanePassport objCtrPanePassport;
    private File fScan;

    public CtrModuleGenericScanStampedPage()
    {
        
    }
    
    public CtrModuleGenericScanStampedPage(Button pBSelectFile, Button pBArchive, TextField pTFLeftPageNumber, TextField pTFRightPageNumber)
    {
        bSelectFile = pBSelectFile;
        bArchive = pBArchive;
        
        tfLeftPage = pTFLeftPageNumber;
        tfRightPage = pTFRightPageNumber;
    }
    
    public void init(CtrPanePassport pCtrPanePassport)
    {
        scanContent = false;
        objCtrPanePassport = pCtrPanePassport;
        
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

    
    public void reset()
    {
        scanContent = false;

        tfLeftPage.setEditable(false);
        tfLeftPage.setText("");
        tfRightPage.setText("");

        bSelectFile.setDisable(true);
        bArchive.setDisable(true);
    }

    //if there is scan content to show on this pane
    public void setPaneContentNotEmpty()
    {
        scanContent = true;
        tfLeftPage.setEditable(false);
    }
    
    public void setPaneContentEmpty()
    {
        scanContent = false;
        tfLeftPage.setEditable(true);
    }

    public void actionUnlockEdit()
    {
        if (scanContent)
        {
            bArchive.setDisable(false);
        } else
        {
            bSelectFile.setDisable(false);
        }
    }

    public void actionLockEdit()
    {
        if (scanContent)
        {
            bArchive.setDisable(true);
        } else
        {
            bSelectFile.setDisable(true);
        }
    }
    
    public void fillData(InfoPassportScanStampedPage objPS, boolean lockStatus)
    {
        reset();
        
        fScan = objPS.getFileScan();
        GUIUtil.loadImageView(ivScan, GUIUtil.IMG_TYPE_PASSPORT, fScan);
        
        getTfPLeftPageNumber().setText(objPS.getLeftPageNumber() + "");
        
        
        if (lockStatus)
        {
            actionLockEdit();
        }
        else
        {
            actionUnlockEdit();
        }
        
    }
    
    private void fillDataContentScans(MonasticProfile p, boolean lockStatus)
    {
        
        
        
          //reset();
          
//        fieldsScan2.reset();
//        fieldsScan3.reset();
//
//        fScan1Selected = null;
//        fScan2Selected = null;
//        fScan3Selected = null;
//
//        refreshExtraPassportScan(p);
//        toggleLockButtonsScanFirstPageNDepartureCard(lockStatus, p);
//
//        fillDataContentScanGeneric(fieldsScan1, objEPS1, lockStatus);
//        fillDataContentScanGeneric(fieldsScan2, objEPS2, lockStatus);
//        fillDataContentScanGeneric(fieldsScan3, objEPS3, lockStatus);
    }
    
    @FXML
    void actionArchiveExtraScan(ActionEvent ae)
    {
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
//        File fSelected;
//        MonasticProfile p;
//
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        //if there is a passport registered allows the user to add other scans
//        if (p.getPassportNumber() != null)
//        {
//            fSelected = CtrFileOperation.selectFile("Extra Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);
//            if (fSelected != null)
//            {
//                ctrGUIMain.getCtrFieldChangeListener().setHasUnsavedChanges();
//                if (ae.getSource().equals(bSelectScan1))
//                {
//                    fScan1Selected = fSelected;
//                    GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
//                    //bAddScan1.setDisable(false);
//                } else if (ae.getSource().equals(bSelectScan2))
//                {
//                    fScan2Selected = fSelected;
//                    GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
//                    //bAddScan2.setDisable(false);
//                } else
//                {
//                    fScan3Selected = fSelected;
//                    GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
//                    //bAddScan3.setDisable(false);
//                }
//            }
//        } else
//        {
//            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
//        }
//    
    }
    
    @FXML
    void actionIMGPassportScanClicked(MouseEvent me)
    {
//        MonasticProfile p;
//        File fImgScan;
//
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//
//        if (me.getSource().equals(ivPassportScan))
//        {
//            fImgScan = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
//        } else if (me.getSource().equals(ivDepartureCardScan))
//        {
//            fImgScan = AppFiles.getScanDepartureCard(p.getNickname());
//        } else if (me.getSource().equals(ivScan1))
//        {
//            //if there is a scan selected, but not yet added
//            if (fScan1Selected != null)
//            {
//                fImgScan = fScan1Selected;
//            } //for a registered scan
//            else
//            {
//                if (objEPS1 != null)
//                {
//                    fImgScan = objEPS1.getFileScan();
//                } else
//                {
//                    fImgScan = null;
//                }
//            }
//        } else if (me.getSource().equals(ivScan2))
//        {
//            //if there is a scan selected, but not yet added
//            if (fScan2Selected != null)
//            {
//                fImgScan = fScan2Selected;
//            } //for a registered scan
//            else
//            {
//                if (objEPS2 != null)
//                {
//                    fImgScan = objEPS2.getFileScan();
//                } else
//                {
//                    fImgScan = null;
//                }
//
//            }
//        } else
//        {
//            //if there is a scan selected, but not yet added
//            if (fScan3Selected != null)
//            {
//                fImgScan = fScan3Selected;
//            } //for a registered scan
//            else
//            {
//                if (objEPS3 != null)
//                {
//                    fImgScan = objEPS3.getFileScan();
//                } else
//                {
//                    fImgScan = null;
//                }
//
//            }
//        }
//
//        CtrFileOperation.openFileOnDefaultProgram(fImgScan);
   }

}