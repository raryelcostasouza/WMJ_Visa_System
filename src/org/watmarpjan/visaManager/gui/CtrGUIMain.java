/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.util.ArrayList;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.model.hibernate.Profile;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private SplitPane paneBysuddhi;

    @FXML
    private CtrPaneDueTasks ctrPaneDueTasks;
    @FXML
    private TabPane paneDueTasks;

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
    private TabPane paneReEntry;

    @FXML
    private CtrPaneAddRenewPassport ctrPaneAddRenewPassport;
    @FXML
    private VBox paneAddRenewPassport;

    @FXML
    private CtrPaneAddChangeVisa ctrPaneAddChangeVisa;
    @FXML
    private TabPane paneAddChangeVisa;

    private CtrDialogSelectExtraScan ctrDialogSelectExtraScan;

    private Dialog<AbstractResultDialogSelectScan> dialogSelectExtraScan;

    @FXML
    private BorderPane centerPane;
    @FXML
    private BorderPane topPane;

    private AbstractChildPaneController currentPaneController;
    private ArrayList<AbstractChildPaneController> listPaneControllers;

    private CtrFieldChangeListener ctrFieldChangeListener;

    public CtrFieldChangeListener getCtrFieldChangeListener()
    {
        return ctrFieldChangeListener;
    }

    @FXML
    void initialize()
    {
        //starts cleaning tmp files
        //in case the app was forced to close
        CtrFileOperation.clearTMPFiles();
        this.ctrMain = new CtrMain(this);
        this.ctrDatePicker = new CtrDatePicker();

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

        initPaneMonasticSelection();
        initPaneEditSave();
        initDialogSelectExtraScan();

        this.ctrFieldChangeListener = new CtrFieldChangeListener(ctrPaneEditSave);
        initChildControllers();
        actionDueTasksButton(null);
    }

    private void initDialogSelectExtraScan()
    {
        FXMLLoader loader;
        DialogPane dp;
        try
        {
            loader = new FXMLLoader(getClass().getResource("dialogPaneSelectExtraScan.fxml"));
            dp = (DialogPane) loader.load();
            ctrDialogSelectExtraScan = ((CtrDialogSelectExtraScan) loader.getController());
            ctrDialogSelectExtraScan.setParent(this);

            dialogSelectExtraScan = new Dialog();
            dialogSelectExtraScan.setTitle("Select scan file");
            dialogSelectExtraScan.setDialogPane(dp);

            ctrDialogSelectExtraScan.init(dialogSelectExtraScan);

        } catch (Exception e)
        {
            CtrAlertDialog.exceptionDialog(e, "Error to load Dialog Panel.");
        }

    }

    private void initPaneMonasticProfile()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneMonasticProfile.fxml"));
            paneMonasticProfile = loader.load();
            ctrPaneMonasticProfile = loader.getController();
            listPaneControllers.add(ctrPaneMonasticProfile);

        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPanePassport()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("panePassport.fxml"));
            panePassport = loader.load();
            ctrPanePassport = loader.getController();
            listPaneControllers.add(ctrPanePassport);

        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPaneBysuddhi()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneBysuddhi.fxml"));
            paneBysuddhi = loader.load();
            ctrPaneBysuddhi = loader.getController();
            listPaneControllers.add(ctrPaneBysuddhi);

        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPaneDueTasks()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneDueTasks.fxml"));
            paneDueTasks = loader.load();
            ctrPaneDueTasks = loader.getController();
            listPaneControllers.add(ctrPaneDueTasks);

        } catch (Exception ex)
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
            listPaneControllers.add(ctrPaneMonastery);

        } catch (Exception ex)
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
            listPaneControllers.add(ctrPaneUpajjhaya);

        } catch (Exception ex)
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
            listPaneControllers.add(ctrPaneVisaExt);

        } catch (Exception ex)
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
            listPaneControllers.add(ctrPaneReEntry);

        } catch (Exception ex)
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
            listPaneControllers.add(ctrPaneAddRenewPassport);

        } catch (Exception ex)
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
            listPaneControllers.add(ctrPaneAddChangeVisa);

        } catch (Exception ex)
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
            listPaneControllers.add(ctrPane90DayNotice);

        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPaneEditSave()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneEditSave.fxml"));
            paneEditSave = loader.load();
            ctrPaneEditSave = loader.getController();
            listPaneControllers.add(ctrPaneEditSave);

        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    private void initPaneMonasticSelection()
    {
        FXMLLoader loader;
        try
        {
            loader = new FXMLLoader(getClass().getResource("paneMonasticSelection.fxml"));
            paneMonasticSelection = loader.load();
            ctrPaneMonasticSelection = loader.getController();
            listPaneControllers.add(ctrPaneMonasticSelection);

        } catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error to load GUI Panel.");
        }
    }

    public CtrMain getCtrMain()
    {
        return ctrMain;
    }

    public CtrDialogSelectExtraScan getCtrDialogSelectExtraScan()
    {
        return ctrDialogSelectExtraScan;
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

        topPane.setRight(null);
        topPane.setLeft(null);
        centerPane.setCenter(paneDueTasks);
        currentPaneController = ctrPaneDueTasks;
        Init.MAIN_STAGE.sizeToScene();
        ctrPaneDueTasks.fillData();

    }

    @FXML
    void actionMonasticProfileButton(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneMonasticProfile);
        currentPaneController = ctrPaneMonasticProfile;
        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonMonastery(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(null);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneMonastery);
        currentPaneController = ctrPaneMonastery;
        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        ctrPaneMonastery.fillMonasteryData(null);
    }

    @FXML
    void actionButtonUpajjhaya(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(null);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneUpajjhaya);
        currentPaneController = ctrPaneUpajjhaya;
        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        ctrPaneUpajjhaya.fillUpajjhayaData(null);
    }

    @FXML
    void actionPassportButton(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(panePassport);
        currentPaneController = ctrPanePassport;
        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        fillMonasticProfileData();
    }

    @FXML
    void actionBysuddhiButton(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(paneEditSave);
        centerPane.setCenter(paneBysuddhi);
        currentPaneController = ctrPaneBysuddhi;
        Init.MAIN_STAGE.sizeToScene();
        ctrPaneEditSave.actionLock();
        fillMonasticProfileData();
    }

    @FXML
    void actionButton90DayNotice(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(pane90DayNotice);
        currentPaneController = ctrPane90DayNotice;
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();

    }

    @FXML
    void actionButtonVisaExt(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneVisaExt);
        currentPaneController = ctrPaneVisaExt;
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonReEntry(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneReEntry);
        currentPaneController = ctrPaneReEntry;
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonAddRenewPassport(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneAddRenewPassport);
        currentPaneController = ctrPaneAddRenewPassport;
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    @FXML
    void actionButtonAddChangeVisa(ActionEvent ae)
    {
        checkUnsavedChanges();

        topPane.setRight(paneMonasticSelection);
        topPane.setLeft(null);
        centerPane.setCenter(paneAddChangeVisa);
        currentPaneController = ctrPaneAddChangeVisa;
        Init.MAIN_STAGE.sizeToScene();
        fillMonasticProfileData();
    }

    public Optional<AbstractResultDialogSelectScan> actionShowDialogSelectScan()
    {
        Profile p;

        p = getCtrPaneSelection().getSelectedProfile();
        if (p != null)
        {
            ctrDialogSelectExtraScan.fillData(p);
        }

        return dialogSelectExtraScan.showAndWait();
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
        Profile p;

        p = ctrPaneMonasticSelection.getSelectedProfile();

        if ((currentPaneController != null) && currentPaneController instanceof IFormMonasticProfile)
        {
            if (p != null)
            {
                ((IFormMonasticProfile) currentPaneController).fillData(p);
            }
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

}
