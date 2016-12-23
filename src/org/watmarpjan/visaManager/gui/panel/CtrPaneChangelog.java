/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.BlockMonasticSelection;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author wmj_user
 */
public class CtrPaneChangelog extends AbstractChildPaneController
{

    @FXML
    private Button bAddEntry;

    @FXML
    private ComboBox<String> cbMonastic;

    @FXML
    private TitledPane tpRegister90D;
    @FXML
    private TreeView<String> tvRegister90D;
    @FXML
    private BlockMonasticSelection cs90D;

    @FXML
    private TitledPane tpVisaExtension;
    @FXML
    private TreeView<String> tvVisaExtension;
    @FXML
    private BlockMonasticSelection csVisaExtension;

    @FXML
    private TitledPane tpEntryReentry;
    @FXML
    private TreeView<String> tvEntryReentry;
    @FXML
    private BlockMonasticSelection csEntryReentry;

    @FXML
    private TitledPane tpAddRenewPassport;
    @FXML
    private TreeView<String> tvAddRenewPassport;
    @FXML
    private BlockMonasticSelection csAddRenewPassport;

    @FXML
    private TitledPane tpTM30;
    @FXML
    private TreeView<String> tvTM30;
    @FXML
    private BlockMonasticSelection csTM30;

    @FXML
    private TitledPane tpAddNewMonastic;
    @FXML
    private TreeView<String> tvAddNewMonastic;
    @FXML
    private BlockMonasticSelection csAddNewMonastic;

    @FXML
    private TitledPane tpUpdatedMonastic;
    @FXML
    private TreeView<String> tvUpdatedMonastic;
    @FXML
    private BlockMonasticSelection csUpdatedMonastic;

    @FXML
    private TitledPane tpUpdatedPassport;
    @FXML
    private TreeView<String> tvUpdatedPassport;
    @FXML
    private BlockMonasticSelection csUpdatedPassport;

    @FXML
    private TitledPane tpUpdatedBysuddhi;
    @FXML
    private TreeView<String> tvUpdatedBysuddhi;
    @FXML
    private BlockMonasticSelection csUpdatedBysuddhi;

    @FXML
    private TitledPane tpNewPassportScans;
    @FXML
    private TreeView<String> tvNewPassportScans;
    @FXML
    private BlockMonasticSelection csNewPassportScans;

    @FXML
    private TitledPane tpNewBysuddhiScans;
    @FXML
    private TreeView<String> tvNewBysuddhiScans;
    @FXML
    private BlockMonasticSelection csNewBysuddhiScans;

    @FXML
    private TitledPane tpUpdateMonastery;
    @FXML
    private TreeView<String> tvUpdateMonastery;
    @FXML
    private BlockMonasticSelection csUpdateMonastery;

    @FXML
    private TextArea taOptionalComment;

    @FXML
    private TextArea taChangelogPreview;

    private ArrayList<BlockMonasticSelection> listChangelogSections;

