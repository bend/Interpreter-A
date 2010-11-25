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
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author benoitdaccache
 */
public class Notes {
    private Hashtable<String, String> notes;
    private static Notes instance;

    public Notes(){
        refreshNotes();
        instance = this;
    }

    public static Notes getInstance(){
        return instance;
    }

    public void editNote(String title, String text) {
        notes.put(title, text);
        Serialize();
    }

    public boolean exists(String text) {
        Enumeration<String> e = notes.keys();
        while(e.hasMoreElements()){
            if(e.nextElement().equals(text))
                return true;
        }
        return false;
    }

    public Hashtable<String, String> getNotes() {
        return notes;
    }

    public void makeNote(String title, String text) {
        if(text!=null){
            notes.put(title, text);
            Serialize();
        }
    }

    public void removeNote(String noteTitle) {
        notes.remove(noteTitle);
        Serialize();
     }

     private void Serialize() {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            File f = new File(IAConstants.PATH_NOTES_DATABASE);
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(notes);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            ErrorLogger.getLogger().logError("", ex);
        }
    }

    public void refreshNotes() {
        try {
            FileInputStream fisuser;
            ObjectInputStream oisuser;
            fisuser = new FileInputStream(IAConstants.PATH_NOTES_DATABASE);
            oisuser = new ObjectInputStream(fisuser);
            notes = (Hashtable<String, String>) oisuser.readObject();
            oisuser.close();
            fisuser.close();
        } catch (FileNotFoundException ex) {
            notes  = new Hashtable<String, String>();
        } catch (ClassNotFoundException ex) {
            notes = new Hashtable<String,String>();
        } catch (IOException ex) {
            notes = new Hashtable<String, String>();
        }
    }



}
