/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.stampedPage.output;

/**
 *
 * @author adhipanyo
 */
public class InfoGenericScanStampedPage
{
    private String leftPageNumber;

    public InfoGenericScanStampedPage(String leftPageNumber)
    {
        this.leftPageNumber = leftPageNumber;
    }

    public String getLeftPageNumber()
    {
        return leftPageNumber;
    }
    
}
