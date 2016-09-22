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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
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
public class CtrDialogSelectExtraScan extends AbstractChildPaneController implements IFormMonasticProfile
{

    @FXML
    private ImageView ivScan1;
    @FXML
    private ImageView ivScan2;
    @FXML
    private ImageView ivScan3;
    @FXML
    private ImageView ivNewScan;

    @FXML
    private ToggleGroup tgScan;
    @FXML
    private RadioButton rbPS1;
    @FXML
    private RadioButton rbPS2;
    @FXML
    private RadioButton rbPS3;
    @FXML
    private RadioButton rbNewScan;

    @FXML
    private Button bSelectScan;
    @FXML
    private Button bApply;

    private File fSelected;
    private Profile selectedProfile;

    public static final String OPTION_NEW_SCAN = "NEW_SCAN";
    public static final String OPTION_SCAN_1 = "SCAN_1";
    public static final String OPTION_SCAN_2 = "SCAN_2";
    public static final String OPTION_SCAN_3 = "SCAN_3";

    public void init(Dialog d)
    {

        d.setResultConverter(new Callback<ButtonType, AbstractResultDialogSelectScan>()
        {
            @Override
            public AbstractResultDialogSelectScan call(ButtonType dialogButton)
            {
                PassportScan ps;
                ArrayList<PassportScan> listPassportScan;

                if (dialogButton != null && dialogButton.equals(ButtonType.APPLY))
                {
                    listPassportScan = new ArrayList<>();
                    listPassportScan.addAll(selectedProfile.getPassportScanSet());

                    if (rbPS1.isSelected())
                    {
                        ps = listPassportScan.get(0);
                        //ps = ctrGUIMain.getCtrMain().getCtrPassportScan().getPassportScanByIndex(selectedProfile.getIdprofile(), 0);
                        return new ResultExistingScan(ps);
                    } else if (rbPS2.isSelected())
                    {
                        ps = listPassportScan.get(1);
                        //ps = ctrGUIMain.getCtrMain().getCtrPassportScan().getPassportScanByIndex(selectedProfile.getIdprofile(), 1);
                        return new ResultExistingScan(ps);
                    } else if (rbPS3.isSelected())
                    {
                        ps = listPassportScan.get(2);
                        //ps = ctrGUIMain.getCtrMain().getCtrPassportScan().getPassportScanByIndex(selectedProfile.getIdprofile(), 2);
                        return new ResultExistingScan(ps);
                    } else
                    {
                        return new ResultNewFileScan(fSelected);
                    }
                }
                return null;
            }
        });

    }

    @Override
    public void fillData(Profile p)
    {
        ArrayList<PassportScan> listPassportScans;

        selectedProfile = p;
        rbPS1.setDisable(true);
        rbPS2.setDisable(true);
        rbPS3.setDisable(true);

        ImgUtil.loadImageView(ivNewScan, ImgUtil.IMG_TYPE_PASSPORT, new File(""));

        if (!p.getPassportScanSet().isEmpty())
        {
            listPassportScans = new ArrayList<>();
            listPassportScans.addAll(p.getPassportScanSet());

            try
            {
                loadScanInfo(rbPS1, ivScan1, p, listPassportScans.get(0));
                loadScanInfo(rbPS2, ivScan2, p, listPassportScans.get(1));
                loadScanInfo(rbPS3, ivScan3, p, listPassportScans.get(2));
            } catch (IndexOutOfBoundsException ex)
            {

            }

        } else
        {
            loadIMGPreview(p, null, ivScan1);
            loadIMGPreview(p, null, ivScan2);
            loadIMGPreview(p, null, ivScan3);
        }

    }

    private void loadScanInfo(RadioButton rb, ImageView iv, Profile p, PassportScan ps)
    {
        if (ps != null)
        {
            rb.setDisable(false);
            rb.setText("Page: " + ps.getPageNumber());

        }
        loadIMGPreview(p, ps, iv);
    }

    private void loadIMGPreview(Profile p, PassportScan ps, ImageView iv)
    {
        File fExtraScan;

        fExtraScan = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), ps);
        ImgUtil.loadImageView(iv, ImgUtil.IMG_TYPE_PASSPORT, fExtraScan);
    }

    @FXML
    void actionSelectScan(ActionEvent ae)
    {
        fSelected = CtrFileOperation.selectFile("Select Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);
        if (fSelected != null)
        {
            ImgUtil.loadImageView(ivNewScan, ImgUtil.IMG_TYPE_PASSPORT, fSelected);
        }
    }

    @FXML
    void actionToggleRadioButton(ActionEvent ae)
    {
        if (rbNewScan.isSelected())
        {
            bSelectScan.setDisable(false);
        } else
        {
            bSelectScan.setDisable(true);
        }
    }

    @FXML
    void actionIMGClicked(MouseEvent me)
    {
        Profile p;
        PassportScan psVisaScan;
        File fImg;

        p = ctrGUIMain.getPaneSelectionController().getSelectedProfile();
        if (me.getSource().equals(ivNewScan))
        {
            if (fSelected != null)
            {
                ImgUtil.openClickedIMG(fSelected);
            }

        } else
        {
            if (me.getSource().equals(ivScan1))
            {
                psVisaScan = ctrGUIMain.getCtrMain().getCtrPassportScan().getPassportScanByIndex(p.getIdprofile(), 0);
            } else if (me.getSource().equals(ivScan2))
            {
                psVisaScan = ctrGUIMain.getCtrMain().getCtrPassportScan().getPassportScanByIndex(p.getIdprofile(), 1);
            } else
            {
                psVisaScan = ctrGUIMain.getCtrMain().getCtrPassportScan().getPassportScanByIndex(p.getIdprofile(), 2);
            }

            fImg = AppFiles.getExtraScan(p.getNickname(), p.getPassportNumber(), psVisaScan);
            ImgUtil.openClickedIMG(fImg);
        }

    }

    public File getNewScanFile()
    {
        return fSelected;
    }

}
