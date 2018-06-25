/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneControllerExportPDF;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.intface.IEditableGUIForm;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.FieldsPaneScanContent;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.model.EntryVisaExt;
import org.watmarpjan.visaManager.model.hibernate.PassportScan;
import org.watmarpjan.visaManager.util.Util;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.AppConstants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import org.watmarpjan.visaManager.AppPaths;
import static java.lang.Integer.parseInt;
import org.watmarpjan.visaManager.control.CtrPDF;
import org.watmarpjan.visaManager.model.eps.ExtraPassportScanLoaded;
import org.watmarpjan.visaManager.model.eps.ExtraPassportScanNew;

/**
 *
 * @author WMJ_user
 */
public class CtrPanePassport extends AChildPaneControllerExportPDF implements IFormMonasticProfile, IEditableGUIForm
{

    @FXML
    private Label labelLock;
    @FXML
    private Label labelLock2;
    @FXML
    private Label labelLock3;
    @FXML
    private Label labelLock4;

    @FXML
    private ImageView ivPassportScan;

    @FXML
    private ImageView ivDepartureCardScan;
    @FXML
    private ImageView ivScan1;
    @FXML
    private ImageView ivScan2;
    @FXML
    private ImageView ivScan3;

    @FXML
    private Button bArchivePassport;

    @FXML
    private Button bArchiveDepartureCard;

    @FXML
    private Button bScanPassport;

    @FXML
    private Button bScanDepartureCard;

    @FXML
    private Button bSelectScan1;
    @FXML
    private Button bSelectScan2;
    @FXML
    private Button bSelectScan3;

    @FXML
    private TextField tfpassportNumber;
    @FXML
    private TextField tfpassportCountry;
    @FXML
    private ComboBox<String> cbPassportIssuedAt;
    @FXML
    private DatePicker dpPassportIssueDate;
    @FXML
    private DatePicker dpPassportExpiryDate;
    @FXML
    private DatePicker dpFirstEntryDate;
    @FXML
    private TextField tfVisaNumber;
    @FXML
    private ComboBox<String> cbVisaType;
    @FXML
    private DatePicker dpVisaExpiryDate;
    @FXML
    private TextField tfArrivalTMNumber;
    @FXML
    private DatePicker dpLastEntry;
    @FXML
    private TextField tfPortOfEntry;
    @FXML
    private TextField tfTravelFrom;
    @FXML
    private ComboBox<String> cbTravelBy;

    @FXML
    private DatePicker dpNext90dayNotice;

    @FXML
    private TextField tfNVisaExt;

    @FXML
    private TableView<EntryVisaExt> tvExtensions;

    @FXML
    private ToggleGroup tgArriveStamp;
    @FXML
    private ToggleGroup tgVisa;
    @FXML
    private ToggleGroup tgLastVisaExt;

    @FXML
    private TextField tfScan1LeftPageNumber;
    @FXML
    private TextField tfScan1RightPageNumber;
    @FXML
    private RadioButton rbScan1ArriveStamp;
    @FXML
    private RadioButton rbScan1Visa;
    @FXML
    private RadioButton rbScan1LastVisaExt;

    @FXML
    private TextField tfScan2LeftPageNumber;
    @FXML
    private TextField tfScan2RightPageNumber;
    @FXML
    private RadioButton rbScan2ArriveStamp;
    @FXML
    private RadioButton rbScan2Visa;
    @FXML
    private RadioButton rbScan2LastVisaExt;

    @FXML
    private TextField tfScan3LeftPageNumber;
    @FXML
    private TextField tfScan3RightPageNumber;
    @FXML
    private RadioButton rbScan3ArriveStamp;
    @FXML
    private RadioButton rbScan3Visa;
    @FXML
    private RadioButton rbScan3LastVisaExt;

    @FXML
    private Button bArchive1;
    @FXML
    private Button bArchive2;
    @FXML
    private Button bArchive3;

    private File fScan1Selected;
    private File fScan2Selected;
    private File fScan3Selected;
    
    private ExtraPassportScanLoaded objEPS1;
    private ExtraPassportScanLoaded objEPS2;
    private ExtraPassportScanLoaded objEPS3;

    private FieldsPaneScanContent fieldsScan1;
    private FieldsPaneScanContent fieldsScan2;
    private FieldsPaneScanContent fieldsScan3;

