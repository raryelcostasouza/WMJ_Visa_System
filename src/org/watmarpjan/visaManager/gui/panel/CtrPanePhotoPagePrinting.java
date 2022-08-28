/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerExportPDF;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.watmarpjan.visaManager.control.CtrPDF;

/**
 *
 * @author wmj_user
 */
public class CtrPanePhotoPagePrinting extends AChildPaneControllerExportPDF
{

    @FXML
    private ComboBox<String> cbMonastic1;

    @FXML
    private ComboBox<String> cbMonastic2;
    
    @FXML
    private RadioButton rbA4Paper;
    
    @FXML
    private RadioButton rb4x6Paper;
    
    @FXML
    private ToggleGroup tgPaperSetting;

    @Override
    public void init()
    {
        super.init();
    }

    public void fillData()
    {
        ArrayList<String> listMonastic;

        listMonastic = ctrGUIMain.getCtrMain().getCtrProfile().loadNicknameList(true);
        
        cbMonastic1.getItems().clear();
        cbMonastic1.getItems().addAll(listMonastic);
        
        cbMonastic2.getItems().clear();
        cbMonastic2.getItems().addAll(listMonastic);
        
        setDisablePrintButtons(true);
    }
    
    @FXML
    void actionSelectMonastic(ActionEvent ae)
    {
        if (ae.getSource().equals(cbMonastic1))
        {
            if (cbMonastic1.getValue() != null)
            {
                setDisablePrintButtons(false);
                //only enable second monastic in case of A4 Papers
                if (rbA4Paper.isSelected())
                {
                    cbMonastic2.setDisable(false);
                }
                
            }
        }
        else
        {
            if (cbMonastic2.getValue() != null)
            {
                setDisablePrintButtons(false);
            }
        }
    }
    
    @FXML
    void actionSelectPaperType(ActionEvent e)
    {
            if (rb4x6Paper.isSelected())
            {
                cbMonastic2.setDisable(true);
                cbMonastic2.setValue(null);
            }
            //only enables second monastic if the first is previously selected
            else if (cbMonastic1.getValue() != null)
            {
                cbMonastic2.setDisable(false);
            }
    }
    
        
    
    private void clearSelection()
    {
        cbMonastic1.setValue(null);
        cbMonastic2.setValue(null);
        
        cbMonastic2.setDisable(true);
        setDisablePrintButtons(true);
    }
    
    @FXML
    void actionPreviewPhotoPage(ActionEvent ae)
    {
        if (rbA4Paper.isSelected())
        {
            ctrGUIMain.getCtrMain().getCtrPDF().generatePhotoPageA4(cbMonastic1.getValue(), cbMonastic2.getValue(), CtrPDF.OPTION_PREVIEW_FORM);
        }
        else
        {
            ctrGUIMain.getCtrMain().getCtrPDF().generatePhotoPage4x6(cbMonastic1.getValue(), cbMonastic2.getValue(), CtrPDF.OPTION_PREVIEW_FORM);
        }
        
        clearSelection();
    }
    
//    @FXML
//    void actionPrintPhotoPage(ActionEvent ae)
//    {
//        ctrGUIMain.getCtrMain().getCtrPDF().generatePhotoPage(cbMonastic1.getValue(), cbMonastic2.getValue(), CtrPDF.OPTION_PRINT_FORM);
//        clearSelection();
//    }
    
    private void setDisablePrintButtons(boolean newStatus)
    {
        bPreview.setDisable(newStatus);
//        bPrint.setDisable(newStatus);
    }

}
