/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.util;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author WMJ_user
 */
public class Util
{

    public final static DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate convertDateToLocalDate(Date d)
    {
        if (d != null)
        {
            return Instant.ofEpochMilli(d.getTime())
                    .atZone(ZoneId.systemDefault()).
                    toLocalDate();
        } else
        {
            return null;
        }

    }

    public static Date convertLocalDateToDate(LocalDate ld)
    {
        GregorianCalendar gc;

        if (ld != null)
        {
            gc = new GregorianCalendar(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
            return gc.getTime();
        } else
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
}
