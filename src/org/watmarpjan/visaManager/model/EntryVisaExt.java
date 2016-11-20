/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class EntryVisaExt
{

    private SimpleStringProperty extNumber;
    private SimpleStringProperty expiryDate;
    private LocalDate ldExpiryDate;

    public EntryVisaExt(String extNumber, Date dExpiryDate)
    {
        this.extNumber = new SimpleStringProperty(extNumber);

        ldExpiryDate = Util.convertDateToLocalDate(dExpiryDate);
        this.expiryDate = new SimpleStringProperty(ldExpiryDate.format(Util.DEFAULT_DATE_FORMAT));
    }

    public String getExtNumber()
    {
        return extNumber.get();
    }

    public String getExpiryDate()
    {
        return expiryDate.get();
    }

    public LocalDate getLdExpiryDate()
    {
        return ldExpiryDate;
    }

    public void setExtNumber(String extNumber)
    {
        this.extNumber.set(extNumber);
    }

    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate.set(expiryDate);
    }

}
