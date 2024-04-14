/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.watmarpjan.visaManager.model;

import java.time.LocalDate;

/**
 *
 * @author raryel
 */
public class ParsedVassaDates
{
    private final int year;
    private final LocalDate vassaStartDate;
    private final LocalDate vassaEndDate;

    public ParsedVassaDates(int year, LocalDate vassa_start, LocalDate vassa_end)
    {
        this.year = year;
        this.vassaStartDate = vassa_start;
        this.vassaEndDate = vassa_end;
    }

    public LocalDate getVassaEndDate()
    {
        return vassaEndDate;
    }

    public LocalDate getVassaStartDate()
    {
        return vassaStartDate;
    }

    public int getYear()
    {
        return year;
    }

    @Override
    public String toString()
    {
        return "{From: "+ getVassaStartDate().toString() + " To: " +getVassaEndDate().toString()+"}";
    }
}
