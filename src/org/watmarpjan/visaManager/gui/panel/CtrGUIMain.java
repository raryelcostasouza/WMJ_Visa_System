/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import java.io.IOException;
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

    @FXML
    private BorderPane centerPane;
    @FXML
    private BorderPane topPane;

    private AbstractChildPaneController currentPaneController;
    private ArrayList<AbstractChildPaneController> listPaneControllers;

    private CtrFieldChangeListener ctrFieldChangeListener;
    private CtrGUISharedUtil ctrGUISharedUtil;

    private CtrGUIMain ctrGUIMainSelfReference = this;
    private BooleanProperty[] flagFXMLLoaded = new BooleanProperty[3];
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

        //initPaneMonasticProfile();
        //initPanePassport();
        //initPaneBysuddhi();
        //initPaneMonastery();
        //initPaneUpajjhaya();
        //initPane90DayNotice();
        //initPaneAddChangeVisa();
        //initPaneAddRenewPassport();
        //initPaneReEntry();
        //initPaneVisaExt();
        //initPaneTM30NotifResidence();
        //initPaneChangelog();
        //initPaneConvert();
        //initPanePhotoPagePrinting();
        //initPanePrintedDocStock();
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
                System.out.println("LoadTime: " + Duration.between(Init.INSTANT_INIT_START, Instant.now()));

                primaryStage = Init.MAIN_STAGE;
                primaryStage.setScene(mainScene);
                primaryStage.setTitle("WMJ Visa System");
                primaryStage.setWidth(1600);
                primaryStage.setHeight(990);

                primaryStage.show();
                //System.out.println("LoadTime: " + Duration.between(Init.INSTANT_INIT_START, Instant.now()));

                //closes the preloader splashscreen
                Init.APP.notifyPreloader(new Preloader.StateChangeNotification(
                        Preloader.StateChangeNotification.Type.BEFORE_START));
            }
        });

    }

    private void initChildPane(AbstractChildPaneController objACC)
    {
        objACC.setParent(this);
        objACC.init();
    }

    private void initPaneMonasticProfile()
    {
//        Task t = new Task<Void>()
//        {
//            @Override
//            protected Void call() throws Exception
//            {
//                FXMLLoader loader;
//                try
//                {
//                    loader = new FXMLLoader(getClass().getResource("paneMonasticProfile.fxml"));
//                    paneMonasticProfile = loader.load();
//                    ctrPaneMonasticProfile = loader.getController();
//                    listPaneControllers.add(ctrPaneMonasticProfile);
//
//                    flagFXMLLoaded[0].setValue(Boolean.TRUE);
//
//                } catch (Exception ex)
//                {
//                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
//                }
//                return null;
//            }
//        };
//        new Thread(t).start();

        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneMonasticProfile.fxml"));
            paneMonasticProfile = loader.load();
            ctrPaneMonasticProfile = loader.getController();
            initChildPane(ctrPaneMonasticProfile);

        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPanePassport()
    {
//        Task t = new Task<Void>()
//        {
//            @Override
//            protected Void call() throws Exception
//            {
//                FXMLLoader loader;
//                try
//                {
//                    loader = new FXMLLoader(getClass().getResource("panePassport.fxml"));
//                    panePassport = loader.load();
//                    ctrPanePassport = loader.getController();
//                    listPaneControllers.add(ctrPanePassport);
//
//                    flagFXMLLoaded[1].setValue(Boolean.TRUE);
//                } catch (Exception ex)
//                {
//                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
//                }
//                return null;
//            }
//        };
//        new Thread(t).start();

        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("panePassport.fxml"));
            panePassport = loader.load();
            ctrPanePassport = loader.getController();

            initChildPane(ctrPanePassport);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPaneBysuddhi()
    {
//        Task t = new Task<Void>()
//        {
//            @Override
//            protected Void call() throws Exception
//            {
//                FXMLLoader loader;
//                try
//                {
//                    loader = new FXMLLoader(getClass().getResource("paneBysuddhi.fxml"));
//                    paneBysuddhi = loader.load();
//                    ctrPaneBysuddhi = loader.getController();
//                    listPaneControllers.add(ctrPaneBysuddhi);
//
//                    flagFXMLLoaded[2].setValue(Boolean.TRUE);
//                } catch (Exception ex)
//                {
//                    CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
//                }
//                return null;
//            }
//        };
//        new Thread(t).start();

        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneBysuddhi.fxml"));
            paneBysuddhi = loader.load();
            ctrPaneBysuddhi = loader.getController();
            System.out.println("hereLoaded");

            initChildPane(ctrPaneBysuddhi);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }

    }

    private void initPaneMonastery()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneMonastery.fxml"));
            paneMonastery = loader.load();
            ctrPaneMonastery = loader.getController();
            initChildPane(ctrPaneMonastery);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }

    }

    private void initPaneUpajjhaya()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneUpajjhaya.fxml"));
            paneUpajjhaya = loader.load();
            ctrPaneUpajjhaya = loader.getController();

            initChildPane(ctrPaneUpajjhaya);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }

    }

    private void initPaneVisaExt()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneVisaExt.fxml"));
            paneVisaExt = loader.load();
            ctrPaneVisaExt = loader.getController();
            initChildPane(ctrPaneVisaExt);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPaneReEntry()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneReEntry.fxml"));
            paneReEntry = loader.load();
            ctrPaneReEntry = loader.getController();
            initChildPane(ctrPaneReEntry);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }

    }

    private void initPaneAddRenewPassport()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneAddRenewPassport.fxml"));
            paneAddRenewPassport = loader.load();
            ctrPaneAddRenewPassport = loader.getController();
            initChildPane(ctrPaneAddRenewPassport);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPaneAddChangeVisa()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneAddChangeVisa.fxml"));
            paneAddChangeVisa = loader.load();
            ctrPaneAddChangeVisa = loader.getController();
            initChildPane(ctrPaneAddChangeVisa);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }

    }

    private void initPane90DayNotice()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("pane90DayNotice.fxml"));
            pane90DayNotice = loader.load();
            ctrPane90DayNotice = loader.getController();
            initChildPane(ctrPane90DayNotice);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
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

                    flagFXMLLoaded[0].setValue(Boolean.TRUE);
                } catch (IOException ex)
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

                    flagFXMLLoaded[1].setValue(Boolean.TRUE);
                } catch (IOException ex)
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

                    flagFXMLLoaded[2].setValue(Boolean.TRUE);
                } catch (IOException ex)
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
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneTM30NotifResidence.fxml"));
            paneTM30NotifResidence = loader.load();
            ctrPaneTM30NotifResidence = loader.getController();
            initChildPane(ctrPaneTM30NotifResidence);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }

    }

    private void initPaneChangelog()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneChangelog.fxml"));
            paneChangelog = loader.load();
            ctrPaneChangelog = loader.getController();
            initChildPane(ctrPaneChangelog);

        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }

    }

    private void initPaneConvert()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneConversionTools.fxml"));
            paneConvert = loader.load();
            ctrPaneConvert = loader.getController();
            initChildPane(ctrPaneConvert);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPanePhotoPagePrinting()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("panePhotoPagePrinting.fxml"));
            panePhotoPagePrint = loader.load();
            ctrPanePhotoPagePrint = loader.getController();
            initChildPane(ctrPanePhotoPagePrint);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPanePrintedDocStock()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("panePrintedDocStock.fxml"));
            panePrintedDocStock = loader.load();
            ctrPanePrintedDocStock = loader.getController();
            initChildPane(ctrPanePrintedDocStock);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
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
        }
        else
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

        if (ctrPaneMonasticProfile == null)
        {
            initPaneMonasticProfile();
        }

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

        if (ctrPaneMonastery == null)
        {
            initPaneMonastery();
        }
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

        if (ctrPaneUpajjhaya == null)
        {
            initPaneUpajjhaya();
        }
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

        if (ctrPanePassport == null)
        {
            initPanePassport();
        }

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

        if (ctrPaneBysuddhi == null)
        {
            initPaneBysuddhi();
        }
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

        if (ctrPane90DayNotice == null)
        {
            initPane90DayNotice();
        }
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

        if (ctrPaneVisaExt == null)
        {
            initPaneVisaExt();
        }
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
        if (ctrPaneReEntry == null)
        {
            initPaneReEntry();
        }
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

        if (ctrPaneAddRenewPassport == null)
        {
            initPaneAddRenewPassport();
        }
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

        if (ctrPaneAddChangeVisa == null)
        {
            initPaneAddChangeVisa();
        }
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

        if (ctrPaneTM30NotifResidence == null)
        {
            initPaneTM30NotifResidence();
        }
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

        if (ctrPaneChangelog == null)
        {
            initPaneChangelog();
        }
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

        if (ctrPaneConvert == null)
        {
            initPaneConvert();
        }
        topPane.setCenter(null);
        topPane.setLeft(null);
        centerPane.setCenter(paneConvert);
        currentPaneController = ctrPaneConvert;
    }

    @FXML
    void actionButtonPhotoPagePrint(ActionEvent ae)
    {
        checkUnsavedChanges();

        if (ctrPanePhotoPagePrint == null)
        {
            initPanePhotoPagePrinting();
        }
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

        if (ctrPanePrintedDocStock == null)
        {
            initPanePrintedDocStock();
        }
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
        }
        else
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
