/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.util;

import org.watmarpjan.visaManager.model.hibernate.Monastery;

/**
 *
 * @author adhipanyo
 */
public class MonasteryUtil
{

    public static String getStringWatAddrFull(Monastery m, boolean includeCountry, boolean includeTambon)
    {
        String sAddr = "";
        
        sAddr =  m.getMonasteryName();
        if (m.getAddrCountry().equals("THAILAND"))
        {
            if (includeTambon)
            {
                sAddr += " ต." +m.getAddrTambon();
            }
            sAddr +=  " อ." + m.getAddrAmpher() + " จ." + m.getAddrJangwat();
        }
        else
        {
            if (includeTambon)
            {
                sAddr += " " +m.getAddrTambon();
            }
            
            sAddr += " " + m.getAddrAmpher() + " " + m.getAddrJangwat();
            
            if (includeCountry)
            {
                sAddr += " " + m.getAddrCountry();
            } 
        }
        return sAddr;
    }
}
