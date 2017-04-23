/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerExportPDF;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.intface.IEditableGUIForm;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.util.Util;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.Upajjhaya;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneBysuddhi extends AChildPaneControllerExportPDF implements IEditableGUIForm, IFormMonasticProfile
{

    @FXML
    private DatePicker dpIssueDate;

    @FXML
    private DatePicker dpPahkahwOrd;
    @FXML
    private DatePicker dpSamaneraOrd;
    @FXML
    private DatePicker dpBhikkhuOrd;

    @FXML
    private TextField tfPaliName;
    @FXML
    private TextField tfPaliNameThai;
    @FXML
    private ComboBox<String> cbOrdainedAt;

    @FXML
    private ComboBox<String> cbUpajjhaya;

    @FXML
    private ImageView ivScan1;
    @FXML
    private ImageView ivScan2;
    @FXML
    private ImageView ivScan3;
    @FXML
    private ImageView ivScan4;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    
    @FXML
    private Button bPreview;
    
//    @FXML
//    private Button bPrint;

    @Override
    public void init()
    {
        super.init();
        
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpIssueDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpPahkahwOrd);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpSamaneraOrd);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpBhikkhuOrd);

        initChangeListener();
        loadContentsCBWat();
        loadContentsCBUpajjhaya();
    }

    private void initChangeListener()
    {
        ArrayList listFields;
        listFields = new ArrayList();

        listFields.add(dpIssueDate);
        listFields.add(dpPahkahwOrd);
        listFields.add(dpSamaneraOrd);
        listFields.add(dpBhikkhuOrd);
        listFields.add(tfPaliName);
        listFields.add(tfPaliNameThai);
        listFields.add(cbOrdainedAt);
        listFields.add(cbUpajjhaya);

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);
    }

    @Override
    public void actionLockEdit()
    {
        dpIssueDate.setDisable(true);
        dpPahkahwOrd.setDisable(true);
        dpSamaneraOrd.setDisable(true);
        dpBhikkhuOrd.setDisable(true);

        tfPaliName.setEditable(false);
        tfPaliNameThai.setEditable(false);

        cbOrdainedAt.setDisable(true);
        cbUpajjhaya.setDisable(true);

    }

    @Override
    public void actionUnlockEdit()
    {
        dpIssueDate.setDisable(false);
        dpPahkahwOrd.setDisable(false);
        dpSamaneraOrd.setDisable(false);
        dpBhikkhuOrd.setDisable(false);

        tfPaliName.setEditable(true);
        tfPaliNameThai.setEditable(true);

        cbOrdainedAt.setDisable(false);
        cbUpajjhaya.setDisable(false);
    }

    @Override
    public void actionSave()
    {
        MonasticProfile p;
        Date issueDate, pkOrdDate, snOrdDate, bkOrdDate;
        Monastery wOrdainedAt;
        Upajjhaya u;
        int operationStatus;
        Integer idSelectedProfile;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        p.setPaliNameEnglish(tfPaliName.getText());
        p.setPaliNameThai(tfPaliNameThai.getText());

        issueDate = Util.convertLocalDateToDate(dpIssueDate.getValue());
        p.setBysuddhiIssueDate(issueDate);

        pkOrdDate = Util.convertLocalDateToDate(dpPahkahwOrd.getValue());
        p.setPahkahwOrdDate(pkOrdDate);

        snOrdDate = Util.convertLocalDateToDate(dpSamaneraOrd.getValue());
        p.setSamaneraOrdDate(snOrdDate);

        bkOrdDate = Util.convertLocalDateToDate(dpBhikkhuOrd.getValue());
        p.setBhikkhuOrdDate(bkOrdDate);

        if (cbOrdainedAt.getValue() != null)
        {
            wOrdainedAt = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryByName(cbOrdainedAt.getValue());
            p.setMonasteryOrdainedAt(wOrdainedAt);
        }

        if (cbUpajjhaya != null)
        {
            u = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadUpajjhayaByName(cbUpajjhaya.getValue());
            p.setUpajjhaya(u);
        }

        operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
        if (operationStatus == 0)
        {
            ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
            CtrAlertDialog.infoDialog("Bysuddhi update", "The bysuddhi information was successfully updated.");
        }
    }
    
    @FXML
    void actionPreviewScansPDF(ActionEvent ae)
    {
        MonasticProfile p;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFBysuddhiScans(p, CtrPDF.OPTION_PREVIEW_FORM);
    }
    
