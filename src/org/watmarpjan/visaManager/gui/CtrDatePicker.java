/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.time.chrono.IsoChronology;
import java.time.chrono.ThaiBuddhistChronology;
import java.util.ArrayList;
import java.util.Locale;
import javafx.scene.control.DatePicker;

/**
 *
 * @author WMJ_user
 */
public class CtrDatePicker
{

    private ArrayList<DatePicker> alDatePicker;

    public CtrDatePicker()
    {
        //english locale for the months name appear in english
        //Locale.setDefault(Locale.US);
        alDatePicker = new ArrayList<>();
    }

    public void registerDatePicker(DatePicker dp)
    {
        alDatePicker.add(dp);
    }

    public void setThaiChronology()
    {
        for (DatePicker dp : alDatePicker)
        {
            dp.setChronology(ThaiBuddhistChronology.INSTANCE);
        }
    }

    public void setISOChronology()
    {
        for (DatePicker dp : alDatePicker)
        {
            dp.setChronology(IsoChronology.INSTANCE);
        }
    }

}
