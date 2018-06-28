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
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;

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
        listFields.add(tgArriveStamp);
        listFields.add(tgLastVisaExt);
        listFields.add(tgVisa);

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
        VisaExtension lastVisaExt;
        LocalDate ldExpLastVisaExt;

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

            dpVisaExpiryDate.setValue(ctrGUIMain.getCtrMain().getCtrVisa().getVisaOrExtExpiryDate(p));
            
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

    private void refreshExtraPassportScan(MonasticProfile p)
    {
        ArrayList<ExtraPassportScanLoaded> listExtraPScan;

        objEPS1 = objEPS2 = objEPS3 = null;
        listExtraPScan = AppFiles.getListExtraScans(p.getNickname(), p.getPassportNumber());

        if (listExtraPScan.size() >= 1)
        {
            objEPS1 = listExtraPScan.get(0);
        }
        if (listExtraPScan.size() >= 2)
        {
            objEPS2 = listExtraPScan.get(1);
        }
        if (listExtraPScan.size() >= 3)
        {
            objEPS3 = listExtraPScan.get(2);
        }
    }

    private void fillDataContentScanGeneric(FieldsPaneScanContent fieldScan, ExtraPassportScanLoaded objExtraScan, boolean lockStatus)
    {
        if (lockStatus)
        {
            fieldScan.actionLockEdit();
        } else
        {
            fieldScan.actionUnlockEdit();
        }

        if (objExtraScan != null)
        {
            fieldScan.setPaneContentNotEmpty();

            fieldScan.getTfPLeftPageNumber().setText(objExtraScan.getLeftPageNumber() + "");
            if (objExtraScan.containsScanArriveStamp())
            {
                fieldScan.getRbArriveStamp().setSelected(true);
            }
            if (objExtraScan.containsScanVisa())
            {
                fieldScan.getRbVisa().setSelected(true);
            }
            if (objExtraScan.containsScanLastVisaExt())
            {
                fieldScan.getRbLastVisaExt().setSelected(true);
            }
        } else
        {
            fieldScan.setPaneContentEmpty();
        }
    }

    private void fillDataContentScans(MonasticProfile p, boolean lockStatus)
    {
        ArrayList<ExtraPassportScanLoaded> listFExtraPScan;
        fieldsScan1.reset();
        fieldsScan2.reset();
        fieldsScan3.reset();

        fScan1Selected = null;
        fScan2Selected = null;
        fScan3Selected = null;

        refreshExtraPassportScan(p);
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

        fillDataContentScanGeneric(fieldsScan1, objEPS1, lockStatus);
        fillDataContentScanGeneric(fieldsScan2, objEPS2, lockStatus);
        fillDataContentScanGeneric(fieldsScan3, objEPS3, lockStatus);
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
            fPassportScan = fDepartureCard = null;
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

    private boolean checkIfNeedRenameScanFile(ExtraPassportScanLoaded objPES, FieldsPaneScanContent objFS)
    {
        boolean stateScanArriveStamp;
        boolean stateScanVisa;
        boolean stateScanLastVisaExt;

        stateScanArriveStamp = objFS.getRbArriveStamp().isSelected();
        stateScanVisa = objFS.getRbVisa().isSelected();
        stateScanLastVisaExt = objFS.getRbLastVisaExt().isSelected();
        //if there was any change on the radio buttons for the scan type

        return (stateScanArriveStamp != objPES.containsScanArriveStamp())
                || (stateScanVisa != objPES.containsScanVisa())
                || (stateScanLastVisaExt != objPES.containsScanLastVisaExt());
    }

    private boolean renameExtraScan(MonasticProfile p, ExtraPassportScanLoaded objPES, FieldsPaneScanContent objFS)
    {
        boolean stateScanArriveStamp;
        boolean stateScanVisa;
        boolean stateScanLastVisaExt;
        File fScanUpdated;
        int ret;

        stateScanArriveStamp = objFS.getRbArriveStamp().isSelected();
        stateScanVisa = objFS.getRbVisa().isSelected();
        stateScanLastVisaExt = objFS.getRbLastVisaExt().isSelected();

        //if there was any change on the radio buttons for the scan type
        //save the changes by updating the name of the scan file
        ExtraPassportScanNew objPSNew = new ExtraPassportScanNew(objPES.getLeftPageNumber(), stateScanArriveStamp, stateScanVisa, stateScanLastVisaExt);
        fScanUpdated = AppFiles.generateFileNameExtraScan(p.getNickname(), p.getPassportNumber(), objPSNew);

        ret = CtrFileOperation.renameFile(objPES.getFileScan(), fScanUpdated);
        if (ret == -1)
        {
            CtrAlertDialog.errorDialog("Unable to rename the extra scan file.");
            return true;
        }

        return false;

    }

    private boolean addNewExtraScan(MonasticProfile p, FieldsPaneScanContent objFS, File fScanSelected)
    {
        ExtraPassportScanNew objPS;
        int leftPageNumber, ret;
        boolean stateScanArriveStamp;
        boolean stateScanVisa;
        boolean stateScanLastVisaExt;
        File fDestination;

        leftPageNumber = parseInt(objFS.getTfPLeftPageNumber().getText());
        stateScanArriveStamp = objFS.getRbArriveStamp().isSelected();
        stateScanVisa = objFS.getRbVisa().isSelected();
        stateScanLastVisaExt = objFS.getRbLastVisaExt().isSelected();

        objPS = new ExtraPassportScanNew(leftPageNumber, stateScanArriveStamp, stateScanVisa, stateScanLastVisaExt);
        fDestination = AppFiles.generateFileNameExtraScan(p.getNickname(), p.getPassportNumber(), objPS);

        ret = CtrFileOperation.copyOperation(fScanSelected, fDestination);
        //if the operation was unsuccessful
        if (ret == -1)
        {
            CtrAlertDialog.errorDialog("Unable to copy the file for Extra Scan " + fScanSelected.getName() + ".");
            return true;
        }

        return false;
    }

    private boolean validateNewExtraScanAdded()
    {
        boolean valid1, valid2, valid3;

        valid1 = valid2 = valid3 = true;
        if (fScan1Selected != null)
        {
            valid1 = validateExtraScanContent(fieldsScan1);
        }
        if (fScan2Selected != null)
        {
            valid2 = validateExtraScanContent(fieldsScan2);
        }
        if (fScan3Selected != null)
        {
            valid3 = validateExtraScanContent(fieldsScan3);
        }

        return valid1 && valid2 && valid3;
    }

    private boolean saveExtraScanGeneric(MonasticProfile p, ExtraPassportScanLoaded objEPS, File fScanSelected, FieldsPaneScanContent fieldsScan)
    {
        if (fScanSelected != null)
        {
            return addNewExtraScan(p, fieldsScan, fScanSelected);
        } //if there was no scan added need to check if there was change on the selected scan types
        else if ((objEPS != null) && checkIfNeedRenameScanFile(objEPS, fieldsScan))
        {
            return renameExtraScan(p, objEPS, fieldsScan);
        }
        return false;
    }

    private int saveExtraScans()
    {
        boolean error1, error2, error3;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        //if there is a passport registered allows the user to add other scans

        error1 = error2 = error3 = false;

        //if there are any new extra scans added
        //only run the save operation if they are fully validated
        //in other cases... can just go ahead and save
        if (validateNewExtraScanAdded())
        {
            error1 = saveExtraScanGeneric(p, objEPS1, fScan1Selected, fieldsScan1);
            error2 = saveExtraScanGeneric(p, objEPS2, fScan2Selected, fieldsScan2);
            error3 = saveExtraScanGeneric(p, objEPS3, fScan3Selected, fieldsScan3);
            fillDataContentScans(p, ctrGUIMain.getPaneEditSaveController().getLockStatus());
            loadIMGPreviews(p);
            //if there was any error on file copying process
            if (error1 || error2 || error3)
            {
                return -1;

            } else
            {
                return 0;
            }
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

    private boolean checkPageNumberNewScanAlreadyExists(int pageNumberNewScan)
    {
        return (objEPS1 != null && objEPS1.getLeftPageNumber() == pageNumberNewScan)
                || (objEPS2 != null && objEPS2.getLeftPageNumber() == pageNumberNewScan)
                || (objEPS3 != null && objEPS3.getLeftPageNumber() == pageNumberNewScan);
    }

    private boolean validateExtraScanContent(FieldsPaneScanContent fieldsScan)
    {
        //if the page number is not empty and at least one of the options is selected
        //returns true
        boolean statusValid;
        boolean validationPageNumber;
        boolean scanTypeSelected;

        validationPageNumber = validatePageNumber(fieldsScan.getTfPLeftPageNumber().getText());
        scanTypeSelected = (fieldsScan.getRbArriveStamp().isSelected()
                || fieldsScan.getRbLastVisaExt().isSelected()
                || fieldsScan.getRbVisa().isSelected());

        statusValid = validationPageNumber && scanTypeSelected;
        //if the scan type or the page number is empty
        if (!scanTypeSelected || fieldsScan.getTfPLeftPageNumber().getText().isEmpty())
        {
            CtrAlertDialog.errorDialog("Please fill all the fields for Extra Scan.");
        }

        return statusValid;
    }

    private boolean validatePageNumber(String strPageNumber)
    {
        int i;
        if (!strPageNumber.isEmpty())
        {
            try
            {
                i = Integer.parseInt(strPageNumber);
                //if the page number of the new scan is repeated somewhere else refuses the operation
                if (checkPageNumberNewScanAlreadyExists(i))
                {
                    CtrAlertDialog.errorDialog("The page number for the newly added scan already exists at another scan.");
                    return false;
                } else
                {
                    return true;
                }

            } catch (NumberFormatException nfe)
            {
                CtrAlertDialog.errorDialog("Invalid page number.");
                return false;
            }
        }
        return false;
    }

    @Override
    public void actionLockEdit()
    {
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

        fieldsScan1.actionLockEdit();
        fieldsScan2.actionLockEdit();
        fieldsScan3.actionLockEdit();
    }

    @Override
    public void actionUnlockEdit()
    {
        cbPassportIssuedAt.setDisable(false);
        dpPassportIssueDate.setDisable(false);
        dpPassportExpiryDate.setDisable(false);
        dpFirstEntryDate.setDisable(false);

        fieldsScan1.actionUnlockEdit();
        fieldsScan2.actionUnlockEdit();
        fieldsScan3.actionUnlockEdit();
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
