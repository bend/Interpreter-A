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
public class AnswerDatabase {

    public static AnswerDatabase instance;
    private Hashtable<String, ArrayList<String>> map;

    public AnswerDatabase() {
        refreshDatabase();
        instance = this;
    }

    public Hashtable<String, ArrayList<String>> getEntireDatabase() {
        return map;
    }

    public static AnswerDatabase getInstance() {
        return instance;
    }

    public void addWord(String word) {
        map.put(word.toLowerCase(), new ArrayList<String>());
        Serialize();
    }

    public int randomNumber(int min, int max) {
        return min + (int) (Math.random() * (max - min));
    }

    public String answer(String key) {
        try {
            ArrayList<String> answer = map.get(key.toLowerCase());
            int rand = randomNumber(0, answer.size() - 1);
            return answer.get(rand);
        } catch (NullPointerException e) {
            return key;
        }
    }

    public void updateSynonyms(String word, ArrayList<String> synonym) {
        map.put(word, new ArrayList<String>());
        Iterator<String> it = synonym.iterator();
        while (it.hasNext())
            map.get(word).add(it.next().toLowerCase());
        Serialize();

    }

    public void refreshDatabase() {
        try {
            FileInputStream fisuser;
            ObjectInputStream oisuser;
            fisuser = new FileInputStream(IAConstants.PATH_WORD_ANSWER);
            oisuser = new ObjectInputStream(fisuser);
            map = (Hashtable<String, ArrayList<String>>) oisuser.readObject();
            oisuser.close();
            fisuser.close();
        } catch (FileNotFoundException ex) {
            map = new Hashtable<String, ArrayList<String>>();
        } catch (ClassNotFoundException ex) {
            map = new Hashtable<String, ArrayList<String>>();
        } catch (IOException ex) {
            map = new Hashtable<String, ArrayList<String>>();
        }
    }

    private void Serialize() {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            File f = new File(IAConstants.PATH_WORD_ANSWER);
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(map);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            ErrorLogger.getLogger().logError("", ex);
        }
    }
}
