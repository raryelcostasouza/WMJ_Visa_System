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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.watmarpjan.visaManager.model.hibernate.Monastery;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneMonastery extends AbstractChildPaneController implements ICreateEditGUIForm
{

    @FXML
    private ComboBox<String> cbMonasteryList;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhoneNumber;

    @FXML
    private RadioButton rbNo;
    @FXML
    private RadioButton rbJkJangwat;
    @FXML
    private RadioButton rbJKAmpher;
    @FXML
    private RadioButton rbJKTambol;

    @FXML
    private ToggleGroup tgJaokana;

    @FXML
    private TextField tfAddrCountry;
    @FXML
    private TextField tfAddrProvince;
    @FXML
    private TextField tfAddrAmpher;
    @FXML
    private TextField tfAddrTambol;
    @FXML
    private TextField tfAddrRoad;
    @FXML
    private TextField tfAddrNumber;

    @FXML
    private ToggleGroup tgCountry;

    @FXML
    private RadioButton rbCountryThailand;
    @FXML
    private RadioButton rbCountryOther;

    @FXML
    private Button bAddNew;

    private static final String JAOKANA_NO = "NO";
    private static final String JAOKANA_TAMBOL = "TAMBOL";
    private static final String JAOKANA_AMPHER = "AMPHER";
    private static final String JAOKANA_JANGWAT = "JANGWAT";

    private static final String COUNTRY_THAILAND = "THAILAND";

    public void init()
    {
        ArrayList listFields;
        listFields = new ArrayList();

        listFields.add(tfName);
        listFields.add(tfAddrCountry);
        listFields.add(tfAddrProvince);
        listFields.add(tfAddrTambol);
        listFields.add(tfAddrRoad);
        listFields.add(tfAddrNumber);
        listFields.add(tfPhoneNumber);
        listFields.add(tgJaokana);
        listFields.add(tgCountry);

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        fillMonasteryList();
    }

    @Override
    public void actionLockEdit()
    {
        tfName.setEditable(false);
        tfPhoneNumber.setEditable(false);

        rbCountryOther.setDisable(true);
        rbCountryThailand.setDisable(true);
        tfAddrCountry.setEditable(false);
        tfAddrProvince.setEditable(false);
        tfAddrAmpher.setEditable(false);
        tfAddrTambol.setEditable(false);
        tfAddrRoad.setEditable(false);
        tfAddrNumber.setEditable(false);

        rbNo.setDisable(true);
        rbJkJangwat.setDisable(true);
        rbJKAmpher.setDisable(true);
        rbJKTambol.setDisable(true);

        bAddNew.setDisable(true);
    }

    @Override
    public void actionSave()
    {
        int opStatus;
        Monastery m;
        String previousName, newName;

        m = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryByName(cbMonasteryList.getValue());

        if (m != null)
        {
            previousName = m.getMonasteryName();
            newName = tfName.getText();

            m.setMonasteryName(tfName.getText());
            m.setPhoneNumber(tfPhoneNumber.getText());

            if (rbJkJangwat.isSelected())
            {
                m.setMonasteryOfJaokana(JAOKANA_JANGWAT);
            } else if (rbJKAmpher.isSelected())
            {
                m.setMonasteryOfJaokana(JAOKANA_AMPHER);
            } else if (rbJKTambol.isSelected())
            {
                m.setMonasteryOfJaokana(JAOKANA_TAMBOL);
            } else
            {
                m.setMonasteryOfJaokana(JAOKANA_NO);
            }

            if (rbCountryThailand.isSelected())
            {
                m.setAddrCountry(COUNTRY_THAILAND);
            } else
            {
                m.setAddrCountry(tfAddrCountry.getText());
            }
            m.setAddrJangwat(tfAddrProvince.getText());
            m.setAddrAmpher(tfAddrAmpher.getText());
            m.setAddrTambon(tfAddrTambol.getText());
            m.setAddrRoad(tfAddrRoad.getText());
            m.setAddrNumber(tfAddrNumber.getText());

            opStatus = ctrGUIMain.getCtrMain().getCtrMonastery().updateMonastery(m);
            if (opStatus == 0)
            {
                //if the name was updated need to refresh the name list
                if (!previousName.equals(newName))
                {
                    fillMonasteryList();
                    cbMonasteryList.setValue(newName);
                }

                ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
                CtrAlertDialog.infoDialog("Monastery update", "The monastery information was successfully updated.");
            }
        }
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

    @FXML
    void actionButtonNewMonastery(ActionEvent ae)
    {
        String nameNewMonastery;

        nameNewMonastery = ctrGUIMain.getCtrMain().getCtrMonastery().addMonastery();
        if (nameNewMonastery != null)
        {
            //reloads the monastery list and selects the new monastery
            fillMonasteryList();
            cbMonasteryList.setValue(nameNewMonastery);
        }
    }

    @Override
    public void actionUnlockEdit()
    {
        tfName.setEditable(true);
        tfPhoneNumber.setEditable(true);

        rbCountryOther.setDisable(false);
        rbCountryThailand.setDisable(false);
        tfAddrCountry.setEditable(true);
        tfAddrProvince.setEditable(true);
        tfAddrAmpher.setEditable(true);
        tfAddrTambol.setEditable(true);
        tfAddrRoad.setEditable(true);
        tfAddrNumber.setEditable(true);

        rbNo.setDisable(false);
        rbJkJangwat.setDisable(false);
        rbJKAmpher.setDisable(false);
        rbJKTambol.setDisable(false);

        bAddNew.setDisable(false);
    }

    @Override
    public void actionUnlockAddNewButton()
    {
        bAddNew.setDisable(false);
    }

    public void fillMonasteryData(Monastery m)
    {
        //if no monastery is passed as parameter,
        //shows the last selected
        if (m == null && !cbMonasteryList.getItems().isEmpty())
        {
            m = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryByName(cbMonasteryList.getValue());
        }

        //if Monastery m exists on the database
        if (m != null)
        {
            tfName.setText(m.getMonasteryName());
            tfPhoneNumber.setText(m.getPhoneNumber());

            if (m.getAddrCountry() != null && m.getAddrCountry().equals(COUNTRY_THAILAND))
            {
                rbCountryThailand.setSelected(true);
                tfAddrCountry.setText("");
            } else
            {
                rbCountryOther.setSelected(true);
                tfAddrCountry.setText(m.getAddrCountry());
            }
            tfAddrProvince.setText(m.getAddrJangwat());
            tfAddrAmpher.setText(m.getAddrAmpher());
            tfAddrTambol.setText(m.getAddrTambon());
            tfAddrRoad.setText(m.getAddrRoad());
            tfAddrNumber.setText(m.getAddrNumber());

            switch (m.getMonasteryOfJaokana())
            {
                case JAOKANA_JANGWAT:
                    rbJkJangwat.setSelected(true);
                    break;
                case JAOKANA_AMPHER:
                    rbJKAmpher.setSelected(true);
                    break;
                case JAOKANA_TAMBOL:
                    rbJKTambol.setSelected(true);
                    break;
                default:
                    rbNo.setSelected(true);
                    break;
            }
        }

    }

    private void fillMonasteryList()
    {
        ArrayList<String> alMonastery;

        alMonastery = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryList();

        cbMonasteryList.getItems().clear();
        cbMonasteryList.getItems().addAll(alMonastery);

        //if the list is not empty selects the first monastery to show the data
        if (!cbMonasteryList.getItems().isEmpty())
        {
            cbMonasteryList.setValue(alMonastery.get(0));
        }
    }

    @FXML
    public void actionSelectMonastery(ActionEvent ae)
    {
        String nameSelectedMonastery;
        Monastery m;

        nameSelectedMonastery = cbMonasteryList.getValue();
        if (nameSelectedMonastery != null)
        {
            ctrGUIMain.getPaneEditSaveController().actionLock();
            m = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryByName(nameSelectedMonastery);
            fillMonasteryData(m);
        }
    }

}
