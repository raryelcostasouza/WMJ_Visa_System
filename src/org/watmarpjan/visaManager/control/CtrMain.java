/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import org.watmarpjan.visaManager.gui.panel.CtrGUIMain;

/**
 *
 * @author WMJ_user
 *
 */
public class CtrMain
{

    private final CtrDatabase ctrDB;
    private final CtrMonasticProfile ctrProfile;
    private final CtrMonastery ctrWat;
    private final CtrForm ctrForm;
    private final CtrVisa ctrVisa;
    private final CtrPassportScan ctrPassportScan;
    private final CtrUpajjhaya ctrUpajjhaya;
    private final CtrPrintoutTM30 ctrPrintoutTM30;

    public CtrMain(CtrGUIMain ctrGUI)
    {
        ctrDB = new CtrDatabase();
        ctrProfile = new CtrMonasticProfile(ctrDB);
        ctrWat = new CtrMonastery(ctrDB);
        ctrUpajjhaya = new CtrUpajjhaya(ctrDB);
        ctrVisa = new CtrVisa(ctrDB);
        ctrPassportScan = new CtrPassportScan(ctrDB);
        ctrForm = new CtrForm(this);
        ctrPrintoutTM30 = new CtrPrintoutTM30(ctrDB, ctrProfile);
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
        return ctrWat;
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
