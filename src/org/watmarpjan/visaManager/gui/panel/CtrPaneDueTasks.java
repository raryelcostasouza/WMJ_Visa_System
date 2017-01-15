/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import org.watmarpjan.visaManager.model.dueTask.EntryDueTask;
import org.watmarpjan.visaManager.model.dueTask.TaskNotice90D;
import org.watmarpjan.visaManager.model.dueTask.TaskExtendVisaOld;
import org.watmarpjan.visaManager.util.Util;

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
    private TableColumn<EntryDueTask, String> tcFirstDay;
    @FXML
    private TableColumn<EntryDueTask, String> tcLastDayOnline;
    @FXML
    private TableColumn<EntryDueTask, String> tcLastDayOffice;

    private ArrayList<TableView<EntryDueTask>> alTV;

    @FXML
    private TextArea taRecentChanges;

    @FXML
    private Button bPrintTH;
    @FXML
    private Button bPreviewTH;

    @FXML
    private Button bPrintAbroad;
    @FXML
    private Button bPreviewAbroad;

    private final Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>> actionCellFactory
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
                            if (objEntry instanceof TaskNotice90D)
                            {
                                ctrGUIMain.actionButton90DayNotice(null);
                            }
                            else if (objEntry instanceof TaskExtendVisaOld)
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

    private final Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>> weekDayCellFactory = new Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryDueTask, String> param)
        {
            final TableCell<EntryDueTask, String> cell = new TableCell<EntryDueTask, String>()
            {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    if (item == null || empty)
                    {
                        setText(null);
                        setTextFill(Color.BLACK);
                    }
                    else
                    {
                        setText(item);
                        if (item.equals("SUN") || item.equals("SAT"))
                        {
                            setTextFill(Color.RED);
                        }
                    }

                }
            };
            return cell;

        }
    };
    private final Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>> dateCellFactory = new Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>>()
    {
        @Override
        public TableCell call(final TableColumn<EntryDueTask, String> param)
        {
            final TableCell<EntryDueTask, String> cell = new TableCell<EntryDueTask, String>()
            {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    if (item == null || empty)
                    {
                        setText(null);
                        setTextFill(Color.BLACK);
                    }
                    else
                    {
                        LocalDate objLD;

                        objLD = LocalDate.parse(item, Util.DEFAULT_DATE_FORMAT);
                        setText(item);
                        //if the date in the cell is passed 
                        if (LocalDate.now().compareTo(objLD) >= 0)
                        {
                            //paint text in red
                            setTextFill(Color.RED);
                        }
                        else
                        {
                            //paint text in black
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

    private void initTableGeneric(TableView<EntryDueTask> tv)
    {
        TableColumn<EntryDueTask, String> tcAction, tcWeekDay, tcDueDate;

        
        tcDueDate = (TableColumn<EntryDueTask, String>) tv.getColumns().get(2);
        tcDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        tcDueDate.setCellFactory(dateCellFactory);
        
        tv.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("profileNickname"));
        
        tv.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("weekDayDueDate"));
        tv.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("remainingTime"));

        tcAction = (TableColumn<EntryDueTask, String>) tv.getColumns().get(0);
        tcAction.setCellValueFactory(new PropertyValueFactory<>(""));
        tcAction.setCellFactory(actionCellFactory);

        tcWeekDay = (TableColumn<EntryDueTask, String>) tv.getColumns().get(3);
        tcWeekDay.setCellFactory(weekDayCellFactory);
    }

    private void initTable90Day(TableView<EntryDueTask> tv)
    {
        TableColumn<EntryDueTask, Boolean> tcOnline;
        initTableGeneric(tv);

        tcOnline = (TableColumn<EntryDueTask, Boolean>) tv.getColumns().get(5);
        tcOnline.setCellFactory(CheckBoxTableCell.forTableColumn(tcOnline));
        tcOnline.setCellValueFactory(new PropertyValueFactory<>("onlineNoticeAccepted"));

        tcFirstDay.setCellValueFactory(new PropertyValueFactory<>("firstDay"));
        tcFirstDay.setCellFactory(dateCellFactory);

        tcLastDayOnline.setCellValueFactory(new PropertyValueFactory<>("lastDayOnline"));
        tcLastDayOnline.setCellFactory(dateCellFactory);

        tcLastDayOffice.setCellValueFactory(new PropertyValueFactory<>("lastDayOffice"));
        tcLastDayOffice.setCellFactory(dateCellFactory);

        GUIUtil.initAutoHeightResize(tv, 2.7);
    }

    private void initTableVisaExtension(TableView<EntryDueTask> tv)
    {
        TableColumn<EntryDueTask, String> tcPrawat, tcSamnakput, tcImmigration;
        initTableGeneric(tv);

        tcPrawat = (TableColumn<EntryDueTask, String>) tv.getColumns().get(5).getColumns().get(0);
        tcPrawat.setCellValueFactory(new PropertyValueFactory<>("prawat"));
        tcPrawat.setCellFactory(dateCellFactory);

        tcSamnakput = (TableColumn<EntryDueTask, String>) tv.getColumns().get(5).getColumns().get(1);
        tcSamnakput.setCellValueFactory(new PropertyValueFactory<>("samnakput"));
        tcSamnakput.setCellFactory(dateCellFactory);

        tcImmigration = (TableColumn<EntryDueTask, String>) tv.getColumns().get(5).getColumns().get(2);
        tcImmigration.setCellValueFactory(new PropertyValueFactory<>("immigration"));
        tcImmigration.setCellFactory(dateCellFactory);

        GUIUtil.initAutoHeightResize(tv, 2.5);
    }

    private void initTablePassportRenew(TableView<EntryDueTask> tv)
    {
        TableColumn<EntryDueTask, String> tcBeginProcBy;

        initTableGeneric(tv);

        tcBeginProcBy = (TableColumn<EntryDueTask, String>) tv.getColumns().get(5);
        tcBeginProcBy.setCellValueFactory(new PropertyValueFactory<>("beginProcessingBy"));
        tcBeginProcBy.setCellFactory(dateCellFactory);

        GUIUtil.initAutoHeightResize(tv, 1.01);
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

        taRecentChanges.setText(CtrFileOperation.loadChangelog());

    }

    @FXML
    void actionPrintDueTasksTH(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFDueTasksTH(tvTH90DayNotice, tvTHVisaExtension, tvTHPassportRenewal, CtrPDF.OPTION_PRINT_FORM);
    }

    @FXML
    void actionPreviewDueTasksTH(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFDueTasksTH(tvTH90DayNotice, tvTHVisaExtension, tvTHPassportRenewal, CtrPDF.OPTION_PREVIEW_FORM);
    }

    @FXML
    void actionPrintDueTasksAbroad(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFDueTasksAbroad(tvAbroadVisaExtension, tvAbroadPassportRenewal, CtrPDF.OPTION_PRINT_FORM);
    }

    @FXML
    void actionPreviewDueTasksAbroad(ActionEvent ae)
    {
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFDueTasksAbroad(tvAbroadVisaExtension, tvAbroadPassportRenewal, CtrPDF.OPTION_PREVIEW_FORM);
    }
}
