/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.intface;

/**
 *
 * @author WMJ_user
 */
public interface IEditableGUIForm
{

    public boolean isSelectionEmpty();

    public void actionLockEdit();

    public void actionUnlockEdit();

    public void actionSave();
}
