/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.common.navigation.InvalidNavigationException;
import org.odftoolkit.simple.common.navigation.TextNavigation;
import org.odftoolkit.simple.common.navigation.TextSelection;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.LetterInputData;
import org.watmarpjan.visaManager.model.hibernate.Embassy;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

public class CtrLetterODF
{

    public static void searchNReplace(TextDocument doc, String search, String replace) throws InvalidNavigationException
    {
        TextNavigation search2 = null;

        if (replace != null)
        {
            search2 = new TextNavigation(search, doc);

            while (search2.hasNext())
            {
                TextSelection item = (TextSelection) search2.nextSelection();

                item.replaceWith(replace);
            }
        }
        else
        {
            CtrAlertDialog.warningDialog("Field Missing: " + search);
        }

    }

    private static String generateLetterFileNameSuffix(MonasticProfile p)
    {
        return p.getMonasticName() + p.getLastName() + "-" + LocalDateTime.now().format(Util.TIMESTAMP_FILE_NAME);
    }

    private static void generateLetterCommonMonasticFields(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        MonasticProfile p;

        p = objLetterInput.getMonasticProfile();

        searchNReplace(objTD, "«name»", p.getMonasticName());
        if (p.getMiddleName() != null)
        {
            searchNReplace(objTD, "«middleName»", p.getMiddleName());
        }
        else
        {
            searchNReplace(objTD, "«middleName»", "");
        }
        
        searchNReplace(objTD, "«lastName»", p.getLastName());
        searchNReplace(objTD, "«nationality»", p.getNationality());
        searchNReplace(objTD, "«passportNumber»", p.getPassportNumber());

    }

    private static void generateLetterCommonEmbassyFields(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        Embassy e;

        e = objLetterInput.getEmbassy();
        searchNReplace(objTD, "«nameEmbassyEnglish»", e.getNameEn());
        searchNReplace(objTD, "«addressEmbassyLine1»", e.getAddressLine1());
        searchNReplace(objTD, "«addressEmbassyLine2»", e.getAddressLine2());
        
        if (e.getAddressLine3() != null)
        {
            searchNReplace(objTD, "«addressEmbassyLine3»", e.getAddressLine3());
        }
        else
        {
            searchNReplace(objTD, "«addressEmbassyLine3»", "");
        }
        if (e.getAddressLine4() != null)
        {
            searchNReplace(objTD, "«addressEmbassyLine4»", e.getAddressLine4());
        }
        else
        {
            searchNReplace(objTD, "«addressEmbassyLine4»", "");
        }
        
    }

    private static void generateLetterLaypersonAbroadEmbassy(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        MonasticProfile p;

        p = objLetterInput.getMonasticProfile();

        generateLetterCommonMonasticFields(objTD, objLetterInput);
        searchNReplace(objTD, "«titleEN»", ProfileUtil.getTitleEN(p));
    }

    private static void generateLetterLaypersonAbroadEmbassyEN(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        MonasticProfile p;

        p = objLetterInput.getMonasticProfile();

        generateLetterCommonMonasticFields(objTD, objLetterInput);
        generateLetterCommonEmbassyFields(objTD, objLetterInput);

        searchNReplace(objTD, "«titleEN»", ProfileUtil.getTitleEN(p));

    }

    private static void generateLetterLaypersonThailandVientianeEmbassy(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        generateLetterLaypersonAbroadEmbassy(objTD, objLetterInput);
    }

    private static void generateLetterMonasticAbroadEmbassy(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        MonasticProfile p;
        Embassy e;

        p = objLetterInput.getMonasticProfile();
        e = objLetterInput.getEmbassy();

        generateLetterCommonMonasticFields(objTD, objLetterInput);
        searchNReplace(objTD, "«titleTH»", ProfileUtil.getTitleTH(p));
        searchNReplace(objTD, "«titleTH2»", ProfileUtil.getTitleTH2(p));

        searchNReplace(objTD, "«OrdinationTypeThai»", ProfileUtil.getOrdinationType(p));
        searchNReplace(objTD, "«firstArrivalDateThai»", Util.toStringThaiDateFormatMonthText(p.getFirstEntryDate()));
        searchNReplace(objTD, "«departureDateThai»", Util.toStringThaiDateFormatMonthText(objLetterInput.getLdDepartureDateThai()));
        searchNReplace(objTD, "«countryEmbassyThai»", e.getCountry());
    }

