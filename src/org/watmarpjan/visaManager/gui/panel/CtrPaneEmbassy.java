package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.watmarpjan.visaManager.gui.intface.ICreateEditGUIForm;
import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneController;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.hibernate.Embassy;

public class CtrPaneEmbassy extends AChildPaneController implements ICreateEditGUIForm
{

    @FXML
    private ComboBox<String> cbEmbassy;

    @FXML
    private TextField tfNameEN;

    @FXML
    private TextField tfNameTH;

    @FXML
    private TextField tfAddr1;

    @FXML
    private TextField tfAddr2;

    @FXML
    private TextField tfAddr3;

    @FXML
    private TextField tfAddr4;

    @FXML
    private TextField tfCountryTH;

    @FXML
    private ComboBox<String> cbCountry;

    public void init()
    {
        ArrayList listFields;
        listFields = new ArrayList();

        listFields.add(tfNameEN);
        listFields.add(tfNameTH);

        listFields.add(tfAddr1);
        listFields.add(tfAddr2);
        listFields.add(tfAddr3);
        listFields.add(tfAddr4);

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        fillEmbassyList();
    }

    public void fillEmbassyData(Embassy e)
    {
        //if no embassy is passed as parameter,
        //shows the last selected
        if (e == null && !cbEmbassy.getItems().isEmpty())
        {
            e = ctrGUIMain.getCtrMain().getCtrEmbassy().loadEmbassyByName(cbEmbassy.getValue());
        }

        //if Embassy m exists on the database
        if (e != null)
        {
            tfNameEN.setText(e.getNameEn());
            tfNameTH.setText(e.getNameTh());
            tfCountryTH.setText(e.getCountry());
            tfAddr1.setText(e.getAddressLine1());
            tfAddr2.setText(e.getAddressLine2());
            tfAddr3.setText(e.getAddressLine3());
            tfAddr4.setText(e.getAddressLine4());
        }
    }

    private void fillEmbassyList()
    {
        ArrayList<String> alEmbassy;

        alEmbassy = ctrGUIMain.getCtrMain().getCtrEmbassy().loadEmbassyList();
        GUIUtil.loadContentComboboxGeneric(cbEmbassy, alEmbassy);

        //if the list is not empty selects the first monastery to show the data
        if (!cbEmbassy.getItems().isEmpty())
        {
            cbEmbassy.setValue(alEmbassy.get(0));
        }
    }

    @Override
    public void actionAddNew()
    {
        String nameEnNewEmbassy;

        nameEnNewEmbassy = ctrGUIMain.getCtrMain().getCtrEmbassy().add();
        if (nameEnNewEmbassy != null)
        {
            //reloads the monastery list and selects the new monastery
            fillEmbassyList();
            cbEmbassy.setValue(nameEnNewEmbassy);
        }
    }

    @Override
    public boolean isSelectionEmpty()
    {
        if (cbEmbassy.getValue() == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void actionLockEdit()
    {
        tfNameEN.setEditable(false);
        tfNameTH.setEditable(false);
        tfCountryTH.setEditable(false);

        tfAddr1.setEditable(false);
        tfAddr2.setEditable(false);
        tfAddr3.setEditable(false);
        tfAddr4.setEditable(false);
    }

    @Override
    public void actionUnlockEdit()
    {
        tfNameEN.setEditable(true);
        tfNameTH.setEditable(true);
        tfCountryTH.setEditable(true);

        tfAddr1.setEditable(true);
        tfAddr2.setEditable(true);
        tfAddr3.setEditable(true);
        tfAddr4.setEditable(true);
    }

    @Override
    public void actionSave()
    {
        int opStatus;
        String previousNameEn, newNameEn;
        Embassy e;

        e = ctrGUIMain.getCtrMain().getCtrEmbassy().loadEmbassyByName(cbEmbassy.getValue());
        if (e != null)
        {
            previousNameEn = e.getNameEn();
            newNameEn = tfNameEN.getText();

            e.setNameEn(tfNameEN.getText());
            e.setNameTh(tfNameTH.getText());
            e.setAddressLine1(tfAddr1.getText());
            e.setAddressLine2(tfAddr2.getText());
            e.setAddressLine3(tfAddr3.getText());
            e.setAddressLine4(tfAddr4.getText());
            e.setCountry(tfCountryTH.getText());

            opStatus = ctrGUIMain.getCtrMain().getCtrEmbassy().update(e);
            if (opStatus == 0)
            {
                //if the name was updated need to refresh the name list
                if (!previousNameEn.equals(newNameEn))
                {
                    fillEmbassyList();
                    cbEmbassy.setValue(newNameEn);
                }

                ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
                CtrAlertDialog.infoDialog("Embassy update", "The embassy information was successfully updated.");
            }
        }
    }

    @FXML
    public void actionSelectEmbassy(ActionEvent ae)
    {
        String nameSelectedEmbassy;
        Embassy e;

        nameSelectedEmbassy = cbEmbassy.getValue();
        if (nameSelectedEmbassy != null)
        {
            ctrGUIMain.getPaneEditSaveController().actionLock();
            e = ctrGUIMain.getCtrMain().getCtrEmbassy().loadEmbassyByName(nameSelectedEmbassy);
            fillEmbassyData(e);
        }
    }

}
