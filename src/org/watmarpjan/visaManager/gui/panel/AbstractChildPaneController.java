/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

/**
 *
 * @author WMJ_user
 */
public abstract class AbstractChildPaneController
{

    protected CtrGUIMain ctrGUIMain;

    public void init()
    {

    }

    public void setParent(CtrGUIMain ctrGUIMain)
    {
        this.ctrGUIMain = ctrGUIMain;
    }
}
