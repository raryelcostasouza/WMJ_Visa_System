/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author pm.dell
 */
public class EntryPrintedDocStock
{

    private final SimpleStringProperty monasticName;
    private final SimpleStringProperty nSigned90DayForms;
    private final SimpleBooleanProperty signedPhotocopies;
    private final SimpleStringProperty nPrintedPhotos;

    public static final String STR_MISSING = "MISSING";

    public EntryPrintedDocStock(String monasticName, Integer nSigned90DayForms, Boolean signedPhotocopies, Integer nPrintedPhotos)
    {
        this.monasticName = new SimpleStringProperty(monasticName);
        if (nSigned90DayForms == null || nSigned90DayForms == 0)
        {
            this.nSigned90DayForms = new SimpleStringProperty(STR_MISSING);
        }
        else
        {
            this.nSigned90DayForms = new SimpleStringProperty(nSigned90DayForms + "");
        }

        if (signedPhotocopies != null)
        {
            this.signedPhotocopies = new SimpleBooleanProperty(signedPhotocopies);
        }
        else
        {
            this.signedPhotocopies = new SimpleBooleanProperty(false);
        }

        if (nPrintedPhotos == null || nPrintedPhotos == 0)
        {
            this.nPrintedPhotos = new SimpleStringProperty(STR_MISSING);
        }
        else
        {
            this.nPrintedPhotos = new SimpleStringProperty(nPrintedPhotos + "");
        }
    }

    public String getMonasticName()
    {
        return monasticName.get();
    }

    public String getNSigned90DayForms()
    {
        return nSigned90DayForms.get();
    }

    public Boolean getSignedPhotocopies()
    {
        return signedPhotocopies.get();
    }

    public String getNPrintedPhotos()
    {
        return nPrintedPhotos.get();
    }

    public BooleanProperty signedPhotocopiesProperty()
    {
        return signedPhotocopies;
    }

}
