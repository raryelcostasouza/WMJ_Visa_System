/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.util.ArrayList;
import javafx.beans.binding.Bindings;
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
    private Button bPrintTH;
    @FXML
    private Button bPreviewTH;
    
    @FXML
    private Button bPrintAbroad;
    @FXML
    private Button bPreviewAbroad;

    @Override
    public void init()
    {
        bPrintTH.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
        bPreviewTH.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        
        bPrintAbroad.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));
        bPreviewAbroad.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));

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

        for (TableView<EntryDueTask> tv : alTV)
        {
            tv.setFixedCellSize(30);
        }
    }
    
    private void initAutoHeightResize(TableView<EntryDueTask> tv, double headerAdjust)
    {
        tv.prefHeightProperty().bind(tv.fixedCellSizeProperty().multiply(Bindings.size(tv.getItems()).add(2.5)));
        tv.minHeightProperty().bind(tv.prefHeightProperty());
        tv.maxHeightProperty().bind(tv.prefHeightProperty());
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
                            btn.setScaleY(0.5);
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
        
        initAutoHeightResize(tv, 2.5);
    }

    private void initTableVisaExtension(TableView<EntryDueTask> tv)
    {
        initTableGeneric(tv);

        tv.getColumns().get(5).getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("prawat"));
        tv.getColumns().get(5).getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("samnakput"));
        tv.getColumns().get(5).getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("immigration"));
        
        initAutoHeightResize(tv, 2.5);
    }

    private void initTablePassportRenew(TableView<EntryDueTask> tv)
    {
        initTableGeneric(tv);
        tv.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("beginProcessingBy"));
        
        initAutoHeightResize(tv, 1.01);
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
    void actionPrintDueTasksTH(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePDFDueTasksTH(tvTH90DayNotice, tvTHVisaExtension, tvTHPassportRenewal, CtrForm.OPTION_PRINT_FORM);
    }

    @FXML
    void actionPreviewDueTasksTH(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePDFDueTasksTH(tvTH90DayNotice, tvTHVisaExtension, tvTHPassportRenewal, CtrForm.OPTION_PREVIEW_FORM);
    }
    
    @FXML
    void actionPrintDueTasksAbroad(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePDFDueTasksAbroad(tvAbroadVisaExtension, tvAbroadPassportRenewal, CtrForm.OPTION_PRINT_FORM);
    }

    @FXML
    void actionPreviewDueTasksAbroad(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrForm().generatePDFDueTasksAbroad(tvAbroadVisaExtension, tvAbroadPassportRenewal, CtrForm.OPTION_PREVIEW_FORM);
    }
}
