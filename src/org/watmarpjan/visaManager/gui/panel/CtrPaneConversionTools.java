/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneController;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 *
 * @author wmj_user
 */
public class CtrPaneConversionTools extends AChildPaneController
{

    @FXML
    private TextField tfYearThai;

    @FXML
    private TextField tfYearWestern;

    @FXML
    private ComboBox<String> cbMonthThai;

    @FXML
    private ComboBox<String> cbMonthWestern;

    @Override
    public void init()
    {
        ArrayList<String> listWesternMonthName, listThaiMonthName;
        Month m;

        listWesternMonthName = new ArrayList<>();
        listThaiMonthName = new ArrayList<>();
        for (int i = 1; i <= 12; i++)
        {
            m = Month.of(i);
            listWesternMonthName.add(m.toString());
            listThaiMonthName.add(m.getDisplayName(TextStyle.FULL, new Locale("th")));
        }
        cbMonthWestern.getItems().addAll(listWesternMonthName);
        cbMonthThai.getItems().addAll(listThaiMonthName);

        tfYearThai.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                removeNonNumericChars(tfYearThai);
            }
        });

        tfYearWestern.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                removeNonNumericChars(tfYearWestern);
            }
        });
    }

    private void removeNonNumericChars(TextField objTF)
    {
        objTF.setText(objTF.getText().replaceAll("[^0-9]", ""));
    }

    @FXML
    void actionSelectThaiMonth(ActionEvent ae)
    {
        int monthIndex;
        String selectedThaiMonth, convertedWesternMonth;

        selectedThaiMonth = cbMonthThai.getValue();
        if (selectedThaiMonth != null && !selectedThaiMonth.isEmpty())
        {
            monthIndex = cbMonthThai.getItems().indexOf(selectedThaiMonth);
            convertedWesternMonth = cbMonthWestern.getItems().get(monthIndex);
            cbMonthWestern.setValue(convertedWesternMonth);
        }
    }

    @FXML
    void actionSelectWesternMonth(ActionEvent ae)
    {
        int monthIndex;
        String selectedWesternMonth, convertedThaiMonth;

        selectedWesternMonth = cbMonthWestern.getValue();
        if (selectedWesternMonth != null && !selectedWesternMonth.isEmpty())
        {
            monthIndex = cbMonthWestern.getItems().indexOf(selectedWesternMonth);
            convertedThaiMonth = cbMonthThai.getItems().get(monthIndex);
            cbMonthThai.setValue(convertedThaiMonth);
        }
    }

    @FXML
    void actionInputWesternYear(ActionEvent ae)
    {
        String strWesternYear;
        int yearWestern, yearThai;

        strWesternYear = tfYearWestern.getText();
        yearWestern = Integer.parseInt(strWesternYear);

        yearThai = yearWestern + 543;
        tfYearThai.setText(yearThai + "");
    }

    @FXML
    void actionInputThaiYear(ActionEvent ae)
    {
        String strThaiYear;
        int yearWestern, yearThai;

        strThaiYear = tfYearThai.getText();
        yearThai = Integer.parseInt(strThaiYear);

        yearWestern = yearThai - 543;
        tfYearWestern.setText(yearWestern + "");
    }

}