    private static void generateLetterMonasticAbroadEmbassyEN(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException, Exception
    {
        MonasticProfile p;

        p = objLetterInput.getMonasticProfile();

        generateLetterCommonMonasticFields(objTD, objLetterInput);
        generateLetterCommonEmbassyFields(objTD, objLetterInput);

        searchNReplace(objTD, "«titleEN»", ProfileUtil.getTitleEN(p));
        searchNReplace(objTD, "«titleEN2»", ProfileUtil.getTitleEN2(p));
        searchNReplace(objTD, "«paliNameEnglish»", p.getPaliNameEnglish());
    }

    private static void generateLetterMonasticAbroadSamnakPut(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        Embassy e;
        MonasticProfile p;

        e = objLetterInput.getEmbassy();
        p = objLetterInput.getMonasticProfile();

        generateLetterCommonEmbassyFields(objTD, objLetterInput);
        generateLetterMonasticAbroadEmbassy(objTD, objLetterInput);
        searchNReplace(objTD, "«nameEmbassyThai»", e.getNameTh());
        searchNReplace(objTD, "«contactEmail»", p.getEmail());
        searchNReplace(objTD, "«contactPhone»", objLetterInput.getPhoneAbroad());
        searchNReplace(objTD, "«addressLine1»", objLetterInput.getAddrMonasticLine1());
        searchNReplace(objTD, "«addressLine2»", objLetterInput.getAddrMonasticLine2());
        searchNReplace(objTD, "«addressLine3»", objLetterInput.getAddrMonasticLine3());
        searchNReplace(objTD, "«addressLine4»", objLetterInput.getAddrMonasticLine4());
    }

    private static void saveLetter(TextDocument objTD, MonasticProfile p, String fileNameTemplateWithoutExtension)
    {
        Path pProfileLetterStorage;
        String fileNameGeneratedLetter;
        File fDestination;

        fileNameGeneratedLetter = fileNameTemplateWithoutExtension + generateLetterFileNameSuffix(p) + ".odt";
        pProfileLetterStorage = AppPaths.getPathToProfileLetters(p.getNickname());
        fDestination = pProfileLetterStorage.resolve(fileNameGeneratedLetter).toFile();

        try
        {
            if (!pProfileLetterStorage.toFile().exists())
            {
                pProfileLetterStorage.toFile().mkdirs();
            }
            objTD.save(fDestination);
            CtrFileOperation.openFileOnDefaultProgram(fDestination);
        }
        catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Unable to save generated Letter.");
        }
    }

    public static void generateLetter(String letterSelected, MonasticProfile p, LetterInputData objLetterInput)
    {
        File fLetterTemplate;
        String monasteryNickname;
        String fileNameTemplateWithoutExtension;

        monasteryNickname = objLetterInput.getMonasticProfile().getMonasteryResidingAt().getMonasteryNickname();
        fileNameTemplateWithoutExtension = "NonImm" + letterSelected.replaceAll("[-  ]+", "");
        fLetterTemplate = AppPaths.getPathToLetterTemplate(monasteryNickname).resolve(fileNameTemplateWithoutExtension + ".odt").toFile();
        try
        {
            TextDocument objTD = TextDocument.loadDocument(fLetterTemplate);
            switch (fileNameTemplateWithoutExtension)
            {
                case "NonImmLaypersonAbroadEmbassy":
                    generateLetterLaypersonAbroadEmbassy(objTD, objLetterInput);
                    break;
                case "NonImmLaypersonAbroadEmbassyEN":
                    generateLetterLaypersonAbroadEmbassyEN(objTD, objLetterInput);
                    break;
                case "NonImmLaypersonThailandVientianeEmbassy":
                    generateLetterLaypersonThailandVientianeEmbassy(objTD, objLetterInput);
                    break;
                case "NonImmMonasticAbroadEmbassy":
                    generateLetterMonasticAbroadEmbassy(objTD, objLetterInput);
                    break;
                case "NonImmMonasticAbroadEmbassyEN":
                    generateLetterMonasticAbroadEmbassyEN(objTD, objLetterInput);
                    break;
                case "NonImmMonasticAbroadSamnakPut":
                    generateLetterMonasticAbroadSamnakPut(objTD, objLetterInput);
                    break;
            }
            saveLetter(objTD, objLetterInput.getMonasticProfile(), fileNameTemplateWithoutExtension);

        }
        catch (InvalidNavigationException ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Error while search/replacing fields on letter template.");
        }
        catch (Exception ex)
        {
            CtrAlertDialog.exceptionDialog(ex, "Unable to generate letter.");
        }

    }
}
