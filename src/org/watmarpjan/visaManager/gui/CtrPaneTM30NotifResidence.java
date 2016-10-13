/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneTM30NotifResidence extends AbstractChildPaneController implements IEditableGUIForm
{

    @FXML
    private Button bArchive;

    @FXML
    private Button bSelectPDF;

    @FXML
    private TreeView<String> tvSavedNotifications;

    @FXML
    private TreeView<String> tvMonastics;

    @FXML
    private DatePicker dpNotification;

    @FXML
    private TextField tfPathPDF;

    private ArrayList<CheckBoxTreeItem<String>> listItemTMonastics;
    private File fSelected;

    @Override
    public void actionLockEdit()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionSave()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionUnlockEdit()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init()
    {
        TreeItem<String> rootSavedNotif;

        listItemTMonastics = new ArrayList<>();
        tvMonastics.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        rootSavedNotif = new TreeItem<>("Saved Residence Notifications");
        tvSavedNotifications.setRoot(rootSavedNotif);
        tvSavedNotifications.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>()
        {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue)
            {
                if (!newValue.isLeaf())
                {
                    bArchive.setDisable(false);
                } else
                {
                    bArchive.setDisable(true);
                }
            }
        });
    }

    public void fillData()
    {
        //loadSavedNotificationTree();
        loadMonasticTree();

        dpNotification.setValue(null);
        tfPathPDF.setText("");

        tvMonastics.getSelectionModel().clearSelection();
    }

    @Override
    public boolean isSelectionEmpty()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadMonasticTree()
    {
        ArrayList<String> monasticNickNameList;

        monasticNickNameList = ctrGUIMain.getCtrMain().getCtrProfile().loadProfileNicknameList(true);
        TreeItem<String> rootItem = new TreeItem<>("Monastics");
        listItemTMonastics.clear();
        for (String nickname : monasticNickNameList)
        {
            CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(nickname);
            listItemTMonastics.add(item);
        }
        rootItem.getChildren().addAll(listItemTMonastics);
        tvMonastics.setRoot(rootItem);

    }

    private void loadSavedNotificationTree()
    {
        TreeItem<String> rootItem = new TreeItem<>("Saved notifications");
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++)
        {
            TreeItem<String> item = new TreeItem<>("Message" + i);
            rootItem.getChildren().add(item);
        }
        tvSavedNotifications.setRoot(rootItem);
    }

    @FXML
    void actionAddNew(ActionEvent ae)
    {
        LocalDate ldNotifDate;
        TreeItem<String> newNotif;
        TreeItem<String> monasticNode;

        ldNotifDate = dpNotification.getValue();

        if (ldNotifDate != null)
        {
            newNotif = new TreeItem<>(ldNotifDate.format(Util.DEFAULT_DATE_FORMAT));
            for (String nickName : getNicknameSelectedMonastics())
            {
                monasticNode = new TreeItem<>(nickName);
                newNotif.getChildren().add(monasticNode);
            }
            tvSavedNotifications.getRoot().getChildren().add(newNotif);
        }
        fillData();
    }

    @FXML
    void actionSelectFile(ActionEvent ae)
    {
        fSelected = CtrFileOperation.selectFile("Select TM30 File", CtrFileOperation.FILE_CHOOSER_TYPE_PDF);
        if (fSelected != null)
        {
            tfPathPDF.setText(fSelected.getAbsolutePath().toString());
        }
    }

    private ArrayList<String> getNicknameSelectedMonastics()
    {
        ArrayList<String> listSelectedMonastics;

        listSelectedMonastics = new ArrayList<>();
        for (CheckBoxTreeItem<String> cbMonasticItem : listItemTMonastics)
        {
            if (cbMonasticItem.isSelected())
            {
                listSelectedMonastics.add(cbMonasticItem.getValue());
            }
        }
        return listSelectedMonastics;
    }
}
