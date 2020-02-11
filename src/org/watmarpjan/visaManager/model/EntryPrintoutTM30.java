/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.PrintoutTm30;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class EntryPrintoutTM30 {

    private final SimpleStringProperty pNotifDate;
    private final SimpleStringProperty pNicknameResidenceMonastery;
    private final SimpleStringProperty pListMonasticNickname;

    private String strListMonasticNickname;
    
    private PrintoutTm30 objTM30;

    public EntryPrintoutTM30(PrintoutTm30 objTM30)
    {
        ArrayList<MonasticProfile> listMonasticProfile;
        MonasticProfile mp;
        LocalDate ldNotifDate;
        String monasteryNickname = "";
        
        this.objTM30 = objTM30;
        
        ldNotifDate = Util.convertDateToLocalDate(objTM30.getNotifDate());
        this.pNotifDate = new SimpleStringProperty(ldNotifDate.format(Util.DEFAULT_DATE_FORMAT));

        strListMonasticNickname = "";
        
        listMonasticProfile = new ArrayList<>();
        if (objTM30.getMonasticProfileSet() != null)
        {
            listMonasticProfile.addAll(objTM30.getMonasticProfileSet());
            
            //all monastic profiles in one report should have the same residence monastery
            //so can retrieve the monastery from the first one
            mp = listMonasticProfile.get(0);
            monasteryNickname = mp.getMonasteryResidingAt().getMonasteryNickname(); 
                    
            for (int i = 0; i < listMonasticProfile.size(); i++)
            {
                mp = listMonasticProfile.get(i);

                strListMonasticNickname += mp.getNickname();
                if (i < (listMonasticProfile.size() - 1))
                {
                    strListMonasticNickname += ", ";
                }
            }
        }

        this.pNicknameResidenceMonastery = new SimpleStringProperty(monasteryNickname);
        this.pListMonasticNickname = new SimpleStringProperty(strListMonasticNickname);
    }

    public String getPNotifDate()
    {
        return pNotifDate.get();
    }

    public String getPNicknameResidenceMonastery()
    {
        return pNicknameResidenceMonastery.get();
    }
    
    public String getPListMonasticNickname()
    {
        return pListMonasticNickname.get();
    }

    public PrintoutTm30 getPrintoutTM30()
    {
        return objTM30;
    }
    
    
}
