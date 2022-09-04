/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.formFiller;

import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.util.Matrix;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.control.CtrMain;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.Upajjhaya;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author raryel
 */
public abstract class PrawatFiller extends PDFFormFiller
{
    protected LocalDate ldVisaExpiry;

    public PrawatFiller(CtrMain ctrMain, MonasticProfile p)
    {
        super(ctrMain);
        File sourceFormFile;
        
        if ((p.getPatimokkhaChanter()== null) || (!p.getPatimokkhaChanter()))
        {
            sourceFormFile = AppFiles.getFormPrawat(p.getMonasteryResidingAt().getMonasteryNickname());
        }
        else
        {
            sourceFormFile = AppFiles.getFormPrawatPatimokkhaChanter(p.getMonasteryResidingAt().getMonasteryNickname());
        }
        
        super.init(sourceFormFile);
        
        try
        {
            fillFormData(p);
        }
        catch(IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error while filling Prawat form!");
        }
        
    }
    
    private void addProfilePhotoPrawat(PDDocument pdfDoc, MonasticProfile p) throws IOException
    {
        File fPhoto;
        PDImageXObject pdImage;
        PDPageContentStream contentStream;

        fPhoto = AppFiles.getProfilePhoto(p.getNickname());
        if (fPhoto.exists())
        {
            pdImage = PDImageXObject.createFromFile(fPhoto.toString(), pdfDoc);
            contentStream = new PDPageContentStream(pdfDoc, pdfDoc.getPage(0), PDPageContentStream.AppendMode.APPEND, true);

            //translation, rotation and scale for the image
            AffineTransform at = new AffineTransform(pdImage.getWidth() * 0.24, 0, 0, pdImage.getHeight() * 0.24, 418, 645);

            //rotates the image overlay 90 degree because the document is landscape
            Matrix tMatrix = new Matrix(at);

            contentStream.drawImage(pdImage, tMatrix);
            contentStream.close();
        }
    }

    @Override
    protected void fillFormData(MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields = new ArrayList<>();
        Monastery mOrdainedAt, mUpajjhaya, mAdviserToCome, mResidingAt, mJaoKanaAmpher, mJaoKanaJangwat, mResidence;
        Upajjhaya u;
        Date dVisaExpiry;
        
        String str_jangwat_country;
        String abbotName;

        addProfilePhotoPrawat(this.pdfDocument, p);

        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("paliNameThai"));
        alThaiFields.add((PDTextField) acroForm.getField("occupationThai"));
        alThaiFields.add((PDTextField) acroForm.getField("ordinationDateThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watOrdainedAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("upajjhayaThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watUpajjhayaThai"));

        alThaiFields.add((PDTextField) acroForm.getField("nameAdviserToComeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watAdviserToComeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrNumberWatAdviserToComeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatAdviserToComeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatAdviserToComeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatAdviserToComeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatAdviserToComeThai_addrCountryWatAdviserToComeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watResidingAtThai"));

        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));

        alThaiFields.add((PDTextField) acroForm.getField("certificateThai"));

        alThaiFields.add((PDTextField) acroForm.getField("nameAbbotWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherJaoKanaAmpherThai_addrJangwatJaoKanaAmpherThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watJaoKanaAmpherThai"));

        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatJaoKanaJangwatThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watJaoKanaJangwatThai"));

