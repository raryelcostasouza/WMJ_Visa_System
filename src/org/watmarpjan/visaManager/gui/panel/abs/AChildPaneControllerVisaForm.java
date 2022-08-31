/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.gui.panel.abs;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.PrintoutTm30;

/**
 *
 * @author raryel
 */
public abstract class AChildPaneControllerVisaForm extends AChildPaneController
{
    
    private File getPrawatTemplate(MonasticProfile p)
    {
        
        if ((p.getPatimokkhaChanter()== null) || (!p.getPatimokkhaChanter()))
        {
            return AppFiles.getFormPrawat(p.getMonasteryResidingAt().getMonasteryNickname());
        }
        else
        {
            return AppFiles.getFormPrawatPatimokkhaChanter(p.getMonasteryResidingAt().getMonasteryNickname());
        }
    }
    
    @FXML
    void actionPreviewAckOverstayPenalties(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormOverstay(p.getMonasteryResidingAt().getMonasteryNickname()), p, CtrPDF.OPTION_PREVIEW_FORM, false);
    }

    @FXML
    void actionPreviewSTM2AckConditions(ActionEvent ae)
    {
        MonasticProfile p;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().fillForm(AppFiles.getFormSTM2AckConditions(p.getMonasteryResidingAt().getMonasteryNickname()), p, CtrPDF.OPTION_PREVIEW_FORM, false);
    }
    
    @FXML
    void actionPreviewTM30NotifResidence(ActionEvent ae)
    {
        MonasticProfile p;
        PrintoutTm30 objTM30;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        objTM30 = p.getPrintoutTm30();

        if (objTM30 != null)
        {
            CtrFileOperation.openFileOnDefaultProgram(AppFiles.getPrintoutTM30(objTM30));
        }
        else
        {
            CtrAlertDialog.errorDialog("No TM30 Printout registered for this monastic profile yet.");
        }
    }
}
