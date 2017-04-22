/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneController;
import org.watmarpjan.visaManager.gui.intface.ICreateEditGUIForm;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.hibernate.Monastery;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneMonastery extends AChildPaneController implements ICreateEditGUIForm
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
    private ComboBox<String> cbAddrCountry;
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
    private TextField tfTHAddrProvince;
    @FXML
    private TextField tfTHAddrAmpher;
    @FXML
    private TextField tfTHAddrTambol;
    @FXML
    private TextField tfTHAddrRoad;
    @FXML
    private TextField tfTHAddrNumber;

    @FXML
    private ToggleGroup tgCountry;

    @FXML
    private RadioButton rbCountryThailand;
    @FXML
    private RadioButton rbCountryOther;

    private static final String JAOKANA_NO = "NO";
    private static final String JAOKANA_TAMBOL = "TAMBOL";
    private static final String JAOKANA_AMPHER = "AMPHER";
    private static final String JAOKANA_JANGWAT = "JANGWAT";

    public void init()
    {
        ArrayList listFields;
        listFields = new ArrayList();

        listFields.add(tfName);
        listFields.add(cbAddrCountry);
        
        listFields.add(tfTHAddrProvince);
        listFields.add(tfTHAddrAmpher);
        listFields.add(tfTHAddrTambol);
        listFields.add(tfTHAddrRoad);
        listFields.add(tfTHAddrNumber);       
        
        listFields.add(tfAddrProvince);
        listFields.add(tfAddrAmpher);
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
        cbAddrCountry.setDisable(true);
        
        tfAddrProvince.setEditable(false);
        tfAddrAmpher.setEditable(false);
        tfAddrTambol.setEditable(false);
        tfAddrRoad.setEditable(false);
        tfAddrNumber.setEditable(false);
        
        tfTHAddrProvince.setEditable(false);
        tfTHAddrAmpher.setEditable(false);
        tfTHAddrTambol.setEditable(false);
        tfTHAddrRoad.setEditable(false);
        tfTHAddrNumber.setEditable(false);

        rbNo.setDisable(true);
        rbJkJangwat.setDisable(true);
        rbJKAmpher.setDisable(true);
        rbJKTambol.setDisable(true);
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
            }
            else if (rbJKAmpher.isSelected())
            {
                m.setMonasteryOfJaokana(JAOKANA_AMPHER);
            }
            else if (rbJKTambol.isSelected())
            {
                m.setMonasteryOfJaokana(JAOKANA_TAMBOL);
            }
            else
            {
                m.setMonasteryOfJaokana(JAOKANA_NO);
            }

            if (rbCountryThailand.isSelected())
            {
                m.setAddrCountry(AppConstants.COUNTRY_THAILAND);
            }
            else
            {
                m.setAddrCountry(cbAddrCountry.getValue());
            }

            if (rbCountryThailand.isSelected())
            {
                 m.setAddrJangwat(tfTHAddrProvince.getText());
                m.setAddrAmpher(tfTHAddrAmpher.getText());
                m.setAddrTambon(tfTHAddrTambol.getText());
                m.setAddrRoad(tfTHAddrRoad.getText());
                m.setAddrNumber(tfTHAddrNumber.getText());
            }
            else
            {
                m.setAddrJangwat(tfAddrProvince.getText());
                m.setAddrAmpher(tfAddrAmpher.getText());
                m.setAddrTambon(tfAddrTambol.getText());
                m.setAddrRoad(tfAddrRoad.getText());
                m.setAddrNumber(tfAddrNumber.getText());
            }

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
        }
        else
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
        cbAddrCountry.setDisable(false);
        tfAddrProvince.setEditable(true);
        tfAddrAmpher.setEditable(true);
        tfAddrTambol.setEditable(true);
        tfAddrRoad.setEditable(true);
        tfAddrNumber.setEditable(true);
        
        tfTHAddrProvince.setEditable(true);
        tfTHAddrAmpher.setEditable(true);
        tfTHAddrTambol.setEditable(true);
        tfTHAddrRoad.setEditable(true);
        tfTHAddrNumber.setEditable(true);

        rbNo.setDisable(false);
        rbJkJangwat.setDisable(false);
        rbJKAmpher.setDisable(false);
        rbJKTambol.setDisable(false);
    }

    public void fillMonasteryData(Monastery m)
    {
        GUIUtil.loadContentComboboxGeneric(cbAddrCountry, ctrGUIMain.getCtrMain().loadListCountry());

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
            clearAddrTextFields();
            if (m.getAddrCountry() != null && m.getAddrCountry().equals(AppConstants.COUNTRY_THAILAND))
            {
                rbCountryThailand.setSelected(true);
                cbAddrCountry.setValue("");
            }
            else
            {
                rbCountryOther.setSelected(true);
                cbAddrCountry.setValue(m.getAddrCountry());
            }
            if (rbCountryOther.isSelected())
            {
                enableTextFieldEnglish(null);
                //for english address use a separate set of TextFields
                //reason: the Thai field need to increase font size using CSS

                tfAddrProvince.setText(m.getAddrJangwat());
                tfAddrAmpher.setText(m.getAddrAmpher());
                tfAddrTambol.setText(m.getAddrTambon());
                tfAddrRoad.setText(m.getAddrRoad());
                tfAddrNumber.setText(m.getAddrNumber());
            }
            else
            {
                enableTextFieldThai(null);
                //for Thai address use a separate set of TextFields
                //reason: the Thai field need to increase font size using CSS
                tfTHAddrProvince.setText(m.getAddrJangwat());
                tfTHAddrAmpher.setText(m.getAddrAmpher());
                tfTHAddrTambol.setText(m.getAddrTambon());
                tfTHAddrRoad.setText(m.getAddrRoad());
                tfTHAddrNumber.setText(m.getAddrNumber());
            }

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

    @Override
    public void actionAddNew()
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

    @FXML
    void enableTextFieldEnglish(ActionEvent ae)
    {
        //makes the English TextFields visible
        tfAddrProvince.setVisible(true);
        tfAddrAmpher.setVisible(true);
        tfAddrTambol.setVisible(true);
        tfAddrRoad.setVisible(true);
        tfAddrNumber.setVisible(true);

        //makes the Thai TextFields invisible
        tfTHAddrProvince.setVisible(false);
        tfTHAddrAmpher.setVisible(false);
        tfTHAddrTambol.setVisible(false);
        tfTHAddrRoad.setVisible(false);
        tfTHAddrNumber.setVisible(false);
    }

    @FXML
    void enableTextFieldThai(ActionEvent ae)
    {
        //makes the English TextFields invisible
        tfAddrProvince.setVisible(false);
        tfAddrAmpher.setVisible(false);
        tfAddrTambol.setVisible(false);
        tfAddrRoad.setVisible(false);
        tfAddrNumber.setVisible(false);

        //makes the Thai TextFields visible
        tfTHAddrProvince.setVisible(true);
        tfTHAddrAmpher.setVisible(true);
        tfTHAddrTambol.setVisible(true);
        tfTHAddrRoad.setVisible(true);
        tfTHAddrNumber.setVisible(true);
    }

    private void clearAddrTextFields()
    {
        tfAddrProvince.setText("");
        tfAddrAmpher.setText("");
        tfAddrTambol.setText("");
        tfAddrRoad.setText("");
        tfAddrNumber.setText("");

        tfTHAddrProvince.setText("");
        tfTHAddrAmpher.setText("");
        tfTHAddrTambol.setText("");
        tfTHAddrRoad.setText("");
        tfTHAddrNumber.setText("");
    }

}
