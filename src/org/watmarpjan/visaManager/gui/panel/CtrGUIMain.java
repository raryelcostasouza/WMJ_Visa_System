/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.time.Duration;
import java.time.Instant;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.intface.IEditableGUIForm;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.CtrDatePicker;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.watmarpjan.visaManager.Init;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.gui.util.CtrFieldChangeListener;
import org.watmarpjan.visaManager.model.ResultDialogSelectScan.AbstractResultDialogSelectScan;

/**
 *
 * @author WMJ_user
 */
public class CtrGUIMain
{

    private CtrMain ctrMain;
    private CtrDatePicker ctrDatePicker;

    @FXML
    private VBox rootPane;

    @FXML
    private RadioButton rbDateFormatWestern;

    @FXML
    private ToggleGroup tgDateFormat;

    @FXML
    private CtrPaneMonasticSelection ctrPaneMonasticSelection;
    @FXML
    private Pane paneMonasticSelection;

    @FXML
    private CtrPaneEditSave ctrPaneEditSave;
    @FXML
    private HBox paneEditSave;

    @FXML
    private CtrPaneMonasticProfile ctrPaneMonasticProfile;
    @FXML
    private VBox paneMonasticProfile;

    @FXML
    private CtrPaneMonastery ctrPaneMonastery;
    @FXML
    private VBox paneMonastery;

    @FXML
    private CtrPaneUpajjhaya ctrPaneUpajjhaya;
    @FXML
    private VBox paneUpajjhaya;

    @FXML
    private CtrPanePassport ctrPanePassport;
    @FXML
    private BorderPane panePassport;

    @FXML
    private CtrPaneBysuddhi ctrPaneBysuddhi;
    @FXML
    private VBox paneBysuddhi;

    @FXML
    private CtrPaneDueTasks ctrPaneDueTasks;
    @FXML
    private BorderPane paneDueTasks;

    @FXML
    private CtrPane90DayNotice ctrPane90DayNotice;
    @FXML
    private TabPane pane90DayNotice;

    @FXML
    private CtrPaneVisaExt ctrPaneVisaExt;
    @FXML
    private TabPane paneVisaExt;

    @FXML
    private CtrPaneAddEntryReEntry ctrPaneReEntry;
    @FXML
    private VBox paneReEntry;

    @FXML
    private CtrPaneAddRenewPassport ctrPaneAddRenewPassport;
    @FXML
    private VBox paneAddRenewPassport;

    @FXML
    private CtrPaneAddChangeVisa ctrPaneAddChangeVisa;
    @FXML
    private VBox paneAddChangeVisa;

    @FXML
    private CtrPaneTM30NotifResidence ctrPaneTM30NotifResidence;
    @FXML
    private TabPane paneTM30NotifResidence;

    @FXML
    private CtrPaneChangelog ctrPaneChangelog;
    @FXML
    private TabPane paneChangelog;

    @FXML
    private CtrPaneConversionTools ctrPaneConvert;
    @FXML
    private VBox paneConvert;

    @FXML
    private CtrPanePhotoPagePrinting ctrPanePhotoPagePrint;
    @FXML
    private VBox panePhotoPagePrint;

    @FXML
    private CtrPanePrintedDocStock ctrPanePrintedDocStock;
    @FXML
    private VBox panePrintedDocStock;

    private Dialog<AbstractResultDialogSelectScan> dialogSelectExtraScan;

    @FXML
    private BorderPane centerPane;
    @FXML
    private BorderPane topPane;

    private AbstractChildPaneController currentPaneController;
    private ArrayList<AbstractChildPaneController> listPaneControllers;

    private CtrFieldChangeListener ctrFieldChangeListener;
    private CtrGUISharedUtil ctrGUISharedUtil;

    private CtrGUIMain ctrGUIMainSelfReference = this;
    private BooleanProperty[] flagFXMLLoaded = new BooleanProperty[18];
    private BooleanProperty flagInitCtrData = new SimpleBooleanProperty(Boolean.FALSE);

    public CtrFieldChangeListener getCtrFieldChangeListener()
    {
        return ctrFieldChangeListener;
    }

