/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.panel.abs.AChildPaneController;
import org.watmarpjan.visaManager.gui.intface.ICreateEditGUIForm;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.GUIUtil;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import org.watmarpjan.visaManager.util.Util;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.watmarpjan.visaManager.util.ProfileUtil;
import static java.lang.Integer.parseInt;
import javafx.scene.input.MouseEvent;
import org.watmarpjan.visaManager.AppPaths;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneMonasticProfile extends AChildPaneController implements IFormMonasticProfile, ICreateEditGUIForm
{

    private final String PATH_DEFAULT_PROFILE_PHOTO = "img/profile/default.png";

    @FXML
    private TextField tfNickname;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfMiddleName;
    @FXML
    private TextField tfLastName;
    @FXML
    private TextField tfMother;
    @FXML
    private TextField tfFather;

    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField tfBirthWeekday;
    @FXML
    private TextField tfAge;

    @FXML
    private ComboBox<String> cbPreviousResidenceCountry;

    @FXML
    private ComboBox<String> cbBirthCountry;
    @FXML
    private TextField tfBirthPlace;
    @FXML
    private ComboBox<String> cbNationality;
    @FXML
    private ComboBox<String> cbEthnicity;

    @FXML
    private ComboBox<String> cbOccupation;
    @FXML
    private ComboBox<String> cbOccupationThai;

    @FXML
    private TextField tfSchool;
    @FXML
    private ComboBox<String> cbCertificate;
    @FXML
    private ComboBox<String> cbCertificateThai;
    @FXML
    private TextField tfDuration;
    @FXML
    private TextField tfGraduationYear;

    @FXML
    private ComboBox<String> cbResidingAt;

    @FXML
    private ComboBox<String> cbAdviserToCome;
    @FXML
    private ComboBox<String> cbAdvisorWat;

    @FXML
    private ToggleGroup tgPatimokkhaChanter;

    @FXML
    private RadioButton rbPatimokkhaChanterYes;

    @FXML
    private RadioButton rbPatimokkhaChanterNo;

    @FXML
    private ToggleGroup tgStatus;
    @FXML
    private RadioButton rbInThailand;
    @FXML
    private RadioButton rbAbroad;
    @FXML
    private RadioButton rbInactive;

    @FXML
    private RadioButton rbOnlineNoticeYes;
    @FXML
    private RadioButton rbOnlineNoticeNo;

    @FXML
    private ToggleGroup tgOnlineNotice;

    @FXML
    private RadioButton rbVisaManagerYes;
    @FXML
    private RadioButton rbVisaManagerNo;

    @FXML
    private ToggleGroup tgVisaManager;

    @FXML
    private ToggleGroup tgDhammaStudies;
    @FXML
    private RadioButton rbDhammaStudiesRegular;
    @FXML
    private RadioButton rbDhammaStudiesNaktamTri;
    @FXML
    private RadioButton rbDhammaStudiesNaktamToh;
    @FXML
    private RadioButton rbDhammaStudiesNaktamEk;

    @FXML
    private TextField tfEmail;
    @FXML
    private TextArea taEmergencyContact;

    @FXML
    private TextField tfPhoneNumber1;
    @FXML
    private TextField tfPhoneNumber2;

    @FXML
    private TextArea taRemark;

    @FXML
    private Button bChangeProfilePhoto;

    @FXML
    private Button bArchiveProfilePhoto;

    @FXML
    private ImageView ivProfilePhoto;

    @FXML
    private TextField tfPathPDFNaktamTri;

    @FXML
    private TextField tfPathPDFNaktamToh;

    @FXML
    private TextField tfPathPDFNaktamEk;

    @FXML
    private Button bAddCertificateNaktamTri;

    @FXML
    private Button bAddCertificateNaktamToh;

    @FXML
    private Button bAddCertificateNaktamEk;

    @FXML
    private Button bRemoveCertificateNaktamTri;

    @FXML
    private Button bRemoveCertificateNaktamToh;

    @FXML
    private Button bRemoveCertificateNaktamEk;

    @FXML
    private Button bCurrentCertificateNaktamTri;

    @FXML
    private Button bCurrentCertificateNaktamToh;

    @FXML
    private Button bCurrentCertificateNaktamEk;
    
    @FXML
    private ComboBox<String> cbPassportKeptAt;

    File fPDFSelectedNaktamTri;
    File fPDFSelectedNaktamToh;
    File fPDFSelectedNaktamEk;

    @Override
    public void init()
    {
        ArrayList listFields;
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpBirthDate);

        listFields = new ArrayList();
        listFields.add(tfNickname);
        listFields.add(tfName);
        listFields.add(tfMiddleName);
        listFields.add(tfLastName);
        listFields.add(tfMother);
        listFields.add(tfFather);
        listFields.add(tgStatus);
        listFields.add(dpBirthDate);
        listFields.add(cbBirthCountry);
        listFields.add(tfBirthPlace);
        listFields.add(cbEthnicity);
        listFields.add(cbNationality);
        listFields.add(cbOccupation);
        listFields.add(cbOccupationThai);
        listFields.add(cbCertificate);
        listFields.add(cbCertificateThai);
        listFields.add(tfSchool);
        listFields.add(tfGraduationYear);
        listFields.add(tfDuration);
        listFields.add(tgPatimokkhaChanter);
        listFields.add(cbPreviousResidenceCountry);
        listFields.add(cbResidingAt);
        listFields.add(cbAdviserToCome);
        listFields.add(cbAdvisorWat);
        listFields.add(tgDhammaStudies);
        listFields.add(tgOnlineNotice);
        listFields.add(tgVisaManager);
        listFields.add(tfEmail);
        listFields.add(taEmergencyContact);
        listFields.add(tfPhoneNumber1);
        listFields.add(tfPhoneNumber2);
        listFields.add(taRemark);
        listFields.add(cbPassportKeptAt);

        bCurrentCertificateNaktamTri.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bCurrentCertificateNaktamToh.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));
        bCurrentCertificateNaktamEk.setGraphic(new ImageView(AppPaths.getPathIconPDF().toUri().toString()));

        bAddCertificateNaktamTri.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("add.png").toUri().toString()));
        bAddCertificateNaktamToh.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("add.png").toUri().toString()));
        bAddCertificateNaktamEk.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("add.png").toUri().toString()));

        bRemoveCertificateNaktamTri.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("remove.png").toUri().toString()));
        bRemoveCertificateNaktamToh.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("remove.png").toUri().toString()));
        bRemoveCertificateNaktamEk.setGraphic(new ImageView(AppPaths.getPathToIconSubfolder().resolve("remove.png").toUri().toString()));

        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        tfNickname.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue != null)
                {
                    //removes any character except letters, number, dot and space
                    tfNickname.setText(newValue.replaceAll("[^a-zA-Z0-9\\. ]", ""));
                }
            }
        });

        tfGraduationYear.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue != null)
                {
                    //removes any non-digits
                    tfGraduationYear.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });

        tfDuration.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (newValue != null)
                {
                    //removes any non-digits
                    tfDuration.setText(newValue.replaceAll("\\D", ""));
                }
            }
        });

        dpBirthDate.valueProperty().addListener(new ChangeListener<LocalDate>()
        {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue)
            {
                Date newBirthDate;

                newBirthDate = Util.convertLocalDateToDate(newValue);
                tfAge.setText(ProfileUtil.getStrAge(newBirthDate) + "");
                tfBirthWeekday.setText(ProfileUtil.getShortenedBirthWeekDay(newBirthDate));
            }
        });
    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getCtrPaneSelection().isSelectionEmpty();
    }

    @Override
    public void fillData(MonasticProfile p)
    {
        ArrayList<String> listCountry, listWat;

        listCountry = ctrGUIMain.getCtrMain().loadListCountry();
        listWat = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryList();

        GUIUtil.loadContentComboboxGeneric(cbEthnicity, ctrGUIMain.getCtrMain().getCtrProfile().loadListEthnicity());
        GUIUtil.loadContentComboboxGeneric(cbNationality, ctrGUIMain.getCtrMain().getCtrProfile().loadListNationality());

        GUIUtil.loadContentComboboxGeneric(cbAdviserToCome, ctrGUIMain.getCtrMain().getCtrProfile().loadListAdviserToCome());

        GUIUtil.loadContentComboboxGeneric(cbBirthCountry, listCountry);
        GUIUtil.loadContentComboboxGeneric(cbPreviousResidenceCountry, listCountry);

        GUIUtil.loadContentComboboxGeneric(cbOccupation, ctrGUIMain.getCtrMain().getCtrProfile().loadOccupationEnglishList());
        GUIUtil.loadContentComboboxGeneric(cbOccupationThai, ctrGUIMain.getCtrMain().getCtrProfile().loadOccupationThaiList());

        GUIUtil.loadContentComboboxGeneric(cbCertificate, ctrGUIMain.getCtrMain().getCtrProfile().loadCertificateEngList());
        GUIUtil.loadContentComboboxGeneric(cbCertificateThai, ctrGUIMain.getCtrMain().getCtrProfile().loadCertificateThaiList());

        GUIUtil.loadContentComboboxGeneric(cbPassportKeptAt, ctrGUIMain.getCtrMain().getCtrProfile().loadListPassportKeptAt());
        
        GUIUtil.loadContentComboboxGeneric(cbResidingAt, listWat);
        GUIUtil.loadContentComboboxGeneric(cbAdvisorWat, listWat);

        if (p != null)
        {
            tfNickname.setText(p.getNickname());

            tfName.setText(p.getMonasticName());
            tfMiddleName.setText(p.getMiddleName());
            tfLastName.setText(p.getLastName());

            tfMother.setText(p.getMotherName());
            tfFather.setText(p.getFatherName());

            cbPreviousResidenceCountry.setValue(p.getPreviousResidenceCountry());
            cbBirthCountry.setValue(p.getBirthCountry());
            tfBirthPlace.setText(p.getBirthPlace());

            dpBirthDate.setValue(Util.convertDateToLocalDate(p.getBirthDate()));
            tfAge.setText(ProfileUtil.getStrAge(p.getBirthDate()) + "");
            tfBirthWeekday.setText(ProfileUtil.getShortenedBirthWeekDay(p.getBirthDate()));

            cbNationality.setValue(p.getNationality());
            cbEthnicity.setValue(p.getEthnicity());

            cbOccupation.setValue(p.getOccupationEnglish());
            cbOccupationThai.setValue(p.getOccupationThai());
            tfSchool.setText(p.getSchool());
            cbCertificate.setValue(p.getCertificateEnglish());
            cbCertificateThai.setValue(p.getCertificateThai());
            cbPassportKeptAt.setValue(p.getPassportKeptAt());
            if (p.getCertificateDuration() != null)
            {
                tfDuration.setText(p.getCertificateDuration() + "");
            }
            else
            {
                tfDuration.setText("");
            }

            if (p.getCertificateGradYear() != null)
            {
                tfGraduationYear.setText(p.getCertificateGradYear() + "");
            }
            else
            {
                tfGraduationYear.setText("");
            }

            if (p.getMonasteryResidingAt() != null)
            {
                cbResidingAt.setValue(p.getMonasteryResidingAt().getMonasteryName());
            }
            else
            {
                cbResidingAt.setValue(null);
            }

            if (p.getMonasteryAdviserToCome() != null)
            {
                cbAdvisorWat.setValue(p.getMonasteryAdviserToCome().getMonasteryName());
            }
            else
            {
                cbAdvisorWat.setValue(null);
            }

            cbAdviserToCome.setValue(p.getNameAdviserToCome());

            if ((p.getPatimokkhaChanter() == null) || (!p.getPatimokkhaChanter()))
            {
                rbPatimokkhaChanterNo.setSelected(true);
            }
            else
            {
                rbPatimokkhaChanterYes.setSelected(true);
            }

            if (p.getOnlineNoticeAccepted() != null)
            {
                if (p.getOnlineNoticeAccepted())
                {
                    rbOnlineNoticeYes.setSelected(true);
                }
                else
                {
                    rbOnlineNoticeNo.setSelected(true);
                }
            }
            else
            {
                rbOnlineNoticeNo.setSelected(true);
            }

            if (p.getVisaManager() != null)
            {
                if (p.getVisaManager())
                {
                    rbVisaManagerYes.setSelected(true);
                }
                else
                {
                    rbVisaManagerNo.setSelected(true);
                }
            }
            else
            {
                rbVisaManagerNo.setSelected(true);
            }

            switch (p.getDhammaStudies())
            {
                case AppConstants.STUDIES_NAKTAM_TRI:
                    rbDhammaStudiesNaktamTri.setSelected(true);
                    break;
                case AppConstants.STUDIES_NAKTAM_TOH:
                    rbDhammaStudiesNaktamToh.setSelected(true);
                    break;
                case AppConstants.STUDIES_NAKTAM_EK:
                    rbDhammaStudiesNaktamEk.setSelected(true);
                    break;
                default:
                    rbDhammaStudiesRegular.setSelected(true);
                    break;
            }

            tfPhoneNumber1.setText(p.getPhoneNumber1());
            tfPhoneNumber2.setText(p.getPhoneNumber2());
            tfEmail.setText(p.getEmail());
            taEmergencyContact.setText(p.getEmergencyContact());

            taRemark.setText(p.getRemark());
            switch (p.getStatus())
            {
                case AppConstants.STATUS_THAILAND:
                    rbInThailand.setSelected(true);
                    break;
                case AppConstants.STATUS_ABROAD:
                    rbAbroad.setSelected(true);
                    break;
                default:
                    rbInactive.setSelected(true);
                    break;
            }
            loadProfilePhoto(p);
            initNaktamCertificateButtons(p);
        }
    }

    private void initNaktamCertificateButtons(MonasticProfile p)
    {
        tfPathPDFNaktamEk.setText("");
        tfPathPDFNaktamToh.setText("");
        tfPathPDFNaktamTri.setText("");

        initButtonNaktamCertificateGeneric(p, bCurrentCertificateNaktamTri, bAddCertificateNaktamTri, bRemoveCertificateNaktamTri, AppConstants.STUDIES_NAKTAM_TRI, tfPathPDFNaktamTri);
        initButtonNaktamCertificateGeneric(p, bCurrentCertificateNaktamToh, bAddCertificateNaktamToh, bRemoveCertificateNaktamToh, AppConstants.STUDIES_NAKTAM_TOH, tfPathPDFNaktamToh);
        initButtonNaktamCertificateGeneric(p, bCurrentCertificateNaktamEk, bAddCertificateNaktamEk, bRemoveCertificateNaktamEk, AppConstants.STUDIES_NAKTAM_EK, tfPathPDFNaktamEk);
    }

    private void initButtonNaktamCertificateGeneric(MonasticProfile p, Button bCurrentCertificate, Button bAddCertificate, Button bRemoveCertificate, String level, TextField tfPathPDF)
    {
        if (AppFiles.getNaktamCertificate(p.getNickname(), level).exists())
        {
            bCurrentCertificate.setDisable(false);

            tfPathPDF.setVisible(false);
            bAddCertificate.setVisible(false);

            bRemoveCertificate.setVisible(true);
        }
        else
        {
            bCurrentCertificate.setDisable(true);

            tfPathPDF.setVisible(true);
            bAddCertificate.setVisible(true);

            bRemoveCertificate.setVisible(false);
        }
    }

    private void loadProfilePhoto(MonasticProfile p)
    {
        File fileProfilePhoto;

        if (p != null)
        {
            fileProfilePhoto = AppFiles.getProfilePhoto(p.getNickname());
        }
        else
        {
            fileProfilePhoto = null;
        }

        reloadButtonsProfilePhoto();

        GUIUtil.loadImageView(ivProfilePhoto, GUIUtil.IMG_TYPE_PROFILE, fileProfilePhoto);
    }

    private void reloadButtonsProfilePhoto()
    {
        File fPhoto;
        MonasticProfile p;
        
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        fPhoto = AppFiles.getProfilePhoto(p.getNickname());
        
        if (ctrGUIMain.getPaneEditSaveController().getLockStatus())
        {
            bChangeProfilePhoto.setDisable(true);
            bArchiveProfilePhoto.setDisable(true);
        }
        else
        {
            if (fPhoto != null && fPhoto.exists())
            {
                bChangeProfilePhoto.setDisable(true);
                bArchiveProfilePhoto.setDisable(false);
            }
            else
            {
                bChangeProfilePhoto.setDisable(false);
                bArchiveProfilePhoto.setDisable(true);
            }
        }
    }

    @FXML
    void actionChooseProfilePhoto(ActionEvent ae)
    {
        MonasticProfile p;
        File fDestination;
        File fSource;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        fDestination = AppFiles.getProfilePhoto(p.getNickname());
        fSource = CtrFileOperation.selectFile("Select Profile Photo", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);

        if (fSource != null)
        {
            CtrFileOperation.copyOperation(fSource, fDestination);
            ctrGUIMain.getCtrPaneSelection().reloadCurrentProfile();
        }
    }

    @FXML
    void actionArchiveProfilePhoto(ActionEvent ae)
    {
        MonasticProfile p;
        String msg;
        boolean confirmation;
        int opStatusArchive;
        File f2Archive;

        msg = "Are you sure that you want to archive this profile photo?\n";

        confirmation = CtrAlertDialog.confirmationDialog("Confirmation", msg);
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            f2Archive = AppFiles.getProfilePhoto(p.getNickname());
            opStatusArchive = CtrFileOperation.archiveProfilePhotoOrCertificate(f2Archive, p.getNickname());
            if (opStatusArchive == 0)
            {
                ctrGUIMain.getCtrPaneSelection().reloadCurrentProfile();
                CtrAlertDialog.infoDialog("Archived successfully", "The profile photo was archived successfully.");
            }
        }
    }

    @Override
    public void actionAddNew()
    {
        String nickNameNewProfile;
        nickNameNewProfile = ctrGUIMain.getCtrMain().getCtrProfile().create();
        if (nickNameNewProfile != null)
        {
            ctrGUIMain.getCtrPaneSelection().reloadNicknameList(nickNameNewProfile);
        }
    }

    @FXML
    void actionIMGProfileClicked(MouseEvent me)
    {
        File fIMG;
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        if (p != null)
        {
            fIMG = AppFiles.getProfilePhoto(p.getNickname());
            if (fIMG != null)
            {
                CtrFileOperation.openFileOnDefaultProgram(fIMG);
            }
        }

    }

    @FXML
    void actionSelectCertificatePDFNaktamTri(ActionEvent ae)
    {
        fPDFSelectedNaktamTri = CtrFileOperation.selectFile("Select Certificate File", CtrFileOperation.FILE_CHOOSER_TYPE_PDF);
        if (fPDFSelectedNaktamTri != null)
        {
            tfPathPDFNaktamTri.setText(fPDFSelectedNaktamTri.getAbsolutePath().toString());
            ctrGUIMain.getCtrFieldChangeListener().setHasUnsavedChanges();
        }
    }

    @FXML
    void actionSelectCertificatePDFNaktamToh(ActionEvent ae)
    {
        fPDFSelectedNaktamToh = CtrFileOperation.selectFile("Select Certificate File", CtrFileOperation.FILE_CHOOSER_TYPE_PDF);
        if (fPDFSelectedNaktamToh != null)
        {
            tfPathPDFNaktamToh.setText(fPDFSelectedNaktamToh.getAbsolutePath().toString());
            ctrGUIMain.getCtrFieldChangeListener().setHasUnsavedChanges();
        }
    }

    @FXML
    void actionSelectCertificatePDFNaktamEk(ActionEvent ae)
    {
        fPDFSelectedNaktamEk = CtrFileOperation.selectFile("Select Certificate File", CtrFileOperation.FILE_CHOOSER_TYPE_PDF);
        if (fPDFSelectedNaktamEk != null)
        {
            tfPathPDFNaktamEk.setText(fPDFSelectedNaktamEk.getAbsolutePath().toString());
            ctrGUIMain.getCtrFieldChangeListener().setHasUnsavedChanges();
        }
    }

    @FXML
    void actionOpenCertificatePDFNaktamTri(ActionEvent ae)
    {
        actionOpenCertificatePDFGeneric(AppConstants.STUDIES_NAKTAM_TRI);
    }

    @FXML
    void actionOpenCertificatePDFNaktamToh(ActionEvent ae)
    {
        actionOpenCertificatePDFGeneric(AppConstants.STUDIES_NAKTAM_TOH);
    }

    @FXML
    void actionOpenCertificatePDFNaktamEk(ActionEvent ae)
    {
        actionOpenCertificatePDFGeneric(AppConstants.STUDIES_NAKTAM_EK);
    }

    private void actionOpenCertificatePDFGeneric(String level)
    {
        MonasticProfile p;

        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
        CtrFileOperation.openFileOnDefaultProgram(AppFiles.getNaktamCertificate(p.getNickname(), level));
    }

    @FXML
    void actionRemoveCertificatePDFNaktamTri(ActionEvent ae)
    {
        actionRemoveCertificatePDFGeneric(AppConstants.STUDIES_NAKTAM_TRI);
    }

    @FXML
    void actionRemoveCertificatePDFNaktamToh(ActionEvent ae)
    {
        actionRemoveCertificatePDFGeneric(AppConstants.STUDIES_NAKTAM_TOH);
    }

    @FXML
    void actionRemoveCertificatePDFNaktamEk(ActionEvent ae)
    {
        actionRemoveCertificatePDFGeneric(AppConstants.STUDIES_NAKTAM_EK);
    }

    private void actionRemoveCertificatePDFGeneric(String level)
    {
        MonasticProfile p;
        String msg;
        boolean confirmation;
        int opStatusArchive;
        File f2Archive;

        msg = "Are you sure that you want to remove the " + level + " certificate?\n"
                + "(Note: The certificate file will be archived)\n ";

        confirmation = CtrAlertDialog.confirmationDialog("Confirmation", msg);
        if (confirmation)
        {
            p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

            f2Archive = AppFiles.getNaktamCertificate(p.getNickname(), level);
            opStatusArchive = CtrFileOperation.archiveProfilePhotoOrCertificate(f2Archive, p.getNickname());
            if (opStatusArchive == 0)
            {
                initNaktamCertificateButtons(p);
                CtrAlertDialog.infoDialog("Archived successfully", "The Naktam certificate was archived successfully.");
            }
        }
    }

    @Override
    public void actionLockEdit()
    {
        bChangeProfilePhoto.setDisable(true);
        bArchiveProfilePhoto.setDisable(true);

        tfNickname.setEditable(false);
        tfName.setEditable(false);
        tfMiddleName.setEditable(false);
        tfLastName.setEditable(false);
        tfMother.setEditable(false);
        tfFather.setEditable(false);

        dpBirthDate.setDisable(true);

        cbPreviousResidenceCountry.setDisable(true);
        cbBirthCountry.setDisable(true);
        tfBirthPlace.setEditable(false);

        cbNationality.setDisable(true);
        cbEthnicity.setDisable(true);

        cbOccupation.setDisable(true);
        cbOccupationThai.setDisable(true);
        cbCertificate.setDisable(true);
        cbCertificateThai.setDisable(true);

        tfSchool.setEditable(false);
        tfDuration.setEditable(false);
        tfGraduationYear.setEditable(false);

        cbResidingAt.setDisable(true);

        cbAdviserToCome.setDisable(true);
        cbAdvisorWat.setDisable(true);

        rbPatimokkhaChanterNo.setDisable(true);
        rbPatimokkhaChanterYes.setDisable(true);

        rbOnlineNoticeNo.setDisable(true);
        rbOnlineNoticeYes.setDisable(true);

        rbVisaManagerNo.setDisable(true);
        rbVisaManagerYes.setDisable(true);

        rbInThailand.setDisable(true);
        rbAbroad.setDisable(true);
        rbInactive.setDisable(true);

        rbDhammaStudiesRegular.setDisable(true);
        rbDhammaStudiesNaktamTri.setDisable(true);
        rbDhammaStudiesNaktamToh.setDisable(true);
        rbDhammaStudiesNaktamEk.setDisable(true);

        tfEmail.setEditable(false);
        taEmergencyContact.setEditable(false);
        tfPhoneNumber1.setEditable(false);
        tfPhoneNumber2.setEditable(false);
        taRemark.setEditable(false);
        
        cbPassportKeptAt.setDisable(true);

        bAddCertificateNaktamTri.setDisable(true);
        bAddCertificateNaktamToh.setDisable(true);
        bAddCertificateNaktamEk.setDisable(true);

        bRemoveCertificateNaktamTri.setDisable(true);
        bRemoveCertificateNaktamToh.setDisable(true);
        bRemoveCertificateNaktamEk.setDisable(true);
        
        reloadButtonsProfilePhoto();
    }

    @Override
    public void actionUnlockEdit()
    {
        tfNickname.setEditable(true);
        tfName.setEditable(true);
        tfMiddleName.setEditable(true);
        tfLastName.setEditable(true);
        tfMother.setEditable(true);
        tfFather.setEditable(true);

        dpBirthDate.setDisable(false);

        cbPreviousResidenceCountry.setDisable(false);
        cbBirthCountry.setDisable(false);
        tfBirthPlace.setEditable(true);

        cbNationality.setDisable(false);
        cbEthnicity.setDisable(false);

        cbOccupation.setDisable(false);
        cbOccupationThai.setDisable(false);
        cbCertificate.setDisable(false);
        cbCertificateThai.setDisable(false);

        tfSchool.setEditable(true);
        tfDuration.setEditable(true);
        tfGraduationYear.setEditable(true);

        cbResidingAt.setDisable(false);

        cbAdviserToCome.setDisable(false);
        cbAdvisorWat.setDisable(false);

        rbPatimokkhaChanterNo.setDisable(false);
        rbPatimokkhaChanterYes.setDisable(false);

        rbOnlineNoticeNo.setDisable(false);
        rbOnlineNoticeYes.setDisable(false);

        rbVisaManagerNo.setDisable(false);
        rbVisaManagerYes.setDisable(false);

        rbInThailand.setDisable(false);
        rbAbroad.setDisable(false);
        rbInactive.setDisable(false);

        rbDhammaStudiesRegular.setDisable(false);
        rbDhammaStudiesNaktamTri.setDisable(false);
        rbDhammaStudiesNaktamToh.setDisable(false);
        rbDhammaStudiesNaktamEk.setDisable(false);

        tfEmail.setEditable(true);
        taEmergencyContact.setEditable(true);

        tfPhoneNumber1.setEditable(true);
        tfPhoneNumber2.setEditable(true);
        taRemark.setEditable(true);

        cbPassportKeptAt.setDisable(false);
        
        bAddCertificateNaktamTri.setDisable(false);
        bAddCertificateNaktamToh.setDisable(false);
        bAddCertificateNaktamEk.setDisable(false);

        bRemoveCertificateNaktamTri.setDisable(false);
        bRemoveCertificateNaktamToh.setDisable(false);
        bRemoveCertificateNaktamEk.setDisable(false);
        
        reloadButtonsProfilePhoto();
    }

    private void actionSaveNaktamCertificates(MonasticProfile p)
    {
        actionSaveNaktamCertificateGeneric(p, fPDFSelectedNaktamTri, AppConstants.STUDIES_NAKTAM_TRI);
        actionSaveNaktamCertificateGeneric(p, fPDFSelectedNaktamToh, AppConstants.STUDIES_NAKTAM_TOH);
        actionSaveNaktamCertificateGeneric(p, fPDFSelectedNaktamEk, AppConstants.STUDIES_NAKTAM_EK);
    }

    private void actionSaveNaktamCertificateGeneric(MonasticProfile p, File fPDFSelected, String level)
    {
        if (fPDFSelected != null && fPDFSelected.exists())
        {
            CtrFileOperation.copyOperation(fPDFSelected, AppFiles.getNaktamCertificate(p.getNickname(), level));
        }
        //reset temp variable after save
        fPDFSelected = null;
    }

    @Override
    public int actionSave()
    {
        MonasticProfile p;
        int operationStatus, gradYear;
        boolean error;
        Monastery wResidingAt, wAdviserToCome;
        Date birthDate;
        String previousNickName, newNickName;

        error = false;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();

        actionSaveNaktamCertificates(p);

        previousNickName = p.getNickname();
        newNickName = tfNickname.getText();

        p.setNickname(tfNickname.getText());
        p.setMonasticName(tfName.getText());
        p.setMiddleName(tfMiddleName.getText());
        p.setLastName(tfLastName.getText());

        p.setMotherName(tfMother.getText());
        p.setFatherName(tfFather.getText());

        birthDate = Util.convertLocalDateToDate(dpBirthDate.getValue());

        p.setBirthDate(birthDate);
        p.setPreviousResidenceCountry(cbPreviousResidenceCountry.getValue());
        p.setBirthCountry(cbBirthCountry.getValue());
        p.setBirthPlace(tfBirthPlace.getText());
        p.setNationality(cbNationality.getValue());
        p.setEthnicity(cbEthnicity.getValue());

        p.setOccupationEnglish(cbOccupation.getValue());
        p.setOccupationThai(cbOccupationThai.getValue());
        p.setSchool(tfSchool.getText());
        p.setCertificateEnglish(cbCertificate.getValue());

        p.setCertificateThai(cbCertificateThai.getValue());
        
        p.setPassportKeptAt(cbPassportKeptAt.getValue());

        //if the duration changed and the text field is not empty
        if ((!tfDuration.getText().equals(p.getCertificateDuration())
                && (!tfDuration.getText().equals(""))))
        {
            p.setCertificateDuration(parseInt(tfDuration.getText()));
        }
        else
        {
            p.setCertificateDuration(null);
        }

        //if the graduation year changed and the text field is not empty
        if ((!tfGraduationYear.getText().equals(p.getCertificateGradYear()))
                && (!tfGraduationYear.getText().equals("")))
        {
            gradYear = parseInt(tfGraduationYear.getText());
            if (gradYear < 2400)
            {
                p.setCertificateGradYear(gradYear);
            }
            else
            {
                CtrAlertDialog.errorDialog("The graduation Year should be on Western format.");
                error = true;
            }
        }
        else
        {
            p.setCertificateGradYear(null);
        }

        if (cbResidingAt.getValue() != null)
        {
            wResidingAt = ctrGUIMain.getCtrMain().getCtrMonastery().loadByName(cbResidingAt.getValue());
            p.setMonasteryResidingAt(wResidingAt);
        }

        if (cbAdvisorWat.getValue() != null)
        {
            wAdviserToCome = ctrGUIMain.getCtrMain().getCtrMonastery().loadByName(cbAdvisorWat.getValue());
            p.setMonasteryAdviserToCome(wAdviserToCome);
        }

        if (rbPatimokkhaChanterYes.isSelected())
        {
            p.setPatimokkhaChanter(true);
        }
        else
        {
            p.setPatimokkhaChanter(false);
        }

        p.setNameAdviserToCome(cbAdviserToCome.getValue());

        if (rbOnlineNoticeYes.isSelected())
        {
            p.setOnlineNoticeAccepted(true);
        }
        else
        {
            p.setOnlineNoticeAccepted(false);
        }

        if (rbVisaManagerYes.isSelected())
        {
            p.setVisaManager(true);
        }
        else
        {
            p.setVisaManager(false);
        }

        if (rbDhammaStudiesNaktamTri.isSelected())
        {
            p.setDhammaStudies(AppConstants.STUDIES_NAKTAM_TRI);
        }
        else if (rbDhammaStudiesNaktamToh.isSelected())
        {
            p.setDhammaStudies(AppConstants.STUDIES_NAKTAM_TOH);
        }
        else if (rbDhammaStudiesNaktamEk.isSelected())
        {
            p.setDhammaStudies(AppConstants.STUDIES_NAKTAM_EK);
        }
        else
        {
            p.setDhammaStudies(AppConstants.STUDIES_REGULAR);
        }

        p.setPhoneNumber1(tfPhoneNumber1.getText());
        p.setPhoneNumber2(tfPhoneNumber2.getText());
        p.setEmail(tfEmail.getText());
        p.setEmergencyContact(taEmergencyContact.getText());

        if (rbInThailand.isSelected())
        {
            p.setStatus(AppConstants.STATUS_THAILAND);
        }
        else if (rbAbroad.isSelected())
        {
            p.setStatus(AppConstants.STATUS_ABROAD);
        }
        else
        {
            p.setStatus(AppConstants.STATUS_INACTIVE);
        }

        p.setRemark(taRemark.getText());

        //if no field caused errors
        if (!error)
        {
            operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().update(p);
            //if the update operation was successful
            if (operationStatus == 0)
            {
                //if the nickname was changed refresh nickname list
                if (!previousNickName.equals(newNickName))
                {
                    CtrFileOperation.renameProfileFolder(previousNickName, newNickName);
                    ctrGUIMain.getCtrPaneSelection().reloadNicknameList(newNickName);
                }
                initNaktamCertificateButtons(p);
                ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
                CtrAlertDialog.infoDialog("Profile Updated", "The monastic profile information was successfully updated");
            }
            return operationStatus;
        }
        return 0;
    }

}
