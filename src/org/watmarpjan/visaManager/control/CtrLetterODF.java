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
import org.watmarpjan.visaManager.AppFileNames;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.LetterInputData;
import org.watmarpjan.visaManager.model.hibernate.Embassy;
import org.watmarpjan.visaManager.model.hibernate.Monastery;
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

    private static void generateLetterCommonMonasticFields(TextDocument objTD, MonasticProfile p) throws InvalidNavigationException
    {
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

        generateLetterCommonMonasticFields(objTD, objLetterInput.getMonasticProfile());
        searchNReplace(objTD, "«titleEN»", ProfileUtil.getTitleEN(p));
    }

    private static void generateLetterLaypersonAbroadEmbassyEN(TextDocument objTD, LetterInputData objLetterInput) throws InvalidNavigationException
    {
        MonasticProfile p;

        p = objLetterInput.getMonasticProfile();

        generateLetterCommonMonasticFields(objTD, objLetterInput.getMonasticProfile());
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

        generateLetterCommonMonasticFields(objTD, objLetterInput.getMonasticProfile());
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

        generateLetterCommonMonasticFields(objTD, objLetterInput.getMonasticProfile());
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

    private static void saveLetter(TextDocument objTD, MonasticProfile p, File fTemplate)
    {
        Path pProfileLetterStorage;
        String fileNameGeneratedLetter;
        File fDestination;

        fileNameGeneratedLetter = Util.getFileNameWithoutExtension(fTemplate) + generateLetterFileNameSuffix(p) + ".odt";
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
    
    public static void generateLetterGeneric(File fTemplate, MonasticProfile p, LetterInputData objLetterInput)
    {
        try
        {
            TextDocument objTD;
           
            // LetterInput Object is only set for new visa because of extra data needed
           if (objLetterInput != null)
           {
               objTD = generateLetterNewVisa(fTemplate, objLetterInput);
           }
           else
           {
               objTD = generateLetterVisaExt(p, fTemplate);
           }
            
            saveLetter(objTD, p, fTemplate);

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
     
    private static TextDocument generateLetterVisaExt(MonasticProfile p, File fTemplate) throws InvalidNavigationException, Exception
    {
        TextDocument objTD = TextDocument.loadDocument(fTemplate);
        switch(fTemplate.getName())
        {
            case AppFileNames.ODT_LETTER_GUARANTEE_SNP:
                generateLetterGuaranteeSNP(objTD, p);
                break;
            case AppFileNames.ODT_LETTER_EXT_SNP:
                break;
            case AppFileNames.ODT_LETTER_EXT_IMM:
                break;
        }
        
        return objTD;
    }
    
    public static void generateLetterGuaranteeSNP(TextDocument objTD, MonasticProfile p) throws InvalidNavigationException
    {
        Monastery mResidence;
        String monasteryAddr;
        
        generateLetterCommonMonasticFields(objTD, p);
        
        mResidence = p.getMonasteryResidingAt();
        monasteryAddr = mResidence.getMonasteryName() + " " 
                        + mResidence.getAddrTambon() + " "
                        + mResidence.getAddrAmpher() + " "
                        + mResidence.getAddrJangwat();
       
        //need to use thai numbers
        searchNReplace(objTD, "«titleTH2»", ProfileUtil.getTitleTH2(p));
        searchNReplace(objTD, "«ageThai»", ProfileUtil.getStrAge(p.getBirthDate()));
        searchNReplace(objTD, "«WatResidingAtThai_addrTambon_addrAmpher_addrJangwat»", monasteryAddr);
    }
    
    private static TextDocument generateLetterNewVisa(File fTemplate, LetterInputData objLetterInput) throws InvalidNavigationException, Exception
    {
        TextDocument objTD = TextDocument.loadDocument(fTemplate);
        switch (fTemplate.getName())
        {
            case AppFileNames.ODT_LETTER_NEW_VISA_NON_IMM_LAYPERSON_ABROAD_EMBASSY:
                generateLetterLaypersonAbroadEmbassy(objTD, objLetterInput);
                break;
            case AppFileNames.ODT_LETTER_NEW_VISA_NON_IMM_LAYPERSON_ABROAD_EMBASSY_EN:
                generateLetterLaypersonAbroadEmbassyEN(objTD, objLetterInput);
                break;
            case AppFileNames.ODT_LETTER_NEW_VISA_NON_IMM_LAYPERSON_THAILAND_VIENTIANE_EMBASSY:
                generateLetterLaypersonThailandVientianeEmbassy(objTD, objLetterInput);
                break;
            case AppFileNames.ODT_LETTER_NEW_VISA_NON_IMM_MONASTIC_ABROAD_EMBASSY:
                generateLetterMonasticAbroadEmbassy(objTD, objLetterInput);
                break;
            case AppFileNames.ODT_LETTER_NEW_VISA_NON_IMM_MONASTIC_ABROAD_EMBASSY_EN:
                generateLetterMonasticAbroadEmbassyEN(objTD, objLetterInput);
                break;
            case AppFileNames.ODT_LETTER_NEW_VISA_NON_IMM_MONASTIC_ABROAD_SNP:
                generateLetterMonasticAbroadSamnakPut(objTD, objLetterInput);
                break;
        }    
        return objTD;
    }
}
