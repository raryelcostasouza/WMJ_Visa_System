/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.gui.panel.abs;


//used only for Monastic Profile, Monastery, Embassy and Upajjhaya
//as they are combobox selectable at their panes 
public abstract class AChildPaneControllerCBSelectableEntity extends AChildPaneController
{
    public abstract void lockCBSelectionEntity();
    public abstract void unlockCBSelectionEntity();
}
