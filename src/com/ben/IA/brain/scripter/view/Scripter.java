/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Scripter.java
 *
 * Created on Feb 2, 2010, 3:54:40 PM
 */
package com.ben.IA.brain.scripter.view;

import com.ben.IA.App.IAApp;
import com.ben.IA.brain.scripter.Interpreter;
import com.ben.IA.brain.scripter.Keys.KeyWord;
import com.ben.IA.brain.scripter.MemUsageView;
import com.ben.IA.brain.scripter.Reader;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.text.StyledDocument;

/**
 *
 * @author benoitdaccache
 */
public class Scripter extends javax.swing.JFrame {

    private Interpreter intertpreter;
    private MemUsageView memusage;
    private StyledDocument styledDocument;
    private ArrayList<ScriptPanel> tabList;
    private JPopupMenu pop;
    private KeyWord keyword;
    private boolean isLoaded=false;

    /** Creates new form Scripter */
    public Scripter() {
        initComponents();
        setLocationRelativeTo(IAApp.getApplication().getMainFrame());
        addPopUpToTab();
        newScriptMenuItemActionPerformed(null);
        isLoaded = true;
        keyword = new KeyWord();
    }

    private void addPopUpToTab() {
        tabList = new ArrayList<ScriptPanel>();
        pop = new JPopupMenu();
        JMenuItem closeItem = new JMenuItem("Close tab");
        closeItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                closeAction();
            }
        });
        pop.add(closeItem);
    }

    private void closeAction() {
        if (scriptTab.getSelectedIndex() == 0) {
            int i = JOptionPane.showConfirmDialog(null, "Do you want to save the script?");
            if (i == JOptionPane.OK_OPTION) {
                saveAsMenuItemActionPerformed(null);
                tabList.get(0).setText("");
                scriptTab.setTitleAt(0, "Script 1");
            } else if (i == JOptionPane.NO_OPTION) {
                tabList.get(0).setText("");
                scriptTab.setTitleAt(0, "Script 1");
            } else
                return;
        } else {
            int i = JOptionPane.showConfirmDialog(null, "Do you want to save the script?");
            if (i == JOptionPane.OK_OPTION) {
                saveAsMenuItemActionPerformed(null);
                scriptTab.remove(scriptTab.getSelectedIndex());
                tabList.remove(scriptTab.getSelectedIndex());
            } else if (i == JOptionPane.NO_OPTION) {
                scriptTab.remove(scriptTab.getSelectedIndex());
                tabList.remove(scriptTab.getSelectedIndex());
            } else
                return;
        }
    }

    public void appendToConsole(String message) {
        try {
            consoleTextArea.setText(consoleTextArea.getText() + message);
            consoleTextArea.setCaretPosition(consoleTextArea.getText().length());
        } catch (Exception e) {
        }
    }

    public void markstop() {
        stopButton.setEnabled(false);
        executeButton.setEnabled(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        consoleTextArea = new javax.swing.JTextArea();
        scriptTab = new javax.swing.JTabbedPane();
        executeButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        labelMem = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        configureMenuItem = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        printMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        runMenuItem = new javax.swing.JMenuItem();
        confMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        newScriptMenuItem = new javax.swing.JMenuItem();
        closeMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        memMenuItem = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setName("jSplitPane1"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.ben.IA.App.IAApp.class).getContext().getResourceMap(Scripter.class);
        consoleTextArea.setBackground(resourceMap.getColor("consoleTextArea.background")); // NOI18N
        consoleTextArea.setColumns(20);
        consoleTextArea.setEditable(false);
        consoleTextArea.setForeground(resourceMap.getColor("consoleTextArea.foreground")); // NOI18N
        consoleTextArea.setLineWrap(true);
        consoleTextArea.setRows(5);
        consoleTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("consoleTextArea.border.title"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Lucida Grande", 0, 13), resourceMap.getColor("consoleTextArea.border.titleColor"))); // NOI18N
        consoleTextArea.setName("consoleTextArea"); // NOI18N
        jScrollPane3.setViewportView(consoleTextArea);

        jSplitPane1.setRightComponent(jScrollPane3);

        scriptTab.setName("scriptTab"); // NOI18N
        scriptTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                scriptTabMousePressed(evt);
            }
        });
        scriptTab.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                scriptTabStateChanged(evt);
            }
        });
        jSplitPane1.setLeftComponent(scriptTab);

        executeButton.setText(resourceMap.getString("executeButton.text")); // NOI18N
        executeButton.setName("executeButton"); // NOI18N
        executeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeButtonActionPerformed(evt);
            }
        });

        clearButton.setText(resourceMap.getString("clearButton.text")); // NOI18N
        clearButton.setName("clearButton"); // NOI18N
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        stopButton.setIcon(resourceMap.getIcon("stopButton.icon")); // NOI18N
        stopButton.setText(resourceMap.getString("stopButton.text")); // NOI18N
        stopButton.setEnabled(false);
        stopButton.setName("stopButton"); // NOI18N
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        labelMem.setText(resourceMap.getString("labelMem.text")); // NOI18N
        labelMem.setName("labelMem"); // NOI18N

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        configureMenuItem.setText(resourceMap.getString("configureMenuItem.text")); // NOI18N
        configureMenuItem.setName("configureMenuItem"); // NOI18N

        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        configureMenuItem.add(openMenuItem);

        saveAsMenuItem.setText(resourceMap.getString("saveAsMenuItem.text")); // NOI18N
        saveAsMenuItem.setName("saveAsMenuItem"); // NOI18N
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        configureMenuItem.add(saveAsMenuItem);

        printMenuItem.setText(resourceMap.getString("printMenuItem.text")); // NOI18N
        printMenuItem.setName("printMenuItem"); // NOI18N
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        configureMenuItem.add(printMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        configureMenuItem.add(jSeparator1);

        runMenuItem.setText(resourceMap.getString("runMenuItem.text")); // NOI18N
        runMenuItem.setName("runMenuItem"); // NOI18N
        runMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runMenuItemActionPerformed(evt);
            }
        });
        configureMenuItem.add(runMenuItem);

        confMenuItem.setText(resourceMap.getString("confMenuItem.text")); // NOI18N
        confMenuItem.setName("confMenuItem"); // NOI18N
        confMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confMenuItemActionPerformed(evt);
            }
        });
        configureMenuItem.add(confMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        configureMenuItem.add(jSeparator2);

        newScriptMenuItem.setText(resourceMap.getString("newScriptMenuItem.text")); // NOI18N
        newScriptMenuItem.setName("newScriptMenuItem"); // NOI18N
        newScriptMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newScriptMenuItemActionPerformed(evt);
            }
        });
        configureMenuItem.add(newScriptMenuItem);

        closeMenuItem.setText(resourceMap.getString("closeMenuItem.text")); // NOI18N
        closeMenuItem.setName("closeMenuItem"); // NOI18N
        closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuItemActionPerformed(evt);
            }
        });
        configureMenuItem.add(closeMenuItem);

        jMenuBar1.add(configureMenuItem);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        memMenuItem.setText(resourceMap.getString("memMenuItem.text")); // NOI18N
        memMenuItem.setName("memMenuItem"); // NOI18N
        memMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(memMenuItem);

        jCheckBoxMenuItem1.setText(resourceMap.getString("jCheckBoxMenuItem1.text")); // NOI18N
        jCheckBoxMenuItem1.setName("jCheckBoxMenuItem1"); // NOI18N
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelMem, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(executeButton))
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(executeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelMem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stopButton))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        consoleTextArea.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void executeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeButtonActionPerformed
        executeButton.setEnabled(false);
        Reader reader;
        reader = new Reader(tabList.get(scriptTab.getSelectedIndex()).getText());
        intertpreter = new Interpreter(reader, this);
        intertpreter.start();
        stopButton.setEnabled(true);
    }//GEN-LAST:event_executeButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        intertpreter.stopInterpreter();
        intertpreter = null;
        System.runFinalization();
        System.gc();
        stopButton.setEnabled(false);
        executeButton.setEnabled(true);
}//GEN-LAST:event_stopButtonActionPerformed

    private void memMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memMenuItemActionPerformed
        memusage = new MemUsageView();
        memusage.setVisible(true);
    }//GEN-LAST:event_memMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        String source = filechoose();
        String text;
        try{
        if (!source.equals("")) {
            if (!source.endsWith(".script"))
                source += ".script";
            try {
                BufferedWriter out = null;
                out = new BufferedWriter(new FileWriter(source, false));
                text = tabList.get(scriptTab.getSelectedIndex()).getText();
                out.write(text);
                out.close();
                scriptTab.setTitleAt(scriptTab.getSelectedIndex(), new File(source).getName());
            } catch (IOException ex) {
                Logger.getLogger(Scripter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        }catch (NullPointerException e){}
}//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        try{
        String s = filechooseOpen();
        if (!s.equals("")){
            newScriptMenuItemActionPerformed(evt);
        try {
            BufferedReader r = new BufferedReader(new FileReader(s));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null)
                str.append(line + "\n");
            tabList.get(scriptTab.getSelectedIndex()).setText(str.toString());
            scriptTab.setTitleAt(scriptTab.getSelectedIndex(), new File(s).getName());
        } catch (IOException ex) {
            Logger.getLogger(Scripter.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
        }catch(NullPointerException e){}
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
        if (!jCheckBoxMenuItem1.isSelected())
            tabList.get(scriptTab.getSelectedIndex()).stopHighlighter();
        else
            tabList.get(scriptTab.getSelectedIndex()).startHighlight();
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private void runMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runMenuItemActionPerformed
        executeButtonActionPerformed(evt);
}//GEN-LAST:event_runMenuItemActionPerformed

    private void newScriptMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newScriptMenuItemActionPerformed
        ScriptPanel pane = new ScriptPanel();
        scriptTab.add("Script " + (scriptTab.getTabCount() + 1), pane);
        tabList.add(pane);
        scriptTab.setSelectedComponent(pane);
}//GEN-LAST:event_newScriptMenuItemActionPerformed

    private void scriptTabMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scriptTabMousePressed
        Point loc;
        pop.setInvoker(scriptTab);
        if (evt.isPopupTrigger()) {
            loc = evt.getPoint();
            pop.show(this, loc.x, loc.y + 20);
        }
    }//GEN-LAST:event_scriptTabMousePressed

    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
        closeAction();
}//GEN-LAST:event_closeMenuItemActionPerformed

    private void confMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confMenuItemActionPerformed
        new ConfigurationView(null, true).setVisible(true);
    }//GEN-LAST:event_confMenuItemActionPerformed

    private void scriptTabStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_scriptTabStateChanged
        if(isLoaded)
        jCheckBoxMenuItem1.setSelected(tabList.get(scriptTab.getSelectedIndex()).isHighlighting());


    }//GEN-LAST:event_scriptTabStateChanged

    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
       tabList.get(scriptTab.getSelectedIndex()).print();
    }//GEN-LAST:event_printMenuItemActionPerformed

    private String filechooseOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(""));
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

            public boolean accept(File f) {
                String name = f.getName().toLowerCase();
                return name.endsWith(".script") || f.isDirectory();
            }
            public String getDescription() {
                return "Script files";
            }
        });
        int r = chooser.showOpenDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            String name1 = chooser.getSelectedFile().getAbsolutePath();
            return name1;
        }
        return "";
    }

    private String filechoose() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("~"));
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

            public boolean accept(File f) {
                String name = f.getName().toLowerCase();
                return name.endsWith(".script") || f.isDirectory();
            }

            public String getDescription() {
                return "Script files";
            }
        });
        int r = chooser.showSaveDialog(this);
        if (r == JFileChooser.APPROVE_OPTION) {
            String name1 = chooser.getSelectedFile().getAbsolutePath();
            return name1;
        }
        return "";
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JMenuItem confMenuItem;
    private javax.swing.JMenu configureMenuItem;
    private javax.swing.JTextArea consoleTextArea;
    private javax.swing.JButton executeButton;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel labelMem;
    private javax.swing.JMenuItem memMenuItem;
    private javax.swing.JMenuItem newScriptMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JMenuItem runMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JTabbedPane scriptTab;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}