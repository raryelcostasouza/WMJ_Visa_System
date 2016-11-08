/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

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

    public FieldsPaneScanContent(Button bSelectFile, Button bArchive, TextField tfLeftPageNumber, TextField tfRightPageNumber, RadioButton rbArriveStamp, RadioButton rbVisa, RadioButton rbLastVisaExt)
    {
        this.bSelectFile = bSelectFile;
        this.bArchive = bArchive;
        this.tfLeftPageNumber = tfLeftPageNumber;
        this.tfRightPageNumber = tfRightPageNumber;
        this.rbArriveStamp = rbArriveStamp;
        this.rbVisa = rbVisa;
        this.rbLastVisaExt = rbLastVisaExt;
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

    public void reset(boolean lockStatus)
    {
        if (!lockStatus)
        {
            tfLeftPageNumber.setEditable(true);
            rbArriveStamp.setDisable(false);
            rbVisa.setDisable(false);
            rbLastVisaExt.setDisable(false);

            tfLeftPageNumber.setText("");
            tfRightPageNumber.setText("");

            rbArriveStamp.setSelected(false);
            rbVisa.setSelected(false);
            rbLastVisaExt.setSelected(false);

            bSelectFile.setDisable(false);
            bArchive.setDisable(true);
        }
        else
        {
            tfLeftPageNumber.setEditable(false);
            rbArriveStamp.setDisable(true);
            rbVisa.setDisable(true);
            rbLastVisaExt.setDisable(true);

            tfLeftPageNumber.setText("");
            tfRightPageNumber.setText("");

            rbArriveStamp.setDisable(true);
            rbVisa.setDisable(true);
            rbLastVisaExt.setDisable(true);

            bSelectFile.setDisable(true);
            bArchive.setDisable(true);
        }

    }

    public void setStatusScan(boolean haveContent)
    {
        //if there is scan content to show on this pane
        if (haveContent)
        {
            bSelectFile.setDisable(true);
            bArchive.setDisable(false);

            rbArriveStamp.setDisable(true);
            rbVisa.setDisable(true);
            rbLastVisaExt.setDisable(true);

            tfLeftPageNumber.setEditable(false);

        }
        else
        {

        }
    }

}
