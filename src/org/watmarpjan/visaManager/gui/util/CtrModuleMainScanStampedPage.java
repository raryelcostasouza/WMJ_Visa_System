/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import org.watmarpjan.visaManager.gui.panel.CtrModuleGenericScanStampedPage;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author adhipanyo
 */
public class CtrModuleMainScanStampedPage extends CtrModuleGenericScanStampedPage
{
    private final RadioButton rbArriveStamp;
    private final RadioButton rbVisa;
    private final RadioButton rbLastVisaExt;
    
    
    //used for managing the control of the components for the main scans (arrive stamp, visa and visa extension)
    public CtrModuleMainScanStampedPage(Button bSelectFile, Button bArchive, TextField tfLeftPageNumber, TextField tfRightPageNumber, RadioButton rbArriveStamp, RadioButton rbVisa, RadioButton rbLastVisaExt)
    {
        super(bSelectFile, bArchive, tfLeftPageNumber, tfRightPageNumber);
        
        this.rbArriveStamp = rbArriveStamp;
        this.rbVisa = rbVisa;
        this.rbLastVisaExt = rbLastVisaExt; 
    }
    
    public void reset()
    {
        super.reset();
        
        rbArriveStamp.setDisable(true);
        rbVisa.setDisable(true);
        rbLastVisaExt.setDisable(true);
        
        rbArriveStamp.setSelected(false);
        rbVisa.setSelected(false);
        rbLastVisaExt.setSelected(false);
    }
    
    public void actionUnlockEdit()
    {
        super.actionUnlockEdit();
        
        rbArriveStamp.setDisable(false);
        rbLastVisaExt.setDisable(false);
        rbVisa.setDisable(false);
    }
    
    public void actionLockEdit()
    {
        super.actionLockEdit();
        
        rbArriveStamp.setDisable(true);
        rbLastVisaExt.setDisable(true);
        rbVisa.setDisable(true);
    }
    
    public RadioButton getRbArriveStamp()
    {
        return rbArriveStamp;
    }

    public RadioButton getRbVisa()
    {
        return rbVisa;
    }

    public RadioButton getRbLastVisaExt()
    {
        return rbLastVisaExt;
    }

}
