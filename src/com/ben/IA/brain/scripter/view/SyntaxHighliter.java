/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter.view;

import java.awt.Color;
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
class SyntaxHighliter extends Thread implements Runnable {

    private JTextPane scriptField;
    private StyledDocument styledDocument;

    public SyntaxHighliter(JTextPane scriptEditorTextPane) {
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
        try {
            styledDocument = scriptField.getStyledDocument();

            int begin = 0;
            int end = 0;
            while (true) {
                end = styledDocument.getLength();
                if (begin < end) {
                    int i = scriptField.getCaretPosition();
                    String s = styledDocument.getText(begin, end - begin);
                    if (s.trim().equals("begin")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.blue));
                        begin = end;//+ 1;
                    } else if (s.trim().equals("end")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.blue));
                        begin = end;//+ 1;
                    } else if (s.trim().equals("while")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.gray));
                        begin = end;//+ 1;
                    } else if (s.trim().equals("if")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.red));
                        begin = end;//+ 1;
                    } else if (s.trim().equals("else")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.red));
                        begin = end;//+ 1;
                    } else if (s.trim().equals("endwhile")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.gray));
                        begin = end;
                    } else if (s.trim().equals("endif")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.red));
                        begin = end;
                    } else if (s.trim().equals("true")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.cyan));
                        begin = end;
                    } else if (s.trim().equals("false")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.red));
                        begin = end;
                    }else if (s.trim().equals("thread")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(true, true, 12, Color.GREEN));
                        begin = end;
                    }else if (s.contains(" ") || s.contains("\n")) {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(false, false, 12, Color.BLACK));
                        begin = end;
                    } else {
                        styledDocument.remove(begin, end - begin);
                        styledDocument.insertString(begin, s, getStyle(false, false, 12, Color.BLACK));
                    }
                    scriptField.setCaretPosition(styledDocument.getLength());
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SyntaxHighliter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(SyntaxHighliter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
