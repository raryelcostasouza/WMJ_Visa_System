/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.model.EntryDueTask;

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

    @Override
    public void init()
    {
        TableColumn<EntryDueTask, String> tcAction;

        alTV = new ArrayList<>();
        alTV.add(tvTH90DayNotice);
        alTV.add(tvTHVisaExtension);
        alTV.add(tvTHPassportRenewal);

        alTV.add(tvAbroadVisaExtension);
        alTV.add(tvAbroadPassportRenewal);

        Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>> actionCellFactory =
                new Callback<TableColumn<EntryDueTask, String>, TableCell<EntryDueTask, String>>()
        {
            @Override
            public TableCell call(final TableColumn<EntryDueTask, String> param)
            {
                final TableCell<EntryDueTask, String> cell = new TableCell<EntryDueTask, String>()
                {

                    final Button btn = new Button(">");

                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (empty)
                        {
                            setGraphic(null);
                            setText(null);
                        } else
                        {
                            btn.setOnAction((ActionEvent event)
                                    -> 
                                    {
                                        //ctrGUIMain.actionButton90DayNotice(null);
                                        System.out.println("hello");
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        for (TableView<EntryDueTask> tv : alTV)
        {
            tv.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("profileNickname"));
            tv.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("dueDate"));
            tv.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("weekDayDueDate"));
            tv.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("remainingTime"));
            tv.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("beginProcessingBy"));

            tcAction = (TableColumn<EntryDueTask, String>) tv.getColumns().get(5);
            tcAction.setCellValueFactory(new PropertyValueFactory<>(""));
            tcAction.setCellFactory(actionCellFactory);

        }

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

}
