/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.util;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author WMJ_user
 */
public class FieldsPaneScanContent
{

    private final Button bSelectFile;
    private final Button bArchive;
    private final TextField tfLeftPageNumber;
    private final TextField tfRightPageNumber;
    private final RadioButton rbArriveStamp;
    private final RadioButton rbVisa;
    private final RadioButton rbLastVisaExt;

    private boolean scanContent;

    public FieldsPaneScanContent(Button bSelectFile, Button bArchive, TextField tfLeftPageNumber, TextField tfRightPageNumber, RadioButton rbArriveStamp, RadioButton rbVisa, RadioButton rbLastVisaExt)
    {
        this.bSelectFile = bSelectFile;
        this.bArchive = bArchive;
        this.tfLeftPageNumber = tfLeftPageNumber;
        this.tfRightPageNumber = tfRightPageNumber;
        this.rbArriveStamp = rbArriveStamp;
        this.rbVisa = rbVisa;
        this.rbLastVisaExt = rbLastVisaExt;
        scanContent = false;
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
        return tfLeftPageNumber;
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

    public void reset()
    {
        scanContent = false;

        tfLeftPageNumber.setEditable(false);
        rbArriveStamp.setDisable(true);
        rbVisa.setDisable(true);
        rbLastVisaExt.setDisable(true);

        tfLeftPageNumber.setText("");
        tfRightPageNumber.setText("");

        rbArriveStamp.setDisable(true);
        rbVisa.setDisable(true);
        rbLastVisaExt.setDisable(true);

        rbArriveStamp.setSelected(false);
        rbVisa.setSelected(false);
        rbLastVisaExt.setSelected(false);

        bSelectFile.setDisable(true);
        bArchive.setDisable(true);
    }

    //if there is scan content to show on this pane
    public void setPaneContentNotEmpty()
    {
        scanContent = true;
        tfLeftPageNumber.setEditable(false);
    }
    
    public void setPaneContentEmpty()
    {
        scanContent = false;
        tfLeftPageNumber.setEditable(true);
    }

    public void actionUnlockEdit()
    {
        rbArriveStamp.setDisable(false);
        rbLastVisaExt.setDisable(false);
        rbVisa.setDisable(false);

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
        rbArriveStamp.setDisable(true);
        rbLastVisaExt.setDisable(true);
        rbVisa.setDisable(true);

        if (scanContent)
        {
            bArchive.setDisable(true);
        } else
        {
            bSelectFile.setDisable(true);
        }
    }

}
