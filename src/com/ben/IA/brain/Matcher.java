/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain;

import com.ben.IA.brain.database.QuestionDatabase;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Compare un String avec les synonymes et regarde si il y a une ressemblance
 * @author benoitdaccache
 */
class Matcher {

    public static boolean matchString(String botMatch, String userQuestion) {
        if (userQuestion.toLowerCase().contains(botMatch.toLowerCase()))
            return true;
        ArrayList<String> arr = QuestionDatabase.getInstance().getSynonyms(botMatch);
        Iterator<String> it = arr.iterator();
        while (it.hasNext())
            if (userQuestion.toLowerCase().contains(it.next().toLowerCase()))
                return true;
        return false;
    }
}