        alThaiFields.add((PDTextField) acroForm.getField("dhammaStudiesThaiPDF1"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension01Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension02Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension03Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension04Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension05Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension06Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension07Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension08Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension09Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaExtension10Thai"));
        alThaiFields.add((PDTextField) acroForm.getField("visaType"));
        
//        alThaiFields.add((PDTextField) acroForm.getField("dhammaStudiesThaiPDF2"));
//        alThaiFields.add((PDTextField) acroForm.getField("dhammaStudiesThaiPDF3"));
        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("fullName").setValue(ProfileUtil.getFullName(p));
        acroForm.getField("paliNameThai").setValue(p.getPaliNameThai());
        acroForm.getField("age").setValue(ProfileUtil.getStrAge(p.getBirthDate()));
        acroForm.getField("ethnicity").setValue(p.getEthnicity());
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("previousResidenceCountry").setValue(p.getPreviousResidenceCountry());
        acroForm.getField("birthPlace_birthCountry").setValue(p.getBirthPlace() + ", " + p.getBirthCountry());
        acroForm.getField("occupationThai").setValue(p.getOccupationThai());
        acroForm.getField("fatherName").setValue(p.getFatherName());
        acroForm.getField("motherName").setValue(p.getMotherName());

        acroForm.getField("ordinationDateThai").setValue(ProfileUtil.getStrOrdinationDatePrawat(p));
        mOrdainedAt = p.getMonasteryOrdainedAt();
        if (mOrdainedAt != null)
        {
            acroForm.getField("watOrdainedAtThai").setValue(p.getMonasteryOrdainedAt().getMonasteryName());
        }

        u = p.getUpajjhaya();
        if (u != null)
        {
            acroForm.getField("upajjhayaThai").setValue(p.getUpajjhaya().getUpajjhayaName());
            mUpajjhaya = u.getMonastery();
            if (mUpajjhaya != null)
            {
                acroForm.getField("watUpajjhayaThai").setValue(u.getMonastery().getMonasteryName());
            }
        }

        acroForm.getField("firstEntryDate").setValue(Util.toStringThaiDateFormat(p.getFirstEntryDate()));
        acroForm.getField("arrivalLastEntryDate").setValue(Util.toStringThaiDateFormat(p.getArrivalLastEntryDate()));

        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("visaType").setValue(p.getVisaType());
        acroForm.getField("passportIssuedAt").setValue(p.getPassportIssuedAt());
        acroForm.getField("passportIssueDate").setValue(Util.toStringThaiDateFormat(p.getPassportIssueDate()));
        acroForm.getField("passportExpiryDate").setValue(Util.toStringThaiDateFormat(p.getPassportExpiryDate()));

        acroForm.getField("nameAdviserToComeThai").setValue(p.getNameAdviserToCome());

        mAdviserToCome = p.getMonasteryAdviserToCome();
        if (mAdviserToCome != null)
        {
            acroForm.getField("watAdviserToComeThai").setValue(mAdviserToCome.getMonasteryName());
            acroForm.getField("addrNumberWatAdviserToComeThai").setValue(mAdviserToCome.getAddrNumber());
            acroForm.getField("addrRoadWatAdviserToComeThai").setValue(mAdviserToCome.getAddrRoad());
            acroForm.getField("addrTambonWatAdviserToComeThai").setValue(mAdviserToCome.getAddrTambon());
            acroForm.getField("addrAmpherWatAdviserToComeThai").setValue(mAdviserToCome.getAddrAmpher());

            if (mAdviserToCome.getAddrCountry() != null
                    && !mAdviserToCome.getAddrCountry().equals(AppConstants.COUNTRY_THAILAND))
            {
                str_jangwat_country = mAdviserToCome.getAddrJangwat() + ", " + mAdviserToCome.getAddrCountry();
            }
            else
            {
                str_jangwat_country = mAdviserToCome.getAddrJangwat();
            }
            acroForm.getField("addrJangwatWatAdviserToComeThai_addrCountryWatAdviserToComeThai").setValue(str_jangwat_country);
        }

        this.ldVisaExpiry = ProfileUtil.getVisaOrExtExpiryDate(p);
        acroForm.getField("visaExpiryDate").setValue(Util.toStringThaiDateFormat(ldVisaExpiry));

        

        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {
            acroForm.getField("watResidingAtThai").setValue(mResidingAt.getMonasteryName());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
        }

        acroForm.getField("baisuddhiIssueDate").setValue(Util.toStringThaiDateFormat(p.getBysuddhiIssueDate()));
        acroForm.getField("certificateGradYear").setValue("" + Util.convertYearToThai(p.getCertificateGradYear()));

        acroForm.getField("school").setValue(p.getSchool());
        acroForm.getField("certificateDuration").setValue(p.getCertificateDuration() + "");
        acroForm.getField("certificateThai").setValue(p.getCertificateThai());
        acroForm.getField("certificateGradYear").setValue("" + Util.convertYearToThai(p.getCertificateGradYear()));

        if (!p.getDhammaStudies().equals(AppConstants.STUDIES_REGULAR))
        {
            ((PDCheckBox) acroForm.getField("buddhistStudiesDhammaPDF")).check();
            if (p.getDhammaStudies().equals(AppConstants.STUDIES_NAKTAM_TRI))
            {
                acroForm.getField("dhammaStudiesThaiPDF1").setValue("นักธรรมตรี");
            }
            if (p.getDhammaStudies().equals(AppConstants.STUDIES_NAKTAM_TOH))
            {
                acroForm.getField("dhammaStudiesThaiPDF1").setValue("นักธรรมโท");
            }
            if (p.getDhammaStudies().equals(AppConstants.STUDIES_NAKTAM_EK))
            {
                acroForm.getField("dhammaStudiesThaiPDF1").setValue("นักธรรมเอก");
            }

        }
        
        mResidence = p.getMonasteryResidingAt();
        abbotName = mResidence.getAbbotName();
        
        if (abbotName != null)
        {
            acroForm.getField("nameAbbotWatResidingAtThai").setValue(abbotName);
        }
        
        mJaoKanaAmpher = ctrMain.getCtrMonastery().loadMonasteryJaoKanaAmpher(mResidence);
        if (mJaoKanaAmpher != null)
        {
            acroForm.getField("addrAmpherJaoKanaAmpherThai_addrJangwatJaoKanaAmpherThai").setValue(mJaoKanaAmpher.getAddrAmpher() + " " + mJaoKanaAmpher.getAddrJangwat());
            acroForm.getField("watJaoKanaAmpherThai").setValue(mJaoKanaAmpher.getMonasteryName());
        }

        mJaoKanaJangwat = ctrMain.getCtrMonastery().loadMonasteryJaoKanaJangwat(mResidence);
        if (mJaoKanaJangwat != null)
        {
            acroForm.getField("addrJangwatJaoKanaJangwatThai").setValue(mJaoKanaJangwat.getAddrJangwat());
            acroForm.getField("watJaoKanaJangwatThai").setValue(mJaoKanaJangwat.getMonasteryName());
        }
    }
    
    protected void fillVisaExpiryDateDesired(LocalDate ldDesiredDate) throws IOException
    {
        acroForm.getField("visaExpiryDateDesired").setValue(Util.toStringThaiDateFormat(ldDesiredDate));
    }
}
