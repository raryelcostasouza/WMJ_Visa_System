/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.watmarpjan.visaManager.gui.panel.CtrPaneWorkflowVisaExt;

/**
 *
 * @author WMJ_user
 */
public class EntryWorkflowVisaExt
{

    private SimpleStringProperty monastic;
    private SimpleStringProperty snpPrawat;
    private SimpleStringProperty snpLetter;
    private SimpleStringProperty snpPhotocopies;

    private SimpleStringProperty immApprovalSNP;
    private SimpleStringProperty immTM7;
    private SimpleStringProperty immLetter;
    private SimpleStringProperty immPhotocopies;
    private SimpleStringProperty immExtra;

    public static final String STATUS_MISSING = "Missing";
    public static final String STATUS_PRINTED = "Printed";
    public static final String STATUS_SIGNED_MONASTIC = "Signed by monastic";

    public EntryWorkflowVisaExt(String monastic, String snpPrawat, String snpLetter,
            String photocopiesSNP, String immApprovalSNP, String immTM7,
            String immLetter, String photocopiesIMM, Boolean immExtra)
    {

        this.monastic = new SimpleStringProperty(monastic);
        if (snpPrawat != null)
        {
            this.snpPrawat = new SimpleStringProperty(snpPrawat);
        }
        else
        {
            this.snpPrawat = new SimpleStringProperty(STATUS_MISSING);
        }

        if (snpLetter != null)
        {
            this.snpLetter = new SimpleStringProperty(snpLetter);
        }
        else
        {
            this.snpLetter = new SimpleStringProperty(STATUS_MISSING);
        }

        if (photocopiesSNP != null)
        {
            this.snpPhotocopies = new SimpleStringProperty(photocopiesSNP);
        }
        else
        {
            this.snpPhotocopies = new SimpleStringProperty(STATUS_MISSING);
        }

        if (immApprovalSNP != null)
        {
            this.immApprovalSNP = new SimpleStringProperty(immApprovalSNP);
        }
        else
        {
            this.immApprovalSNP = new SimpleStringProperty(STATUS_MISSING);
        }

        if (immTM7 != null)
        {
            this.immTM7 = new SimpleStringProperty(immTM7);
        }
        else
        {
            this.immTM7 = new SimpleStringProperty(STATUS_MISSING);
        }

        if (immLetter != null)
        {
            this.immLetter = new SimpleStringProperty(immLetter);
        }
        else
        {
            this.immLetter = new SimpleStringProperty(STATUS_MISSING);
        }

        if (photocopiesIMM != null)
        {
            this.immPhotocopies = new SimpleStringProperty(photocopiesIMM);
        }
        else
        {
            this.immPhotocopies = new SimpleStringProperty(STATUS_MISSING);
        }

        if (immExtra != null && immExtra)
        {
            this.immExtra = new SimpleStringProperty(STATUS_PRINTED);

        }
        else
        {
            this.immExtra = new SimpleStringProperty(STATUS_MISSING);
        }

    }

    public String getMonastic()
    {
        return monastic.get();
    }

    public String getSnpPrawat()
    {
        return snpPrawat.get();
    }

    public String getSnpLetter()
    {
        return snpLetter.get();
    }

    public String getSnpPhotocopies()
    {
        return snpPhotocopies.get();
    }

    public String getImmApprovalSNP()
    {
        return immApprovalSNP.get();
    }

    public String getImmTM7()
    {
        return immTM7.get();
    }

    public String getImmLetter()
    {
        return immLetter.get();
    }

    public String getImmPhotocopies()
    {
        return immPhotocopies.get();
    }

    public String getImmExtra()
    {
        return immExtra.get();
    }

}
