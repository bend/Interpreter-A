/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ben.IA.brain;

import com.ben.IA.App.constants.IAConstants;
import com.ben.IA.ErrorLogger.ErrorLogger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author benoitdaccache
 */
public class BrainSettings {
    private BrainIdentity identity;


    public BrainSettings(){
    }

    public void newIdentity(String name) {
        identity = new BrainIdentity(name);
        Serialize();
    }

    public BrainIdentity refreshIdentity() {
        BrainIdentity ident = null;
        try {
            FileInputStream fisuser;
            ObjectInputStream oisuser;
            fisuser = new FileInputStream(IAConstants.PATH_IDENTITY);
            oisuser = new ObjectInputStream(fisuser);
            ident = (BrainIdentity) oisuser.readObject();
            oisuser.close();
            fisuser.close();
        } catch (FileNotFoundException ex) {
            Brain.getBrain().restart();
            return null;
        } catch (ClassNotFoundException ex) {
            Brain.getBrain().restart();
            return null;
        } catch (IOException ex) {
            Brain.getBrain().restart();
            return null;
        }
        return ident;
    }

    public String getAge() {
        return identity.getAge();
    }

    public String getName(){
        return identity.getName();
    }

private void Serialize() {
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            File f = new File(IAConstants.PATH_IDENTITY);
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(identity);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            ErrorLogger.getLogger().logError("", ex);
        }
    }

}
