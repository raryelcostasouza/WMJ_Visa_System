/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.util;

import java.io.File;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author WMJ_user
 */
public class Util
{

    public final static DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public final static DateTimeFormatter CHANGELOG_DATE_TIME_FORMAT_ = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public final static DateTimeFormatter DEFAULT_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public final static DateTimeFormatter TIMESTAMP_FILE_NAME = DateTimeFormatter.ofPattern("yyyy-MM-dd-kk'h'mm'm'ss's'");
    private final static Locale THAI_LOCALE = new Locale("th", "TH", "TH");
    private final static NumberFormat numberFormatterThai = NumberFormat.getNumberInstance(THAI_LOCALE);
    
    public static String getLastElement(List<String> list)
    {
        int indexLastElement;

        indexLastElement = list.size() - 1;
        return list.get(indexLastElement);
    }

    //creates a new arraylist with the distinct values from the input arraylist
    public static ArrayList<String> filterDistinctElement(ArrayList<String> list)
    {
        ArrayList<String> listDistinct;
        String previousElement, currentElement;

        listDistinct = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
        {
            currentElement = list.get(i);
            if (i == 0)
            {
                listDistinct.add(currentElement);
            }
            else
            {
                previousElement = list.get(i - 1);
                if (!currentElement.equals(previousElement))
                {
                    listDistinct.add(currentElement);
                }
            }
        }
        return listDistinct;
    }

    public static Integer convertYearToThai(Integer iso_year)
    {
        if (iso_year != null)
        {
            return iso_year + 543;
        }
        else
        {
            return null;
        }

    }

    public static LocalDate convertDateToLocalDate(Date d)
    {
        if (d != null)
        {
            return Instant.ofEpochMilli(d.getTime())
                    .atZone(ZoneId.systemDefault()).
                    toLocalDate();
        }
        else
        {
            return null;
        }

    }

    public static String toStringThaiDateFormat(LocalDate ld)
    {
        if (ld != null)
        {
            return ThaiBuddhistDate.from(ld).format(DEFAULT_DATE_FORMAT);
        }
        else
        {
            return null;
        }
    }

    public static String toStringThaiDateFormat(Date d)
    {
        LocalDate ld;

        ld = convertDateToLocalDate(d);
        if (ld != null)
        {
            return ThaiBuddhistDate.from(ld).format(DEFAULT_DATE_FORMAT);
        }
        else
        {
            return null;
        }
    }
    
    public static String toStringThaiDateFormatMonthText(LocalDate ld)
    {
        String monthNameThai;
        
        if (ld != null)
        {
            monthNameThai = convertMonthToThaiLang(ld);
            return ld.getDayOfMonth() + " " + monthNameThai + " " + Util.convertYearToThai(ld.getYear());
        }
        else
        {
            return null;
        }
    }
    
    public static String toStringThaiDateFormatMonthText(Date d)
    {
        LocalDate ld;
        
        if (d != null)
        {
            ld = Util.convertDateToLocalDate(d);
            return toStringThaiDateFormatMonthText(ld);
        }
        else
        {
            return null;
        }
    }
    
    public static String convertDayToThaiDigits(LocalDate ld)
    {
        return (ld == null) ? null : numberFormatterThai.format(ld.getDayOfMonth());
    }
    
    public static String convertMonthToThaiLang(LocalDate ld)
    {
        return (ld == null) ? null : ld.getMonth().getDisplayName(TextStyle.FULL, new Locale("th"));
    }
    
    public static String convertYearToThaiDigits(LocalDate ld)
    {
        //removes the thousands separator char
        numberFormatterThai.setGroupingUsed(false);
        
        return (ld == null) ? null : numberFormatterThai.format(convertYearToThai(ld.getYear()));
    }
    
    public static String convertNumberToThaiDigits(long i)
    {
        return numberFormatterThai.format(i);
    }

    public static Date convertLocalDateToDate(LocalDate ld)
    {
        GregorianCalendar gc;

        if (ld != null)
        {
            gc = new GregorianCalendar(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
            return gc.getTime();
        }
        else
        {
            return null;
        }
    }

    public static String getFileExtension(File f)
    {
        String extension = "";
        int i = f.getName().lastIndexOf('.');

        if (i >= 0)
        {
            extension = f.getName().substring(i + 1);
        }

        return extension;
    }

    public static String getFileNameWithoutExtension(File f)
    {
        String fileNameWithoutExtension = "";
        int i = f.getName().lastIndexOf('.');

        if (i >= 0)
        {
            fileNameWithoutExtension = f.getName().substring(0, i);
        }

        return fileNameWithoutExtension;
    }
    
    //if the input string is null
    //returns empty string
    //otherwise
    //returns the input string
    public static String filterStringNull(String input)
    {
        if (input == null)
        {
            return "";
        }
        else
        {
            return input;
        }
    }
}
