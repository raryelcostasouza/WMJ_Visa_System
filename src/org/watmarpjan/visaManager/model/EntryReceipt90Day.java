/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author wmj_user
 */
public class EntryReceipt90Day 
{
    private final SimpleStringProperty receiptDate;
    private final SimpleStringProperty receiptType;
    private final SimpleStringProperty refNumber;
    
    private final LocalDate ldReceiptDate;

    public EntryReceipt90Day(LocalDate pLdReceiptDate, String strRefNumber, String strReceiptType)
    {
        this.receiptDate = new SimpleStringProperty(pLdReceiptDate.format(Util.DEFAULT_DATE_FORMAT));
        this.refNumber = new SimpleStringProperty(strRefNumber);
        this.receiptType = new SimpleStringProperty(strReceiptType);
        
        this.ldReceiptDate = pLdReceiptDate;
    }

    public String getReceiptDate()
    {
        return receiptDate.get();
    }

    public String getRefNumber()
    {
        return refNumber.get();
    }

    public String getReceiptType()
    {
        return receiptType.get();
    }

    public LocalDate getLdReceiptDate()
    {
        return ldReceiptDate;
    }
    

}