//    @FXML
//    void actionPrintScans(ActionEvent ae)
//    {
//        MonasticProfile p;
//        
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFBysuddhiScans(p, CtrPDF.OPTION_PRINT_FORM);
//    }

    @Override
    public void fillData(MonasticProfile p)
    {
        LocalDate issueDate, pahkahwOrd, samaneraOrd, monkOrd;

        loadContentsCBWat();
        loadContentsCBUpajjhaya();

        loadIMGPreviews(p);
        if (p != null)
        {
            tfPaliName.setText(p.getPaliNameEnglish());
            tfPaliNameThai.setText(p.getPaliNameThai());

            issueDate = Util.convertDateToLocalDate(p.getBysuddhiIssueDate());

            pahkahwOrd = Util.convertDateToLocalDate(p.getPahkahwOrdDate());
            samaneraOrd = Util.convertDateToLocalDate(p.getSamaneraOrdDate());
            monkOrd = Util.convertDateToLocalDate(p.getBhikkhuOrdDate());

            if (issueDate != null)
            {
                dpIssueDate.setValue(issueDate);
            }
            else
            {
                dpIssueDate.setValue(null);
            }

            if (pahkahwOrd != null)
            {
                dpPahkahwOrd.setValue(pahkahwOrd);
            }
            else
            {
                dpPahkahwOrd.setValue(null);
            }

            if (samaneraOrd != null)
            {
                dpSamaneraOrd.setValue(samaneraOrd);
            }
            else
            {
                dpSamaneraOrd.setValue(null);
            }

            if (monkOrd != null)
            {
                dpBhikkhuOrd.setValue(monkOrd);
            }
            else
            {
                dpBhikkhuOrd.setValue(null);
            }

            if (p.getUpajjhaya() != null)
            {
                cbUpajjhaya.setValue(p.getUpajjhaya().getUpajjhayaName());
            }
            else
            {
                cbUpajjhaya.setValue(null);
            }

            if (p.getMonasteryOrdainedAt() != null)
            {
                cbOrdainedAt.setValue(p.getMonasteryOrdainedAt().getMonasteryName());
            }
            else
            {
                cbOrdainedAt.setValue(null);
            }
        }

    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getCtrPaneSelection().isSelectionEmpty();
    }

    private void loadIMGPreviews(MonasticProfile p)
    {
        File f1, f2, f3, f4;
        String nickname;

        if (p != null)
        {
            nickname = p.getNickname();
            f1 = AppFiles.getScanBysuddhi(nickname, 1);
            f2 = AppFiles.getScanBysuddhi(nickname, 2);
            f3 = AppFiles.getScanBysuddhi(nickname, 3);
            f4 = AppFiles.getScanBysuddhi(nickname, 4);
        }
        else
        {
            f1 = f2 = f3 = f4 = null;
        }

        GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_BYSUDDHI, f1);
        GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_BYSUDDHI, f2);
        GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_BYSUDDHI, f3);
        GUIUtil.loadImageView(ivScan4, GUIUtil.IMG_TYPE_BYSUDDHI, f4);
    }

    @FXML
    void actionChooseBysuddhiScan(ActionEvent ae)
    {
        MonasticProfile p;
        File fDestination, fSource;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        if (ae.getSource().equals(b1))
        {
            fDestination = AppFiles.getScanBysuddhi(p.getNickname(), 1);
        }
        else if (ae.getSource().equals(b2))
        {
            fDestination = AppFiles.getScanBysuddhi(p.getNickname(), 2);
        }
        else if (ae.getSource().equals(b3))
        {
            fDestination = AppFiles.getScanBysuddhi(p.getNickname(), 3);
        }
        else
        {
            fDestination = AppFiles.getScanBysuddhi(p.getNickname(), 4);
        }
        fSource = CtrFileOperation.selectFile("Select Bysuddhi Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);

        if (fSource != null)
        {
            CtrFileOperation.copyOperation(fSource, fDestination);
            loadIMGPreviews(p);
        }

    }

    @FXML
    void actionIMGBysuddhiScanClicked(MouseEvent me)
    {
        MonasticProfile p;
        File fImg;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (me.getSource().equals(ivScan1))
        {
            fImg = AppFiles.getScanBysuddhi(p.getNickname(), 1);
        }
        else if (me.getSource().equals(ivScan2))
        {
            fImg = AppFiles.getScanBysuddhi(p.getNickname(), 2);

        }
        else if (me.getSource().equals(ivScan3))
        {
            fImg = AppFiles.getScanBysuddhi(p.getNickname(), 3);

        }
        else
        {
            fImg = AppFiles.getScanBysuddhi(p.getNickname(), 4);
        }

        GUIUtil.openClickedIMG(fImg);
    }

    private void loadContentsCBWat()
    {
        ArrayList<String> alWatList;

        alWatList = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryList();
        cbOrdainedAt.getItems().clear();
        cbOrdainedAt.getItems().addAll(alWatList);
    }

    private void loadContentsCBUpajjhaya()
    {
        ArrayList<String> alUpajjhayaList;

        alUpajjhayaList = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadUpajjhayaList();
        cbUpajjhaya.getItems().clear();
        cbUpajjhaya.getItems().addAll(alUpajjhayaList);
    }
}
