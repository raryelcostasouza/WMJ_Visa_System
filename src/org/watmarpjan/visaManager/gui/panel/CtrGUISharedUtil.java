/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import org.watmarpjan.visaManager.model.BlockMonasticSelection;

/**
 *
 * @author wmj_user
 */
public class CtrGUISharedUtil
{
    private CtrGUIMain ctrGUIMain;

    public CtrGUISharedUtil(CtrGUIMain ctrGUIMain)
    {
        this.ctrGUIMain = ctrGUIMain;
    }
    
   
    
    public void loadMonasticTree(BlockMonasticSelection objTVC)
    {
        ArrayList<String> monasticNickNameList;

        monasticNickNameList = ctrGUIMain.getCtrMain().getCtrProfile().loadNicknameList(true);
        TreeItem<String> rootItem = new TreeItem<>("Monastics");
        objTVC.getListCheckBoxMonastics().clear();
        for (String nickname : monasticNickNameList)
        {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(nickname);
            objTVC.getListCheckBoxMonastics().add(item);
        }
        rootItem.getChildren().addAll(objTVC.getListCheckBoxMonastics());
        objTVC.getTreeView().setRoot(rootItem);
    }
    
    
    
    
}
