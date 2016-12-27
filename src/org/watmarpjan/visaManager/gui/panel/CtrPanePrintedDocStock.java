/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.watmarpjan.visaManager.gui.intface.IEditableGUIForm;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.EntryPrintedDocStock;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;

/**
 *
 * @author pm.dell
 */
public class CtrPanePrintedDocStock extends AbstractChildPaneController implements IFormMonasticProfile, IEditableGUIForm
{

    @FXML
    private Spinner<Integer> spPrintedPhotos;
    @FXML
    private Spinner<Integer> spSigned90DayForms;

    @FXML
    private CheckBox cbSignedPhotocopies;

    @FXML
    private TableView<EntryPrintedDocStock> tvOverview;

    @FXML
    private TableColumn<EntryPrintedDocStock, Boolean> tcSignedPhotocopies;
    @FXML
    private TableColumn<EntryPrintedDocStock, String> tcNSigned90DForms;
    @FXML
    private TableColumn<EntryPrintedDocStock, String> tcNPrintedPhotos;

    private ArrayList listFields;

    private Callback<TableColumn<EntryPrintedDocStock, String>, TableCell<EntryPrintedDocStock, String>> stringCellFactory = new Callback<TableColumn<EntryPrintedDocStock, String>, TableCell<EntryPrintedDocStock, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryPrintedDocStock, String> param)
        {
            final TableCell<EntryPrintedDocStock, String> cell = new TableCell<EntryPrintedDocStock, String>()
            {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    if (item == null || empty)
                    {
                        setText(null);
                    }
                    else
                    {
                        setText(item);
                        if (item.equals(EntryPrintedDocStock.STR_MISSING))
                        {
                            setTextFill(Color.RED);
                        }
                        else
                        {
                            setTextFill(Color.BLACK);
                        }
                    }

                }
            };
            return cell;

        }
    };

    @Override
    public void init()
    {
        listFields = new ArrayList();

        listFields.add(spPrintedPhotos);
        listFields.add(spSigned90DayForms);
        listFields.add(cbSignedPhotocopies);
        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        spPrintedPhotos.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20));
        spSigned90DayForms.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20));

        initTableView();
    }

    private void initTableView()
    {
        tcNSigned90DForms.setCellFactory(stringCellFactory);
        tcNPrintedPhotos.setCellFactory(stringCellFactory);
        tcSignedPhotocopies.setCellFactory(CheckBoxTableCell.forTableColumn(tcSignedPhotocopies));

        tvOverview.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("monasticName"));
        tcSignedPhotocopies.setCellValueFactory(new PropertyValueFactory<EntryPrintedDocStock, Boolean>("signedPhotocopies"));
        tcNSigned90DForms.setCellValueFactory(new PropertyValueFactory<>("nSigned90DayForms"));
        tcNPrintedPhotos.setCellValueFactory(new PropertyValueFactory<>("nPrintedPhotos"));

        tvOverview.setFixedCellSize(30);
        GUIUtil.initAutoHeightResize(tvOverview, 1.2);
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        loadOverviewTableView();
        if (p.getNPrintedPhotos() != null)
        {
            spPrintedPhotos.getValueFactory().setValue(p.getNPrintedPhotos());
        }
        else
        {
            spPrintedPhotos.getValueFactory().setValue(0);
        }

        if (p.getSignedPhotocopies() != null)
        {
            cbSignedPhotocopies.setSelected(p.getSignedPhotocopies());
        }
        else
        {
            cbSignedPhotocopies.setSelected(false);
        }

        if (p.getNSigned90dForms() != null)
        {
            spSigned90DayForms.getValueFactory().setValue(p.getNSigned90dForms());
        }
        else
        {
            spSigned90DayForms.getValueFactory().setValue(0);
        }

    }

    private void loadOverviewTableView()
    {
        ArrayList<EntryPrintedDocStock> listPrintedDocStock;

        listPrintedDocStock = ctrGUIMain.getCtrMain().getCtrProfile().loadListPrintedDocStock();

        tvOverview.getItems().clear();
        tvOverview.getItems().addAll(listPrintedDocStock);
    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getCtrPaneSelection().isSelectionEmpty();
    }

    @Override
    public void actionLockEdit()
    {
        spPrintedPhotos.setDisable(true);
        spSigned90DayForms.setDisable(true);
        cbSignedPhotocopies.setDisable(true);
    }

    @Override
    public void actionUnlockEdit()
    {
        spPrintedPhotos.setDisable(false);
        spSigned90DayForms.setDisable(false);
        cbSignedPhotocopies.setDisable(false);
    }

    @Override
    public void actionSave()
    {
        MonasticProfile p;
        int operationStatus;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        if (p != null)
        {
            p.setSignedPhotocopies(cbSignedPhotocopies.isSelected());
            p.setNPrintedPhotos(spPrintedPhotos.getValue());
            p.setNSigned90dForms(spSigned90DayForms.getValue());

            operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            if (operationStatus == 0)
            {
                ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
                loadOverviewTableView();
                CtrAlertDialog.infoDialog("Doc Stock Updated", "The document stock for this monastic was successfully updated");

            }

        }
    }

}
