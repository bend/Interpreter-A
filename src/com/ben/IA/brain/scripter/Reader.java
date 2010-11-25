/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter;

/**
 *
 * @author benoitdaccache
 */
public class Reader {

    private String script;

    public Reader(String script) {
        this.script = script;
    }

    public String readLine() {
        int i = script.indexOf("\n");
        if (i != -1) {
            String temp = script.substring(0, i);
            script = script.substring(i + 1);
            return temp.trim();
        }
        return script.trim();
    }

    boolean canRead() {
         return script.indexOf("\n") != -1 && script.length() > 0;
    }

    public String nextLine() {
        int i = script.indexOf("\n");
        if (i != -1)
            return script.substring(0, i).trim();
        return script.trim();
    }
}

