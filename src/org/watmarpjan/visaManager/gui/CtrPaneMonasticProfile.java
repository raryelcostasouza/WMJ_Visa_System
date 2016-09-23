/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
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
import org.watmarpjan.visaManager.model.hibernate.Profile;
import javafx.scene.Node;
import org.watmarpjan.visaManager.AppConstants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrFileOperation;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import static java.lang.Integer.parseInt;

/**
 *
 * @author WMJ_user
 */
public class CtrPaneMonasticProfile extends AbstractChildPaneController implements IFormMonasticProfile, IEditableGUIForm
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
    private TextField tfPreviousResidenceCountry;

    @FXML
    private TextField tfBirthCountry;
    @FXML
    private TextField tfBirthPlace;
    @FXML
    private TextField tfNationality;
    @FXML
    private TextField tfEthnicity;

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
    private TextField tfAdviserToCome;
    @FXML
    private ComboBox<String> cbAdvisorWat;

    @FXML
    private ToggleGroup tgStatus;
    @FXML
    private RadioButton rbInThailand;
    @FXML
    private RadioButton rbAbroad;
    @FXML
    private RadioButton rbInactive;

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

    private static final String STUDIES_REGULAR = "Regular";
    private static final String STUDIES_NAKTAM_TRI = "Naktam Tri";
    private static final String STUDIES_NAKTAM_TOH = "Naktam Toh";
    private static final String STUDIES_NAKTAM_EK = "Naktam Ek";

    @FXML
    private TextField tfEmail;
    @FXML
    private TextArea taEmergencyContact;

    @FXML
    private Button bNewProfile;
    @FXML
    private Button bChangeProfilePhoto;

    @FXML
    private ImageView ivProfilePhoto;

    private ArrayList<Node> fields;

    @Override
    public void init()
    {
        ctrGUIMain.getCtrDatePicker().registerDatePicker(dpBirthDate);

        initChangeListener();
        loadContentsCBWat();
    }

    @Override
    public boolean isSelectionEmpty()
    {
        return ctrGUIMain.getPaneSelectionController().isSelectionEmpty();
    }

    private void initChangeListener()
    {
        fields = new ArrayList<>();
        fields.add(tfNickname);
        fields.add(tfMiddleName);
        fields.add(tfLastName);

        for (Node field : fields)
        {
            if (field instanceof TextField)
            {
                ((TextField) field).textProperty().addListener(new ChangeListener<String>()
                {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                    {
                        ctrGUIMain.setContainsUnsavedChanges(true);
                    }
                });
            }
        }
    }

    @Override
    public void fillData(Profile p)
    {
        Period age;
        LocalDate birthDate;

        loadContentsCBOccupation();
        loadContentsCBCertificate();

        tfNickname.setText(p.getNickname());

        loadProfilePhoto(p.getNickname());

        tfName.setText(p.getName());
        tfMiddleName.setText(p.getMiddleName());
        tfLastName.setText(p.getLastName());

        tfMother.setText(p.getMotherName());
        tfFather.setText(p.getFatherName());

        tfPreviousResidenceCountry.setText(p.getPreviousResidenceCountry());
        tfBirthCountry.setText(p.getBirthCountry());
        tfBirthPlace.setText(p.getBirthPlace());

        if (p.getBirthDate() != null)
        {
            birthDate = Util.convertDateToLocalDate(p.getBirthDate());
            dpBirthDate.setValue(birthDate);
            age = Period.between(birthDate, LocalDate.now());
            tfAge.setText(age.getYears() + "");
            tfBirthWeekday.setText((String) birthDate.getDayOfWeek().toString().subSequence(0, 3));

        } else
        {
            dpBirthDate.setValue(null);
        }

        tfPreviousResidenceCountry.setText(p.getPreviousResidenceCountry());
        tfNationality.setText(p.getNationality());
        tfEthnicity.setText(p.getEthnicity());

        cbOccupation.setValue(p.getOccupationEnglish());
        cbOccupationThai.setValue(p.getOccupationThai());
        tfSchool.setText(p.getSchool());
        cbCertificate.setValue(p.getCertificateEnglish());
        cbCertificateThai.setValue(p.getCertificateThai());
        tfDuration.setText(p.getCertificateDuration() + "");
        tfGraduationYear.setText(p.getCertificateGradYear() + "");

        if (p.getMonasteryResidingAt() != null)
        {
            cbResidingAt.setValue(p.getMonasteryResidingAt().getName());
        } else
        {
            cbResidingAt.setValue(null);
        }

        if (p.getMonasteryAdviserToCome() != null)
        {
            cbAdvisorWat.setValue(p.getMonasteryAdviserToCome().getName());
        } else
        {
            cbAdvisorWat.setValue(null);
        }

        tfAdviserToCome.setText(p.getNameAdviserToCome());

        switch (p.getDhammaStudies())
        {
            case STUDIES_NAKTAM_TRI:
                rbDhammaStudiesNaktamTri.setSelected(true);
                break;
            case STUDIES_NAKTAM_TOH:
                rbDhammaStudiesNaktamToh.setSelected(true);
                break;
            case STUDIES_NAKTAM_EK:
                rbDhammaStudiesNaktamEk.setSelected(true);
                break;
            default:
                rbDhammaStudiesRegular.setSelected(true);
                break;
        }

        tfEmail.setText(p.getEmail());
        taEmergencyContact.setText(p.getEmergencyContact());
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
    }

    private void loadProfilePhoto(String nickName)
    {
        File fileProfilePhoto;

        fileProfilePhoto = AppFiles.getProfilePhoto(nickName);

        ImgUtil.loadImageView(ivProfilePhoto, ImgUtil.IMG_TYPE_PROFILE, fileProfilePhoto);
    }

    @FXML
    void actionChooseProfilePhoto(ActionEvent ae)
    {
        Profile p;
        File fDestination;
        File fSource;

        p = ctrGUIMain.getPaneSelectionController().getSelectedProfile();
        fDestination = AppFiles.getProfilePhoto(p.getNickname());
        fSource = CtrFileOperation.selectFile("Select Profile Photo", CtrFileOperation.FILE_CHOOSER_TYPE_JPG);

        if (fSource != null)
        {
            CtrFileOperation.copyOperation(fSource, fDestination);
            loadProfilePhoto(p.getNickname());
        }

    }

    @FXML
    void actionNewProfileButton(ActionEvent ae)
    {
        String nickNameNewProfile;
        nickNameNewProfile = ctrGUIMain.getCtrMain().getCtrProfile().addProfile();
        if (nickNameNewProfile != null)
        {
            ctrGUIMain.getPaneSelectionController().reloadNicknameList(nickNameNewProfile);
            //CtrFileOperation.createProfileFolderStructure(ctrGUIMain.getPaneSelectionController().getIDSelectedProfile());
        }
    }

    private void loadContentsCBWat()
    {
        ArrayList<String> alWatList;

        alWatList = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryList();

        cbResidingAt.getItems().addAll(alWatList);
        cbAdvisorWat.getItems().addAll(alWatList);
    }

    private void loadContentsCBOccupation()
    {
        ArrayList<String> alOccupationEnglish, alOccupationThai;

        alOccupationEnglish = ctrGUIMain.getCtrMain().getCtrProfile().loadOccupationEnglishList();
        alOccupationThai = ctrGUIMain.getCtrMain().getCtrProfile().loadOccupationThaiList();

        //if the list is not empty clear before re-adding items
        if (!cbOccupation.getItems().isEmpty())
        {
            cbOccupation.getItems().clear();
        }

        if (!cbOccupationThai.getItems().isEmpty())
        {
            cbOccupationThai.getItems().clear();
        }

        cbOccupation.getItems().addAll(alOccupationEnglish);
        cbOccupationThai.getItems().addAll(alOccupationThai);
    }

    private void loadContentsCBCertificate()
    {
        ArrayList<String> alCertificateEnglish, alCertificateThai;
        alCertificateEnglish = ctrGUIMain.getCtrMain().getCtrProfile().loadCertificateEngList();
        alCertificateThai = ctrGUIMain.getCtrMain().getCtrProfile().loadCertificateThaiList();

        //if the list is not empty clear before re-adding items
        if (!cbCertificate.getItems().isEmpty())
        {
            cbCertificate.getItems().clear();
        }

        if (!cbCertificateThai.getItems().isEmpty())
        {
            cbCertificateThai.getItems().clear();
        }

        cbCertificate.getItems().addAll(alCertificateEnglish);
        cbCertificateThai.getItems().addAll(alCertificateThai);

    }

    @Override
    public void actionLockEdit()
    {
        tfNickname.setEditable(false);
        tfName.setEditable(false);
        tfMiddleName.setEditable(false);
        tfLastName.setEditable(false);
        tfMother.setEditable(false);
        tfFather.setEditable(false);

        dpBirthDate.setDisable(true);

        tfPreviousResidenceCountry.setEditable(false);
        tfBirthCountry.setEditable(false);
        tfBirthPlace.setEditable(false);

        tfNationality.setEditable(false);
        tfEthnicity.setEditable(false);

        cbOccupation.setDisable(true);
        cbOccupationThai.setDisable(true);
        cbCertificate.setDisable(true);
        cbCertificateThai.setDisable(true);

        tfSchool.setEditable(false);
        tfDuration.setEditable(false);
        tfGraduationYear.setEditable(false);

        cbResidingAt.setDisable(true);

        tfAdviserToCome.setEditable(false);
        cbAdvisorWat.setDisable(true);

        rbInThailand.setDisable(true);
        rbAbroad.setDisable(true);
        rbInactive.setDisable(true);

        rbDhammaStudiesRegular.setDisable(true);
        rbDhammaStudiesNaktamTri.setDisable(true);
        rbDhammaStudiesNaktamToh.setDisable(true);
        rbDhammaStudiesNaktamEk.setDisable(true);

        tfEmail.setEditable(false);
        taEmergencyContact.setEditable(false);

        bNewProfile.setDisable(true);
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

        tfPreviousResidenceCountry.setEditable(true);
        tfBirthCountry.setEditable(true);
        tfBirthPlace.setEditable(true);

        tfNationality.setEditable(true);
        tfEthnicity.setEditable(true);

        cbOccupation.setDisable(false);
        cbOccupationThai.setDisable(false);
        cbCertificate.setDisable(false);
        cbCertificateThai.setDisable(false);

        tfSchool.setEditable(true);
        tfDuration.setEditable(true);
        tfGraduationYear.setEditable(true);

        cbResidingAt.setDisable(false);

        tfAdviserToCome.setEditable(true);
        cbAdvisorWat.setDisable(false);

        rbInThailand.setDisable(false);
        rbAbroad.setDisable(false);
        rbInactive.setDisable(false);

        rbDhammaStudiesRegular.setDisable(false);
        rbDhammaStudiesNaktamTri.setDisable(false);
        rbDhammaStudiesNaktamToh.setDisable(false);
        rbDhammaStudiesNaktamEk.setDisable(false);

        tfEmail.setEditable(true);
        taEmergencyContact.setEditable(true);

        bNewProfile.setDisable(false);
    }

    @Override
    public void actionSave()
    {
        Profile p;
        int operationStatus;
        Monastery wResidingAt, wAdviserToCome;
        Date birthDate;
        String previousNickName, newNickName;

        p = ctrGUIMain.getPaneSelectionController().getSelectedProfile();
        previousNickName = p.getNickname();
        newNickName = tfNickname.getText();

        p.setNickname(tfNickname.getText());
        p.setName(tfName.getText());
        p.setMiddleName(tfMiddleName.getText());
        p.setLastName(tfLastName.getText());

        p.setMotherName(tfMother.getText());
        p.setFatherName(tfFather.getText());

        birthDate = Util.convertLocalDateToDate(dpBirthDate.getValue());

        p.setBirthDate(birthDate);
        p.setPreviousResidenceCountry(tfPreviousResidenceCountry.getText());
        p.setBirthCountry(tfBirthCountry.getText());
        p.setBirthPlace(tfBirthPlace.getText());
        p.setNationality(tfNationality.getText());
        p.setNationality(tfNationality.getText());
        p.setEthnicity(tfEthnicity.getText());

        p.setOccupationEnglish(cbOccupation.getValue());
        p.setOccupationThai(cbOccupationThai.getValue());
        p.setSchool(tfSchool.getText());
        p.setCertificateEnglish(cbCertificate.getValue());

        p.setCertificateThai(cbCertificateThai.getValue());

        try
        {
            p.setCertificateDuration(parseInt(tfDuration.getText()));
        } catch (NumberFormatException nfe)
        {
            CtrAlertDialog.errorDialog("Invalid number for 'Certificate Duration'");
        }

        try
        {
            p.setCertificateGradYear(parseInt(tfGraduationYear.getText()));

        } catch (NumberFormatException nfe)
        {
            CtrAlertDialog.errorDialog("Invalid number for 'Graduation Year'");
        }

        if (cbResidingAt.getValue() != null)
        {
            wResidingAt = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryByName(cbResidingAt.getValue());
            p.setMonasteryResidingAt(wResidingAt);
        }

        if (cbAdvisorWat.getValue() != null)
        {
            wAdviserToCome = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryByName(cbAdvisorWat.getValue());
            p.setMonasteryAdviserToCome(wAdviserToCome);
        }

        p.setNameAdviserToCome(tfAdviserToCome.getText());

        if (rbDhammaStudiesNaktamTri.isSelected())
        {
            p.setDhammaStudies(STUDIES_NAKTAM_TRI);
        } else if (rbDhammaStudiesNaktamToh.isSelected())
        {
            p.setDhammaStudies(STUDIES_NAKTAM_TOH);
        } else if (rbDhammaStudiesNaktamEk.isSelected())
        {
            p.setDhammaStudies(STUDIES_NAKTAM_EK);
        } else
        {
            p.setDhammaStudies(STUDIES_REGULAR);
        }

        p.setEmail(tfEmail.getText());
        p.setEmergencyContact(taEmergencyContact.getText());

        if (rbInThailand.isSelected())
        {
            p.setStatus(AppConstants.STATUS_THAILAND);
        } else if (rbAbroad.isSelected())
        {
            p.setStatus(AppConstants.STATUS_ABROAD);
        } else
        {
            p.setStatus(AppConstants.STATUS_INACTIVE);
        }

        //if the update operation was successful
        operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
        if (operationStatus == 0)
        {
            //if the nickname was changed refresh nickname list
            if (!previousNickName.equals(newNickName))
            {
                ctrGUIMain.getPaneSelectionController().reloadNicknameList(newNickName);
                CtrFileOperation.renameProfileFolder(previousNickName, newNickName);
            }
            ctrGUIMain.setContainsUnsavedChanges(false);
            CtrAlertDialog.infoDialog("Profile Updated", "The monastic profile information was successfully updated");

        }
    }

}
