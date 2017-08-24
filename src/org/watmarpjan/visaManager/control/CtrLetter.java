/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.control;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.watmarpjan.visaManager.AppPaths;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.LetterInputData;
import org.watmarpjan.visaManager.model.hibernate.Embassy;
import org.watmarpjan.visaManager.model.hibernate.MonasticProfile;
import org.watmarpjan.visaManager.util.ProfileUtil;
import org.watmarpjan.visaManager.util.Util;

/**
 *
 * @author KroobaHariel
 */
public class CtrLetter
{

    private static String[][] fillMatrixData(LetterInputData objLetterInput)
    {
        String[][] data2CSV = new String[2][27];
        MonasticProfile p;
        Embassy e;
        LocalDate ldDepartureThai;
        
        p = objLetterInput.getMonasticProfile();
        e = objLetterInput.getEmbassy();
        
        data2CSV[0][0] = "titleEN";
        data2CSV[1][0] = ProfileUtil.getTitleEN(p);

        data2CSV[0][1] = "titleTH2";
        data2CSV[1][1] = ProfileUtil.getTitleTH2(p);

        data2CSV[0][2] = "OrdinationTypeThai";
        data2CSV[1][2] = ProfileUtil.getOrdinationType(p);

        data2CSV[0][3] = "titleTH";
        data2CSV[1][3] = ProfileUtil.getTitleTH(p);

        data2CSV[0][4] = "titleEN2";
        data2CSV[1][4] = ProfileUtil.getTitleEN2(p);

        data2CSV[0][5] = "name";
        data2CSV[1][5] = p.getMonasticName();

        data2CSV[0][6] = "middleName";
        data2CSV[1][6] = Util.filterStringNull(p.getMiddleName());

        data2CSV[0][7] = "lastName";
        data2CSV[1][7] = p.getLastName();

        data2CSV[0][8] = "paliNameEnglish";
        data2CSV[1][8] = p.getPaliNameEnglish();

        data2CSV[0][9] = "nationality";
        data2CSV[1][9] = p.getNationality();

        data2CSV[0][10] = "passportNumber";
        data2CSV[1][10] = p.getPassportNumber();

        data2CSV[0][11] = "addressLine1";
        data2CSV[1][11] = Util.filterStringNull(objLetterInput.getAddrMonasticLine1());
        

        data2CSV[0][12] = "addressLine2";
        data2CSV[1][12] = Util.filterStringNull(objLetterInput.getAddrMonasticLine2());

        data2CSV[0][13] = "addressLine3";
        data2CSV[1][13] =Util.filterStringNull(objLetterInput.getAddrMonasticLine3());

        data2CSV[0][14] = "addressLine4";
        data2CSV[1][14] = Util.filterStringNull(objLetterInput.getAddrMonasticLine4());

        data2CSV[0][15] = "contactEmail";
        data2CSV[1][15] = p.getEmail();

        data2CSV[0][16] = "contactPhone";
        data2CSV[1][16] = Util.filterStringNull(objLetterInput.getPhoneAbroad());

        if (e != null)
        {
            data2CSV[0][17] = "nameEmbassyThai";
            data2CSV[1][17] = e.getNameTh();

            data2CSV[0][18] = "nameEmbassyEnglish";
            data2CSV[1][18] = e.getNameEn();

            data2CSV[0][19] = "countryEmbassyThai";
            data2CSV[1][19] = e.getCountry();

            data2CSV[0][20] = "addressEmbassyLine1";
            data2CSV[1][20] = Util.filterStringNull(e.getAddressLine1());

            data2CSV[0][21] = "addressEmbassyLine2";
            data2CSV[1][21] = Util.filterStringNull(e.getAddressLine2());

            data2CSV[0][22] = "addressEmbassyLine3";
            data2CSV[1][22] = Util.filterStringNull(e.getAddressLine3());

            data2CSV[0][23] = "addressEmbassyLine4";
            data2CSV[1][23] = Util.filterStringNull(e.getAddressLine4());
        }
        else
        {
            data2CSV[0][17] = "nameEmbassyThai";
            data2CSV[1][17] = "";

            data2CSV[0][18] = "nameEmbassyEnglish";
            data2CSV[1][18] = "";

            data2CSV[0][19] = "countryEmbassyThai";
            data2CSV[1][19] = "";

            data2CSV[0][20] = "addressEmbassyLine1";
            data2CSV[1][20] = "";

            data2CSV[0][21] = "addressEmbassyLine2";
            data2CSV[1][21] = "";

            data2CSV[0][22] = "addressEmbassyLine3";
            data2CSV[1][22] = "";

            data2CSV[0][23] = "addressEmbassyLine4";
            data2CSV[1][23] = "";
        }

        data2CSV[0][24] = "departureDateThai";
        ldDepartureThai = objLetterInput.getLdDepartureDateThai();
        if (ldDepartureThai != null)
        {
            data2CSV[1][24] = Util.toStringThaiDateFormatMonthText(ldDepartureThai);
        }
        else
        {
            data2CSV[1][24] = "";
        }

        data2CSV[0][25] = "firstArrivalDateThai";
        data2CSV[1][25] = Util.toStringThaiDateFormatMonthText(p.getFirstEntryDate());

        data2CSV[0][26] = "destinationPath";
        data2CSV[1][26] = AppPaths.getPathToProfileLetters(p.getNickname()).toAbsolutePath().toString();
        
        return data2CSV;
    }

