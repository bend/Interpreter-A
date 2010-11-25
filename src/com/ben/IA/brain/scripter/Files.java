/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter;

import com.ben.IA.ErrorLogger.ScriptErrorException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author benoitdaccache
 */
public class Files {

    public static void performFileOperation(String readLine, HashMap buffer) throws ScriptErrorException {
        FileOutputStream fop = null;
        BufferedWriter writer = null;
        if (readLine.startsWith("writetofile(")) {//writetofile(file,string)
            String filevar = readLine.substring(readLine.indexOf("(") + 1, readLine.indexOf(","));
            if (buffer.containsKey(filevar))
                if (buffer.get(filevar) instanceof File)
                    try {
                        File f = (File) buffer.get(filevar);
                        fop = new FileOutputStream(f);
                        String text = readLine.substring(readLine.indexOf(",") + 1, readLine.indexOf(")"));
                        if (text.contains("\""))
                            text = text.substring(text.indexOf("\"") + 1, text.lastIndexOf("\""));
                        else if (buffer.containsKey(text))
                            text = (String) buffer.get(text);
                        else
                            throw new ScriptErrorException("<Error: variable " + text + "doesen't exists");
                        if (text.contains("\n"))
                            text.replace('\n', '\n');
                        fop.write(text.getBytes());
                    } catch (IOException ex) {
                        throw new ScriptErrorException(ex.getMessage());
                    } finally {
                        try {
                            fop.close();
                        } catch (IOException ex) {
                            throw new ScriptErrorException(ex.getMessage());
                        }
                    }
                else
                    throw new ScriptErrorException("<Error: variable " + filevar + " is not a file>");
            else
                throw new ScriptErrorException("<Error: variable " + filevar + " doesen't exists>");
        } else if (readLine.startsWith("appendtofile(")) {
            String filevar = readLine.substring(readLine.indexOf("(") + 1, readLine.indexOf(","));
            if (buffer.containsKey(filevar))
                if (buffer.get(filevar) instanceof File)
                    try {
                        File f = (File) buffer.get(filevar);
                        writer = new BufferedWriter(new FileWriter(f, true));
                        String text = readLine.substring(readLine.indexOf(",") + 1, readLine.indexOf(")"));
                        if (text.contains("\""))
                            text = text.substring(text.indexOf("\"") + 1, text.lastIndexOf("\""));
                        else if (buffer.containsKey(text))
                            text = (String) buffer.get(text);
                        else
                            throw new ScriptErrorException("<Error: variable " + text + "doesen't exists");
                        if (text.contains("\n"))
                            text.replaceAll("\n", "\n");
                        writer.write(text);
                    } catch (IOException ex) {
                        throw new ScriptErrorException(ex.getMessage());
                    } finally {
                        try {
                            writer.close();
                        } catch (IOException ex) {
                            throw new ScriptErrorException(ex.getMessage());
                        }
                    }
                else
                    throw new ScriptErrorException("<Error: variable " + filevar + " is not a file>");
            else
                throw new ScriptErrorException("<Error: variable " + filevar + " doesen't exists>");
        } else if (readLine.startsWith("deletefile(")) {
            String filevar = readLine.substring(readLine.indexOf("(") + 1, readLine.lastIndexOf(")"));
            if (buffer.containsKey(filevar))
                if (buffer.get(filevar) instanceof File)
                    ((File) buffer.get(filevar)).delete();
                else
                    throw new ScriptErrorException("<Error: variable " + filevar + "is not a file");
            else
                throw new ScriptErrorException("<Error: variable " + filevar + "doesen't exists");
        }
    }

    public static boolean isFileOperation(String readLine) {
        return readLine.startsWith("writetofile(") || readLine.startsWith("readfromfile(") || readLine.startsWith("deletefile") || readLine.startsWith("closefile(") || readLine.startsWith("appendtofile(");
    }

    public static void newFile(String readLine, HashMap buffer) throws ScriptErrorException {
        String var = readLine.substring(0, readLine.indexOf("=")).trim();
        if (readLine.contains("\"")) {
            int i = readLine.indexOf("\"") + 1;
            int j = readLine.lastIndexOf("\"");
            String path = readLine.substring(i, j);
            buffer.put(var, new File(path));
        } else {
            String var2 = readLine.substring(readLine.indexOf("(" + 1), readLine.indexOf(")"));
            if (buffer.containsKey(var2))
                buffer.put(var, new File((String) buffer.get(var2)));
            else
                throw new ScriptErrorException("<Error: variable " + var + " is not initialized");
        }

    }
}
