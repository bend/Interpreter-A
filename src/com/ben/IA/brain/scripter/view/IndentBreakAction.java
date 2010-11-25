/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.IA.brain.scripter.view;

import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

/**
 *
 * @author benoitdaccache
 */
public class IndentBreakAction extends TextAction {

    /**
     * Creates this object with the appropriate identifier.
     */
    public IndentBreakAction() {
        super(DefaultEditorKit.insertBreakAction);
    }

    /**
     * The operation to perform when this action is triggered.
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        JTextComponent target = getTextComponent(e);
        if (target == null)
            return;
        if ((!target.isEditable()) || (!target.isEnabled())) {
            UIManager.getLookAndFeel().provideErrorFeedback(target);
            return;
        }
        try {
            Document doc = target.getDocument();
            Element rootElement = doc.getDefaultRootElement();
            int selectionStart = target.getSelectionStart();
            int line = rootElement.getElementIndex(selectionStart);
            int start = rootElement.getElement(line).getStartOffset();
            int end = rootElement.getElement(line).getEndOffset();
            int length = end - start;
            String text = doc.getText(start, length);
            int offset = 0;
            for (offset = 0; offset < length; offset++) {
                char c = text.charAt(offset);
                if (c != ' ' && c != '\t')
                    break;
            }
            if (selectionStart - start > offset)
                target.replaceSelection("\n" + text.substring(0, offset));
            else
                target.replaceSelection("\n");
        } catch (BadLocationException ble) {
        }
    }
}