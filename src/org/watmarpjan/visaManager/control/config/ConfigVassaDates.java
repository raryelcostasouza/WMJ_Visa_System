/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.control.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import org.watmarpjan.visaManager.AppFiles;
import org.watmarpjan.visaManager.gui.util.CtrAlertDialog;
import org.watmarpjan.visaManager.model.ParsedVassaDates;

/**
 *
 * @author raryel
 */
public class ConfigVassaDates
{

    private HashMap<Integer, ParsedVassaDates> dictVassaDates;

    public ConfigVassaDates()
    {
        ArrayList<LocalDate> listRawVassaRelatedDatesFromCSV;
        ArrayList<ParsedVassaDates> listParsedVassaDates;
        File fCSV;

        dictVassaDates = new HashMap<>();
        fCSV = AppFiles.getVassaDatesCSV();
        if (fCSV.exists())
        {
            try
            {
                listRawVassaRelatedDatesFromCSV = readCSVFile(fCSV);
                listParsedVassaDates = parseVassaDates(listRawVassaRelatedDatesFromCSV);
                loadHashMapVassaDates(listParsedVassaDates);
            }
            catch (IOException ex)
            {
                CtrAlertDialog.exceptionDialog(ex, "Unable to load vassa dates config file: " + fCSV.toString());
            }
        }
        else
        {
            CtrAlertDialog.errorDialog("Unable to find vassa dates config file: " + fCSV.toString());
        }
    }

    private ArrayList<LocalDate> readCSVFile(File fCSV) throws IOException
    {
        ArrayList<LocalDate> listRawDates = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fCSV));
        
        String line = "";
        while ((line = br.readLine()) != null)
        {
            //filters only the rows that contain vassa begin and end dates
            if (line.contains("First") || line.contains("Last"))
            {
                listRawDates.add(extractDateFromRow(line));
            }
        }

        return listRawDates;

    }

    private LocalDate extractDateFromRow(String csvRow)
    {
        //on CSV date is the first field before the first comma, extracting only that
        String strDate;

        strDate = csvRow.substring(0, csvRow.indexOf(","));

        return LocalDate.parse(strDate);
    }

    private ArrayList<ParsedVassaDates> parseVassaDates(ArrayList<LocalDate> listDates)
    {
        ArrayList<ParsedVassaDates> listVassaDates = new ArrayList<>();

        for (int i = 0; i < listDates.size(); i += 2)
        {
            LocalDate firstDay = listDates.get(i);
            LocalDate lastDay = listDates.get(i + 1);

            listVassaDates.add(new ParsedVassaDates(firstDay.getYear(), firstDay, lastDay));
        }
        return listVassaDates;

    }

    private void loadHashMapVassaDates(ArrayList<ParsedVassaDates> listParsedVassaDates)
    {
        for (ParsedVassaDates objVassaDates : listParsedVassaDates)
        {
            dictVassaDates.put(objVassaDates.getYear(), objVassaDates);
        }
        System.out.println("VassaDatesLoaded:");
        System.out.println(dictVassaDates.toString());
    }

    public HashMap<Integer, ParsedVassaDates> getDictVassaDates()
    {
        return dictVassaDates;
    }
}
