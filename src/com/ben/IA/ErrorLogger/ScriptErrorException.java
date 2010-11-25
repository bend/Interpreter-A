/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ben.IA.ErrorLogger;

/**
 *
 * @author benoitdaccache
 */
public class ScriptErrorException extends Exception{
    public ScriptErrorException(String Message){
        super(Message);
    }
}
