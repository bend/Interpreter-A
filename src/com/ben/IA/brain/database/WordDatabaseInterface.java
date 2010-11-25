/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ben.IA.brain.database;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author benoitdaccache
 */
public interface WordDatabaseInterface {
    
    public Hashtable<String, ArrayList<String>> getEntireDatabase();
    public ArrayList<String> getSynonyms(String text);
    public void addSynonym(String word, String syonym);
    public void addWord(String word);

}
