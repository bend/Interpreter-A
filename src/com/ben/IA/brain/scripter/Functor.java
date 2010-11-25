/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter;

import com.ben.IA.ErrorLogger.ScriptErrorException;
import com.ben.IA.brain.scripter.Keys.KeyWord;

/**
 *
 * @author benoitdaccache
 */
public class Functor {

    public static void sleep(String readLine) throws ScriptErrorException {
        try {
            String time = readLine.substring(readLine.indexOf("(") + 1, readLine.indexOf(")"));
            Thread.sleep(Long.parseLong(time));
        } catch (InterruptedException ex) {
            throw new ScriptErrorException("< " + ex.getMessage() + " >");
        } catch (NumberFormatException e) {
            throw new ScriptErrorException("<Error: expected number>");
        }
    }

    public static boolean isOsTime(String readLine) {
        return readLine.startsWith(KeyWord.getInstance().ostime+"(");
    }

    public static boolean isSleep(String readLine) {
        return readLine.startsWith(KeyWord.getInstance().sleep+"(");
    }

    public static void osTime(String readLine) throws ScriptErrorException {
        String a = readLine.substring(readLine.indexOf("(") + 1, readLine.indexOf(")"));
        Interpreter.putInBuffer(a, System.currentTimeMillis());
    }
}
