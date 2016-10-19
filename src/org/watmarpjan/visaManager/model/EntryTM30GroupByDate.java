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
public class EntryTM30GroupByDate
{

    private SimpleStringProperty pNotifDate;
    private SimpleStringProperty pListMonasticNickname;

    private LocalDate ldNotifDate;
    private Date dateNotif;
    private String strListMonasticNickname;

    public EntryTM30GroupByDate(Date dateNotif)
    {
        strListMonasticNickname = "";
        this.dateNotif = dateNotif;
        this.ldNotifDate = Util.convertDateToLocalDate(dateNotif);

        this.pNotifDate = new SimpleStringProperty(ldNotifDate.format(Util.DEFAULT_DATE_FORMAT));
        this.pListMonasticNickname = new SimpleStringProperty(strListMonasticNickname);

    }

    public EntryTM30GroupByDate(LocalDate ldNotif)
    {
        this(Util.convertLocalDateToDate(ldNotif));
    }

    public String getPNotifDate()
    {
        return pNotifDate.get();
    }

    public String getPListMonasticNickname()
    {
        return pListMonasticNickname.get();
    }

    public LocalDate getaLdNotifDate()
    {
        return ldNotifDate;
    }

    public Date getDateNotif()
    {
        return dateNotif;
    }

    public String getStrListMonasticNickname()
    {
        return strListMonasticNickname;
    }

    public void addNickname(String pNickname)
    {
        pListMonasticNickname.set(pListMonasticNickname.get() + pNickname + ", ");
    }

    public void closeNickNameList()
    {
        String strListNickname;

        //removes the last comma from the string
        strListNickname = pListMonasticNickname.get();
        pListMonasticNickname.set(strListNickname.substring(0, strListNickname.length() - 2));
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
        {
            return false;
        } else if (this.getDateNotif().equals(((EntryTM30GroupByDate) o).getDateNotif()))
        {
            return true;
        } else
        {
            return false;
        }
    }

}
