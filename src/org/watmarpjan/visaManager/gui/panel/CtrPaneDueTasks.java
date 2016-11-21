/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrForm;
import org.watmarpjan.visaManager.model.dueTask.EntryDueTask;
import org.watmarpjan.visaManager.model.dueTask.Notice90DayTaskEntry;
import org.watmarpjan.visaManager.model.dueTask.VisaExtTaskEntry;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneDueTasks extends AbstractChildPaneController
{

    @FXML
    private TableView<EntryDueTask> tvTH90DayNotice;
    @FXML
    private TableView<EntryDueTask> tvTHVisaExtension;
    @FXML
    private TableView<EntryDueTask> tvTHPassportRenewal;
    @FXML
    private TableView<EntryDueTask> tvAbroadVisaExtension;
    @FXML
    private TableView<EntryDueTask> tvAbroadPassportRenewal;

    @FXML
    private TitledPane tPAux90DayTH;
    @FXML
    private TitledPane tPAuxVisaExtTH;
    @FXML
    private TitledPane tPAuxPsptTH;

    private ArrayList<TableView<EntryDueTask>> alTV;

    @FXML
    private Button bPrint;
    @FXML
    private Button bPreview;

    @Override
    public void init()
    {
        bPrint.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
        bPreview.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));

        alTV = new ArrayList<>();
        alTV.add(tvTH90DayNotice);
        alTV.add(tvTHVisaExtension);
        alTV.add(tvTHPassportRenewal);

        alTV.add(tvAbroadVisaExtension);
        alTV.add(tvAbroadPassportRenewal);

        initTable90Day(tvTH90DayNotice);
        initTableVisaExtension(tvTHVisaExtension);
        initTableVisaExtension(tvAbroadVisaExtension);

        initTablePassportRenew(tvTHPassportRenewal);
        initTablePassportRenew(tvAbroadPassportRenewal);
    }

    private void initTableGeneric(TableView<EntryDueTask> tv)
    {
        TableColumn<EntryDueTask, String> tcAction;

        Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>> actionCellFactory
                = new Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>>()
        {
            @Override
            public TableCell call(final TableColumn<EntryDueTask, String> param)
            {
                final TableCell<EntryDueTask, String> cell = new TableCell<EntryDueTask, String>()
                {
                    final ImageView ivActionIcon = new ImageView(AppPaths.getPathToIconSubfolder().resolve("action.png").toUri().toString());
                    final Button btn = new Button("");

                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        }
                        else
                        {
                            btn.setGraphic(ivActionIcon);
                            btn.setOnAction((ActionEvent event)
                                    ->
                            {
                                EntryDueTask objEntry;

                                objEntry = getTableView().getItems().get(getIndex());
                                ctrGUIMain.getCtrPaneSelection().setSelectedProfileByNickname(objEntry.getProfileNickname());
                                if (objEntry instanceof Notice90DayTaskEntry)
                                {
                                    ctrGUIMain.actionButton90DayNotice(null);
                                }
                                else if (objEntry instanceof VisaExtTaskEntry)
                                {
                                    ctrGUIMain.actionButtonVisaExt(null);
                                }
                                else
                                {
                                    ctrGUIMain.actionButtonAddRenewPassport(null);
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        tv.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("profileNickname"));
        tv.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        tv.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("weekDayDueDate"));
        tv.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("remainingTime"));

        tcAction = (TableColumn<EntryDueTask, String>) tv.getColumns().get(0);
        tcAction.setCellValueFactory(new PropertyValueFactory<>(""));
        tcAction.setCellFactory(actionCellFactory);
    }

    private void initTable90Day(TableView<EntryDueTask> tv)
    {
        initTableGeneric(tv);

        tv.getColumns().get(5).getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("firstDay"));
        tv.getColumns().get(5).getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("lastDayOnline"));
        tv.getColumns().get(5).getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("lastDayOffice"));
    }

    private void initTableVisaExtension(TableView<EntryDueTask> tv)
    {
        initTableGeneric(tv);

        tv.getColumns().get(5).getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("prawat"));
        tv.getColumns().get(5).getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("samnakput"));
        tv.getColumns().get(5).getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("immigration"));
    }

    private void initTablePassportRenew(TableView<EntryDueTask> tv)
    {
        initTableGeneric(tv);
        tv.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("beginProcessingBy"));
    }

    public void fillData()
    {
        ArrayList<EntryDueTask> alTHDue90dNotice, alTHDueVisaExtension, alTHPassportRenewal,
                alAbroadDueVisaExtension, alAbroadPassportRenewal;

        alTHDue90dNotice = ctrGUIMain.getCtrMain().getCtrProfile().loadListDue90DayNotice();
        alTHDueVisaExtension = ctrGUIMain.getCtrMain().getCtrProfile().loadListDueVisaExtension(AppConstants.STATUS_THAILAND);
        alTHPassportRenewal = ctrGUIMain.getCtrMain().getCtrProfile().loadListDuePassportRenewal(AppConstants.STATUS_THAILAND);

        alAbroadDueVisaExtension = ctrGUIMain.getCtrMain().getCtrProfile().loadListDueVisaExtension(AppConstants.STATUS_ABROAD);
        alAbroadPassportRenewal = ctrGUIMain.getCtrMain().getCtrProfile().loadListDuePassportRenewal(AppConstants.STATUS_ABROAD);

        for (TableView tv : alTV)
        {
            tv.getItems().clear();
        }

        tvTH90DayNotice.getItems().addAll(alTHDue90dNotice);
        tvTHVisaExtension.getItems().addAll(alTHDueVisaExtension);
        tvTHPassportRenewal.getItems().addAll(alTHPassportRenewal);

        tvAbroadVisaExtension.getItems().addAll(alAbroadDueVisaExtension);
        tvAbroadPassportRenewal.getItems().addAll(alAbroadPassportRenewal);
    }

    @FXML
    void actionPrintDueTasks(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePDFDueTasks(tPAuxVisaExtTH, tPAux90DayTH, tPAuxPsptTH, CtrForm.OPTION_PRINT_FORM);
    }

    @FXML
    void actionPreviewDueTasks(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePDFDueTasks(tPAux90DayTH, tPAuxVisaExtTH, tPAuxPsptTH, CtrForm.OPTION_PREVIEW_FORM);
    }
}