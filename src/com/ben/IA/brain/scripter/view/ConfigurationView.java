/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConfigurationView.java
 *
 * Created on Feb 24, 2010, 5:29:11 PM
 */
package com.ben.IA.brain.scripter.view;

import com.ben.IA.App.IAApp;
import com.ben.IA.brain.scripter.Keys.KeyWord;

/**
 *
 * @author benoitdaccache
 */
public class ConfigurationView extends javax.swing.JDialog {

    /** Creates new form ConfigurationView */
    public ConfigurationView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initVals();
        this.setLocationRelativeTo(IAApp.getApplication().getMainFrame());
    }

    private void initVals() {
        keyTable.getModel().setValueAt("begin", 0, 0);
        keyTable.getModel().setValueAt("if", 1, 0);
        keyTable.getModel().setValueAt("else", 2, 0);
        keyTable.getModel().setValueAt("while", 3, 0);
        keyTable.getModel().setValueAt("thread", 4, 0);
        keyTable.getModel().setValueAt("function", 5, 0);
        keyTable.getModel().setValueAt("return", 6, 0);
        keyTable.getModel().setValueAt("read", 7, 0);
        keyTable.getModel().setValueAt("write", 8, 0);
        keyTable.getModel().setValueAt("sleep", 9, 0);
        keyTable.getModel().setValueAt("ostime", 10, 0);
        keyTable.getModel().setValueAt("end", 11, 0);

        keyTable.getModel().setValueAt("", 0, 1);
        keyTable.getModel().setValueAt("", 1, 1);
        keyTable.getModel().setValueAt("", 2, 1);
        keyTable.getModel().setValueAt("", 3, 1);
        keyTable.getModel().setValueAt("", 4,1);
        keyTable.getModel().setValueAt("", 5, 1);
        keyTable.getModel().setValueAt("", 6, 1);
        keyTable.getModel().setValueAt("", 7, 1);
        keyTable.getModel().setValueAt("", 8, 1);
        keyTable.getModel().setValueAt("", 9, 1);
        keyTable.getModel().setValueAt("", 10, 1);
        keyTable.getModel().setValueAt("", 11, 1);
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
        keyTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);
        setUndecorated(true);

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        keyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "KeyWord", "New KeyWord"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        keyTable.setName("keyTable"); // NOI18N
        jScrollPane1.setViewportView(keyTable);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.ben.IA.App.IAApp.class).getContext().getResourceMap(ConfigurationView.class);
        keyTable.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("keyTable.columnModel.title0")); // NOI18N
        keyTable.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("keyTable.columnModel.title1")); // NOI18N

        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(cancelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
                .addComponent(saveButton))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        KeyWord key = KeyWord.getInstance();
        keyTable.getModel();
        keyTable.getModel().getValueAt(0, 1);
        if(keyTable.getModel().getValueAt(0, 1).equals("")){
            key.begin = "begin";
        }else key.begin = (String) keyTable.getModel().getValueAt(0, 1);

        if(keyTable.getModel().getValueAt(1, 1).equals("")){
            key.ifCond = "if";
        }else key.ifCond = (String) keyTable.getModel().getValueAt(1, 1);

        if(keyTable.getModel().getValueAt(2, 1).equals("")){
            key.elseCond = "else";
        }else key.elseCond = (String) keyTable.getModel().getValueAt(2, 1);

        if(keyTable.getModel().getValueAt(3, 1).equals("")){
            key.whileLoop = "while";
        }else key.whileLoop = (String) keyTable.getModel().getValueAt(3, 1);

        if(keyTable.getModel().getValueAt(4, 1).equals("")){
            key.thread = "thread";
        }else key.thread = (String) keyTable.getModel().getValueAt(4, 1);

        if(keyTable.getModel().getValueAt(5, 1).equals("")){
            key.function = "function";
        }else key.function = (String) keyTable.getModel().getValueAt(5, 1);

        if(keyTable.getModel().getValueAt(6, 1).equals("")){
            key.returnStatement = "return";
        }else key.returnStatement = (String) keyTable.getModel().getValueAt(6, 1);

        if(keyTable.getModel().getValueAt(7, 1).equals("")){
            key.read = "read";
        }else key.read = (String) keyTable.getModel().getValueAt(7, 1);

        if(keyTable.getModel().getValueAt(8, 1).equals("")){
            key.write = "write";
        }else key.write = (String) keyTable.getModel().getValueAt(8, 1);

        if(keyTable.getModel().getValueAt(9, 1).equals("")){
            key.sleep = "sleep";
        }else key.sleep = (String) keyTable.getModel().getValueAt(9, 1);

        if(keyTable.getModel().getValueAt(10, 1).equals("")){
            key.ostime = "ostime";
        }else key.ostime = (String) keyTable.getModel().getValueAt(10, 1);

        if(keyTable.getModel().getValueAt(11, 1).equals("")){
            key.end = "end";
        }else key.end = (String) keyTable.getModel().getValueAt(11, 1);
        this.dispose();

    }//GEN-LAST:event_saveButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable keyTable;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
