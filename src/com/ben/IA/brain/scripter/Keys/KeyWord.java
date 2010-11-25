/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ben.IA.brain.scripter.Keys;

import java.util.Hashtable;

/**
 *
 * @author benoitdaccache
 */
public class KeyWord{

    public String read="read";
    public String write="write";
    public String ifCond="if";
    public String elseCond="else";
    public String thread="thread";
    public String sleep="sleep";
    public String ostime="ostime";
    public String function="function";
    public String whileLoop="while";
    public String returnStatement="return";
    public String begin="begin";
    public String end="end";

    public Hashtable<String, String> keyTable;
    private static KeyWord instance;

    
    public KeyWord(){
        instance =this;
    }

    public static KeyWord getInstance(){
        return instance;
    }


    public void setKeyWords(){
        
    }

    public void saveKeyWords(){

    }

    public void readKeyWord(){
        
    }
}
