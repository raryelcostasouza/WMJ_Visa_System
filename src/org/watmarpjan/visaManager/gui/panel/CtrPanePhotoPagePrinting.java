/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrForm;

/**
 *
 * @author wmj_user
 */
public class CtrPanePhotoPagePrinting extends AbstractChildPaneController
{

    @FXML
    private ComboBox<String> cbMonastic1;

    @FXML
    private ComboBox<String> cbMonastic2;

    @FXML
    private Button bPreview;
    @FXML
    private Button bPrint;

    @Override
    public void init()
    {
        bPreview.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bPrint.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
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
        ctrGUIMain.getCtrMain().getCtrForm().generatePhotoPage(cbMonastic1.getValue(), cbMonastic2.getValue(), CtrForm.OPTION_PREVIEW_FORM);
        clearSelection();
    }
    
    @FXML
    void actionPrintPhotoPage(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePhotoPage(cbMonastic1.getValue(), cbMonastic2.getValue(), CtrForm.OPTION_PRINT_FORM);
        clearSelection();
    }
    
    private void setDisablePrintButtons(boolean newStatus)
    {
        bPreview.setDisable(newStatus);
        bPrint.setDisable(newStatus);
    }

}
