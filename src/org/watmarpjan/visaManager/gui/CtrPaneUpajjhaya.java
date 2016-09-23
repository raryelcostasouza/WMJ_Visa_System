/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.watmarpjan.visaManager.model.hibernate.Upajjhaya;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneUpajjhaya extends AbstractChildPaneController implements IEditableGUIForm
{

    @FXML
    private ComboBox<String> cbUpajjhayaList;

    @FXML
    private TextField tfName;

    @FXML
    private ComboBox<String> cbMonasteryList;

    @FXML
    private Button bNewUpajjhaya;

    @Override
    public void init()
    {
        fillUpajjhayaList();
        fillMonasteryList();
    }

    @Override
    public void actionLockEdit()
    {
        tfName.setEditable(false);
        cbMonasteryList.setDisable(true);
        bNewUpajjhaya.setDisable(true);
    }

    @Override
    public void actionSave()
    {
        Upajjhaya u;
        int opStatus;
        String previousName, newName;

        u = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadUpajjhayaByName(cbUpajjhayaList.getValue());

        if (u != null)
        {
            previousName = u.getName();
            newName = tfName.getText();

            u.setName(tfName.getText());
            u.setMonastery(ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryByName(cbMonasteryList.getValue()));

            opStatus = ctrGUIMain.getCtrMain().getCtrUpajjhaya().update(u);
            if (opStatus == 0)
            {
                //if the name was updated need to refresh the name list
                if (!previousName.equals(newName))
                {
                    fillUpajjhayaList();
                    cbUpajjhayaList.setValue(newName);
                }

                CtrAlertDialog.infoDialog("Upajjhaya update", "The upajjhaya information was successfully updated.");
            }
        }
    }

    @Override
    public void actionUnlockEdit()
    {
        tfName.setEditable(true);
        cbMonasteryList.setDisable(false);
        bNewUpajjhaya.setDisable(false);
    }

    @Override
    public boolean isSelectionEmpty()
    {
        if (cbMonasteryList.getValue() == null)
        {
            return true;
        } else
        {
            return false;
        }
    }

    private void fillMonasteryList()
    {
        cbMonasteryList.getItems().clear();
        cbMonasteryList.getItems().addAll(ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryList());
    }

    private void fillUpajjhayaList()
    {
        ArrayList<String> alUpajjhaya;

        alUpajjhaya = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadUpajjhayaList();

        cbUpajjhayaList.getItems().clear();
        cbUpajjhayaList.getItems().addAll(alUpajjhaya);

        //if the list is not empty selects the first monastery to show the data
        if (!cbUpajjhayaList.getItems().isEmpty())
        {
            cbUpajjhayaList.setValue(alUpajjhaya.get(0));
        }
    }

    public void fillUpajjhayaData(Upajjhaya u)
    {
        //If no Upajjhaya is passed as parameter
        //shows the last selected 
        if (u == null)
        {
            u = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadUpajjhayaByName(cbUpajjhayaList.getValue());
        }

        //If Upajjhaya u exists on DB
        if (u != null)
        {
            tfName.setText(u.getName());
            if (u.getMonastery() != null)
            {
                cbMonasteryList.setValue(u.getMonastery().getName());
            } else
            {
                cbMonasteryList.setValue(null);
            }
        }
    }

    @FXML
    void actionSelectUpajjhaya(ActionEvent ae)
    {
        String nameSelectedUpajjhaya;
        Upajjhaya u;

        nameSelectedUpajjhaya = cbUpajjhayaList.getValue();
        if (nameSelectedUpajjhaya != null)
        {
            ctrGUIMain.getPaneEditSaveController().actionLock();
            u = ctrGUIMain.getCtrMain().getCtrUpajjhaya().loadUpajjhayaByName(nameSelectedUpajjhaya);
            fillUpajjhayaData(u);
        }
    }

    @FXML
    void actionButtonNewUpajjhaya(ActionEvent ae)
    {
        String nameNewUpajjhaya;

        nameNewUpajjhaya = ctrGUIMain.getCtrMain().getCtrUpajjhaya().addNew();
        if (nameNewUpajjhaya != null)
        {
            //reloads the monastery list and selects the new monastery
            fillUpajjhayaList();
            cbUpajjhayaList.setValue(nameNewUpajjhaya);
        }
    }

}
