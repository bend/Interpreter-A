/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.ErrorLogger;


import com.ben.IA.App.constants.IAConstants;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bendaccache
 */
public class ErrorLogger {

    private static ErrorLogger logger;

    public ErrorLogger() {
        logger = this;
    }

    public static ErrorLogger getLogger() {
        return logger;
    }

    public void logError(String s, Exception e) {
        try {
            Calendar date = Calendar.getInstance();
            File f = new File(IAConstants.PATH_TO_LOG);
            BufferedWriter out = new BufferedWriter(new FileWriter(f, true)); //true pour pas que l'on ecrase le fichier
            out.write(date.getTime().toString());
            out.write("\n");
            out.write(s);
            out.write(e.getMessage() + "\n");
            for(int i =0; i<e.getStackTrace().length ; i++)
                out.write(e.getStackTrace()[i].toString()+"\n");
            out.write("\n");
            out.flush();
            out.close();
        } catch (IOException ex) {
            
        }
    }

    public void log(String s) {
        File f = new File(IAConstants.PATH_TO_LOG);
        try {
            Calendar date = Calendar.getInstance();
            System.out.println("\r\r" +"on "+date.getTime()+" : \n"+ s + "\n>>");
            BufferedWriter out = new BufferedWriter(new FileWriter(f, true)); //true pour pas que l'on ecrase le fichier
            out.write(date.getTime().toString());
            out.write("\n");
            out.write(s);
            out.write("\n");
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ErrorLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
