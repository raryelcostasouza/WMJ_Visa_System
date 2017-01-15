/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.watmarpjan.visaManager.control.CtrPDF;

/**
 *
 * @author wmj_user
 */
public class CtrPanePhotoPagePrinting extends AbstractPDFPreviewPrintController
{

    @FXML
    private ComboBox<String> cbMonastic1;

    @FXML
    private ComboBox<String> cbMonastic2;

    @Override
    public void init()
    {
        super.init();
    }

    public void fillData()
    {
        ArrayList<String> listMonastic;

        listMonastic = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileNicknameList(true);
        
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
                cbMonastic2.setDisable(false);
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
        ctrGUIMain.getCtrMain().getCtrForm().generatePhotoPage(cbMonastic1.getValue(), cbMonastic2.getValue(), CtrPDF.OPTION_PREVIEW_FORM);
        clearSelection();
    }
    
    @FXML
    void actionPrintPhotoPage(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePhotoPage(cbMonastic1.getValue(), cbMonastic2.getValue(), CtrPDF.OPTION_PRINT_FORM);
        clearSelection();
    }
    
    private void setDisablePrintButtons(boolean newStatus)
    {
        bPreview.setDisable(newStatus);
        bPrint.setDisable(newStatus);
    }

}
