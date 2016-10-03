/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.awt.Desktop;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.ThaiBuddhistDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;
import org.apache.pdfbox.cos.COSName;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.printing.PDFPrintable;
import org.watmarpjan.visaManager.AppConstants;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.gui.CtrAlertDialog;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
import org.watmarpjan.visaManager.model.hibernate.Profile;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author WMJ_user
 */
public class CtrForm
{

    public static final int OPTION_PRINT_FORM = 0;
    public static final int OPTION_PREVIEW_FORM = 1;

    public CtrForm()
    {

    }

    /*
     * public void fillFormTM7ReqExtension(Profile p, int option) { PDFont font;
     * COSName fontName; Path pathForm90Notice; Path pathOutputFile; Path
     * pathFontFile; File outputFile; LocalDate ldArrival;
     * ArrayList<PDTextField> alThaiFields; String title; int age;
     *
     * pathForm90Notice =
     * Paths.get(System.getProperty("user.dir")).resolve("appData/forms/TM7ReqExtension.pdf");
     * pathOutputFile = Paths.get(System.getProperty("user.dir")).resolve("tmp/"
     * + p.getIdprofile() + "TM7ReqExtension.pdf"); pathFontFile =
     * Paths.get(System.getProperty("user.dir")).resolve("appData/forms/font/angsa.tthaiTextField");
     * outputFile = new File(pathOutputFile.toUri());
     *
     * try { Files.copy(pathForm90Notice, pathOutputFile,
     * StandardCopyOption.REPLACE_EXISTING); } catch (IOException ex) {
     * System.out.println("IO Error: " + ex.getMessage()); }
     *
     * // load the document PDDocument pdfDocument; try { pdfDocument =
     * PDDocument.load(outputFile);
     *
     * //load the thai font font = PDType0Font.load(pdfDocument, new
     * File(pathFontFile.toUri()));
     *
     * // get the document catalog PDAcroForm acroForm =
     * pdfDocument.getDocumentCatalog().getAcroForm(); fontName =
     * acroForm.getDefaultResources().add(font);
     *
     * // as there might not be an AcroForm entry a null check is necessary if
     * (acroForm != null) { // Retrieve an individual field and set its value.
     * acroForm.getField("titleANDLastName").setValue(p.getLastName());
     * acroForm.getField("name").setValue(p.getName());
     * acroForm.getField("middleName").setValue(p.getMiddleName());
     *
     * acroForm.getField("age").setValue(p.getMiddleName());
     *
     * acroForm.getField("birthDateDay").setValue(p.getMiddleName());
     * acroForm.getField("birthDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("birthDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("birthPlace").setValue(p.getMiddleName());
     * acroForm.getField("nationality").setValue(p.getMiddleName());
     * acroForm.getField("passportNumber").setValue(p.getMiddleName());
     * acroForm.getField("passportIssueDateDay").setValue(p.getMiddleName());
     * acroForm.getField("passportIssueDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("passportIssueDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("passportIssuedAt").setValue(p.getMiddleName());
     * acroForm.getField("passportExpiryDateDay").setValue(p.getMiddleName());
     * acroForm.getField("passportExpiryDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("passportExpiryDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("visaType").setValue(p.getMiddleName());
     * acroForm.getField("arrivalTravelBy").setValue(p.getMiddleName());
     *
     * acroForm.getField("arrivalTravelFrom").setValue(p.getMiddleName());
     * acroForm.getField("arrivalPortOfEntry").setValue(p.getMiddleName());
     * acroForm.getField("arrivalLastEntryDateDay").setValue(p.getMiddleName());
     * acroForm.getField("arrivalLastEntryDateMonth").setValue(p.getMiddleName());
     * acroForm.getField("arrivalLastEntryDateYear").setValue(p.getMiddleName());
     *
     * acroForm.getField("departureCardNumber").setValue(p.getMiddleName());
     * acroForm.getField("extensionPeriod").setValue(p.getMiddleName());
     * acroForm.getField("extensionReason").setValue(p.getMiddleName());
     *
     * acroForm.getField("thaiNameWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrNumberWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrRoadWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrRoadWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrRoadWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrTambolWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrAmpherWatResidingAt").setValue(p.getMiddleName());
     * acroForm.getField("addrJangwatWatResidingAt").setValue(p.getMiddleName());
     *
     *
     * ldArrival =
     * org.watmarpjan.visaManager.util.Util.convertDateToLocalDate(p.getArrivalLastEntryDate());
     *
     * if (ldArrival != null) {
     * acroForm.getField("Day").setValue(ldArrival.getDayOfMonth() + "");
     * acroForm.getField("Month").setValue(ldArrival.getMonthValue() + "");
     * acroForm.getField("Year").setValue((ldArrival.getYear() + 543) + "");
     *
     * }
     *
     * acroForm.getField("ArrivedBy").setValue(p.getArrivalTravelBy());
     * acroForm.getField("PassportNumber").setValue(p.getPassportNumber());
     * acroForm.getField("ArrivalCardNumber").setValue(p.getArrivalCardNumber());
     *
     * if (p.getWatByIdWatResidingAt() != null) { alThaiFields = new
     * ArrayList<>(); alThaiFields.add((PDTextField)
     * acroForm.getField("Addr1")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrRoad")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrTambol")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrAmphur")); alThaiFields.add((PDTextField)
     * acroForm.getField("AddrProvince"));
     *
     * for (PDTextField thaiTextField : alThaiFields) { adjustFontThaiField(thaiTextField, fontName);
     * }
     *
     * acroForm.getField("Addr1").setValue(p.getWatByIdWatResidingAt().getThaiName());
     * acroForm.getField("AddrRoad").setValue(p.getWatByIdWatResidingAt().getAddrRoad());
     * acroForm.getField("AddrTambol").setValue(p.getWatByIdWatResidingAt().getAddrTambon());
     * acroForm.getField("AddrAmphur").setValue(p.getWatByIdWatResidingAt().getAddrAmpher());
     * acroForm.getField("AddrProvince").setValue(p.getWatByIdWatResidingAt().getAddrJangwat());
     * acroForm.getField("PhoneNumber").setValue(p.getWatByIdWatResidingAt().getPhoneNumber());
     * } }
     *
     * // Save and close the filled out form. pdfDocument.save(outputFile);
     *
     * if (option == OPTION_PRINT_FORM) { printPDF(pdfDocument); } else {
     * openPDFOnDefaultProgram(outputFile); }
     *
     * } catch (IOException ex) { System.out.println("IO Error: " +
     * ex.getMessage()); } }
     */
    public static void fillFormTM47_90DayNotice(Profile p, int option)
    {
        PDDocument pdfDocument;
        PDAcroForm acroForm;
        COSName fontName;
        File outputFile;
        File sourceFile;
        LocalDate ldArrival;
        ArrayList<PDTextField> alThaiFields;
        ReturnLoadFont objRetLoadFont;

        sourceFile = AppFiles.getFormTM47Notice90Day();
        outputFile = AppFiles.getFormTMPOutputPDF(sourceFile.getName());

        // load the document
        try
        {
            pdfDocument = PDDocument.load(sourceFile);

            objRetLoadFont = loadThaiFont(pdfDocument);
            acroForm = objRetLoadFont.getAcroForm();
            fontName = objRetLoadFont.getFontName();

            if (objRetLoadFont != null)
            {
                Monastery mResidingAt;

                alThaiFields = new ArrayList<>();
                alThaiFields.add((PDTextField) acroForm.getField("titleThai"));
                alThaiFields.add((PDTextField) acroForm.getField("WatResidingAtThai"));
                alThaiFields.add((PDTextField) acroForm.getField("addrRoadWatResidingAtThai"));
                alThaiFields.add((PDTextField) acroForm.getField("addrTambonWatResidingAtThai"));
                alThaiFields.add((PDTextField) acroForm.getField("addrAmpherWatResidingAtThai"));
                alThaiFields.add((PDTextField) acroForm.getField("addrJangwatWatResidingAtThai"));
                adjustFontThaiField(alThaiFields, fontName);

                acroForm.getField("titleThai").setValue(generateTitle(p));
                acroForm.getField("fullName").setValue(generateFullName(p));
                acroForm.getField("nationality").setValue(p.getNationality());
                acroForm.getField("passportNumber").setValue(p.getPassportNumber());
                acroForm.getField("departureCardNumber").setValue(p.getArrivalCardNumber());
                acroForm.getField("arrivalTravelBy").setValue(p.getArrivalTravelBy());

                if (p.getVisaType().equals("Tourist"))
                {
                    ((PDCheckBox) acroForm.getField("TOURIST")).check();
                    ((PDCheckBox) acroForm.getField("NONIMM")).unCheck();
                }

                ldArrival = Util.convertDateToLocalDate(p.getArrivalLastEntryDate());

                if (ldArrival != null)
                {
                    acroForm.getField("arrivalLastEntryDateDay").setValue(ldArrival.getDayOfMonth() + "");
                    acroForm.getField("arrivalLastEntryDateMonth").setValue(ldArrival.getMonthValue() + "");
                    acroForm.getField("arrivalLastEntryDateYear").setValue((ldArrival.getYear() + 543) + "");
                }

                mResidingAt = p.getMonasteryResidingAt();
                if (mResidingAt != null)
                {

                    acroForm.getField("WatResidingAtThai").setValue(mResidingAt.getName());
                    acroForm.getField("addrRoadWatResidingAtThai").setValue(mResidingAt.getAddrRoad());
                    acroForm.getField("addrTambonWatResidingAtThai").setValue(mResidingAt.getAddrTambon());
                    acroForm.getField("addrAmpherWatResidingAtThai").setValue(mResidingAt.getAddrAmpher());
                    acroForm.getField("addrJangwatWatResidingAtThai").setValue(mResidingAt.getAddrJangwat());
                    acroForm.getField("addrPhoneWatResidingAtThai").setValue(mResidingAt.getPhoneNumber());
                }
            }

            // Save and close the filled out form.
            pdfDocument.save(outputFile);
            pdfDocument.close();

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDocument);

            } else
            {
                openPDFOnDefaultProgram(outputFile);
            }

        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error when generating form from template.");
        }
    }

    public static void fillLetterSamnakPut(Profile p, int option)
    {
        PDDocument pdfDocument = null;
        PDAcroForm acroForm;
        COSName fontName;
        File outputFile;
        File sourceFile;
        ArrayList<PDTextField> alThaiFields;
        ReturnLoadFont objRetLoadFont;

        sourceFile = AppFiles.getExtReqLetterSNP();
        outputFile = AppFiles.getFormTMPOutputPDF(sourceFile.getName());

        // load the document
        try
        {
            pdfDocument = PDDocument.load(sourceFile);

            objRetLoadFont = loadThaiFont(pdfDocument);
            acroForm = objRetLoadFont.getAcroForm();
            fontName = objRetLoadFont.getFontName();

            //if the font is properly load and the form detected
            if (objRetLoadFont != null)
            {
                String strFullName, strTitle, strMOrdainedAt;
                Monastery mOrdainedAt, mResidingAt;
                Date dVisaExpiry, dArrivalLastEntry;
                LocalDate ldVisaExpiry, ldNewExpiryDate;
                int extensionsCount;

                alThaiFields = new ArrayList<>();
                alThaiFields.add((PDTextField) acroForm.getField("titleThai1"));
                alThaiFields.add((PDTextField) acroForm.getField("titleThai2"));
                alThaiFields.add((PDTextField) acroForm.getField("titleThai3"));
                alThaiFields.add((PDTextField) acroForm.getField("ordinationTypeThai"));
                alThaiFields.add((PDTextField) acroForm.getField("WatOrdainedAtThai_addrAmpher_addrJangwat_addrCountry"));
                alThaiFields.add((PDTextField) acroForm.getField("WatResidingAtThai_addrAmpher_addrJangwat"));

                adjustFontThaiField(alThaiFields, fontName);
                strTitle = generateTitle(p);
                strFullName = generateFullName(p);
                mOrdainedAt = p.getMonasteryOrdainedAt();
                mResidingAt = p.getMonasteryResidingAt();

                acroForm.getField("currentDate").setValue(Util.toStringThaiDateFormat(LocalDate.now()));
                acroForm.getField("titleThai1").setValue(strTitle);
                acroForm.getField("titleThai2").setValue(strTitle);
                acroForm.getField("titleThai3").setValue(strTitle);
                acroForm.getField("fullName1").setValue(strFullName);
                acroForm.getField("fullName2").setValue(strFullName);
                acroForm.getField("fullName3").setValue(strFullName);
                acroForm.getField("nationality").setValue(p.getNationality());
                acroForm.getField("passportNumber").setValue(p.getPassportNumber());
                acroForm.getField("ordinationTypeThai").setValue(generateOrdinationType(p));
                acroForm.getField("ordinationDate").setValue(getStrOrdinationDate(p));

                dArrivalLastEntry = p.getArrivalLastEntryDate();
                if (dArrivalLastEntry != null)
                {
                    acroForm.getField("arrivalLastEntryDate").setValue(Util.toStringThaiDateFormat(dArrivalLastEntry));
                }

                dVisaExpiry = p.getVisaExpiryDate();
                if (dVisaExpiry != null)
                {
                    ldVisaExpiry = Util.convertDateToLocalDate(dVisaExpiry);
                    acroForm.getField("visaExpiryDate").setValue(Util.toStringThaiDateFormat(ldVisaExpiry));

                    ldNewExpiryDate = ldVisaExpiry.plusYears(1);
                    acroForm.getField("visaExpiryDateDesired").setValue(Util.toStringThaiDateFormat(ldVisaExpiry));
                }

                if (p.getVisaExtensionSet() != null)
                {
                    extensionsCount = p.getVisaExtensionSet().size();
                    acroForm.getField("extensionsCount").setValue(extensionsCount + "");
                }

                if (mOrdainedAt != null)
                {
                    if (mOrdainedAt.getAddrCountry().equals("THAILAND"))
                    {
                        strMOrdainedAt = mOrdainedAt.getName() + " อ." + mOrdainedAt.getAddrAmpher() + " จ." + mOrdainedAt.getAddrJangwat();

                    } else
                    {
                        strMOrdainedAt = mOrdainedAt.getName() + " " + mOrdainedAt.getAddrAmpher() + " " + mOrdainedAt.getAddrJangwat() + " " + mOrdainedAt.getAddrCountry();
                    }
                    acroForm.getField("WatOrdainedAtThai_addrAmpher_addrJangwat_addrCountry").setValue(strMOrdainedAt);
                }

                if (mResidingAt != null)
                {
                    if (mResidingAt.getAddrCountry().equals("THAILAND"))
                    {
                        strMOrdainedAt = mResidingAt.getName() + " อ." + mResidingAt.getAddrAmpher() + " จ." + mResidingAt.getAddrJangwat();

                    } else
                    {
                        strMOrdainedAt = mResidingAt.getName() + " " + mResidingAt.getAddrAmpher() + " " + mResidingAt.getAddrJangwat();
                    }
                    acroForm.getField("WatResidingAtThai_addrAmpher_addrJangwat").setValue(strMOrdainedAt);
                }
            }

            // Save and close the filled out form.
            pdfDocument.save(outputFile);
            pdfDocument.close();

            if (option == OPTION_PRINT_FORM)
            {
                printPDF(pdfDocument);

            } else
            {
                openPDFOnDefaultProgram(outputFile);
            }

        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error when generating form from template.");
        }
    }

    private static String generateFullName(Profile p)
    {
        String strFullName;

        strFullName = p.getName();
        if (p.getMiddleName() != null)
        {
            strFullName += " " + p.getMiddleName();
        }

        strFullName += " " + p.getLastName();

        return strFullName;
    }

    private static String generateTitle(Profile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "พระภิกษุ";
        } else if (p.getSamaneraOrdDate() != null)
        {
            return "สามเณร";
        } else
        {
            return "นาด";
        }
    }

    private static String generateOrdinationType(Profile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return "อุปสมบท";
        } else if (p.getSamaneraOrdDate() != null)
        {
            return "บรรพชา";
        } else
        {
            return "";
        }
    }

    private static String getStrOrdinationDate(Profile p)
    {
        if (p.getBhikkhuOrdDate() != null)
        {
            return Util.toStringThaiDateFormat(p.getBhikkhuOrdDate());
        } else if (p.getSamaneraOrdDate() != null)
        {
            return Util.toStringThaiDateFormat(p.getSamaneraOrdDate());
        } else
        {
            return null;
        }
    }

    private static ReturnLoadFont loadThaiFont(PDDocument pdfDocument)
    {
        try
        {
            PDFont font;
            COSName fontName;
            PDAcroForm acroForm;

            //load the thai font
            font = PDType0Font.load(pdfDocument, AppFiles.getThaiFont());

            // get the document catalog
            acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
            fontName = acroForm.getDefaultResources().add(font);

            return new ReturnLoadFont(acroForm, fontName);
        } catch (IOException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Unable to load 'Angsana New' font file");
            return null;
        }
    }

    private static void openPDFOnDefaultProgram(File f)
    {
        //show the generated form on the default pdf viewer
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
        {
            try
            {
                Desktop.getDesktop().open(f);
            } catch (IOException ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Error to open PDF file.");
            }

        } else
        {
            CtrAlertDialog.errorDialog("No support for opening files on this OS.");
        }
    }

    private static void printPDF(PDDocument p)
    {
        //shows the print dialog
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PDFPrintable(p));
        if (pj.printDialog())
        {
            try
            {
                pj.print();
            } catch (PrinterException ex)
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
    private static void adjustFontThaiField(ArrayList<PDTextField> listThaiFields, COSName fontName)
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
            afterFieldAppearance = "/" + fontName.getName() + subStringAppearance;

            thaiTextField.setDefaultAppearance(afterFieldAppearance);
        }

    }

}