    @FXML
    void initialize()
    {
        //flags to control the threads that will load the FXML files in parallel

        //the array value is false when the thread still didn`t finish its task
        //the array value changes to true when the thread finish
        for (int i = 0; i < flagFXMLLoaded.length; i++)
        {
            flagFXMLLoaded[i] = new SimpleBooleanProperty(false);
            flagFXMLLoaded[i].addListener(new ChangeListener<Boolean>()
            {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
                {
                    actionThreadFinishTask();
                }
            });
        }

        flagInitCtrData.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue)
            {
                actionThreadFinishTask();
            }
        });

        //starts cleaning tmp files
        //in case the app was forced to close
        CtrFileOperation.clearTMPFiles();
        initDataControllers();
        this.ctrDatePicker = new CtrDatePicker();
        this.ctrGUISharedUtil = new CtrGUISharedUtil(this);

        Init.MAIN_STAGE.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent event)
            {
                Init.MAIN_STAGE.close();
                CtrFileOperation.clearTMPFiles();
                ctrMain.getCtrDB().closeDBConnection();
                System.exit(0);
            }
        });

        listPaneControllers = new ArrayList<>();
        initPaneDueTasks();

        initPaneMonasticProfile();
        initPanePassport();
        initPaneBysuddhi();
        initPaneMonastery();
        initPaneUpajjhaya();

        initPane90DayNotice();
        initPaneAddChangeVisa();
        initPaneAddRenewPassport();
        initPaneReEntry();
        initPaneVisaExt();
        initPaneTM30NotifResidence();

        initPaneChangelog();
        initPaneConvert();
        initPanePhotoPagePrinting();
        initPanePrintedDocStock();

        initPaneMonasticSelection();
        initPaneEditSave();

    }

    private void actionThreadFinishTask()
    {
        //whenever a thread finishes its task this section will be executed
        boolean finished = true;
        //if the Initialization of the data controllers finished
        if (flagInitCtrData.getValue().equals(Boolean.TRUE))
        {
            //test if ALL the other threads finish their work
            for (int j = 0; j < flagFXMLLoaded.length; j++)
            {
                //if any thread still didn`t finish its work
                if (flagFXMLLoaded[j].getValue().equals(Boolean.FALSE))
                {
                    finished = false;
                    break;
                }
            }
            //if ALL the other threads finish their work
            //then the GUI data can be initialized.
            if (finished)
            {
                initGUIAfterFXMLLoad();
            }
        }
    }

    private void initDataControllers()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                try
                {
                    ctrMain = new CtrMain(ctrGUIMainSelfReference);
                    flagInitCtrData.setValue(Boolean.TRUE);

                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to init app.");
                }
                return null;
            }
        };
        new Thread(t).start();
    }

    private void initGUIAfterFXMLLoad()
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                Stage primaryStage;
                Scene mainScene = new Scene(rootPane);

                ctrFieldChangeListener = new CtrFieldChangeListener(ctrPaneEditSave);
                initChildControllers();
                actionDueTasksButton(null);

                primaryStage = Init.MAIN_STAGE;
                primaryStage.setScene(mainScene);
                primaryStage.setTitle("WMJ Visa System");
                primaryStage.setWidth(1600);
                primaryStage.setHeight(990);

                primaryStage.show();
                System.out.println("LoadTime: " + Duration.between(Init.INSTANT_INIT_START, Instant.now()));

                //closes the preloader splashscreen
                Init.APP.notifyPreloader(new Preloader.StateChangeNotification(
                        Preloader.StateChangeNotification.Type.BEFORE_START));
            }
        });

    }

    private void initPaneMonasticProfile()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneMonasticProfile.fxml"));
                    paneMonasticProfile = loader.load();
                    ctrPaneMonasticProfile = loader.getController();
                    listPaneControllers.add(ctrPaneMonasticProfile);

                    flagFXMLLoaded[0].setValue(Boolean.TRUE);

                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();
    }

    private void initPanePassport()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("panePassport.fxml"));
                    panePassport = loader.load();
                    ctrPanePassport = loader.getController();
                    listPaneControllers.add(ctrPanePassport);

                    flagFXMLLoaded[1].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();
    }

    private void initPaneBysuddhi()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneBysuddhi.fxml"));
                    paneBysuddhi = loader.load();
                    ctrPaneBysuddhi = loader.getController();
                    listPaneControllers.add(ctrPaneBysuddhi);

                    flagFXMLLoaded[2].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneDueTasks()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneDueTasks.fxml"));
                    paneDueTasks = loader.load();
                    ctrPaneDueTasks = loader.getController();
                    listPaneControllers.add(ctrPaneDueTasks);

                    flagFXMLLoaded[3].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneMonastery()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneMonastery.fxml"));
                    paneMonastery = loader.load();
                    ctrPaneMonastery = loader.getController();
                    listPaneControllers.add(ctrPaneMonastery);

                    flagFXMLLoaded[4].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneUpajjhaya()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneUpajjhaya.fxml"));
                    paneUpajjhaya = loader.load();
                    ctrPaneUpajjhaya = loader.getController();
                    listPaneControllers.add(ctrPaneUpajjhaya);

                    flagFXMLLoaded[5].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneVisaExt()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneVisaExt.fxml"));
                    paneVisaExt = loader.load();
                    ctrPaneVisaExt = loader.getController();
                    listPaneControllers.add(ctrPaneVisaExt);

                    flagFXMLLoaded[6].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneReEntry()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneReEntry.fxml"));
                    paneReEntry = loader.load();
                    ctrPaneReEntry = loader.getController();
                    listPaneControllers.add(ctrPaneReEntry);

                    flagFXMLLoaded[7].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneAddRenewPassport()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneAddRenewPassport.fxml"));
                    paneAddRenewPassport = loader.load();
                    ctrPaneAddRenewPassport = loader.getController();
                    listPaneControllers.add(ctrPaneAddRenewPassport);

                    flagFXMLLoaded[8].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneAddChangeVisa()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneAddChangeVisa.fxml"));
                    paneAddChangeVisa = loader.load();
                    ctrPaneAddChangeVisa = loader.getController();
                    listPaneControllers.add(ctrPaneAddChangeVisa);

                    flagFXMLLoaded[9].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPane90DayNotice()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("pane90DayNotice.fxml"));
                    pane90DayNotice = loader.load();
                    ctrPane90DayNotice = loader.getController();
                    listPaneControllers.add(ctrPane90DayNotice);

                    flagFXMLLoaded[10].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneEditSave()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneEditSave.fxml"));
                    paneEditSave = loader.load();
                    ctrPaneEditSave = loader.getController();
                    listPaneControllers.add(ctrPaneEditSave);

                    flagFXMLLoaded[11].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneMonasticSelection()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneMonasticSelection.fxml"));
                    paneMonasticSelection = loader.load();
                    ctrPaneMonasticSelection = loader.getController();
                    listPaneControllers.add(ctrPaneMonasticSelection);

                    flagFXMLLoaded[12].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneTM30NotifResidence()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneTM30NotifResidence.fxml"));
                    paneTM30NotifResidence = loader.load();
                    ctrPaneTM30NotifResidence = loader.getController();
                    listPaneControllers.add(ctrPaneTM30NotifResidence);

                    flagFXMLLoaded[13].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneChangelog()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneChangelog.fxml"));
                    paneChangelog = loader.load();
                    ctrPaneChangelog = loader.getController();
                    listPaneControllers.add(ctrPaneChangelog);

                    flagFXMLLoaded[14].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPaneConvert()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("paneConversionTools.fxml"));
                    paneConvert = loader.load();
                    ctrPaneConvert = loader.getController();
                    listPaneControllers.add(ctrPaneConvert);

                    flagFXMLLoaded[15].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();

    }

    private void initPanePhotoPagePrinting()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("panePhotoPagePrinting.fxml"));
                    panePhotoPagePrint = loader.load();
                    ctrPanePhotoPagePrint = loader.getController();
                    listPaneControllers.add(ctrPanePhotoPagePrint);

                    flagFXMLLoaded[16].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();
    }

    private void initPanePrintedDocStock()
    {
        Task t = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                FXMLLoader loader;
                try
                {
                    loader = new FXMLLoader(getClass().getResource("panePrintedDocStock.fxml"));
                    panePrintedDocStock = loader.load();
                    ctrPanePrintedDocStock = loader.getController();
                    listPaneControllers.add(ctrPanePrintedDocStock);

                    flagFXMLLoaded[17].setValue(Boolean.TRUE);
                } catch (Exception ex)
                {
                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
                }
                return null;
            }
        };
        new Thread(t).start();
    }

    public CtrMain getCtrMain()
    {
        return ctrMain;
    }

    @FXML
    void actionChangeDateFormat(ActionEvent ae)
    {
        if (ae.getSource().equals(rbDateFormatWestern))
        {
            ctrDatePicker.setISOChronology();
        } else
        {
            ctrDatePicker.setThaiChronology();
        }
    }

    private void checkUnsavedChanges()
    {
        boolean actionSaveBefore;
        if ((currentPaneController instanceof IEditableGUIForm) && (ctrFieldChangeListener.hasUnsavedChanges()))
        {
            actionSaveBefore = CtrAlertDialog.confirmationUnsavedChanges();
            if (actionSaveBefore)
            {
                ((IEditableGUIForm) currentPaneController).actionSave();
            }
            ctrFieldChangeListener.resetUnsavedChanges();
        }
    }

    @FXML
    void actionDueTasksButton(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(null);
        topPane.setLeft(null);
        centerPane.setCenter(paneDueTasks);
        currentPaneController = ctrPaneDueTasks;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneDueTasks.fillData();

    }

    @FXML
    void actionMonasticProfileButton(ActionEvent ae)
    {
        checkUnsavedChanges();

        ctrPaneEditSave.setVisible_ButtonAddNew(true);
        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneMonasticProfile);
        currentPaneController = ctrPaneMonasticProfile;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonMonastery(ActionEvent ae)
    {
        checkUnsavedChanges();

        ctrPaneEditSave.setVisible_ButtonAddNew(true);
        topPane.setCenter(null);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneMonastery);
        currentPaneController = ctrPaneMonastery;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        ctrPaneMonastery.fillMonasteryData(null);
    }

    @FXML
    void actionButtonUpajjhaya(ActionEvent ae)
    {
        checkUnsavedChanges();

        ctrPaneEditSave.setVisible_ButtonAddNew(true);
        topPane.setCenter(null);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneUpajjhaya);
        currentPaneController = ctrPaneUpajjhaya;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        ctrPaneUpajjhaya.fillUpajjhayaData(null);
    }

    @FXML
    void actionPassportButton(ActionEvent ae)
    {
        checkUnsavedChanges();

        ctrPaneEditSave.setVisible_ButtonAddNew(false);
        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(panePassport);
        currentPaneController = ctrPanePassport;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        fillMonasticProfileData();
    }

    @FXML
    void actionBysuddhiButton(ActionEvent ae)
    {
        checkUnsavedChanges();

        ctrPaneEditSave.setVisible_ButtonAddNew(false);
        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneBysuddhi);
        currentPaneController = ctrPaneBysuddhi;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        fillMonasticProfileData();
    }

    @FXML
    void actionButton90DayNotice(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(pane90DayNotice);
        currentPaneController = ctrPane90DayNotice;
//        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();

    }

    @FXML
    void actionButtonVisaExt(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneVisaExt);
        currentPaneController = ctrPaneVisaExt;
