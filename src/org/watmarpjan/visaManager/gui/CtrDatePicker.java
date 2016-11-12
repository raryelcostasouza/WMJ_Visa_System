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
        Locale.setDefault(Locale.UK);
        alDatePicker = new ArrayList<>();
    }

    public void registerDatePicker(DatePicker dp)
    {
        alDatePicker.add(dp);
    }

    public void setThaiChronology()
    {
        Locale.setDefault(new Locale("th"));
        for (DatePicker dp : alDatePicker)
        {
            dp.setChronology(ThaiBuddhistChronology.INSTANCE);
        }
    }

    public void setISOChronology()
    {
        Locale.setDefault(Locale.UK);
        for (DatePicker dp : alDatePicker)
        {
            dp.setChronology(IsoChronology.INSTANCE);
        }
    }

}
