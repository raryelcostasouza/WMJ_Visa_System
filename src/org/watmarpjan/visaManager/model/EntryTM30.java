/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.util.Date;

/**
 *
 * @author WMJ_user
 */
public class EntryTM30
{

    private Date dateNotif;
    private String monasticNickname;

    public EntryTM30(Date dateNotif, String monasticNickname)
    {
        this.dateNotif = dateNotif;
        this.monasticNickname = monasticNickname;
    }

    public Date getDateNotif()
    {
        return dateNotif;
    }

    public String getMonasticNickname()
    {
        return monasticNickname;
    }
}
