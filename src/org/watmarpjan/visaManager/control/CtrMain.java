/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import org.watmarpjan.visaManager.gui.CtrGUIMain;

/**
 *
 * @author WMJ_user
 *
 */
public class CtrMain
{

    private final CtrDatabase ctrDB;
    private final CtrProfile ctrProfile;
    private final CtrMonastery ctrWat;
    private final CtrForm ctrForm;
    private final CtrVisa ctrVisa;
    private final CtrPassportScan ctrPassportScan;
    private final CtrGUIMain ctrGUI;
    private final CtrUpajjhaya ctrUpajjhaya;

    public CtrMain(CtrGUIMain ctrGUI)
    {
        ctrDB = new CtrDatabase();
        ctrProfile = new CtrProfile(ctrDB);
        ctrWat = new CtrMonastery(ctrDB);
        ctrUpajjhaya = new CtrUpajjhaya(ctrDB);
        ctrVisa = new CtrVisa(ctrDB);
        ctrPassportScan = new CtrPassportScan(ctrDB);
        ctrForm = new CtrForm(this);

        this.ctrGUI = ctrGUI;
    }

    public CtrForm getCtrForm()
    {
        return ctrForm;
    }

    public CtrDatabase getCtrDB()
    {
        return ctrDB;
    }

    public CtrProfile getCtrProfile()
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
