/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.gui.panel;

import org.watmarpjan.visaManager.gui.intface.ICreateEditGUIForm;
import org.watmarpjan.visaManager.gui.intface.IFormMonasticProfile;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.gui.util.ImgUtil;
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

/**
 *
 * @author WMJ_user
 */
public class CtrPaneMonasticProfile extends AbstractChildPaneController implements IFormMonasticProfile, ICreateEditGUIForm
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

    @FXML
    private TextField tfEmail;
    @FXML
    private TextArea taEmergencyContact;
    
    @FXML
    private TextArea taPhoneNumber;
    
    @FXML
    private TextArea taRemark;
    

    @FXML
    private Button bChangeProfilePhoto;

    @FXML
    private ImageView ivProfilePhoto;

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
        listFields.add(tfBirthCountry);
        listFields.add(tfBirthPlace);
        listFields.add(tfEthnicity);
        listFields.add(tfNationality);
        listFields.add(cbOccupation);
        listFields.add(cbOccupationThai);
        listFields.add(cbCertificate);
        listFields.add(cbCertificateThai);
        listFields.add(tfSchool);
        listFields.add(tfGraduationYear);
        listFields.add(tfDuration);
        listFields.add(tfPreviousResidenceCountry);
        listFields.add(cbResidingAt);
        listFields.add(tfAdviserToCome);
        listFields.add(cbAdvisorWat);
        listFields.add(tgDhammaStudies);
        listFields.add(tfEmail);
        listFields.add(taEmergencyContact);
        listFields.add(taPhoneNumber);
        listFields.add(taRemark);
        ctrGUIMain.getCtrFieldChangeListener().registerChangeListener(listFields);

        loadContentsCBWat();
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
        loadContentsCBOccupation();
        loadContentsCBCertificate();
        loadContentsCBWat();

        loadProfilePhoto(p);
        if (p != null)
        {
            tfNickname.setText(p.getNickname());

            tfName.setText(p.getMonasticName());
            tfMiddleName.setText(p.getMiddleName());
            tfLastName.setText(p.getLastName());

            tfMother.setText(p.getMotherName());
            tfFather.setText(p.getFatherName());

            tfPreviousResidenceCountry.setText(p.getPreviousResidenceCountry());
            tfBirthCountry.setText(p.getBirthCountry());
            tfBirthPlace.setText(p.getBirthPlace());

            dpBirthDate.setValue(Util.convertDateToLocalDate(p.getBirthDate()));
            tfAge.setText(ProfileUtil.getStrAge(p.getBirthDate()) + "");
            tfBirthWeekday.setText(ProfileUtil.getShortenedBirthWeekDay(p.getBirthDate()));

            tfPreviousResidenceCountry.setText(p.getPreviousResidenceCountry());
            tfNationality.setText(p.getNationality());
            tfEthnicity.setText(p.getEthnicity());

            cbOccupation.setValue(p.getOccupationEnglish());
            cbOccupationThai.setValue(p.getOccupationThai());
            tfSchool.setText(p.getSchool());
            cbCertificate.setValue(p.getCertificateEnglish());
            cbCertificateThai.setValue(p.getCertificateThai());
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

            tfAdviserToCome.setText(p.getNameAdviserToCome());

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

            taPhoneNumber.setText(p.getPhoneNumber());
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

        ImgUtil.loadImageView(ivProfilePhoto, ImgUtil.IMG_TYPE_PROFILE, fileProfilePhoto);
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
            loadProfilePhoto(p);
        }

    }

    @Override
    public void actionAddNew()
    {
        String nickNameNewProfile;
        nickNameNewProfile = ctrGUIMain.getCtrMain().getCtrProfile().addProfile();
        if (nickNameNewProfile != null)
        {
            ctrGUIMain.getCtrPaneSelection().reloadNicknameList(nickNameNewProfile);
        }
    }

    private void loadContentsCBWat()
    {
        ArrayList<String> alWatList;

        alWatList = ctrGUIMain.getCtrMain().getCtrMonastery().loadMonasteryList();

        cbResidingAt.getItems().clear();
        cbAdvisorWat.getItems().clear();
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
        bChangeProfilePhoto.setDisable(true);
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
        taPhoneNumber.setEditable(false);
        taRemark.setEditable(false);
    }

    @Override
    public void actionUnlockEdit()
    {
        bChangeProfilePhoto.setDisable(false);
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
        
        taPhoneNumber.setEditable(true);
        taRemark.setEditable(true);
    }

    @Override
    public void actionSave()
    {
        MonasticProfile p;
        int operationStatus, gradYear;
        boolean error;
        Monastery wResidingAt, wAdviserToCome;
        Date birthDate;
        String previousNickName, newNickName;

        error = false;
        p = ctrGUIMain.getCtrPaneSelection().getSelectedProfile();
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

        //if the duration changed and the text field is not empty
        if ((!tfDuration.getText().equals(p.getCertificateDuration())
                && (!tfDuration.getText().equals(""))))
        {
            try
            {
                p.setCertificateDuration(parseInt(tfDuration.getText()));
            } catch (NumberFormatException nfe)
            {
                CtrAlertDialog.errorDialog("Invalid number for 'Certificate Duration'");
                error = true;
            }
        }
        else
        {
            p.setCertificateDuration(null);
        }

        //if the graduation year changed and the text field is not empty
        if ((!tfGraduationYear.getText().equals(p.getCertificateGradYear()))
                && (!tfGraduationYear.getText().equals("")))
        {
            try
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

            } catch (NumberFormatException nfe)
            {

                CtrAlertDialog.errorDialog("Invalid number for 'Graduation Year'");
                error = true;
            }
        }
        else
        {
            p.setCertificateGradYear(null);
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
        
        p.setPhoneNumber(taPhoneNumber.getText());
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
            operationStatus = ctrGUIMain.getCtrMain().getCtrProfile().updateProfile(p);
            //if the update operation was successful
            if (operationStatus == 0)
            {
                //if the nickname was changed refresh nickname list
                if (!previousNickName.equals(newNickName))
                {
                    ctrGUIMain.getCtrPaneSelection().reloadNicknameList(newNickName);
                    CtrFileOperation.renameProfileFolder(previousNickName, newNickName);
                }
                ctrGUIMain.getCtrFieldChangeListener().resetUnsavedChanges();
                CtrAlertDialog.infoDialog("Profile Updated", "The monastic profile information was successfully updated");
            }
        }
    }

}
