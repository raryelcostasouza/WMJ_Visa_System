/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

/**
 *
 * @author WMJ_user
 */
public abstract class AbstractControllerDB
{

    protected final CtrDatabase ctrDB;

    public AbstractControllerDB(CtrDatabase ctrDB)
    {
        this.ctrDB = ctrDB;
    }
}
