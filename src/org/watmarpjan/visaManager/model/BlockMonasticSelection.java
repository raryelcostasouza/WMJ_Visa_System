/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model;

import java.util.ArrayList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

/**
 *
 * @author wmj_user
 */
public class BlockMonasticSelection
{

    private TreeView<String> objTV;
    private ArrayList<CheckBoxTreeItem<String>> listCheckBoxMonastics;
    private TitledPane objParentTitledPane = null;

    public BlockMonasticSelection(TreeView<String> objTV)
    {
        this.objTV = objTV;
        this.objTV.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        this.listCheckBoxMonastics = new ArrayList<>();
    }

    public BlockMonasticSelection(TreeView<String> objTV, TitledPane pObjParentTitledPane)
    {
        this(objTV);
        this.objParentTitledPane = pObjParentTitledPane;
    }

    public TreeView<String> getTreeView()
    {
        return objTV;
    }

    public ArrayList<CheckBoxTreeItem<String>> getListCheckBoxMonastics()
    {
        return listCheckBoxMonastics;
    }

    public TitledPane getParentTitledPane()
    {
        return objParentTitledPane;
    }
}
