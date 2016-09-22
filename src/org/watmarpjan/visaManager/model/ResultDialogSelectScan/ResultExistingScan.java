/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.watmarpjan.visaManager.model.ResultDialogSelectScan;

import org.watmarpjan.visaManager.model.hibernate.PassportScan;

/**
 *
 * @author WMJ_user
 */
public class ResultExistingScan extends AbstractResultDialogSelectScan
{

    private final PassportScan existingPassportScan;

    public ResultExistingScan(PassportScan existingPassportScan)
    {
        this.existingPassportScan = existingPassportScan;
    }

    public PassportScan getExistingPassportScan()
    {
        return existingPassportScan;
    }

}
