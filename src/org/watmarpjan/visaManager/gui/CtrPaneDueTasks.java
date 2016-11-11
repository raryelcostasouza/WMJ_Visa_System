/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.Init;
import org.watmarpjan.visaManager.model.EntryDueTask;
import org.watmarpjan.visaManager.model.Notice90DayTaskEntry;
import org.watmarpjan.visaManager.model.VisaExtTaskEntry;

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

    private ArrayList<TableView<EntryDueTask>> alTV;

    @FXML
    private BorderPane bPaneDueTasksTH;

    @FXML
    private GridPane bPaneDueTasksAbroad;

    @FXML
    private Button bPrint;

    @Override
    public void init()
    {
        bPrint.setGraphic(new ImageView(AppPaths.getPathIconPrint().toUri().toString()));

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
        PrinterJob pj = PrinterJob.createPrinterJob();
        boolean success;
        Rectangle rect;
        WritableImage writableImage;
        ImageView imgView;
        Printer printer;
        PageLayout pageLayout;
        boolean showDialog;

        rect = new Rectangle(0, 0, bPaneDueTasksTH.getWidth(), bPaneDueTasksTH.getHeight());
        bPaneDueTasksTH.setClip(rect);
        writableImage = new WritableImage((int) bPaneDueTasksTH.getWidth(), (int) bPaneDueTasksTH.getHeight());
        bPaneDueTasksTH.snapshot(null, writableImage);

        imgView = new ImageView(writableImage);
        printer = Printer.getDefaultPrinter();
        pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / imgView.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / imgView.getBoundsInParent().getHeight();
        imgView.getTransforms().add(new Scale(scaleX, scaleY));

        if (pj != null)
        {
           showDialog = pj.showPrintDialog(Init.MAIN_STAGE);
            if (showDialog)
            {
                success = pj.printPage(pageLayout, imgView);
                if (success)
                {
                    pj.endJob();
                }
            }

        }

    }

}