//        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonReEntry(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneReEntry);
        currentPaneController = ctrPaneReEntry;
//        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonAddRenewPassport(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneAddRenewPassport);
        currentPaneController = ctrPaneAddRenewPassport;
//        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonAddChangeVisa(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneAddChangeVisa);
        currentPaneController = ctrPaneAddChangeVisa;
//        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonTM30NotifResidence(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(null);
        topPane.setLeft(null);
        centerPane.setCenter(paneTM30NotifResidence);
        currentPaneController = ctrPaneTM30NotifResidence;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneTM30NotifResidence.fillData();
    }

    @FXML
    void actionButtonChangelog(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(null);
        topPane.setLeft(null);
        centerPane.setCenter(paneChangelog);
        currentPaneController = ctrPaneChangelog;
//        Init.MAIN_STAGE.sizeToScene();
        ctrPaneChangelog.fillData();
    }

    @FXML
    void actionButtonConversionTools(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(null);
        topPane.setLeft(null);
        centerPane.setCenter(paneConvert);
        currentPaneController = ctrPaneConvert;
    }

    @FXML
    void actionButtonPhotoPagePrint(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setCenter(null);
        topPane.setLeft(null);
        centerPane.setCenter(panePhotoPagePrint);
        currentPaneController = ctrPanePhotoPagePrint;
        ctrPanePhotoPagePrint.fillData();
    }

    @FXML
    void actionButtonPrintedDocStock(ActionEvent ae)
    {
        checkUnsavedChanges();

        ctrPaneEditSave.setVisible_ButtonAddNew(false);
        topPane.setCenter(paneMonasticSelection);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(panePrintedDocStock);
        currentPaneController = ctrPanePrintedDocStock;
        fillMonasticProfileData();
    }

    public void initChildControllers()
    {
        for (AbstractChildPaneController acc : listPaneControllers)
        {
            acc.setParent(this);
            acc.init();
        }
    }

    public CtrPaneMonasticSelection getCtrPaneSelection()
    {
        return ctrPaneMonasticSelection;
    }

    public Dialog getDialogSelectExtraScan()
    {
        return dialogSelectExtraScan;
    }

    public CtrPaneMonasticProfile getPaneMonasticProfileController()
    {
        return ctrPaneMonasticProfile;
    }

    public void fillMonasticProfileData()
    {
        MonasticProfile p;

        p = ctrPaneMonasticSelection.getSelectedProfile();

        if ((currentPaneController != null) && currentPaneController instanceof IFormMonasticProfile)
        {
            ((IFormMonasticProfile) currentPaneController).fillData(p);
        }
    }

    public IEditableGUIForm getCurrentEditableGUIFormController()
    {
        if (currentPaneController instanceof IEditableGUIForm)
        {
            return (IEditableGUIForm) currentPaneController;
        } else
        {
            return null;
        }
    }

    public CtrPaneEditSave getPaneEditSaveController()
    {
        return ctrPaneEditSave;
    }

    public CtrDatePicker getCtrDatePicker()
    {
        return ctrDatePicker;
    }

    public CtrGUISharedUtil getCtrGUISharedUtil()
    {
        return ctrGUISharedUtil;
    }

}
