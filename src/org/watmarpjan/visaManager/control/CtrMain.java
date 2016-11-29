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
    private final CtrForm ctrForm;
    private final CtrVisa ctrVisa;
    private final CtrPassportScan ctrPassportScan;
    private final CtrUpajjhaya ctrUpajjhaya;
    private final CtrPrintoutTM30 ctrPrintoutTM30;

    public CtrMain(CtrGUIMain ctrGUI)
    {
        ctrDB = new CtrDatabase();
        ctrProfile = new CtrMonasticProfile(ctrDB);
        ctrMonastery = new CtrMonastery(ctrDB);
        ctrUpajjhaya = new CtrUpajjhaya(ctrDB);
        ctrVisa = new CtrVisa(ctrDB);
        ctrPassportScan = new CtrPassportScan(ctrDB);
        ctrForm = new CtrForm(this);
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

    public CtrForm getCtrForm()
    {
        return ctrForm;
    }

    public CtrDatabase getCtrDB()
    {
        return ctrDB;
    }

    public CtrMonasticProfile getCtrProfile()
    {
        return ctrProfile;
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

    public CtrPassportScan getCtrPassportScan()
    {
        return ctrPassportScan;
    }
}
