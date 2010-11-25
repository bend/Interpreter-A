/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RssReaderView.java
 *
 * Created on Mar 3, 2010, 10:01:42 AM
 */
package com.ben.IA.Views.utilities.RssReader;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author benoitdaccache
 */
public class RssReaderView extends javax.swing.JFrame {

    ArrayList<String> urlList = new ArrayList<String>();

    /** Creates new form RssReaderView */
    public RssReaderView() {
        initComponents();
        jComboBox1.getEditor().getEditorComponent().addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    goButtonActionPerformed(null);
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        });
        refresh();

    }

    private void refresh() {
        FileInputStream fisdp = null;
        {
            try {
                ObjectInputStream oisdp = null;
                FileInputStream fishost = null;
                FileInputStream fisport = null;
                FileInputStream fisuser = null;
                ObjectInputStream oishost = null;
                ObjectInputStream oisuser = null;
                ObjectInputStream oisport = null;
                File f = new File("Conf");
                f.mkdir();
                fisuser = new FileInputStream("Conf/url.dat");
                oisuser = new ObjectInputStream(fisuser);
                urlList = (ArrayList<String>) oisuser.readObject();
                if (urlList == null)
                    urlList = new ArrayList<String>();
            } catch (FileNotFoundException e) {
                urlList = new ArrayList<String>();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RssReaderView.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RssReaderView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jComboBox1.removeAllItems();
        for (int i = 0; i < urlList.size(); i++)
            jComboBox1.addItem(urlList.get(i));
    }

    private void save() {
        try {
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            File c = new File("Conf");
            c.mkdir();
            File f = new File("Conf/url.dat");
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(urlList);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(RssReaderView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showFeed(String URL) {
        try {
            RSSReader rss = RSSReader.getInstance();
            jEditorPane1.setContentType("text/html");
            jEditorPane1.setEditable(false);
            jEditorPane1.setText("");
            jEditorPane1.addHyperlinkListener(new HyperlinkListener() {

                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType()))
                        OpenURI.openURL(e.getURL().toString());

                }
            });
            HTMLEditorKit kit = (HTMLEditorKit) jEditorPane1.getEditorKit();
            Document doc = jEditorPane1.getDocument();
            StringReader reader = new StringReader(rss.getNews(URL));
            kit.read(reader, doc, 0);
        } catch (IOException ex) {
            Logger.getLogger(RssReaderView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadLocationException ex) {
            Logger.getLogger(RssReaderView.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jComboBox1 = new javax.swing.JComboBox();
        goButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jEditorPane1.setName("jEditorPane1"); // NOI18N
        jScrollPane1.setViewportView(jEditorPane1);

        jComboBox1.setEditable(true);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "http://" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.ben.IA.App.IAApp.class).getContext().getResourceMap(RssReaderView.class);
        goButton.setText(resourceMap.getString("goButton.text")); // NOI18N
        goButton.setName("goButton"); // NOI18N
        goButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jComboBox1, 0, 367, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(goButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(goButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void goButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goButtonActionPerformed
        if (!urlList.contains((String) jComboBox1.getSelectedItem())) {
            if (!((String) jComboBox1.getSelectedItem()).startsWith("http://")) {
                urlList.add("http://" + (String) jComboBox1.getSelectedItem());
                showFeed("http://" + (String) jComboBox1.getSelectedItem());
            } else {
                urlList.add((String) jComboBox1.getSelectedItem());
                showFeed((String) jComboBox1.getSelectedItem());
            }
            save();
            refresh();
        }
}//GEN-LAST:event_goButtonActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton goButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
