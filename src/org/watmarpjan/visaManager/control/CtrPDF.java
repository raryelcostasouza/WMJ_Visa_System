/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PageRanges;
import org.apache.pdfbox.cos.COSName;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.util.Matrix;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.dueTask.EntryDueTask;
import org.watmarpjan.visaManager.model.eps.ExtraPassportScanLoaded;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.model.hibernate.Upajjhaya;
import org.watmarpjan.visaManager.model.hibernate.VisaExtension;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrPDF
{

    private CtrMain ctrMain;

    public static final int OPTION_PRINT_FORM = 0;
    public static final int OPTION_PREVIEW_FORM = 1;
    public static final int ORIENTATION_PORTRAIT = 0;
    public static final int ORIENTATION_LANDSCAPE = 1;

    public static final String DESTINATION_SAMNAKPUT = "SNP";
    public static final String DESTINATION_IMMIGRATION = "IMM";

    private final String MSG_ERROR = "Error while generating PDF form.";

    private COSName loadedThaiFontName = null;

    //Passport Scan Real size 170mm x 125mm
    //Converts the passport scan width to pixels
    //Passport Scan real Width  170mm
    //A4 Width pixel size: PDRectangle.A4.getWidth() 
    //A4 Width real size 210mm
    private final float DEFAULT_HEIGHT_PASSPORT_SCAN_PX = (PDRectangle.A4.getHeight() * 125) / 297.0f;

    //Converts the passport scan width to pixels
    //Passport Scan real Width  125mm
    //A4 Height pixel size: PDRectangle.A4.getWidth() 
    //A4 Height real size 297mm
    private final float DEFAULT_WIDTH_PASSPORT_SCAN_PX = (PDRectangle.A4.getWidth() * 170) / 210.0f;

    private final float PAGE_A4_HEIGHT_PX = PDRectangle.A4.getHeight();
    private final float PAGE_A4_WIDTH_PX = PDRectangle.A4.getWidth();

    private final float SCALE_DUE_TASKS_SNAPSHOT = 0.3f;

    public CtrPDF(CtrMain pCtrMain)
    {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        this.ctrMain = pCtrMain;
    }

    private void fillPrawat(PDDocument pdfDoc, PDAcroForm acroForm, MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields = new ArrayList<>();
        Monastery mOrdainedAt, mUpajjhaya, mAdviserToCome, mResidingAt, mJaoKanaAmpher, mJaoKanaJangwat;
        Upajjhaya u;
        LocalDate ldVisaExpiryDateDesired, ldVisaExpiry;
        Date dVisaExpiry;
        String str_jangwat_country;

        addProfilePhotoPrawat(pdfDoc, p);

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

        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherJaoKanaAmpherThai_addrJangwatJaoKanaAmpherThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watJaoKanaAmpherThai"));

        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatJaoKanaJangwatThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watJaoKanaJangwatThai"));

        alThaiFields.add((PDTextField) acroForm.getField("dhammaStudiesThaiPDF1"));
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

        //if the visa for this monastic has already been extended
        //retrieves the expiry date of the most recent extension
        if (p.getVisaExtensionSet() != null && !p.getVisaExtensionSet().isEmpty())
        {
            dVisaExpiry = ctrMain.getCtrVisa().getLastExtension(p).getExpiryDate();
            ldVisaExpiry = Util.convertDateToLocalDate(dVisaExpiry);
        } //otherwise retrieves the expiry date of the original visa
        else
        {
            ldVisaExpiry = Util.convertDateToLocalDate(p.getVisaExpiryDate());
        }

        acroForm.getField("visaExpiryDate").setValue(Util.toStringThaiDateFormat(ldVisaExpiry));

        if (ldVisaExpiry != null)
        {
            ldVisaExpiryDateDesired = ldVisaExpiry.plusYears(1);
            acroForm.getField("visaExpiryDateDesired").setValue(Util.toStringThaiDateFormat(ldVisaExpiryDateDesired));
        }

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

        mJaoKanaAmpher = ctrMain.getCtrMonastery().loadMonasteryJaoKanaAmpher();
        if (mJaoKanaAmpher != null)
        {
            acroForm.getField("addrAmpherJaoKanaAmpherThai_addrJangwatJaoKanaAmpherThai").setValue(mJaoKanaAmpher.getAddrAmpher() + " " + mJaoKanaAmpher.getAddrJangwat());
            acroForm.getField("watJaoKanaAmpherThai").setValue(mJaoKanaAmpher.getMonasteryName());
        }

        mJaoKanaJangwat = ctrMain.getCtrMonastery().loadMonasteryJaoKanaJangwat();
        if (mJaoKanaJangwat != null)
        {
            acroForm.getField("addrJangwatJaoKanaJangwatThai").setValue(mJaoKanaJangwat.getAddrJangwat());
            acroForm.getField("watJaoKanaJangwatThai").setValue(mJaoKanaJangwat.getMonasteryName());
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

    private void fillTM7ReqExtension(PDAcroForm acroForm, MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        LocalDate ldPassportIssue, ldPassportExp, ldBirthDate, ldLastEntry;
        Monastery mResidingAt;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrNumberWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));

        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("lastName").setValue(p.getLastName());
        acroForm.getField("name").setValue(p.getMonasticName());
        if (p.getMiddleName() != null)
        {
            acroForm.getField("middleName").setValue(p.getMiddleName());
        }

        acroForm.getField("age").setValue(ProfileUtil.getStrAge(p.getBirthDate()));

        ldBirthDate = Util.convertDateToLocalDate(p.getBirthDate());
        if (ldBirthDate != null)
        {
            acroForm.getField("birthDateDay").setValue(ldBirthDate.getDayOfMonth() + "");
            acroForm.getField("birthDateMonth").setValue(ldBirthDate.getMonthValue() + "");
            acroForm.getField("birthDateYear").setValue(Util.convertYearToThai(ldBirthDate.getYear()) + "");
        }

        acroForm.getField("birthPlace_birthCountry").setValue(p.getBirthPlace() + ", " + p.getBirthCountry());
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());

        ldPassportIssue = Util.convertDateToLocalDate(p.getPassportIssueDate());
        acroForm.getField("passportIssueDateDay").setValue(ldPassportIssue.getDayOfMonth() + "");
        acroForm.getField("passportIssueDateMonth").setValue(ldPassportIssue.getMonthValue() + "");
        acroForm.getField("passportIssueDateYear").setValue(Util.convertYearToThai(ldPassportIssue.getYear()) + "");
        acroForm.getField("passportIssuedAt").setValue(p.getPassportIssuedAt());

        ldPassportExp = Util.convertDateToLocalDate(p.getPassportExpiryDate());
        if (ldPassportExp != null)
        {
            acroForm.getField("passportExpiryDateDay").setValue(ldPassportExp.getDayOfMonth() + "");
            acroForm.getField("passportExpiryDateMonth").setValue(ldPassportExp.getMonthValue() + "");
            acroForm.getField("passportExpiryDateYear").setValue(Util.convertYearToThai(ldPassportExp.getYear()) + "");
        }

        acroForm.getField("visaType").setValue(p.getVisaType());
        acroForm.getField("arrivalTravelBy").setValue(p.getArrivalTravelBy());
        acroForm.getField("arrivalTravelFrom").setValue(p.getArrivalTravelFrom());
        acroForm.getField("arrivalPortOfEntry").setValue(p.getArrivalPortOfEntry());

        ldLastEntry = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
        if (ldLastEntry != null)
        {
            acroForm.getField("arrivalLastEntryDateDay").setValue(ldLastEntry.getDayOfMonth() + "");
            acroForm.getField("arrivalLastEntryDateMonth").setValue(ldLastEntry.getMonthValue() + "");
            acroForm.getField("arrivalLastEntryDateYear").setValue(Util.convertYearToThai(ldLastEntry.getYear()) + "");
        }

        acroForm.getField("departureCardNumber").setValue(p.getArrivalCardNumber());

        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {
            acroForm.getField("watResidingAtThai").setValue(mResidingAt.getMonasteryName());
            acroForm.getField("addrNumberWatResidingAtThai").setValue(mResidingAt.getAddrNumber());
            acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());

        }
    }

    private void fillFormTM47_90DayNotice(PDAcroForm acroForm, MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        LocalDate ldArrival;
        Monastery mResidingAt;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));
        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("fullName").setValue(ProfileUtil.getFullName(p));
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("departureCardNumber").setValue(p.getArrivalCardNumber());
        acroForm.getField("arrivalTravelBy").setValue(p.getArrivalTravelBy());

        if (p.getVisaType() != null && p.getVisaType().equals("Tourist"))
        {
            ((PDCheckBox) acroForm.getField("TOURIST")).check();
            ((PDCheckBox) acroForm.getField("NONIMM")).unCheck();
        }

        ldArrival = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());

        if (ldArrival != null)
        {
            acroForm.getField("arrivalLastEntryDateDay").setValue(ldArrival.getDayOfMonth() + "");
            acroForm.getField("arrivalLastEntryDateMonth").setValue(ldArrival.getMonthValue() + "");
            acroForm.getField("arrivalLastEntryDateYear").setValue(Util.convertYearToThai(ldArrival.getYear()) + "");
        }

        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {

            acroForm.getField("watResidingAtThai").setValue(mResidingAt.getMonasteryName());
            acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
            acroForm.getField("addrPhoneWatResidingAtThai").setValue(mResidingAt.getPhoneNumber());
        }

    }

    public void fillFormSTM2AckConditions2(PDAcroForm acroForm, MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("fullName").setValue(ProfileUtil.getFullName(p));
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("age").setValue(ProfileUtil.getStrAge(p.getBirthDate()));

    }

    public void fillFormTM86VisaChange(PDAcroForm acroForm, MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        Monastery mResidingAt;
        LocalDate ldBirthDate, ldPassportIssue, ldPassportExpiry, ldLastEntry;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrNumberWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));
        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("lastName").setValue(p.getLastName());
        acroForm.getField("name").setValue(p.getMonasticName());
        acroForm.getField("middleName").setValue(p.getMiddleName());

        acroForm.getField("age").setValue(ProfileUtil.getStrAge(p.getBirthDate()));

        ldBirthDate = Util.convertDateToLocalDate(p.getBirthDate());
        if (ldBirthDate != null)
        {
            acroForm.getField("birthDateDay").setValue(ldBirthDate.getDayOfMonth() + "");
            acroForm.getField("birthDateMonth").setValue(ldBirthDate.getMonthValue() + "");
            acroForm.getField("birthDateYear").setValue(Util.convertYearToThai(ldBirthDate.getYear()) + "");
        }

        acroForm.getField("birthPlace_birthCountry").setValue(p.getBirthPlace() + ", " + p.getBirthCountry());
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());

        ldPassportIssue = Util.convertDateToLocalDate(p.getPassportIssueDate());
        if (ldPassportIssue != null)
        {
            acroForm.getField("passportIssueDateDay").setValue(ldPassportIssue.getDayOfMonth() + "");
            acroForm.getField("passportIssueDateMonth").setValue(ldPassportIssue.getMonthValue() + "");
            acroForm.getField("passportIssueDateYear").setValue(Util.convertYearToThai(ldPassportIssue.getYear()) + "");
        }
        acroForm.getField("passportIssuedAt").setValue(p.getPassportIssuedAt());

        ldPassportExpiry = Util.convertDateToLocalDate(p.getPassportExpiryDate());
        if (ldPassportExpiry != null)
        {
            acroForm.getField("passportExpiryDateDay").setValue(ldPassportExpiry.getDayOfMonth() + "");
            acroForm.getField("passportExpiryDateMonth").setValue(ldPassportExpiry.getMonthValue() + "");
            acroForm.getField("passportExpiryDateYear").setValue(Util.convertYearToThai(ldPassportExpiry.getYear()) + "");
        }

        acroForm.getField("visaType").setValue(p.getVisaType());
        acroForm.getField("arrivalTravelBy").setValue(p.getArrivalTravelBy());
        acroForm.getField("arrivalTravelFrom").setValue(p.getArrivalTravelFrom());
        acroForm.getField("arrivalPortOfEntry").setValue(p.getArrivalPortOfEntry());

        ldLastEntry = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
        if (ldLastEntry != null)
        {
            acroForm.getField("arrivalLastEntryDateDay").setValue(ldLastEntry.getDayOfMonth() + "");
            acroForm.getField("arrivalLastEntryDateMonth").setValue(ldLastEntry.getMonthValue() + "");
            acroForm.getField("arrivalLastEntryDateYear").setValue(Util.convertYearToThai(ldLastEntry.getYear()) + "");
        }
        acroForm.getField("departureCardNumber").setValue(p.getArrivalCardNumber());

        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {
            acroForm.getField("watResidingAtThai").setValue(mResidingAt.getMonasteryName());
            acroForm.getField("addrNumberWatResidingAtThai").setValue(mResidingAt.getAddrNumber());
            acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
        }
    }

    public void fillFormTM8Reentry(PDAcroForm acroForm, MonasticProfile p, boolean reentryTogetherExtension) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        Monastery mResidingAt;
        LocalDate ldBirthDate, ldLastEntry, ldVisaExpiry, ldPassportIssue,
                ldPassportExpiry;
        Date dVisaExpiry;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("watResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
        alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));
        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("lastName").setValue(p.getLastName());
        acroForm.getField("name").setValue(p.getMonasticName());
        acroForm.getField("middleName").setValue(p.getMiddleName());

        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("age").setValue(ProfileUtil.getStrAge(p.getBirthDate()));

        ldBirthDate = Util.convertDateToLocalDate(p.getBirthDate());
        if (ldBirthDate != null)
        {
            acroForm.getField("birthDateDay").setValue(ldBirthDate.getDayOfMonth() + "");
            acroForm.getField("birthDateMonth").setValue(ldBirthDate.getMonthValue() + "");
            acroForm.getField("birthDateYear").setValue(Util.convertYearToThai(ldBirthDate.getYear()) + "");
        }

        acroForm.getField("birthPlace").setValue(p.getBirthPlace());
        acroForm.getField("birthCountry").setValue(p.getBirthCountry());

        mResidingAt = p.getMonasteryResidingAt();
        if (mResidingAt != null)
        {
            acroForm.getField("watResidingAtThai").setValue(mResidingAt.getMonasteryName());
            acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
            acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
            acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
            acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
        }

        acroForm.getField("passportCountry").setValue(p.getPassportCountry());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("passportIssuedAt").setValue(p.getPassportIssuedAt());

        ldPassportIssue = Util.convertDateToLocalDate(p.getPassportIssueDate());
        if (ldPassportIssue != null)
        {
            acroForm.getField("passportIssueDateDay").setValue(ldPassportIssue.getDayOfMonth() + "");
            acroForm.getField("passportIssueDateMonth").setValue(ldPassportIssue.getMonthValue() + "");
            acroForm.getField("passportIssueDateYear").setValue(Util.convertYearToThai(ldPassportIssue.getYear()) + "");

        }

        ldPassportExpiry = Util.convertDateToLocalDate(p.getPassportExpiryDate());
        if (ldPassportExpiry != null)
        {
            acroForm.getField("passportExpiryDateDay").setValue(ldPassportExpiry.getDayOfMonth() + "");
            acroForm.getField("passportExpiryDateMonth").setValue(ldPassportExpiry.getMonthValue() + "");
            acroForm.getField("passportExpiryDateYear").setValue(Util.convertYearToThai(ldPassportExpiry.getYear()) + "");
        }

        ldLastEntry = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
        if (ldLastEntry != null)
        {
            acroForm.getField("arrivalLastEntryDateDay").setValue(ldLastEntry.getDayOfMonth() + "");
            acroForm.getField("arrivalLastEntryDateMonth").setValue(ldLastEntry.getMonthValue() + "");
            acroForm.getField("arrivalLastEntryDateYear").setValue(Util.convertYearToThai(ldLastEntry.getYear()) + "");
        }

        //if the visa for this monastic has already been extended
        //retrieves the expiry date of the most recent extension
        if (p.getVisaExtensionSet() != null && !p.getVisaExtensionSet().isEmpty())
        {
            dVisaExpiry = ctrMain.getCtrVisa().getLastExtension(p).getExpiryDate();
            ldVisaExpiry = Util.convertDateToLocalDate(dVisaExpiry);
        } //otherwise retrieves the expiry date of the original visa
        else
        {
            ldVisaExpiry = Util.convertDateToLocalDate(p.getVisaExpiryDate());
        }

        if (ldVisaExpiry != null)
        {
            acroForm.getField("visaExpiryDateDay").setValue(ldVisaExpiry.getDayOfMonth() + "");
            acroForm.getField("visaExpiryDateMonth").setValue(ldVisaExpiry.getMonthValue() + "");
            if (reentryTogetherExtension)
            {
                acroForm.getField("visaExpiryDateYear").setValue(Util.convertYearToThai(ldVisaExpiry.plusYears(1).getYear()) + "");
            }
            else
            {
                acroForm.getField("visaExpiryDateYear").setValue(Util.convertYearToThai(ldVisaExpiry.getYear()) + "");
            }

        }
    }

    public void fillForm(File sourceFile, MonasticProfile p, int option, boolean extraOption)
    {
        PDDocument pdfDocument;
        PDAcroForm acroForm;
        PDFont font;
        File outputFile;

        outputFile = AppFiles.getFormTMPOutputPDF(sourceFile.getName());

        // load the document
        try
        {
            pdfDocument = PDDocument.load(sourceFile);
            //load the thai font
            font = PDType0Font.load(pdfDocument, AppFiles.getThaiFont());

            // get the document catalog
            acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

            //if the document is a PDF Form
            if (acroForm != null)
            {
                loadedThaiFontName = acroForm.getDefaultResources().add(font);
            }

            if (sourceFile.getName().equals(AppFiles.getFormSTM2AckConditions().getName()))
            {
                fillFormSTM2AckConditions2(acroForm, p);
            }
            else if (sourceFile.getName().equals(AppFiles.getFormOverstay().getName()))
            {
                fillFormAckOverstayPenalties(acroForm, p);
            }
            else if (sourceFile.getName().equals(AppFiles.getFormTM47Notice90Day().getName()))
            {
                fillFormTM47_90DayNotice(acroForm, p);
            }
            else if (sourceFile.getName().equals(AppFiles.getExtReqLetterSNP().getName()))
            {
                fillExtReqLetter(acroForm, p, DESTINATION_SAMNAKPUT);
            }
            else if (sourceFile.getName().equals(AppFiles.getExtReqLetterIMM().getName()))
            {
                fillExtReqLetter(acroForm, p, DESTINATION_IMMIGRATION);
            }
            else if (sourceFile.getName().equals(AppFiles.getFormTM7ReqExtension().getName()))
            {
                fillTM7ReqExtension(acroForm, p);
            }
            else if ((sourceFile.getName().equals(AppFiles.getFormPrawat().getName()))
                    || (sourceFile.getName().equals(AppFiles.getFormPrawatPatimokkhaChanter().getName())))
            {
                fillPrawat(pdfDocument, acroForm, p);
            }
            else if (sourceFile.getName().equals(AppFiles.getFormTM8Reentry().getName()))
            {
                fillFormTM8Reentry(acroForm, p, extraOption);
            }
            else if (sourceFile.getName().equals(AppFiles.getFormTM86VisaChange().getName()))
            {
                fillFormTM86VisaChange(acroForm, p);
            }
            else if (sourceFile.getName().contains("TM30-"))
            {
                overlayMonasteryWatermark(pdfDocument);
            }
            else
            {
                CtrAlertDialog.errorDialog("No action registered for form with name: " + sourceFile.getName());
            }

            // Save and close the filled out form.
            pdfDocument.save(outputFile);
            pdfDocument.close();

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDocument);
            }
            else
            {
                CtrFileOperation.openFileOnDefaultProgram(outputFile);
            }

        }
        catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, MSG_ERROR);
        }
    }

    public void fillFormAckOverstayPenalties(PDAcroForm acroForm, MonasticProfile p) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        adjustFontThaiField(alThaiFields);

        acroForm.getField("titleThai").setValue(ProfileUtil.getTitleTH(p));
        acroForm.getField("lastName").setValue(p.getLastName());
        acroForm.getField("name").setValue(p.getMonasticName());
        acroForm.getField("middleName").setValue(p.getMiddleName());
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("age").setValue(ProfileUtil.getStrAge(p.getBirthDate()));

    }

    public void fillExtReqLetter(PDAcroForm acroForm, MonasticProfile p, String destination) throws IOException
    {
        ArrayList<PDTextField> alThaiFields;
        String strFullName, strTitle, strMOrdainedAt;
        Monastery mOrdainedAt, mResidingAt;
        Date dVisaExpiry, dArrivalLastEntry;
        LocalDate ldVisaExpiry, ldVisaExpiryDateDesired;
        VisaExtension lastExt;
        int extensionsCount;

        alThaiFields = new ArrayList<>();
        alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
        alThaiFields.add((PDTextField) acroForm.getField("ordinationTypeThai"));
        alThaiFields.add((PDTextField) acroForm.getField("WatOrdainedAtThai_addrAmpher_addrJangwat_addrCountry"));
        alThaiFields.add((PDTextField) acroForm.getField("WatResidingAtThai_addrAmpher_addrJangwat"));

        adjustFontThaiField(alThaiFields);
        strTitle = ProfileUtil.getTitleTH(p);
        strFullName = ProfileUtil.getFullName(p);
        mOrdainedAt = p.getMonasteryOrdainedAt();
        mResidingAt = p.getMonasteryResidingAt();

        acroForm.getField("currentDate").setValue(Util.toStringThaiDateFormat(LocalDate.now()));
        acroForm.getField("titleThai").setValue(strTitle);

        acroForm.getField("fullName").setValue(strFullName);
        acroForm.getField("nationality").setValue(p.getNationality());
        acroForm.getField("passportNumber").setValue(p.getPassportNumber());
        acroForm.getField("ordinationTypeThai").setValue(ProfileUtil.getOrdinationType(p));
        acroForm.getField("ordinationDate").setValue(ProfileUtil.getStrOrdinationDate(p));

        dArrivalLastEntry = p.getArrivalLastEntryDate();
        if (dArrivalLastEntry != null)
        {
            acroForm.getField("arrivalLastEntryDate").setValue(Util.toStringThaiDateFormat(dArrivalLastEntry));
        }

        //if the visa for this monastic has already been extended
        //retrieves the expiry date of the most recent extension
        if (p.getVisaExtensionSet() != null && !p.getVisaExtensionSet().isEmpty())
        {
            dVisaExpiry = ctrMain.getCtrVisa().getLastExtension(p).getExpiryDate();
            ldVisaExpiry = Util.convertDateToLocalDate(dVisaExpiry);
        } //otherwise retrieves the expiry date of the original visa
        else
        {
            ldVisaExpiry = Util.convertDateToLocalDate(p.getVisaExpiryDate());
        }

        acroForm.getField("visaExpiryDate").setValue(Util.toStringThaiDateFormat(ldVisaExpiry));

        if (ldVisaExpiry != null)
        {
            ldVisaExpiryDateDesired = ldVisaExpiry.plusYears(1);
            acroForm.getField("visaExpiryDateDesired").setValue(Util.toStringThaiDateFormat(ldVisaExpiryDateDesired));
        }

        if (mOrdainedAt != null)
        {
            if (mOrdainedAt.getAddrCountry().equals("THAILAND"))
            {
                strMOrdainedAt = mOrdainedAt.getMonasteryName() + " อ." + mOrdainedAt.getAddrAmpher() + " จ." + mOrdainedAt.getAddrJangwat();

            }
            else
            {
                strMOrdainedAt = mOrdainedAt.getMonasteryName() + " " + mOrdainedAt.getAddrAmpher() + " " + mOrdainedAt.getAddrJangwat() + " " + mOrdainedAt.getAddrCountry();
            }
            acroForm.getField("WatOrdainedAtThai_addrAmpher_addrJangwat_addrCountry").setValue(strMOrdainedAt);
        }

        if (mResidingAt != null)
        {
            if (mResidingAt.getAddrCountry().equals("THAILAND"))
            {
                strMOrdainedAt = mResidingAt.getMonasteryName() + " อ." + mResidingAt.getAddrAmpher() + " จ." + mResidingAt.getAddrJangwat();

            }
            else
            {
                strMOrdainedAt = mResidingAt.getMonasteryName() + " " + mResidingAt.getAddrAmpher() + " " + mResidingAt.getAddrJangwat();
            }
            acroForm.getField("WatResidingAtThai_addrAmpher_addrJangwat").setValue(strMOrdainedAt);
        }

        if (destination.equals(DESTINATION_SAMNAKPUT))
        {
            if (p.getVisaExtensionSet() != null)
            {
                extensionsCount = p.getVisaExtensionSet().size();
                acroForm.getField("visaExtensionsCount").setValue(extensionsCount + "");
            }
        }

    }

    public void overlayMonasteryWatermark(PDDocument pdfDoc) throws IOException
    {
        PDImageXObject pdImage;
        PDPageContentStream contentStream;

        pdImage = PDImageXObject.createFromFile(AppFiles.getOverlayWatermark().toString(), pdfDoc);
        contentStream = new PDPageContentStream(pdfDoc, pdfDoc.getPage(0), PDPageContentStream.AppendMode.APPEND, true);

        //translation, rotation and scale for the image
        AffineTransform at = new AffineTransform(pdImage.getHeight() * 0.3, 0, 0, pdImage.getWidth() * 0.3, 565, 450);

        //rotates the image overlay 90 degree because the document is landscape
        at.rotate(Math.toRadians(90));
        Matrix tMatrix = new Matrix(at);

        contentStream.drawImage(pdImage, tMatrix);
        contentStream.close();

    }

    private void fillPrintDate(PDPageContentStream objContentStream, PDPage objPage) throws IOException
    {
        float posX, posY;
        //for landscape page orientation
        if (objPage.getRotation() == 90)
        {
            posX = PAGE_A4_HEIGHT_PX - 210;
            posY = PAGE_A4_WIDTH_PX - 15;
        }
        //for default orientation
        else
        {
            posX = PAGE_A4_WIDTH_PX - 210;
            posY = PAGE_A4_HEIGHT_PX - 15;
        }

        objContentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        objContentStream.beginText();
        objContentStream.newLineAtOffset(posX, posY);
        objContentStream.showText("Print Date: " + LocalDateTime.now().format(Util.DEFAULT_DATE_TIME_FORMAT));
        objContentStream.endText();
    }

    public void generatePDFDueTasksTH(TableView<EntryDueTask> tp90DayTH, TableView<EntryDueTask> tpNonImmVisaExtTH, TableView<EntryDueTask> tpTouristExtTH, TableView<EntryDueTask> tpPsptTH, int option)
    {
        PDDocument pdfDoc;
        PDPage page1, page2, page3;
        PDPageContentStream contentStream;
        File outputFile;
        PDFont fontTitle = PDType1Font.HELVETICA_BOLD;

        int fontSizeTitle = 18;
        BufferedImage img90DayTH, imgNonImmVisaExtTH, imgTouristVisaExtTH, imgPassptRenew;
        PDImageXObject pdfImg90DayTH, pdfImgNonImmVisaExtTH, pdfImgTouristVisaExtTH, pdfImgPassptRenew;

        outputFile = AppFiles.getFormTMPOutputPDF("DueTasksTH");
        pdfDoc = new PDDocument();
        page1 = new PDPage(PDRectangle.A4);
        page2 = new PDPage(PDRectangle.A4);
        page3 = new PDPage(PDRectangle.A4);
        pdfDoc.addPage(page1);
        pdfDoc.addPage(page2);
        pdfDoc.addPage(page3);

        img90DayTH = snapshotGUIComponent(tp90DayTH);
        imgNonImmVisaExtTH = snapshotGUIComponent(tpNonImmVisaExtTH);
        imgTouristVisaExtTH = snapshotGUIComponent(tpTouristExtTH);
        imgPassptRenew = snapshotGUIComponent(tpPsptTH);

        try
        {
            pdfImg90DayTH = LosslessFactory.createFromImage(pdfDoc, img90DayTH);
            pdfImgNonImmVisaExtTH = LosslessFactory.createFromImage(pdfDoc, imgNonImmVisaExtTH);
            pdfImgTouristVisaExtTH = LosslessFactory.createFromImage(pdfDoc, imgTouristVisaExtTH);
            pdfImgPassptRenew = LosslessFactory.createFromImage(pdfDoc, imgPassptRenew);

            contentStream = new PDPageContentStream(pdfDoc, page1, PDPageContentStream.AppendMode.APPEND, true);

            fillPrintDate(contentStream, page1);

            contentStream.setFont(fontTitle, fontSizeTitle);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, PAGE_A4_HEIGHT_PX - 40);
            contentStream.showText("90 Day Notice");
            contentStream.endText();
            contentStream.drawImage(pdfImg90DayTH, 50, PAGE_A4_HEIGHT_PX - pdfImg90DayTH.getHeight() * SCALE_DUE_TASKS_SNAPSHOT - 50, pdfImg90DayTH.getWidth() * SCALE_DUE_TASKS_SNAPSHOT, pdfImg90DayTH.getHeight() * SCALE_DUE_TASKS_SNAPSHOT);

            contentStream.beginText();
            contentStream.newLineAtOffset(50, 60 + pdfImgNonImmVisaExtTH.getHeight() * SCALE_DUE_TASKS_SNAPSHOT);
            contentStream.showText("Non-Immigrant Visa Extension");
            contentStream.endText();
            contentStream.drawImage(pdfImgNonImmVisaExtTH, 50, 50, pdfImgNonImmVisaExtTH.getWidth() * SCALE_DUE_TASKS_SNAPSHOT, pdfImgNonImmVisaExtTH.getHeight() * SCALE_DUE_TASKS_SNAPSHOT);
            contentStream.close();

             contentStream = new PDPageContentStream(pdfDoc, page2, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.setFont(fontTitle, fontSizeTitle);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, PAGE_A4_HEIGHT_PX - 40);
            contentStream.showText("Tourist Visa Extension");
            contentStream.endText();
            contentStream.drawImage(pdfImgTouristVisaExtTH,50, PAGE_A4_HEIGHT_PX - pdfImgTouristVisaExtTH.getHeight() * SCALE_DUE_TASKS_SNAPSHOT - 50, pdfImgTouristVisaExtTH.getWidth() * SCALE_DUE_TASKS_SNAPSHOT, pdfImgTouristVisaExtTH.getHeight() * SCALE_DUE_TASKS_SNAPSHOT);
            contentStream.close();
            
            contentStream = new PDPageContentStream(pdfDoc, page3, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.setFont(fontTitle, fontSizeTitle);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, PAGE_A4_HEIGHT_PX - 40);
            contentStream.showText("Passport Renewal");
            contentStream.endText();

            contentStream.drawImage(pdfImgPassptRenew, 100, PAGE_A4_HEIGHT_PX - pdfImgPassptRenew.getHeight() * SCALE_DUE_TASKS_SNAPSHOT - 50, pdfImgPassptRenew.getWidth() * SCALE_DUE_TASKS_SNAPSHOT, pdfImgPassptRenew.getHeight() * SCALE_DUE_TASKS_SNAPSHOT);

            contentStream.close();
            pdfDoc.save(outputFile);

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDoc);
            }
            else
            {
                CtrFileOperation.openFileOnDefaultProgram(outputFile);
            }
            pdfDoc.close();

        }
        catch (IOException e)
        {
            CtrAlertDialog.errorDialog("Error to generate pdf with Due Tasks printout.");
        }

    }

    public void generatePDFDueTasksAbroad(TableView<EntryDueTask> tvVisaExtAbroad, TableView<EntryDueTask> tvPsptAbroad, int option)
    {
        PDDocument pdfDoc;
        PDPage page1, page2;
        PDPageContentStream contentStream;
        File outputFile;
        PDFont font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 18;
        BufferedImage imgVisaExtAbroad, imgPassptAbroad;
        PDImageXObject pdfImgVisaExtAbroad, pdfImgPassptAbroad;

        outputFile = AppFiles.getFormTMPOutputPDF("DueTasks-Abroad");
        pdfDoc = new PDDocument();
        page1 = new PDPage(PDRectangle.A4);
        pdfDoc.addPage(page1);

        imgVisaExtAbroad = snapshotGUIComponent(tvVisaExtAbroad);
        imgPassptAbroad = snapshotGUIComponent(tvPsptAbroad);

        try
        {
            pdfImgVisaExtAbroad = LosslessFactory.createFromImage(pdfDoc, imgVisaExtAbroad);
            pdfImgPassptAbroad = LosslessFactory.createFromImage(pdfDoc, imgPassptAbroad);

            contentStream = new PDPageContentStream(pdfDoc, page1, PDPageContentStream.AppendMode.APPEND, true);

            fillPrintDate(contentStream, page1);

            contentStream.setFont(font, fontSize);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, PAGE_A4_HEIGHT_PX - 40);
            contentStream.showText("Visa Extension - Abroad");
            contentStream.endText();
            contentStream.drawImage(pdfImgVisaExtAbroad, 50, PAGE_A4_HEIGHT_PX - pdfImgVisaExtAbroad.getHeight() * SCALE_DUE_TASKS_SNAPSHOT - 50, pdfImgVisaExtAbroad.getWidth() * SCALE_DUE_TASKS_SNAPSHOT, pdfImgVisaExtAbroad.getHeight() * SCALE_DUE_TASKS_SNAPSHOT);

            contentStream.beginText();
            contentStream.newLineAtOffset(50, PAGE_A4_HEIGHT_PX / 2.0f + 10);
            contentStream.showText("Passport Renewal - Abroad");
            contentStream.endText();
            contentStream.drawImage(pdfImgPassptAbroad, 50, PAGE_A4_HEIGHT_PX / 2.0f - pdfImgPassptAbroad.getHeight() * SCALE_DUE_TASKS_SNAPSHOT, pdfImgPassptAbroad.getWidth() * SCALE_DUE_TASKS_SNAPSHOT, pdfImgPassptAbroad.getHeight() * SCALE_DUE_TASKS_SNAPSHOT);
            contentStream.close();

            pdfDoc.save(outputFile);

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDoc);
            }
            else
            {
                CtrFileOperation.openFileOnDefaultProgram(outputFile);
            }
            pdfDoc.close();

        }
        catch (IOException e)
        {
            CtrAlertDialog.errorDialog("Error to generate pdf with Due Tasks printout.");
        }

    }

    public void generatePDFWorkflow(TableView tvWorkflowVisaExt, int option)
    {
        generatePDFSnapshotTableView(tvWorkflowVisaExt, "Workflow Visa Extension", ORIENTATION_LANDSCAPE, 0.33f, option);
    }

    public void generatePDFPrintedDocStock(TableView tvPrintedDocStock, int option)
    {
        generatePDFSnapshotTableView(tvPrintedDocStock, "Printed Documents Stock", ORIENTATION_PORTRAIT, 0.33f, option);
    }

    public void generatePDFSnapshotTableView(TableView objTV, String title, int orientation, float scale, int option)
    {
        String filePrefix;
        float posYTopPage, posX, paperWidth;
        PDDocument pdfDoc;
        PDPage page1;
        PDPageContentStream contentStream;
        File outputFile;
        PDFont font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 18;
        BufferedImage objBufferedImgSnapshot;
        PDImageXObject pdImgSnapshot;

        //removes any non-word characters from the title
        filePrefix = title.replaceAll("\\W ", "");
        outputFile = AppFiles.getFormTMPOutputPDF(filePrefix);

        pdfDoc = new PDDocument();
        page1 = new PDPage(PDRectangle.A4);

        if (orientation == ORIENTATION_LANDSCAPE)
        {
            page1.setRotation(90);
        }
        else
        {
            page1.setRotation(0);
        }
        pdfDoc.addPage(page1);

        objBufferedImgSnapshot = snapshotGUIComponent(objTV);

        try
        {
            pdImgSnapshot = LosslessFactory.createFromImage(pdfDoc, objBufferedImgSnapshot);

            contentStream = new PDPageContentStream(pdfDoc, page1, PDPageContentStream.AppendMode.APPEND, true);

            if (orientation == ORIENTATION_LANDSCAPE)
            {
                // including a translation of pageWidth to use the lower left corner as 0,0 reference
                contentStream.transform(new Matrix(0, 1, -1, 0, page1.getMediaBox().getWidth(), 0));
                //on the landscape orientation, the height of the page is the same as the 
                //width of the page on portrait orientation
                posYTopPage = PAGE_A4_WIDTH_PX;
                paperWidth = PAGE_A4_HEIGHT_PX;
            }
            else
            {
                posYTopPage = PAGE_A4_HEIGHT_PX;
                paperWidth = PAGE_A4_WIDTH_PX;
            }

            fillPrintDate(contentStream, page1);

            //left margin for centering the snapshot
            posX = (paperWidth - pdImgSnapshot.getWidth() * scale) / 2.0f;

            contentStream.setFont(font, fontSize);
            contentStream.beginText();
            contentStream.newLineAtOffset(posX, posYTopPage - 40);
            contentStream.showText(title);
            contentStream.endText();

            contentStream.drawImage(pdImgSnapshot, posX, posYTopPage - pdImgSnapshot.getHeight() * scale - 50, pdImgSnapshot.getWidth() * scale, pdImgSnapshot.getHeight() * scale);
            contentStream.close();

            pdfDoc.save(outputFile);

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDoc);
            }
            else
            {
                CtrFileOperation.openFileOnDefaultProgram(outputFile);
            }
            pdfDoc.close();

        }
        catch (IOException e)
        {
            CtrAlertDialog.errorDialog("Error to generate pdf for " + title);
        }

    }

    private BufferedImage snapshotGUIComponent(TableView pGUIComponent)
    {
        Rectangle rect;
        WritableImage writableImage;
        ImageView imgView;
        SnapshotParameters spa;
        final int pixelScaleFactor = 2;

        //doubles the snapshot resolution for better quality/sharpness
        spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(pixelScaleFactor, pixelScaleFactor));

        writableImage = new WritableImage((int) Math.rint(pGUIComponent.getWidth() * pixelScaleFactor), (int) Math.rint(pGUIComponent.getHeight() * pixelScaleFactor));
        pGUIComponent.snapshot(spa, writableImage);

        imgView = new ImageView(writableImage);

        return SwingFXUtils.fromFXImage(imgView.getImage(), null);
    }

    public void generatePDFBysuddhiScans(MonasticProfile p, int option)
    {
        //bysuddhi size 18.5 cm X 12.5 cm
        File fScan1, fScan2, fScan3, fScan4;
        PDPageContentStream contentStream;
        PDImageXObject imgScan1, imgScan2, imgScan3, imgScan4;
        PDPage page1;
        float bysuddhiScanWidth, bysuddhiScanHeight;
        float landscape_A4_width_px, landscape_A4_height_px;

        PDDocument pdfDoc;
        File outputFile;

        outputFile = AppFiles.getFormTMPOutputPDF(p.getNickname() + "-BysuddhiScans");

        pdfDoc = new PDDocument();
        page1 = new PDPage(PDRectangle.A4);

        //landscape PDF
        page1.setRotation(90);
        pdfDoc.addPage(page1);

        //on a landscape PDF the width and the height of the page are switched
        landscape_A4_width_px = page1.getMediaBox().getHeight();
        landscape_A4_height_px = page1.getMediaBox().getWidth();

        //Bysuddhi Real size 185mm x 125mm
        //Converts the Bysuddhi width to pixels
        //Bysuddhi real Width  185mm
        //A4 Width pixel size: PDRectangle.A4.getWidth() 
        //A4 Width real size 210mm
        bysuddhiScanWidth = (PDRectangle.A4.getWidth() * 185) / 210.0f;

        //Converts the Bysuddhi to pixels
        //Bysuddhi real Height 125mm
        //A4 Height pixel size: PDRectangle.A4.getHeight() 
        //A4 Height real size 297mm
        bysuddhiScanHeight = (PDRectangle.A4.getHeight() * 125) / 297.0f;

        bysuddhiScanHeight /= 1.5f;
        bysuddhiScanWidth /= 1.5f;
        fScan1 = AppFiles.getScanBysuddhi(p.getNickname(), 1);
        fScan2 = AppFiles.getScanBysuddhi(p.getNickname(), 2);
        fScan3 = AppFiles.getScanBysuddhi(p.getNickname(), 3);
        fScan4 = AppFiles.getScanBysuddhi(p.getNickname(), 4);

        try
        {
            contentStream = new PDPageContentStream(pdfDoc, page1, PDPageContentStream.AppendMode.APPEND, true);
            // add the rotation using the current transformation matrix
            // including a translation of pageWidth to use the lower left corner as 0,0 reference
            contentStream.transform(new Matrix(0, 1, -1, 0, page1.getMediaBox().getWidth(), 0));

            imgScan1 = PDImageXObject.createFromFile(fScan1.toString(), pdfDoc);
            contentStream.drawImage(imgScan1, 50, landscape_A4_height_px - bysuddhiScanHeight - 50, bysuddhiScanWidth, bysuddhiScanHeight);

            imgScan2 = PDImageXObject.createFromFile(fScan2.toString(), pdfDoc);
            contentStream.drawImage(imgScan2, landscape_A4_width_px - bysuddhiScanWidth - 50, landscape_A4_height_px - bysuddhiScanHeight - 50, bysuddhiScanWidth, bysuddhiScanHeight);

            imgScan3 = PDImageXObject.createFromFile(fScan3.toString(), pdfDoc);
            contentStream.drawImage(imgScan3, 50, 50, bysuddhiScanWidth, bysuddhiScanHeight);

            if (fScan4.exists())
            {
                imgScan4 = PDImageXObject.createFromFile(fScan4.toString(), pdfDoc);
                contentStream.drawImage(imgScan4, landscape_A4_width_px - bysuddhiScanWidth - 50, 50, bysuddhiScanWidth, bysuddhiScanHeight);
            }

            contentStream.close();
            pdfDoc.save(outputFile);

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDoc);
            }
            else
            {
                CtrFileOperation.openFileOnDefaultProgram(outputFile);
            }
            pdfDoc.close();

        }
        catch (IOException ex)
        {
            CtrAlertDialog.errorDialog("Error to generate pdf with passport scans.");
        }
    }

    public void generatePDFPassportScans(MonasticProfile p, int option)
    {
        //passport size 17cm X 12.5 cm
        ArrayList<ExtraPassportScanLoaded> listEPS;
        PDDocument pdfDoc;
        File outputFile;

        outputFile = AppFiles.getFormTMPOutputPDF(p.getNickname() + "-PassportScans");

        pdfDoc = new PDDocument();

        try
        {
            listEPS = AppFiles.getListExtraScans(p.getNickname(), p.getPassportNumber());
            generateScansPage1(pdfDoc, p);
            generateScansPage2(pdfDoc, p, listEPS);
            generateScansPage3(pdfDoc, p, listEPS);

            pdfDoc.save(outputFile);

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDoc);
            }
            else
            {
                CtrFileOperation.openFileOnDefaultProgram(outputFile);
            }
            pdfDoc.close();

        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());
            CtrAlertDialog.errorDialog("Error to generate pdf with passport scans.");
        }
    }

    private void generateScansPage1(PDDocument pdfDoc, MonasticProfile p) throws IOException
    {
        File fScanPassportFirstPage, fScanDepartureCard;
        PDPageContentStream contentStream;
        PDImageXObject imgPassportScan, imgDepartureCardScan;
        PDPage page1;
        float departureCardScanWidth, departureCardScanHeight;
        Matrix tMatrix;
        AffineTransform at;

        //Departure Card Real size 185mm x 80mm
        //Converts the departure card width to pixels
        //Departure card real Width  185mm
        //A4 Width pixel size: PDRectangle.A4.getWidth() 
        //A4 Width real size 210mm
        departureCardScanWidth = (PDRectangle.A4.getWidth() * 185) / 210.0f;

        //Converts the departure card height to pixels
        //Departure card real Height 80mm
        //A4 Height pixel size: PDRectangle.A4.getHeight() 
        //A4 Height real size 297mm
        departureCardScanHeight = (PDRectangle.A4.getHeight() * 80) / 297.0f;

        fScanPassportFirstPage = AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber());
        fScanDepartureCard = AppFiles.getScanDepartureCard(p.getNickname());

        if (fScanPassportFirstPage.exists() || fScanDepartureCard.exists())
        {
            page1 = new PDPage(PDRectangle.A4);
            pdfDoc.addPage(page1);

            contentStream = new PDPageContentStream(pdfDoc, page1, PDPageContentStream.AppendMode.APPEND, true);
            if (fScanPassportFirstPage.exists())
            {
                imgPassportScan = PDImageXObject.createFromFile(AppFiles.getScanPassportFirstPage(p.getNickname(), p.getPassportNumber()).toString(), pdfDoc);

                at = new AffineTransform(DEFAULT_WIDTH_PASSPORT_SCAN_PX, 0, 0, DEFAULT_HEIGHT_PASSPORT_SCAN_PX, 50, PAGE_A4_HEIGHT_PX - 50);
                //rotates the image 90 degree
                at.rotate(Math.toRadians(-90));
                tMatrix = new Matrix(at);

                contentStream.drawImage(imgPassportScan, tMatrix);
            }

            if (fScanDepartureCard.exists())
            {
                imgDepartureCardScan = PDImageXObject.createFromFile(AppFiles.getScanDepartureCard(p.getNickname()).toString(), pdfDoc);
                contentStream.drawImage(imgDepartureCardScan, 40, 50, departureCardScanWidth, departureCardScanHeight);
            }

            contentStream.close();
        }

    }

    private void generateScansPage2(PDDocument pdfDoc, MonasticProfile p, ArrayList<ExtraPassportScanLoaded> listEPS) throws IOException
    {
        File fScan1, fScan2;
        PDPageContentStream contentStream;
        PDImageXObject imgScan1, imgScan2;
        PDPage page2;

        if (listEPS.size() >= 1)
        {
            page2 = new PDPage(PDRectangle.A4);
            pdfDoc.addPage(page2);

            listEPS = AppFiles.getListExtraScans(p.getNickname(), p.getPassportNumber());

            contentStream = new PDPageContentStream(pdfDoc, page2, PDPageContentStream.AppendMode.APPEND, true);

            fScan1 = listEPS.get(0).getFileScan();
            imgScan1 = PDImageXObject.createFromFile(fScan1.toString(), pdfDoc);
            contentStream.drawImage(imgScan1, 50, PAGE_A4_HEIGHT_PX - DEFAULT_HEIGHT_PASSPORT_SCAN_PX - 50, DEFAULT_WIDTH_PASSPORT_SCAN_PX, DEFAULT_HEIGHT_PASSPORT_SCAN_PX);

            if (listEPS.size() >= 2)
            {
                fScan2 = listEPS.get(1).getFileScan();
                imgScan2 = PDImageXObject.createFromFile(fScan2.toString(), pdfDoc);
                contentStream.drawImage(imgScan2, 50, 50, DEFAULT_WIDTH_PASSPORT_SCAN_PX, DEFAULT_HEIGHT_PASSPORT_SCAN_PX);
            }

            contentStream.close();
        }

    }

    private void generateScansPage3(PDDocument pdfDoc, MonasticProfile p, ArrayList<ExtraPassportScanLoaded> listEPS) throws IOException
    {
        File fScan3;
        PDPageContentStream contentStream;
        PDImageXObject imgScan3;
        PDPage page3;

        if (listEPS.size() == 3)
        {
            page3 = new PDPage(PDRectangle.A4);
            pdfDoc.addPage(page3);

            fScan3 = listEPS.get(2).getFileScan();
            imgScan3 = PDImageXObject.createFromFile(fScan3.toString(), pdfDoc);

            contentStream = new PDPageContentStream(pdfDoc, page3, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.drawImage(imgScan3, 50, PAGE_A4_HEIGHT_PX - DEFAULT_HEIGHT_PASSPORT_SCAN_PX - 50, DEFAULT_WIDTH_PASSPORT_SCAN_PX, DEFAULT_HEIGHT_PASSPORT_SCAN_PX);
            contentStream.close();
        }
    }

    public void generatePhotoPage(String nicknameMonastic1, String nicknameMonastic2, int option)
    {
        PDDocument pdfDoc;
        File outputFile;
        PDPage page1;
        PDPageContentStream contentStream;
        PDImageXObject pdIMG1, pdIMG2;
        float step;

        //Photo Real Real size 40mm x 60mm
        //Converts the photo height to pixels
        //Photo Real Height  60mm
        //A4 Height pixel size: PDRectangle.A4.getHeight() 
        //A4 Height real size 297mm
        final float photoHeight_PX = (PDRectangle.A4.getHeight() * 60) / 297.0f;

        //Converts the photo Width to pixels
        //Photo Real Width  40mm
        //A4 Width pixel size: PDRectangle.A4.getHeight() 
        //A4 Width real size 210mm
        final float photoWidth_PX = (PDRectangle.A4.getWidth() * 40) / 210.0f;

        pdfDoc = new PDDocument();
        page1 = new PDPage(PDRectangle.A4);
        pdfDoc.addPage(page1);
        outputFile = AppFiles.getFormTMPOutputPDF("PhotoPage");
        try
        {
            contentStream = new PDPageContentStream(pdfDoc, page1, PDPageContentStream.AppendMode.APPEND, true);
            pdIMG1 = PDImageXObject.createFromFile(AppFiles.getProfilePhoto(nicknameMonastic1).toString(), pdfDoc);
            for (int i = 0; i < 4; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    contentStream.drawImage(pdIMG1, 50 + i * (photoWidth_PX + 10), PAGE_A4_HEIGHT_PX - photoHeight_PX - 50 - j * (photoHeight_PX + 10), photoWidth_PX, photoHeight_PX);
                }

            }

            if (nicknameMonastic2 != null)
            {
                pdIMG2 = PDImageXObject.createFromFile(AppFiles.getProfilePhoto(nicknameMonastic2).toString(), pdfDoc);
                for (int i = 0; i < 4; i++)
                {
                    for (int j = 0; j < 2; j++)
                    {
                        contentStream.drawImage(pdIMG2, 50 + i * (photoWidth_PX + 10), PAGE_A4_HEIGHT_PX / 2.0f - photoHeight_PX - j * (photoHeight_PX + 10), photoWidth_PX, photoHeight_PX);
                    }

                }

            }
            contentStream.close();
            pdfDoc.save(outputFile);

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDoc);
            }
            else
            {
                CtrFileOperation.openFileOnDefaultProgram(outputFile);
            }
            pdfDoc.close();
        }
        catch (IOException ioe)
        {
            CtrAlertDialog.errorDialog("Error to generate pdf with Photo Page.");
        }

    }

    private void printPDF(PDDocument p)
    {
        //shows the print dialog
        PrinterJob pj = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();

        pj.setPrintable(new PDFPrintable(p));
        attr.add(new PageRanges(1, p.getNumberOfPages()));
        if (pj.printDialog(attr))
        {
            try
            {
                pj.print(attr);
            }
            catch (PrinterException ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Error to print form.");
            }
        }
    }

    /*
     * For filling the fields with Thai characters it's necessary to: 1) load
     * the font file manually 2) reset the field appearance to use the loaded
     * font
     */
    private void adjustFontThaiField(ArrayList<PDTextField> listThaiFields)
    {
        int indexFirstSpace;
        String subStringAppearance;
        String beforeFieldAppearance;
        String afterFieldAppearance;

        for (PDTextField thaiTextField : listThaiFields)
        {
            /*
             * The appearance string has a format similar to the following example
             * "/Arial 50 Tf 0 g"
             */
            beforeFieldAppearance = thaiTextField.getDefaultAppearance();
            //looks for the position of the first space on the string
            indexFirstSpace = beforeFieldAppearance.indexOf(" ");

            //the substring is everything from the space on...
            //following with the example, it would be 
            //" 50 Tf 0 g"
            subStringAppearance = beforeFieldAppearance.substring(indexFirstSpace, beforeFieldAppearance.length());

            //the new field appearance needs to use the loaded font name
            //but keeping the other appearance settings like size etc.
            //so the final result would be something like "/F1 50 Tf 0 g"
            afterFieldAppearance = "/" + loadedThaiFontName.getName() + subStringAppearance;
            
            thaiTextField.setDefaultAppearance(afterFieldAppearance);
        }

    }

}
