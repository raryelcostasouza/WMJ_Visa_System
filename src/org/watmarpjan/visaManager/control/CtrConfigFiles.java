package org.watmarpjan.visaManager.control;

import org.watmarpjan.visaManager.control.config.ConfigVassaDates;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author raryel
 */
public class CtrConfigFiles
{

    private ConfigVassaDates configVassaDates;

    public CtrConfigFiles()
    {
        System.out.println("Config Files Load Started ");
        configVassaDates = new ConfigVassaDates();
        System.out.println("Config Files Load Finished ");
    }

    public ConfigVassaDates getConfigVassaDates()
    {
        return configVassaDates;
    }
    
}
