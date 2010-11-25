/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter.view;

import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/**
 *
 * @author benoitdaccache
 */
class SyntaxHighliter2 extends Thread implements Runnable {

    private JTextPane scriptField;
    private StyledDocument styledDocument;

    public SyntaxHighliter2(JTextPane scriptEditorTextPane) {
        super("Highlighter");
        this.scriptField = scriptEditorTextPane;
    }

    private static String getNameStyle(boolean bold, boolean italic, int size, Color color) {
        StringBuffer sb = new StringBuffer();
        if (bold)
            sb.append("1");
        else
            sb.append("0");
        if (italic)
            sb.append("1");
        else
            sb.append("0");
        sb.append(size);
        sb.append(color.getRGB());

        return sb.toString();
    }

    private Style getStyle(boolean bold, boolean italic, int size, Color color) {
        String styleName = getNameStyle(bold, italic, size, color);
        Style style = styledDocument.getStyle(styleName);
        if (style != null)
            return style;
        else {
            Style styleDefaut = styledDocument.getStyle(StyleContext.DEFAULT_STYLE);
            style = styledDocument.addStyle(styleName, styleDefaut);
            StyleConstants.setBold(style, bold);
            StyleConstants.setItalic(style, italic);
            StyleConstants.setFontSize(style, size);
            StyleConstants.setForeground(style, color);

            return style;
        }
    }

    private boolean isNumber(String var2) {
        try {
            Long.parseLong(var2.trim());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        String text = scriptField.getText();
        StringTokenizer stk = new StringTokenizer(text);
        styledDocument = scriptField.getStyledDocument();
        try {
            styledDocument.remove(0, styledDocument.getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(SyntaxHighliter2.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (stk.hasMoreTokens())
            try {
                String token = stk.nextToken(" \n\r\f");
                if (token.trim().equals("function")) {
                    styledDocument.insertString(styledDocument.getLength(), token, getStyle(true, true, 12, Color.blue));
                    try {
                        String temp = stk.nextToken(")");
                        styledDocument.insertString(styledDocument.getLength(), temp, getStyle(false, false, 12, Color.BLACK));
                    } catch (NoSuchElementException e) {
                    }
                } else if (token.startsWith("\"")) {
                    styledDocument.insertString(styledDocument.getLength(), token + " ", getStyle(true, true, 12, Color.ORANGE));
                    try {
                        String temp = stk.nextToken("\"");
                        styledDocument.insertString(styledDocument.getLength(), temp, getStyle(true, true, 12, Color.ORANGE));
                    } catch (NoSuchElementException e) {
                    }
                } else if (token.startsWith("//")) {
                    styledDocument.insertString(styledDocument.getLength(), token, getStyle(false, true, 12, Color.GRAY));
                    try {
                        String temp = stk.nextToken("\n");
                        styledDocument.insertString(styledDocument.getLength(), temp + "\n", getStyle(false, true, 12, Color.GRAY));
                    } catch (NoSuchElementException e) {
                    }
                } else if (token.trim().equals("begin"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.blue));
                else if (token.trim().equals("end"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.blue));
                else if (token.trim().equals("endfunction"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.blue));
                else if (token.trim().equals("while"))
                    styledDocument.insertString(styledDocument.getLength(), token + " ", getStyle(true, true, 12, Color.gray));
                else if (token.trim().equals("if"))
                    styledDocument.insertString(styledDocument.getLength(), token + " ", getStyle(true, true, 12, Color.red));
                else if (token.trim().equals("else"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.red));
                else if (token.trim().equals("endwhile"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.gray));
                else if (token.trim().equals("endif"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.red));
                else if (token.trim().equals("true"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.cyan));
                else if (token.trim().equals("false"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.red));
                else if (token.trim().equals("thread"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.GREEN));
                else if (token.trim().equals("endthread"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.GREEN));
                else if (token.trim().equals("endthread"))
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(true, true, 12, Color.GREEN));
                else if (token.trim().equals("return")) {
                    styledDocument.insertString(styledDocument.getLength(), token + " ", getStyle(false, true, 12, Color.BLUE));
                    styledDocument.insertString(styledDocument.getLength(), stk.nextToken(" \t\n\r\f") + "\n", getStyle(false, true, 12, Color.BLUE));
                } else
                    styledDocument.insertString(styledDocument.getLength(), token + "\n", getStyle(false, false, 12, Color.BLACK));
                scriptField.setCaretPosition(styledDocument.getLength());
            } catch (BadLocationException ex) {
                Logger.getLogger(SyntaxHighliter2.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
}
