/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.intface.ICreateEditGUIForm;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerCBSelectableEntity;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.hibernate.Monastery;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneMonastery extends AChildPaneControllerCBSelectableEntity implements ICreateEditGUIForm
{

    @FXML
    private ComboBox<String> cbMonasteryList;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfNameEnglish;
    
    @FXML
    private TextField tfNickname;
    
    @FXML
    private TextField tfAbbotName;

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
    private TextField tfAddrProvince90Day;
    @FXML
    private TextField tfAddrAmpher90Day;
    @FXML
    private TextField tfAddrTambol90Day;
    @FXML
    private TextField tfAddrRoad90Day;
    @FXML
    private TextField tfAddrNumber90Day;

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
    
    private Monastery currentSelectedMonastery;

    private static final String JAOKANA_NO = "NO";
    private static final String JAOKANA_TAMBOL = "TAMBOL";
    private static final String JAOKANA_AMPHER = "AMPHER";
    private static final String JAOKANA_JANGWAT = "JANGWAT";

    public void init()
    {
        ArrayList listFields;
        listFields = new ArrayList();

        listFields.add(tfName);
        listFields.add(tfNameEnglish);
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
        
        listFields.add(tfAddrProvince90Day);
        listFields.add(tfAddrAmpher90Day);
        listFields.add(tfAddrTambol90Day);
        listFields.add(tfAddrRoad90Day);
        listFields.add(tfAddrNumber90Day);
        
        listFields.add(tfNickname);
        listFields.add(tfAbbotName);
        listFields.add(tfPhoneNumber);
        listFields.add(tgJaokana);
        listFields.add(tgCountry);
        
        tfNickname.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue != null)
                {
                    //as the monastery nickname will be used as a subfolder name for letter templates better to prevent
                    //special characters input
                    
                    //this listener
                    //removes any character except letters, number, dot and space
                    tfNickname.setText(newValue.replaceAll("[^a-zA-Z0-9\\. ]", ""));
                }
            }
        });
        

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        fillMonasteryList();
    }

    @Override
    public void actionLockEdit()
    {
        tfName.setEditable(false);
        tfNameEnglish.setEditable(false);
        tfNickname.setEditable(false);
        tfAbbotName.setEditable(false);
        tfPhoneNumber.setEditable(false);

        rbCountryOther.setDisable(true);
        rbCountryThailand.setDisable(true);
        cbAddrCountry.setDisable(true);
        
        tfAddrProvince.setEditable(false);
        tfAddrAmpher.setEditable(false);
        tfAddrTambol.setEditable(false);
        tfAddrRoad.setEditable(false);
        tfAddrNumber.setEditable(false);
        
        tfAddrProvince90Day.setEditable(false);
        tfAddrAmpher90Day.setEditable(false);
        tfAddrTambol90Day.setEditable(false);
        tfAddrRoad90Day.setEditable(false);
        tfAddrNumber90Day.setEditable(false);
        
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
    public int actionSave()
    {
        int opStatus;
        Monastery m;
        String previousName, newName;

        m = currentSelectedMonastery;

        if (m != null)
        {
            previousName = m.getMonasteryName();
            newName = tfName.getText();

            m.setMonasteryName(tfName.getText());
            m.setMonasteryNameEnglish(tfNameEnglish.getText());
            m.setMonasteryNickname(tfNickname.getText());
            m.setAbbotName(tfAbbotName.getText());
            m.setPhoneNumber(tfPhoneNumber.getText());
            
            m.setAddrJangwat90DayOnline(tfAddrProvince90Day.getText());
            m.setAddrAmpher90DayOnline(tfAddrAmpher90Day.getText());
            m.setAddrTambon90DayOnline(tfAddrTambol90Day.getText());
            m.setAddrRoad90DayOnline(tfAddrRoad90Day.getText());
            m.setAddrNumber90DayOnline(tfAddrNumber90Day.getText());

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

            opStatus = ctrGUIMain.getCtrMain().getCtrMonastery().update(m);
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
            return opStatus;
        }
        return 0;
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

        nameNewMonastery = ctrGUIMain.getCtrMain().getCtrMonastery().create();
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
        tfNameEnglish.setEditable(true);
        tfNickname.setEditable(true);
        tfAbbotName.setEditable(true);
        tfPhoneNumber.setEditable(true);

        rbCountryOther.setDisable(false);
        rbCountryThailand.setDisable(false);
        cbAddrCountry.setDisable(false);
        tfAddrProvince.setEditable(true);
        tfAddrAmpher.setEditable(true);
        tfAddrTambol.setEditable(true);
        tfAddrRoad.setEditable(true);
        tfAddrNumber.setEditable(true);
        
        tfAddrProvince90Day.setEditable(true);
        tfAddrAmpher90Day.setEditable(true);
        tfAddrTambol90Day.setEditable(true);
        tfAddrRoad90Day.setEditable(true);
        tfAddrNumber90Day.setEditable(true);
        
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
    
    @Override
    public void lockCBSelectionEntity()
    {
        cbMonasteryList.setDisable(true);
    }

    @Override
    public void unlockCBSelectionEntity()
    {
        cbMonasteryList.setDisable(false);
    }

    public void fillMonasteryData(Monastery m)
    {
        GUIUtil.loadContentComboboxGeneric(cbAddrCountry, ctrGUIMain.getCtrMain().loadListCountry());

        //if no monastery is passed as parameter,
        //shows the last selected
        if (m == null && !cbMonasteryList.getItems().isEmpty())
        {
            m = ctrGUIMain.getCtrMain().getCtrMonastery().loadByName(cbMonasteryList.getValue());
            currentSelectedMonastery = m;
        }

        //if Monastery m exists on the database
        if (m != null)
        {
            tfName.setText(m.getMonasteryName());
            tfNameEnglish.setText(m.getMonasteryNameEnglish());
            
            tfPhoneNumber.setText(m.getPhoneNumber());
            tfNickname.setText(m.getMonasteryNickname());
            tfAbbotName.setText(m.getAbbotName());
            
            tfAddrProvince90Day.setText(m.getAddrJangwat90DayOnline());
            tfAddrAmpher90Day.setText(m.getAddrAmpher90DayOnline());
            tfAddrTambol90Day.setText(m.getAddrTambon90DayOnline());
            tfAddrRoad90Day.setText(m.getAddrRoad90DayOnline());
            tfAddrNumber90Day.setText(m.getAddrNumber90DayOnline());            
            
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

        //check if there is unsaved changes before filling the data of the newly selected monastery
        if (ctrGUIMain.checkUnsavedChanges() == 0)
        {
            nameSelectedMonastery = cbMonasteryList.getValue();
            if (nameSelectedMonastery != null)
            {
                ctrGUIMain.getPaneEditSaveController().actionLock();
                m = ctrGUIMain.getCtrMain().getCtrMonastery().loadByName(nameSelectedMonastery);
                currentSelectedMonastery = m;
                fillMonasteryData(m);
            }
        }
    }

    @Override
    public void actionAddNew()
    {
        String nameNewMonastery;

        nameNewMonastery = ctrGUIMain.getCtrMain().getCtrMonastery().create();
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
