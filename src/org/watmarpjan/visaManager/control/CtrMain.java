/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.util.ArrayList;
import org.watmarpjan.visaManager.gui.panel.CtrGUIMain;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 *
 */
public class CtrMain
{

    private final CtrDatabase ctrDB;
    private final CtrMonasticProfile ctrProfile;
    private final CtrMonastery ctrMonastery;
    private final CtrPDF ctrPDF;
    private final CtrEmbassy ctrEmbassy;
    private final CtrVisa ctrVisa;
    private final CtrUpajjhaya ctrUpajjhaya;
    private final CtrPrintoutTM30 ctrPrintoutTM30;
    private final CtrConfigFiles ctrConfig;

    public CtrMain(CtrGUIMain ctrGUI)
    {
        ctrDB = new CtrDatabase();
        ctrConfig = new CtrConfigFiles();
        ctrProfile = new CtrMonasticProfile(ctrDB);
        ctrMonastery = new CtrMonastery(ctrDB);
        ctrUpajjhaya = new CtrUpajjhaya(ctrDB);
        ctrEmbassy = new CtrEmbassy(ctrDB);
        ctrVisa = new CtrVisa(ctrDB);
        ctrPDF = new CtrPDF(this);
        ctrPrintoutTM30 = new CtrPrintoutTM30(ctrDB, ctrProfile);
    }
    
    public ArrayList<String> loadListCountry()
    {
        ArrayList<String> listCountryMerged, listCountryDistinct;
        
        listCountryMerged = new ArrayList<>();
        listCountryMerged.addAll(ctrProfile.loadListBirthCountry());
        listCountryMerged.addAll(ctrProfile.loadListPassportCountry());
        listCountryMerged.addAll(ctrProfile.loadListPreviousResidenceCountry());
        listCountryMerged.addAll(ctrMonastery.loadListMonasteryCountry());

        listCountryMerged.sort(null);
        
        listCountryDistinct = Util.filterDistinctElement(listCountryMerged);
        
        return listCountryDistinct;
    }

    public CtrPrintoutTM30 getCtrPrintoutTM30()
    {
        return ctrPrintoutTM30;
    }

    public CtrPDF getCtrPDF()
    {
        return ctrPDF;
    }

    public CtrDatabase getCtrDB()
    {
        return ctrDB;
    }

    public CtrMonasticProfile getCtrProfile()
    {
        return ctrProfile;
    }

    public CtrEmbassy getCtrEmbassy()
    {
        return ctrEmbassy;
    }
    
    public CtrMonastery getCtrMonastery()
    {
        return ctrMonastery;
    }

    public CtrVisa getCtrVisa()
    {
        return ctrVisa;
    }

    public CtrUpajjhaya getCtrUpajjhaya()
    {
        return ctrUpajjhaya;
    }

    public CtrConfigFiles getCtrConfig()
    {
        return ctrConfig;
    }
}
