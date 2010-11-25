package com.ben.IA.Views.utilities;


import com.ben.IA.ErrorLogger.ErrorLogger;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bendaccache
 */
public class OpenFile {
    public static void openFile(File f){
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(f);
        } catch (IOException ex) {
            ErrorLogger.getLogger().logError("", ex);
        }

    }

    public static void openFile(String path){
        try {
            File f = new File(path);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(f);
        } catch (IOException ex) {
            ErrorLogger.getLogger().logError("", ex);
        }
    }

}
