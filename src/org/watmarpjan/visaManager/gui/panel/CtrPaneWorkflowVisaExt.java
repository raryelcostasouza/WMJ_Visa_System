/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.watmarpjan.visaManager.gui.intface.IEditableGUIForm;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.EntryPrintedDocStock;
import org.watmarpjan.visaManager.model.EntryWorkflowVisaExt;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneWorkflowVisaExt extends AbstractChildPaneController implements IEditableGUIForm, IFormMonasticProfile
{

    @FXML
    private Spinner<String> spPrawat;
    @FXML
    private Spinner<String> spLetterSNP;
    @FXML
    private Spinner<String> spPhotocopiesSNP;

    @FXML
    private Spinner<String> spApprovalSNP;
    @FXML
    private Spinner<String> spTM7;
    @FXML
    private Spinner<String> spLetterIMM;
    @FXML
    private Spinner<String> spPhotocopiesIMM;
    @FXML
    private Spinner<String> spExtraIMM;

    @FXML
    private Button bReset;

    @FXML
    private TableView<EntryWorkflowVisaExt> tvOverview;

    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcSNP;
    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcSNPPrawat;
    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcSNPLetter;
    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcSNPPhotocopies;

    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcIMMApprovalSNP;
    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcIMMTM7;
    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcIMMLetter;
    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcIMMExtra;
    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcIMMPhotocopies;

    @FXML
    private TableColumn<EntryWorkflowVisaExt, String> tcIMM;

    private ObservableList<String> valuesPrawat, valuesLetter, valuesApprovalSNP, valuesTM7, valuesExtraIMM, valuesPhotocopies;

    private static final String STATUS_MISSING = "Missing";

    private Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>> prawatCellFactory = new Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryWorkflowVisaExt, String> param)
        {
            final TableCell<EntryWorkflowVisaExt, String> cell = new TableCell<EntryWorkflowVisaExt, String>()
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
                        if (item.equals(EntryWorkflowVisaExt.STATUS_MISSING))
                        {
                            setTextFill(Color.RED);
                        }
                        else if (item.equals(Util.getLastElement(valuesPrawat)))
                        {
                            setTextFill(Color.GREEN);
                        }
                        else
                        {
                            setTextFill(Color.DARKGOLDENROD);
                        }
                    }

                }
            };
            return cell;

        }
    };

    private Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>> letterCellFactory = new Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryWorkflowVisaExt, String> param)
        {
            final TableCell<EntryWorkflowVisaExt, String> cell = new TableCell<EntryWorkflowVisaExt, String>()
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
                        if (item.equals(EntryWorkflowVisaExt.STATUS_MISSING))
                        {
                            setTextFill(Color.RED);
                        }
                        else if (item.equals(Util.getLastElement(valuesLetter)))
                        {
                            setTextFill(Color.GREEN);
                        }
                        else
                        {
                            setTextFill(Color.DARKGOLDENROD);
                        }
                    }

                }
            };
            return cell;

        }
    };

    private Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>> photocopiesCellFactory = new Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryWorkflowVisaExt, String> param)
        {
            final TableCell<EntryWorkflowVisaExt, String> cell = new TableCell<EntryWorkflowVisaExt, String>()
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
                        if (item.equals(EntryWorkflowVisaExt.STATUS_MISSING))
                        {
                            setTextFill(Color.RED);
                        }
                        else if (item.equals(Util.getLastElement(valuesPhotocopies)))
                        {
                            setTextFill(Color.GREEN);
                        }
                        else
                        {
                            setTextFill(Color.DARKGOLDENROD);
                        }
                    }

                }
            };
            return cell;

        }
    };

    private Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>> approvalSNPCellFactory = new Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryWorkflowVisaExt, String> param)
        {
            final TableCell<EntryWorkflowVisaExt, String> cell = new TableCell<EntryWorkflowVisaExt, String>()
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
                        if (item.equals(EntryWorkflowVisaExt.STATUS_MISSING))
                        {
                            setTextFill(Color.RED);
                        }
                        else if (item.equals(Util.getLastElement(valuesApprovalSNP)))
                        {
                            setTextFill(Color.GREEN);
                        }
                        else
                        {
                            setTextFill(Color.DARKGOLDENROD);
                        }
                    }

                }
            };
            return cell;

        }
    };

    private Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>> tm7CellFactory = new Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryWorkflowVisaExt, String> param)
        {
            final TableCell<EntryWorkflowVisaExt, String> cell = new TableCell<EntryWorkflowVisaExt, String>()
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
                        if (item.equals(EntryWorkflowVisaExt.STATUS_MISSING))
                        {
                            setTextFill(Color.RED);
                        }
                        else if (item.equals(Util.getLastElement(valuesTM7)))
                        {
                            setTextFill(Color.GREEN);
                        }
                        else
                        {
                            setTextFill(Color.DARKGOLDENROD);
                        }
                    }

                }
            };
            return cell;

        }
    };
    private Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>> extraCellFactory = new Callback<TableColumn<EntryWorkflowVisaExt, String>, TableCell<EntryWorkflowVisaExt, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryWorkflowVisaExt, String> param)
        {
            final TableCell<EntryWorkflowVisaExt, String> cell = new TableCell<EntryWorkflowVisaExt, String>()
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
                        if (item.equals(EntryWorkflowVisaExt.STATUS_MISSING))
                        {
                            setTextFill(Color.RED);
                        }
                        else if (item.equals(Util.getLastElement(valuesExtraIMM)))
                        {
                            setTextFill(Color.GREEN);
                        }
                        else
                        {
                            setTextFill(Color.DARKGOLDENROD);
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
        initChangeListener();
        initSpinner();
        initTableView();

    }

    private void initChangeListener()
    {
        ArrayList listFields;

        listFields = new ArrayList();

        listFields.add(spPrawat);
        listFields.add(spLetterSNP);
        listFields.add(spPhotocopiesSNP);

        listFields.add(spApprovalSNP);
        listFields.add(spLetterIMM);
        listFields.add(spTM7);
        listFields.add(spPhotocopiesIMM);
        listFields.add(spExtraIMM);

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);
    }

    private void initSpinner()
    {
        ArrayList<Spinner> listSpinner;

        listSpinner = new ArrayList<>();
        listSpinner.add(spPrawat);
        listSpinner.add(spLetterSNP);
        listSpinner.add(spPhotocopiesSNP);

        listSpinner.add(spApprovalSNP);
        listSpinner.add(spTM7);
        listSpinner.add(spLetterIMM);
        listSpinner.add(spPhotocopiesIMM);
        listSpinner.add(spExtraIMM);

        for (Spinner objSpinner : listSpinner)
        {
            objSpinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
        }

        valuesPrawat = FXCollections.observableArrayList("Missing", "Printed", "Signed by abbot",
                "Sent to Jaokana", "Signed by Jaokana");

        valuesPhotocopies = FXCollections.observableArrayList("Missing", "Printed", "Signed by monastic");
        valuesLetter = FXCollections.observableArrayList("Missing", "Printed", "Signed by abbot");
        valuesApprovalSNP = FXCollections.observableArrayList("Missing", "Request sent", "Received");
        valuesTM7 = FXCollections.observableArrayList("Missing", "Printed", "Photo attached");
        valuesExtraIMM = FXCollections.observableArrayList("Missing", "Printed");

        spPrawat.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesPrawat));
        spLetterSNP.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesLetter));
        spPhotocopiesSNP.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesPhotocopies));

        spApprovalSNP.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesApprovalSNP));
        spTM7.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesTM7));
        spPhotocopiesIMM.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesPhotocopies));
        spLetterIMM.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesLetter));
        spExtraIMM.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(valuesExtraIMM));
    }

    private void initTableView()
    {
        tvOverview.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("monastic"));

        tcSNPPrawat.setCellValueFactory(new PropertyValueFactory<>("snpPrawat"));
        tcSNPPrawat.setCellFactory(prawatCellFactory);

        tcSNPLetter.setCellValueFactory(new PropertyValueFactory<>("snpLetter"));
        tcSNPLetter.setCellFactory(letterCellFactory);

        tcSNPPhotocopies.setCellValueFactory(new PropertyValueFactory<>("snpPhotocopies"));
        tcSNPPhotocopies.setCellFactory(photocopiesCellFactory);

        tcIMMApprovalSNP.setCellValueFactory(new PropertyValueFactory<>("immApprovalSNP"));
        tcIMMApprovalSNP.setCellFactory(approvalSNPCellFactory);

        tcIMMTM7.setCellValueFactory(new PropertyValueFactory<>("immTM7"));
        tcIMMTM7.setCellFactory(tm7CellFactory);

        tcIMMLetter.setCellValueFactory(new PropertyValueFactory<>("immLetter"));
        tcIMMLetter.setCellFactory(letterCellFactory);

        tcIMMPhotocopies.setCellValueFactory(new PropertyValueFactory<>("immPhotocopies"));
        tcIMMPhotocopies.setCellFactory(photocopiesCellFactory);

        tcIMMExtra.setCellValueFactory(new PropertyValueFactory<>("immExtra"));
        tcIMMExtra.setCellFactory(extraCellFactory);
    }

    @Override
    public void actionLockEdit()
    {
        bReset.setDisable(true);

        spPrawat.setDisable(true);
        spLetterSNP.setDisable(true);
        spPhotocopiesSNP.setDisable(true);

        spApprovalSNP.setDisable(true);
        spTM7.setDisable(true);
        spLetterIMM.setDisable(true);
        spPhotocopiesIMM.setDisable(true);
        spExtraIMM.setDisable(true);
    }

    @Override
    public void actionSave()
    {
        MonasticProfile p;
        int opStatus;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p != null)
        {
            p.setWfExtPrawat(spPrawat.getValue());
            p.setWfExtLetterSnp(spLetterSNP.getValue());
            p.setWfExtPhotocopiesSnp(spPhotocopiesSNP.getValue());

            p.setWfExtApprovalSnp(spApprovalSNP.getValue());
            p.setWfExtTm7(spTM7.getValue());
            p.setWfExtLetterImm(spLetterIMM.getValue());

            //if status printed
            if (spExtraIMM.getValue().equals(valuesExtraIMM.get(1)))
            {
                p.setWfExtExtraImm(Boolean.TRUE);
            }
            //if status missing
            else
            {
                p.setWfExtExtraImm(Boolean.FALSE);
            }

            p.setWfExtPhotocopiesImm(spPhotocopiesIMM.getValue());

            opStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            if (opStatus == 0)
            {
                fillData(p);
                ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
                CtrAlertDialog.infoDialog("Workflow Visa Extension Updated", "The visa workflow for this monastic was updated successfully.");
            }
        }
    }

    @Override
    public void actionUnlockEdit()
    {
        bReset.setDisable(false);

        spPrawat.setDisable(false);
        spLetterSNP.setDisable(false);
        spPhotocopiesSNP.setDisable(false);

        spApprovalSNP.setDisable(false);
        spTM7.setDisable(false);
        spLetterIMM.setDisable(false);
        spPhotocopiesIMM.setDisable(false);
        spExtraIMM.setDisable(false);
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        loadTableOverview();

        if (p != null)
        {
            if (p.getWfExtPrawat() != null)
            {
                spPrawat.getValueFactory().setValue(p.getWfExtPrawat());
            }
            else
            {
                spPrawat.getValueFactory().setValue(STATUS_MISSING);
            }

            if (p.getWfExtLetterSnp() != null)
            {
                spLetterSNP.getValueFactory().setValue(p.getWfExtLetterSnp());
            }
            else
            {
                spLetterSNP.getValueFactory().setValue(STATUS_MISSING);
            }

            if (p.getWfExtPhotocopiesSnp() != null)
            {
                spPhotocopiesSNP.getValueFactory().setValue(p.getWfExtPhotocopiesSnp());
            }
            else
            {
                spPhotocopiesSNP.getValueFactory().setValue(STATUS_MISSING);
            }

            if (p.getWfExtApprovalSnp() != null)
            {
                spApprovalSNP.getValueFactory().setValue(p.getWfExtApprovalSnp());
            }
            else
            {
                spApprovalSNP.getValueFactory().setValue(STATUS_MISSING);
            }

            if (p.getWfExtTm7() != null)
            {
                spTM7.getValueFactory().setValue(p.getWfExtTm7());
            }
            else
            {
                spTM7.getValueFactory().setValue(STATUS_MISSING);
            }

            if (p.getWfExtLetterImm() != null)
            {
                spLetterIMM.getValueFactory().setValue(p.getWfExtLetterImm());
            }
            else
            {
                spLetterIMM.getValueFactory().setValue(STATUS_MISSING);
            }

            if (p.getWfExtPhotocopiesImm() != null)
            {
                spPhotocopiesIMM.getValueFactory().setValue(p.getWfExtPhotocopiesImm());
            }
            else
            {
                spPhotocopiesIMM.getValueFactory().setValue(STATUS_MISSING);
            }

            if ((p.getWfExtExtraImm() != null) && (p.getWfExtExtraImm()))
            {
                //printed
                spExtraIMM.getValueFactory().setValue(valuesExtraIMM.get(1));
            }
            else
            {
                //missing
                spExtraIMM.getValueFactory().setValue(valuesExtraIMM.get(0));
            }
        }
    }

    private void loadTableOverview()
    {
        ArrayList<EntryWorkflowVisaExt> listWFVisaExt;

        listWFVisaExt = ctrGUIMain.getCtrMain().getCtrProfile().loadListWorkflowVisaExt();

        tvOverview.getItems().clear();
        tvOverview.getItems().addAll(listWFVisaExt);

    }

    @FXML
    void actionResetWorkflow(ActionEvent ae)
    {
        int opStatus;
        boolean confirmation;
        MonasticProfile p;

        confirmation = CtrAlertDialog.confirmationDialog("Confirmation", "Are you sure you want to RESET the workflow for this monastic?");
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            p.setWfExtPrawat(STATUS_MISSING);
            p.setWfExtLetterSnp(STATUS_MISSING);
            p.setWfExtPhotocopiesSnp(STATUS_MISSING);

            p.setWfExtApprovalSnp(STATUS_MISSING);
            p.setWfExtTm7(STATUS_MISSING);
            p.setWfExtLetterImm(STATUS_MISSING);
            p.setWfExtPhotocopiesImm(STATUS_MISSING);
            p.setWfExtExtraImm(false);

            opStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            if (opStatus == 0)
            {
                fillData(p);
                ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
                CtrAlertDialog.infoDialog("Workflow Visa Extension Reset", "The visa workflow for this monastic was RESET successfully.");
            }
        }
    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getCtrPaneSelection().isSelectionEmpty();
    }

}
