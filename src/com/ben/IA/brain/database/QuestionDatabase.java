/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.database;

import com.ben.IA.App.constants.IAConstants;
import com.ben.IA.ErrorLogger.ErrorLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 *
 * @author benoitdaccache
 */
public class QuestionDatabase implements WordDatabaseInterface {

    private Hashtable<String, ArrayList<String>> table;
    public static QuestionDatabase instance;

    public QuestionDatabase() {
        refreshDatabase();
        instance = this;
    }

    public static QuestionDatabase getInstance() {
        return instance;
    }

    public Hashtable<String, ArrayList<String>> getEntireDatabase() {
        return table;
    }

    public ArrayList<String> getSynonyms(String text) {
        if (table.get(text) != null)
            return table.get(text);
        else
            return new ArrayList<String>();
    }

    private void Serialize() {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            File f = new File(IAConstants.PATH_WORD_DATABASE);
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(table);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            ErrorLogger.getLogger().logError("", ex);
        }
    }

    public void refreshDatabase() {
        try {
            FileInputStream fisuser;
            ObjectInputStream oisuser;
            fisuser = new FileInputStream(IAConstants.PATH_WORD_DATABASE);
            oisuser = new ObjectInputStream(fisuser);
            table = (Hashtable<String, ArrayList<String>>) oisuser.readObject();
            oisuser.close();
            fisuser.close();
        } catch (FileNotFoundException ex) {
            table = new Hashtable<String, ArrayList<String>>();
        } catch (ClassNotFoundException ex) {
            table = new Hashtable<String, ArrayList<String>>();
        } catch (IOException ex) {
            table = new Hashtable<String, ArrayList<String>>();
        }
    }

    public void addSynonym(String word, String synonym) {
        if (table.get(word) == null)
            table.put(word, new ArrayList<String>());
        table.get(word).add(synonym);
        Serialize();
    }

    public void updateSynonyms(String word, ArrayList<String> synonym) {
        table.put(word, new ArrayList<String>());
        Iterator<String> it = synonym.iterator();
        while (it.hasNext())
            table.get(word).add(it.next());
        Serialize();
    }

    public void addWord(String word) {
        table.put(word, new ArrayList<String>());
        Serialize();
    }
}
