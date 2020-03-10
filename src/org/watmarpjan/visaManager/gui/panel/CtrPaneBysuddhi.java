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
    private ImageView ivScan5;
    @FXML
    private ImageView ivScan6;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;
    @FXML
    private Button b5;
    @FXML
    private Button b6;

    @FXML
    private Button b1Archive;
    @FXML
    private Button b2Archive;
    @FXML
    private Button b3Archive;
    @FXML
    private Button b4Archive;
    @FXML
    private Button b5Archive;
    @FXML
    private Button b6Archive;

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

        b1.setDisable(true);
        b2.setDisable(true);
        b3.setDisable(true);
        b4.setDisable(true);
        b5.setDisable(true);
        b6.setDisable(true);

        b1Archive.setDisable(true);
        b2Archive.setDisable(true);
        b3Archive.setDisable(true);
        b4Archive.setDisable(true);
        b5Archive.setDisable(true);
        b6Archive.setDisable(true);
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
        reloadScanButtons();
    }

    @Override
    public int actionSave()
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
            wOrdainedAt = ctrGUIMain.getCtrMain().getCtrMonastery().loadByName(cbOrdainedAt.getValue());
            p.setMonasteryOrdainedAt(wOrdainedAt);
        }

        if (cbUpajjhaya != null)
        {
            u = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadByName(cbUpajjhaya.getValue());
            p.setUpajjhaya(u);
        }

        operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().update(p);
        if (operationStatus == 0)
        {
            ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
            CtrAlertDialog.infoDialog("Bysuddhi update", "The bysuddhi information was successfully updated.");
        }
        return operationStatus;
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
        File f1, f2, f3, f4, f5, f6;
        String nickname;

        if (p != null)
        {
            nickname = p.getNickname();
            f1 = AppFiles.getScanBysuddhi(nickname, 1);
            f2 = AppFiles.getScanBysuddhi(nickname, 2);
            f3 = AppFiles.getScanBysuddhi(nickname, 3);
            f4 = AppFiles.getScanBysuddhi(nickname, 4);
            f5 = AppFiles.getScanBysuddhi(nickname, 5);
            f6 = AppFiles.getScanBysuddhi(nickname, 6);
        }
        else
        {
            f1 = f2 = f3 = f4 = f5 = f6 = null;
        }

        initScanButtonsGeneric(f1, b1, b1Archive);
        initScanButtonsGeneric(f2, b2, b2Archive);
        initScanButtonsGeneric(f3, b3, b3Archive);
        initScanButtonsGeneric(f4, b4, b4Archive);
        initScanButtonsGeneric(f5, b5, b5Archive);
        initScanButtonsGeneric(f6, b6, b6Archive);

        GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_BYSUDDHI, f1);
        GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_BYSUDDHI, f2);
        GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_BYSUDDHI, f3);
        GUIUtil.loadImageView(ivScan4, GUIUtil.IMG_TYPE_BYSUDDHI, f4);
        GUIUtil.loadImageView(ivScan5, GUIUtil.IMG_TYPE_BYSUDDHI, f5);
        GUIUtil.loadImageView(ivScan6, GUIUtil.IMG_TYPE_BYSUDDHI, f6);
    }

    private void reloadScanButtons()
    {
        File f1, f2, f3, f4, f5, f6;
        MonasticProfile p;
        String nickname;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        nickname = p.getNickname();
        f1 = AppFiles.getScanBysuddhi(nickname, 1);
        f2 = AppFiles.getScanBysuddhi(nickname, 2);
        f3 = AppFiles.getScanBysuddhi(nickname, 3);
        f4 = AppFiles.getScanBysuddhi(nickname, 4);
        f5 = AppFiles.getScanBysuddhi(nickname, 5);
        f6 = AppFiles.getScanBysuddhi(nickname, 6);

        initScanButtonsGeneric(f1, b1, b1Archive);
        initScanButtonsGeneric(f2, b2, b2Archive);
        initScanButtonsGeneric(f3, b3, b3Archive);
        initScanButtonsGeneric(f4, b4, b4Archive);
        initScanButtonsGeneric(f5, b5, b5Archive);
        initScanButtonsGeneric(f6, b6, b6Archive);
    }

    private void initScanButtonsGeneric(File f, Button bScan, Button bArchive)
    {
        if (ctrGUIMain.getPaneEditSaveController().getLockStatus())
        {
            bScan.setDisable(true);
            bArchive.setDisable(true);
        }
        else
        {
            if (f != null && f.exists())
            {
                bScan.setDisable(true);
                bArchive.setDisable(false);
            }
            else
            {
                bScan.setDisable(false);
                bArchive.setDisable(true);
            }
        }
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
        else if (ae.getSource().equals(b4))
        {
            fDestination = AppFiles.getScanBysuddhi(p.getNickname(), 4);
        }
        else if (ae.getSource().equals(b5))
        {
            fDestination = AppFiles.getScanBysuddhi(p.getNickname(), 5);
        }
        else
        {
            fDestination = AppFiles.getScanBysuddhi(p.getNickname(), 6);
        }
        fSource = CtrFileOperation.selectFile("Select Bysuddhi Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);

        if (fSource != null)
        {
            CtrFileOperation.copyOperation(fSource, fDestination);
            loadIMGPreviews(p);
        }
    }

    @FXML
    void actionArchiveBysuddhiScan1(ActionEvent ae)
    {
        actionArchiveBysuddhiScanGeneric(1);
    }

    @FXML
    void actionArchiveBysuddhiScan2(ActionEvent ae)
    {
        actionArchiveBysuddhiScanGeneric(2);
    }

    @FXML
    void actionArchiveBysuddhiScan3(ActionEvent ae)
    {
        actionArchiveBysuddhiScanGeneric(3);
    }

    @FXML
    void actionArchiveBysuddhiScan4(ActionEvent ae)
    {
        actionArchiveBysuddhiScanGeneric(4);
    }
    
    @FXML
    void actionArchiveBysuddhiScan5(ActionEvent ae)
    {
        actionArchiveBysuddhiScanGeneric(5);
    }
    @FXML
    void actionArchiveBysuddhiScan6(ActionEvent ae)
    {
        actionArchiveBysuddhiScanGeneric(6);
    }

    private void actionArchiveBysuddhiScanGeneric(int scanNumber)
    {
        MonasticProfile p;
        String msg;
        boolean confirmation;
        int opStatusArchive;
        File f2Archive;

        msg = "Are you sure that you want to archive this bysuddhi scan?";

        confirmation = CtrAlertDialog.confirmationDialog("Confirmation", msg);
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            f2Archive = AppFiles.getScanBysuddhi(p.getNickname(), scanNumber);
            opStatusArchive = CtrFileOperation.archiveBysuddhiScan(f2Archive, p.getNickname());
            if (opStatusArchive == 0)
            {
                loadIMGPreviews(p);
                CtrAlertDialog.infoDialog("Archived successfully", "The bysuddhi scan was archived successfully.");
            }
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
        else if (me.getSource().equals(ivScan4))
        {
            fImg = AppFiles.getScanBysuddhi(p.getNickname(), 4);

        }
        else if (me.getSource().equals(ivScan5))
        {
            fImg = AppFiles.getScanBysuddhi(p.getNickname(), 5);

        }
        else
        {
            fImg = AppFiles.getScanBysuddhi(p.getNickname(), 6);
        }

        CtrFileOperation.openFileOnDefaultProgram(fImg);
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

        alUpajjhayaList = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadList();
        cbUpajjhaya.getItems().clear();
        cbUpajjhaya.getItems().addAll(alUpajjhayaList);
    }
}