    private final String ERROR_NO_PASSPORT_REGISTERED = "Please register a passport to this profile before adding scans.";

    @Override
    public void init()
    {
        super.init();
        TableColumn tc1;

        labelLock.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));
        labelLock2.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));
        labelLock3.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));
        labelLock4.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("unlock.png").toUri().toString()));

        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpPassportExpiryDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpPassportIssueDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpFirstEntryDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpLastEntry);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpVisaExpiryDate);
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpNext90dayNotice);

        cbTravelBy.getItems().addAll(AppConstants.LIST_TRAVEL_BY);
        cbVisaType.getItems().addAll(AppConstants.LIST_VISA_TYPES);

        tc1 = tvExtensions.getColumns().get(0);
        tc1.setCellValueFactory(new PropertyValueFactory<>("extNumber"));

        tvExtensions.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        fieldsScan1 = new FieldsPaneScanContent(bSelectScan1, bArchive1, tfScan1LeftPageNumber, tfScan1RightPageNumber, rbScan1ArriveStamp, rbScan1Visa, rbScan1LastVisaExt);
        fieldsScan2 = new FieldsPaneScanContent(bSelectScan2, bArchive2, tfScan2LeftPageNumber, tfScan2RightPageNumber, rbScan2ArriveStamp, rbScan2Visa, rbScan2LastVisaExt);
        fieldsScan3 = new FieldsPaneScanContent(bSelectScan3, bArchive3, tfScan3LeftPageNumber, tfScan3RightPageNumber, rbScan3ArriveStamp, rbScan3Visa, rbScan3LastVisaExt);

        initNonDigitFilter(tfScan1LeftPageNumber);
        initNonDigitFilter(tfScan2LeftPageNumber);
        initNonDigitFilter(tfScan3LeftPageNumber);

        initChangeListener();
    }

    //listener to remove any non-digit char from the text field
    private void initNonDigitFilter(TextField objTF)
    {
        objTF.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue != null)
                {
                    objTF.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });
    }

    private void initChangeListener()
    {
        ArrayList listFields;
        listFields = new ArrayList();

        listFields.add(cbPassportIssuedAt);
        listFields.add(dpPassportIssueDate);
        listFields.add(dpPassportExpiryDate);
        listFields.add(dpFirstEntryDate);

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        tfScan1LeftPageNumber.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                int parsedPageNumberValue;
                if (newValue != null)
                {
                    try
                    {
                        parsedPageNumberValue = parseInt(tfScan1LeftPageNumber.getText());
                        tfScan1RightPageNumber.setText(parsedPageNumberValue + 1 + "");

                    } catch (NumberFormatException nfe)
                    {
                        tfScan1RightPageNumber.setText("");
                    }
                }
            }
        });
        tfScan2LeftPageNumber.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                int parsedPageNumberValue;
                if (newValue != null)
                {
                    try
                    {
                        parsedPageNumberValue = parseInt(tfScan2LeftPageNumber.getText());
                        tfScan2RightPageNumber.setText(parsedPageNumberValue + 1 + "");

                    } catch (NumberFormatException nfe)
                    {
                        tfScan2RightPageNumber.setText("");
                    }
                }
            }
        });
        tfScan3LeftPageNumber.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                int parsedPageNumberValue;
                if (newValue != null)
                {
                    try
                    {
                        parsedPageNumberValue = parseInt(tfScan3LeftPageNumber.getText());
                        tfScan3RightPageNumber.setText(parsedPageNumberValue + 1 + "");

                    } catch (NumberFormatException nfe)
                    {
                        tfScan3RightPageNumber.setText("");
                    }
                }
            }
        }
        );

    }

    @Override
    public void fillData(MonasticProfile p)
    {
        ArrayList<EntryVisaExt> alVisaExtensions;
        LocalDate ldPassportExp, ldPassptIssue, ldVisaExp, ldNext90day, ldLastEntry, ldFirstEntry;

        GUIUtil.loadContentComboboxGeneric(cbPassportIssuedAt, ctrGUIMain.getCtrMain().getCtrProfile().loadListPassportIssuedAt());
        loadIMGPreviews(p);
        if (p != null)
        {
            tfpassportNumber.setText(p.getPassportNumber());
            tfpassportCountry.setText(p.getPassportCountry());
            cbPassportIssuedAt.setValue(p.getPassportIssuedAt());

            fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());

            if (p.getPassportExpiryDate() != null)
            {
                ldPassportExp = Util.convertDateToLocalDate(p.getPassportExpiryDate());
                dpPassportExpiryDate.setValue(ldPassportExp);
            } else
            {
                dpPassportExpiryDate.setValue(null);
            }

            if (p.getPassportIssueDate() != null)
            {
                ldPassptIssue = Util.convertDateToLocalDate(p.getPassportIssueDate());
                dpPassportIssueDate.setValue(ldPassptIssue);
            } else
            {
                dpPassportIssueDate.setValue(null);
            }

            if (p.getFirstEntryDate() != null)
            {
                ldFirstEntry = Util.convertDateToLocalDate(p.getFirstEntryDate());
                dpFirstEntryDate.setValue(ldFirstEntry);
            } else
            {
                dpFirstEntryDate.setValue(null);
            }

            tfVisaNumber.setText(p.getVisaNumber());
            cbVisaType.setValue(p.getVisaType());

            if (p.getVisaExpiryDate() != null)
            {
                ldVisaExp = Util.convertDateToLocalDate(p.getVisaExpiryDate());
                dpVisaExpiryDate.setValue(ldVisaExp);
            } else
            {
                dpVisaExpiryDate.setValue(null);
            }

            if (p.getNext90DayNotice() != null)
            {
                ldNext90day = Util.convertDateToLocalDate(p.getNext90DayNotice());
                dpNext90dayNotice.setValue(ldNext90day);
            } else
            {
                dpNext90dayNotice.setValue(null);
            }

            tfArrivalTMNumber.setText(p.getArrivalCardNumber());
            tfPortOfEntry.setText(p.getArrivalPortOfEntry());
            tfTravelFrom.setText(p.getArrivalTravelFrom());
            cbTravelBy.setValue(p.getArrivalTravelBy());

            ldLastEntry = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
            dpLastEntry.setValue(ldLastEntry);

            alVisaExtensions = ctrGUIMain.getCtrMain().getCtrVisa().loadListExtensions(p.getIdProfile());
            tvExtensions.getItems().clear();
            tvExtensions.getItems().addAll(alVisaExtensions);

            tfNVisaExt.setText("" + alVisaExtensions.size());
        }

    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getCtrPaneSelection().isSelectionEmpty();
    }
    
    private void registerExtraPassportScan(ArrayList<ExtraPassportScanLoaded> listExtraPScan)
    {
       objEPS1 = objEPS2 = objEPS3 =  null;
       
       if (listExtraPScan.size()>=1) 
       {
           objEPS1 = listExtraPScan.get(0);
       }
       if (listExtraPScan.size()>=2)
       {
           objEPS2 = listExtraPScan.get(1);
       }
       if (listExtraPScan.size()>=3)
       {
           objEPS3 = listExtraPScan.get(2);
       }
    }

    private void fillDataContentScans(MonasticProfile p, boolean lockStatus)
    {
        ArrayList<ExtraPassportScanLoaded> listFExtraPScan;
        fieldsScan1.reset(lockStatus);
        fieldsScan2.reset(lockStatus);
        fieldsScan3.reset(lockStatus);

        fScan1Selected = null;
        fScan2Selected = null;
        fScan3Selected = null;

        listFExtraPScan = AppFiles.getListExtraScans(p.getNickname(), p.getPassportNumber());
        registerExtraPassportScan(listFExtraPScan);
        //if the system is on edit mode
        if (!lockStatus)
        {
            if (AppFiles.getScanDepartureCard(p.getNickname()).exists())
            {
                bArchiveDepartureCard.setDisable(false);
                bScanDepartureCard.setDisable(true);
            } else
            {
                bArchiveDepartureCard.setDisable(true);
                bScanDepartureCard.setDisable(false);
            }

            if (AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()).exists())
            {
                bArchivePassport.setDisable(false);
                bScanPassport.setDisable(true);
            } else
            {
                bArchivePassport.setDisable(true);
                bScanPassport.setDisable(false);
            }
        } //system on view mode
        else
        {
            bArchiveDepartureCard.setDisable(true);
            bScanDepartureCard.setDisable(true);

            bArchivePassport.setDisable(true);
            bScanPassport.setDisable(true);
        }

        if (objEPS1 != null)
        {
            fieldsScan1.setContentTrue(lockStatus);

            tfScan1LeftPageNumber.setText(objEPS1.getLeftPageNumber() + "");
            if (objEPS1.containsScanArriveStamp())
            {
                /*
                     * if this scan contains the Arrive Stamp blocks the
                     * selection of ArriveStamp option for other scans
                 */
                rbScan1ArriveStamp.setSelected(true);

                //rbScan2ArriveStamp.setDisable(true);
                //rbScan3ArriveStamp.setDisable(true);
            }
            if (objEPS1.containsScanVisa())
            {
                /*
                     * if this scan contains the Visa page blocks the selection
                     * of Visa option for other scans
                 */
                rbScan1Visa.setSelected(true);

                //rbScan2Visa.setDisable(true);
                //rbScan3Visa.setDisable(true);
            }
            if (objEPS1.containsScanLastVisaExt())
            {
                /*
                     * if this scan contains the Last Visa Ext blocks the
                     * selection of Last Visa Ext option for other scans
                 */
                rbScan1LastVisaExt.setSelected(true);

                //rbScan2LastVisaExt.setDisable(true);
                //rbScan3LastVisaExt.setDisable(true);
            }
        }

        if (objEPS2 != null)
        {
            fieldsScan2.setContentTrue(lockStatus);

            tfScan2LeftPageNumber.setText(objEPS2.getLeftPageNumber() + "");
            if (objEPS2.containsScanArriveStamp())
            {
                rbScan2ArriveStamp.setSelected(true);

                //rbScan1ArriveStamp.setDisable(true);
                //rbScan3ArriveStamp.setDisable(true);
            }
            if (objEPS2.containsScanVisa())
            {
                rbScan2Visa.setSelected(true);

                //rbScan1Visa.setDisable(true);
                //rbScan3Visa.setDisable(true);
            }
            if (objEPS2.containsScanLastVisaExt())
            {
                rbScan2LastVisaExt.setSelected(true);

                //rbScan1LastVisaExt.setDisable(true);
                //rbScan3LastVisaExt.setDisable(true);
            }
        }

        if (objEPS3 != null)
        {
            fieldsScan3.setContentTrue(lockStatus);

            tfScan3LeftPageNumber.setText(objEPS3.getLeftPageNumber() + "");
            if (objEPS3.containsScanArriveStamp())
            {
                rbScan3ArriveStamp.setSelected(true);

                //rbScan1ArriveStamp.setDisable(true);
                //rbScan2ArriveStamp.setDisable(true);
            }
            if (objEPS3.containsScanVisa())
            {
                rbScan3Visa.setSelected(true);

                //rbScan1Visa.setDisable(true);
                //rbScan2Visa.setDisable(true);
            }
            if (objEPS3.containsScanLastVisaExt())
            {
                rbScan3LastVisaExt.setSelected(true);

                //rbScan1LastVisaExt.setDisable(true);
                //rbScan2LastVisaExt.setDisable(true);
            }
        }
    }

    private void loadIMGPreviews(MonasticProfile p)
    {
        File fPassportScan, fDepartureCard, fScan1 = null, fScan2 = null, fScan3 = null;
        ArrayList<ExtraPassportScanLoaded> listFExtraPScan;
        

        if (p != null)
        {
            fPassportScan = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
            fDepartureCard = AppFiles.getScanDepartureCard(p.getNickname());

            listFExtraPScan = AppFiles.getListExtraScans(p.getNickname(), p.getPassportNumber());
            if (!listFExtraPScan.isEmpty())
            {
                if (listFExtraPScan.size() >= 1)
                {
                    fScan1 = listFExtraPScan.get(0).getFileScan();
                }
                if (listFExtraPScan.size() >= 2)
                {
                    fScan2 = listFExtraPScan.get(1).getFileScan();
                }
                if (listFExtraPScan.size() >= 3)
                {
                    fScan3 = listFExtraPScan.get(2).getFileScan();
                }
            }
        } else
        {
            fPassportScan = fDepartureCard  = null;
        }

        GUIUtil.loadImageView(ivPassportScan, GUIUtil.IMG_TYPE_PASSPORT, fPassportScan);
        GUIUtil.loadImageView(ivDepartureCardScan, GUIUtil.IMG_TYPE_PASSPORT, fDepartureCard);
        GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_PASSPORT, fScan1);
        GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_PASSPORT, fScan2);
        GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_PASSPORT, fScan3);

    }

    @FXML
    void actionArchiveExtraScan(ActionEvent ae)
    {
        MonasticProfile p;
        File fScan2Archive;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (ae.getSource().equals(bArchive1))
        {
            fScan2Archive = objEPS1.getFileScan();
        } else if (ae.getSource().equals(bArchive2))
        {
            fScan2Archive = objEPS2.getFileScan();
        } else
        {
            fScan2Archive = objEPS3.getFileScan();
        }

        CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, fScan2Archive);

        fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
        loadIMGPreviews(p);
    }

    @FXML
    void actionArchivePassportFirstPage(ActionEvent ae)
    {
        int opStatus;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        opStatus = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()));
        if (opStatus == 0)
        {
            fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
            loadIMGPreviews(p);
            CtrAlertDialog.infoDialog("Archived successfully", "Passport scan archived successfully.");
        }

    }

    @FXML
    void actionArchiveDepartureCard(ActionEvent ae)
    {
        int opStatus;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        opStatus = CtrFileOperation.archiveScanFile(p.getNickname(), CtrFileOperation.SCAN_TYPE_PASSPORT, AppFiles.getScanDepartureCard(p.getNickname()));
        if (opStatus == 0)
        {
            fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
            loadIMGPreviews(p);
            CtrAlertDialog.infoDialog("Archived successfully", "Departure card scan archived successfully.");
        }

    }

    @FXML
    void actionIMGPassportScanClicked(MouseEvent me)
    {
        MonasticProfile p;
        File fImgScan;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        if (me.getSource().equals(ivPassportScan))
        {
            fImgScan = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
        } else if (me.getSource().equals(ivDepartureCardScan))
        {
            fImgScan = AppFiles.getScanDepartureCard(p.getNickname());
        } else if (me.getSource().equals(ivScan1))
        {
            //if there is a scan selected, but not yet added
            if (fScan1Selected != null)
            {
                fImgScan = fScan1Selected;
            } //for a registered scan
            else
            {
                if (objEPS1 != null)
                {
                    fImgScan = objEPS1.getFileScan();
                } else
                {
                    fImgScan = null;
                }
            }
        } else if (me.getSource().equals(ivScan2))
        {
            //if there is a scan selected, but not yet added
            if (fScan2Selected != null)
            {
                fImgScan = fScan2Selected;
            } //for a registered scan
            else
            {
                if (objEPS2 != null)
                {
                    fImgScan = objEPS2.getFileScan();
                } else
                {
                    fImgScan = null;
                }

            }
        } else
        {
            //if there is a scan selected, but not yet added
            if (fScan3Selected != null)
            {
                fImgScan = fScan3Selected;
            } //for a registered scan
            else
            {
                if (objEPS3 != null)
                {
                    fImgScan = objEPS3.getFileScan();
                } else
                {
                    fImgScan = null;
                }

            }
        }

        GUIUtil.openClickedIMG(fImgScan);
    }

    @FXML
    void actionChooseScanPassport(ActionEvent ae)
    {
        MonasticProfile p;
        File fScanDestination;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p.getPassportNumber() != null)
        {
            fScanDestination = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
            actionChooseScan(p, fScanDestination, "Passport First Page Scan");
        } else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }

    }

    @FXML
    void actionChooseScanDepartureCard(ActionEvent ae)
    {
        MonasticProfile p;
        File fScanDestination;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p.getPassportNumber() != null)
        {
            fScanDestination = AppFiles.getScanDepartureCard(p.getNickname());
            actionChooseScan(p, fScanDestination, "Departure Card Scan");
        } else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }

    }

    private void actionChooseScan(MonasticProfile profile, File fScanDestination, String title)
    {
        File fSelected;
        int opStatus;

        fSelected = CtrFileOperation.selectFile(title, CtrFileOperation.FILE_CHOOSER_TYPE_JPG);

        if (fSelected != null)
        {
            opStatus = CtrFileOperation.copyOperation(fSelected, fScanDestination);

            //if the operation was successful
            //saves the scan content information as well
            if (opStatus == 0)
            {
                //refresh the profile because the passportScan list was updated
                ctrGUIMain.getCtrMain().getCtrProfile().refresh(profile);
                loadIMGPreviews(profile);
                fillDataContentScans(profile, ctrGUIMain.getPaneEditSaveController().getLockStatus());
            }
        }
    }

    private int saveExtraScans()
    {
        boolean copyError, validationError;
        MonasticProfile p;
        ExtraPassportScanNew[] arrayPS = new ExtraPassportScanNew[3];
        File[] arrayFSelected = new File[3];
        File[] arrayFDestination = new File[3];
        Integer[] statusCopyOperation = new Integer[]
        {
            1, 1, 1
        };

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans

        validationError = false;
        copyError = false;
        if (fScan1Selected != null)
        {
            if (validateExtraScanContent(1))
            {
                arrayPS[0] = new ExtraPassportScanNew(parseInt(tfScan1LeftPageNumber.getText()), rbScan1ArriveStamp.isSelected(), rbScan1Visa.isSelected(), rbScan1LastVisaExt.isSelected());
                arrayFSelected[0] = fScan1Selected;
                arrayFDestination[0] = AppFiles.generateFileNameExtraScan(p.getNickname(), p.getPassportNumber(), arrayPS[0]);
            } else
            {
                validationError = true;
            }

        }

        if (fScan2Selected != null)
        {
            if (validateExtraScanContent(2))
            {
                arrayPS[1] = new ExtraPassportScanNew(parseInt(tfScan2LeftPageNumber.getText()), rbScan2ArriveStamp.isSelected(), rbScan2Visa.isSelected(), rbScan2LastVisaExt.isSelected());
                arrayFSelected[1] = fScan2Selected;
                arrayFDestination[1] = AppFiles.generateFileNameExtraScan(p.getNickname(), p.getPassportNumber(), arrayPS[1]);
            } else
            {
                validationError = true;
            }

        }

        if (fScan3Selected != null)
        {
            if (validateExtraScanContent(3))
            {
                arrayPS[2] = new ExtraPassportScanNew(parseInt(tfScan3LeftPageNumber.getText()), rbScan3ArriveStamp.isSelected(), rbScan3Visa.isSelected(), rbScan3LastVisaExt.isSelected());
                arrayFSelected[2] = fScan3Selected;
                arrayFDestination[2] = AppFiles.generateFileNameExtraScan(p.getNickname(), p.getPassportNumber(), arrayPS[2]);
            } else
            {
                validationError = true;
            }
        }

        //if no validation error occurred for the scan information
        //copy the files to the app storages
        if (!validationError)
        {
            for (int i = 0; i < arrayFSelected.length; i++)
            {
                if (arrayFSelected[i] != null)
                {
                    statusCopyOperation[i] = CtrFileOperation.copyOperation(arrayFSelected[i], arrayFDestination[i]);
                } else
                {
                    statusCopyOperation[i] = 0;
                }

                //if the operation was unsuccessful
                if (statusCopyOperation[i] == -1)
                {
                    copyError = true;
                    CtrAlertDialog.errorDialog("Unable to copy the file for Extra Scan " + i + ".");
                }
            }
        }
        //if the filecopy was successful
        //save the necessary information on DB
        if (!validationError && !copyError)
        {
            loadIMGPreviews(p);
            fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
            return 0;
        } else
        {
            return -1;
        }
    }

    @FXML
    void actionChooseExtraScan(ActionEvent ae)
    {
        File fSelected;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans
        if (p.getPassportNumber() != null)
        {
            fSelected = CtrFileOperation.selectFile("Extra Scan", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);
            if (fSelected != null)
            {
                ctrGUIMain.getCtrFieldChangeListener().setHasUnsavedChanges();
                if (ae.getSource().equals(bSelectScan1))
                {
                    fScan1Selected = fSelected;
                    GUIUtil.loadImageView(ivScan1, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                    //bAddScan1.setDisable(false);
                } else if (ae.getSource().equals(bSelectScan2))
                {
                    fScan2Selected = fSelected;
                    GUIUtil.loadImageView(ivScan2, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                    //bAddScan2.setDisable(false);
                } else
                {
                    fScan3Selected = fSelected;
                    GUIUtil.loadImageView(ivScan3, GUIUtil.IMG_TYPE_PASSPORT, fSelected);
                    //bAddScan3.setDisable(false);
                }
            }
        } else
        {
            CtrAlertDialog.errorDialog(ERROR_NO_PASSPORT_REGISTERED);
        }
    }

    private boolean validateExtraScanContent(int indexScan2Validate)
    {
        //if the page number is not empty and at least one of the options is selected
        //returns true
        boolean statusValid;
        switch (indexScan2Validate)
        {
            case 1:
                statusValid = validatePageNumber(tfScan1LeftPageNumber.getText())
                        && (rbScan1ArriveStamp.isSelected()
                        || rbScan1LastVisaExt.isSelected()
                        || rbScan1Visa.isSelected());
                break;
            case 2:
                statusValid = validatePageNumber(tfScan2LeftPageNumber.getText())
                        && (rbScan2ArriveStamp.isSelected()
                        || rbScan2LastVisaExt.isSelected()
                        || rbScan2Visa.isSelected());
                break;
            case 3:
                statusValid = validatePageNumber(tfScan3LeftPageNumber.getText())
                        && (rbScan3ArriveStamp.isSelected()
                        || rbScan3LastVisaExt.isSelected()
                        || rbScan3Visa.isSelected());
                break;
            default:
                statusValid = false;
                break;
        }
        if (!statusValid)
        {
            CtrAlertDialog.errorDialog("Please fill all the fields for Extra Scan  " + indexScan2Validate);
        }

        return statusValid;
    }

    private boolean validatePageNumber(String strPageNumber)
    {
        try
        {
            Integer.parseInt(strPageNumber);
            return true;
        } catch (NumberFormatException nfe)
        {
            return false;
        }
    }

    @Override
    public void actionLockEdit()
    {
        MonasticProfile p;

        cbPassportIssuedAt.setDisable(true);
        dpPassportIssueDate.setDisable(true);
        dpPassportExpiryDate.setDisable(true);
        dpFirstEntryDate.setDisable(true);

        bScanPassport.setDisable(true);
        bScanDepartureCard.setDisable(true);

        bSelectScan1.setDisable(true);
        bSelectScan2.setDisable(true);
        bSelectScan3.setDisable(true);

        bArchive1.setDisable(true);
        bArchive2.setDisable(true);
        bArchive3.setDisable(true);
        bArchivePassport.setDisable(true);
        bArchiveDepartureCard.setDisable(true);

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        fillDataContentScans(p, true);
    }

    @Override
    public void actionUnlockEdit()
    {
        MonasticProfile p;

        cbPassportIssuedAt.setDisable(false);
        dpPassportIssueDate.setDisable(false);
        dpPassportExpiryDate.setDisable(false);
        dpFirstEntryDate.setDisable(false);

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        fillDataContentScans(p, false);
    }

    @Override
    public int actionSave()
    {
        MonasticProfile p;

        int opStatus1, opStatus2;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        p.setPassportIssuedAt(cbPassportIssuedAt.getValue());
        p.setPassportIssueDate(Util.convertLocalDateToDate(dpPassportIssueDate.getValue()));
        p.setPassportExpiryDate(Util.convertLocalDateToDate(dpPassportExpiryDate.getValue()));
        p.setFirstEntryDate(Util.convertLocalDateToDate(dpFirstEntryDate.getValue()));

        opStatus1 = ctrGUIMain.getCtrMain().getCtrProfile().update(p);
        opStatus2 = saveExtraScans();

        if ((opStatus1 == 0) && (opStatus2 == 0))
        {
            ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
            CtrAlertDialog.infoDialog("Passport update", "The passport information was successfully updated.");
            return 0;
        } else
        {
            return -1;
        }
    }

    @FXML
    void actionPreviewScansPDF(ActionEvent ae)
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFPassportScans(p, CtrPDF.OPTION_PREVIEW_FORM);
    }

//    @FXML
//    void actionPrintScans(ActionEvent ae)
//    {
//        MonasticProfile p;
//        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
//        ctrGUIMain.getCtrMain().getCtrPDF().generatePDFPassportScans(p, CtrPDF.OPTION_PRINT_FORM);
//    }
}
