/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain;

import com.ben.IA.ErrorLogger.ErrorLogger;
import com.ben.IA.brain.scripter.view.Scripter;
import com.ben.IA.Views.utilities.TranslateView;
import com.ben.IA.Views.notes.NoteCreator;
import com.ben.IA.Views.notes.NotesViewer;
import com.ben.IA.Views.utilities.OpenFile;
import com.ben.IA.Views.utilities.RssReader.OpenURI;
import com.ben.IA.Views.utilities.RssReader.RssReaderView;
import com.ben.IA.Views.utilities.SynonymsAnswerView;
import com.ben.IA.Views.utilities.SynonymsView;
import com.ben.IA.brain.database.AnswerDatabase;
import com.ben.IA.brain.database.Notes;
import com.ben.IA.brain.database.QuestionDatabase;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author benoitdaccache
 */
public class Brain extends Observable {

    private static Brain instance;
    private BrainSettings brainSettings;
    private String lastQuote = "";
    private String lastAnswer = "";
    private NotesViewer notesViewer = null;
    private NoteCreator noteCreator;

    public Brain() {
        instance = this;
        new ErrorLogger();
        brainSettings = new BrainSettings();
        brainSettings.refreshIdentity();
    }

    public static Brain getBrain() {
        return instance;
    }

    public void performAsk(String text) {
        String answer = null;
        answer = checkDemand(text);
        setChanged();
        notifyObservers(answer);
    }

    public void restart() {
        String name = JOptionPane.showInputDialog("Please Give me a name");
        brainSettings.newIdentity(name);
    }

    private void checkNotes() {
        notesViewer = new NotesViewer(Notes.getInstance().getNotes());
        notesViewer.setVisible(true);
    }

    private boolean containsKnowledgeData(String text) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean containsMath(String text) {
        return containsNumerics(text) && containsOperator(text);
    }

    private boolean containsNumerics(String text) {
        char[] c = text.toCharArray();
        for (int i = 0; i < c.length; i++)
            if (Character.isDigit(c[i]))
                return true;
        return false;
    }

    private boolean containsOperator(String text) {
        return text.contains("+") || text.contains("-") || text.contains("/") || text.contains("*");
    }

    private String getKnowledge(String text) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private boolean isQuestion(String str) {
        return str.contains("?");
    }

    private void mail(String question) {
        String adress = null;
        try {
            adress = question.substring(question.indexOf("\"") + 1, question.lastIndexOf("\""));
            Runtime.getRuntime().exec("open mailto:" + adress);
        } catch (IOException ex) {
            Logger.getLogger(Brain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (StringIndexOutOfBoundsException e) {
            try {
                Runtime.getRuntime().exec("open mailto:");
            } catch (IOException ex) {
                Logger.getLogger(Brain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean match(String text, String text2) {
        return Matcher.matchString(text, text2);
    }

    private void openFile(String question) {
        try {
            File f;
            if (question.contains("\"")) {
                f = new File(question.substring(question.indexOf("\"") + 1, question.lastIndexOf("\"")));
                if (f.exists())
                    OpenFile.openFile(f);
            }
        } catch (StringIndexOutOfBoundsException e) {
        }
    }

    private void openUrl(String question) {
        try {
            String url = question.substring(question.indexOf("\"") + 1, question.lastIndexOf("\""));
            if (!url.startsWith("http://"))
                url = "http://" + url;
            OpenURI.openURL(url);
        } catch (IndexOutOfBoundsException e) {
            OpenURI.openURL("http://www.google.com");
        }
    }

    private void openNoteCreator() {
        noteCreator = new NoteCreator();
        noteCreator.setVisible(true);
    }

    /**
     * Analyse la discussion et renvoie la bonne reponse
     * @param text le texte qu'a tappé l'utilisateur
     * @return la reponse du bot
     */
    private String checkDemand(String question) {
        question = question.trim();
        if (match("how", lastQuote) && match("are", lastQuote) && match("you", lastQuote))
            if (match("you", question)) {
                lastAnswer = randomAnswer("fine");
                lastQuote = question;
                return lastAnswer;
            } else if (match("fine", question)) {
                lastAnswer = randomAnswer("Good :)");
                lastQuote = question;
                return lastAnswer;
            } else if (match("good", question) && match("not", question)) {
                lastAnswer = randomAnswer("why");
                return lastAnswer;
            }
        if (match("fine", lastAnswer) && match("you", lastAnswer)) {
            if (match("good", question) && match("not", question)) {
                lastAnswer = "why aren't you feeling ok?";
                return randomAnswer(lastAnswer);
            } else if (match("good", question)) {
                lastAnswer = "good";
                return randomAnswer("good");
            }
            lastQuote = question;
        }
        if (match("why", lastAnswer) && match("aren't", question) && match("feeling", question) && match("ok", lastAnswer)) {
            if (match("I ", question) || match("because", question)) {
                lastAnswer = "Ok";
                return randomAnswer("good");
            }
            lastQuote = question;
        } else if (match("how", lastQuote) && match("are", lastQuote) && match("you", lastQuote)) {
            lastQuote = question;
            return randomAnswer("Good");
        } else if (match("hey", question) && match("how", question) && match("are", question) && match("you", question)) {
            lastQuote = question;
            lastAnswer = "hey fine and you ?";
            return randomAnswer("hey fine and you");
        } else if (match("how", question) && match("are", question) && match("you", question)) {
            lastQuote = question;
            lastAnswer = "fine and you";
            return randomAnswer("fine and you");
        } else if (match("fine", question) && match("you", question)) {
            lastQuote = question;
            lastAnswer = "I'm ok";
            return randomAnswer("I'm ok");
        } else if (match("hey", question)) {
            lastQuote = question;
            lastAnswer = "hey";
            return randomAnswer("hey");
        } else if (match("bye", question))
            return randomAnswer("bye");
        else if (match("synonym", question) && match("key", question))
            new SynonymsView(QuestionDatabase.getInstance().getEntireDatabase()).setVisible(true);
        else if (match("synonym", question) && match("answer", question))
            new SynonymsAnswerView(AnswerDatabase.getInstance().getEntireDatabase()).setVisible(true);
        else if (match("date", question) && match("what", question))
            return Calendar.getInstance().getTime().toString();
        else if (match("age", question) && match("what", question))
            return brainSettings.getAge();
        else if (match("name", question) && match("your", question) && match("what", question))
            return brainSettings.getName();
        else if (match("creator", question) && match("your", question) && match("who", question))
            return "It's Ben";
        else if (match("script", question))
            new Scripter().setVisible(true);
        else if (match("consult", question) && match("note", question)) {
            checkNotes();
            return "Ok";
        } else if (match("note", question)) {
            lastQuote = question;
            openNoteCreator();
            return ("Ok I will save the note for you");
        } else if (match("translate", question))
            new TranslateView().setVisible(true);
        else if (match("get news", question))
            new RssReaderView().setVisible(true);
        else if (match("open", question))
            openFile(question);
        else if (match("browse", question))
            openUrl(question);
        else if (match("mail", question))
            mail(question);
        else
            return randomAnswer("non understandable");
        return "";
    }

    private String randomAnswer(String string) {
        return AnswerDatabase.getInstance().answer(string);
    }
}