    @Override
    public void init()
    {
        listChangelogSections = new ArrayList<>();
        cs90D = new BlockMonasticSelection(tvRegister90D, tpRegister90D);
        csAddNewMonastic = new BlockMonasticSelection(tvAddNewMonastic, tpAddNewMonastic);
        csAddRenewPassport = new BlockMonasticSelection(tvAddRenewPassport, tpAddRenewPassport);
        csEntryReentry = new BlockMonasticSelection(tvEntryReentry, tpEntryReentry);
        csNewBysuddhiScans = new BlockMonasticSelection(tvNewBysuddhiScans, tpNewBysuddhiScans);
        csNewPassportScans = new BlockMonasticSelection(tvNewPassportScans, tpNewPassportScans);
        csTM30 = new BlockMonasticSelection(tvTM30, tpTM30);

        csUpdateMonastery = new BlockMonasticSelection(tvUpdateMonastery, tpUpdateMonastery);
        csUpdatedBysuddhi = new BlockMonasticSelection(tvUpdatedBysuddhi, tpUpdatedBysuddhi);
        csUpdatedMonastic = new BlockMonasticSelection(tvUpdatedMonastic, tpUpdatedMonastic);
        csUpdatedPassport = new BlockMonasticSelection(tvUpdatedPassport, tpUpdatedPassport);
        csVisaExtension = new BlockMonasticSelection(tvVisaExtension, tpVisaExtension);

        listChangelogSections.add(cs90D);
        listChangelogSections.add(csAddNewMonastic);
        listChangelogSections.add(csAddRenewPassport);
        listChangelogSections.add(csEntryReentry);
        listChangelogSections.add(csNewBysuddhiScans);
        listChangelogSections.add(csNewPassportScans);
        listChangelogSections.add(csTM30);
        listChangelogSections.add(csUpdateMonastery);
        listChangelogSections.add(csUpdatedBysuddhi);
        listChangelogSections.add(csUpdatedMonastic);
        listChangelogSections.add(csUpdatedPassport);
        listChangelogSections.add(csVisaExtension);
    }

    public void fillData()
    {
        ArrayList<String> nickNameList;

        nickNameList = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileVisaManager();
        cbMonastic.getItems().clear();
        cbMonastic.getItems().addAll(nickNameList);
        cbMonastic.setValue(null);

        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(cs90D);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csAddNewMonastic);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csAddRenewPassport);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csEntryReentry);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csNewBysuddhiScans);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csNewPassportScans);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csTM30);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csUpdateMonastery);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csUpdatedBysuddhi);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csUpdatedMonastic);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csUpdatedPassport);
        ctrGUIMain.getCtrGUISharedUtil().loadMonasticTree(csVisaExtension);

        taOptionalComment.clear();
        taChangelogPreview.setText(CtrFileOperation.loadChangelog());
    }

    private String generateChangelogSection(BlockMonasticSelection objBMS)
    {
        boolean haveCheckboxSelected;
        String logSection;

        logSection = "";
        haveCheckboxSelected = false;
        for (CheckBoxTreeItem<String> objCB : objBMS.getListCheckBoxMonastics())
        {
            if (objCB.isSelected())
            {
                //before generating the monastic name list for
                //the section need to generate the title of the section
                if (!haveCheckboxSelected)
                {
                    logSection += "         " + objBMS.getParentTitledPane().getText() + " for ";
                    haveCheckboxSelected = true;
                }
                logSection += objCB.getValue() + ", ";
            }
        }

        //if the changelog section has output, removes the last comma and add a dot
        if (haveCheckboxSelected)
        {
            logSection = logSection.substring(0, logSection.length() - 2);
            logSection += ".";
        }

        return logSection;
    }

    @FXML
    void actionAddNewEntry(ActionEvent ae)
    {
        String logLine, logSection;

        if (validateFields())
        {
            logLine = LocalDateTime.now().format(Util.CHANGELOG_DATE_TIME_FORMAT_) + " ";
            logLine += cbMonastic.getValue() + ":";

            for (BlockMonasticSelection objChangelogSection : listChangelogSections)
            {
                logSection = generateChangelogSection(objChangelogSection);
                if (!logSection.equals(""))
                {
                    logLine += "\n" + logSection;
                }
            }

            //if there is a optional comment
            if (!taOptionalComment.getText().equals(""))
            {
                logLine += "\n         Remark: ";
                logLine += "\n                 " + taOptionalComment.getText().replaceAll("\n", "\n                 ");
            }

            logLine += "\n";

            CtrFileOperation.saveChangelog(logLine);
            fillData();
        }

    }

    @FXML
    void actionSelectedVisaManagerUser(ActionEvent ae)
    {
        if (cbMonastic.getValue() != null)
        {
            bAddEntry.setDisable(false);
        }
        else
        {
            bAddEntry.setDisable(true);
        }
    }

    private boolean validateFields()
    {
        return (cbMonastic.getValue() != null);
    }

}