    private static String array2CSVString(String[] data)
    {
        String lineCSV;
        int i;

        lineCSV = "";
        for (i = 0; i < data.length - 1; i++)
        {
            lineCSV += data[i] + ", ";
        }
        lineCSV += data[i];
        return lineCSV;
    }

    private static List<String> matrix2CSVString(String[][] data)
    {
        LinkedList<String> linesCSV = new LinkedList<String>();

        linesCSV.add(array2CSVString(data[0]));
        linesCSV.add(array2CSVString(data[1]));

        return linesCSV;
    }

    public static int generateCSV(MonasticProfile p, String[][] data)
    {
        Path pFolderLetterTemplate, pCSV;
        List<String> linesCSV;

        linesCSV = matrix2CSVString(data);
        pFolderLetterTemplate = AppPaths.getPathToLetterTemplate();

        pCSV = pFolderLetterTemplate.resolve("letterInput.csv");
        try
        {
            //deletes the file if it previously exists
            if (pCSV.toFile().exists())
            {
                Files.delete(pCSV);
            }
            Files.write(pCSV, linesCSV);

            return 0;
        }
        catch (Exception ioex)
        {
            CtrAlertDialog.exceptionDialog(ioex, "Unable to save letterInput.csv file.");
            return -1;
        }

    }

    public static void generateLetter(String letterSelected, MonasticProfile p, LetterInputData objLetterInput)
    {
        Process pCMD;
        Path pFolderLetterTemplate, pProfileLetterStorage;
        String fileName;
        String[][] matrixData;
        int output;

        fileName = "NonImm" + letterSelected.replaceAll("[-  ]+", "") + ".dotm";
        pFolderLetterTemplate = AppPaths.getPathToLetterTemplate();
        pProfileLetterStorage = AppPaths.getPathToProfileLetters(p.getNickname());

        matrixData = fillMatrixData(objLetterInput);
        output = generateCSV(p, matrixData);
        if (output == 0)
        {
            try
            {
                if (!pProfileLetterStorage.toFile().exists())
                {
                    pProfileLetterStorage.toFile().mkdirs();
                }

                //Run the word macro
                pCMD = Runtime.getRuntime().exec("cmd /c start /wait winword /embedded /q " + fileName + " /mvisaLetterGenerator", null, pFolderLetterTemplate.toFile());
                pCMD.waitFor(5, TimeUnit.SECONDS);
            }
            catch (Exception ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Unable to generate letter.");
            }
        }
    }
}
