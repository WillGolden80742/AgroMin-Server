/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Server;

import Model.DAO.clientDAO;
import Model.bean.Cliente;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author will
 */
public class Nivel extends javax.swing.JFrame {

    private clientDAO cliDAO = new clientDAO();
    private Cliente currentCli;
    private List<Cliente> clientes;

    public Nivel() {
        initComponents();
        setLocation(200, 200);
        readClientes();
    }

    private void readClientes() {
        DefaultTableModel modelo = (DefaultTableModel) usuarios.getModel();
        modelo.setNumRows(0);
        clientes = cliDAO.read();
        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                c.getNickName()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        usuarios = new javax.swing.JTable();
        ok = new javax.swing.JToggleButton();
        levelComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        usuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "nickName"
            }
        ));
        usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usuariosMouseReleased(evt);
            }
        });
        usuarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usuariosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(usuarios);

        ok.setText("SALVAR");
        ok.setEnabled(false);
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        levelComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));

        jLabel1.setText("Nivel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ok, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(levelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(ok))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usuariosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usuariosKeyReleased
        selectUsuario();
    }//GEN-LAST:event_usuariosKeyReleased

    private void usuariosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuariosMouseReleased
        selectUsuario();

    }//GEN-LAST:event_usuariosMouseReleased

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        cliDAO.editLevel(currentCli.getNickName(),levelComboBox.getSelectedIndex());
        ok.setEnabled(false);
        readClientes();
    }//GEN-LAST:event_okActionPerformed

    private void selectUsuario() {
        if (usuarios.isFocusOwner()) {
            ok.setEnabled(true);
        }
        currentCli = clientes.get(usuarios.getSelectedRow());
        levelComboBox.setSelectedIndex(currentCli.getNivel());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> levelComboBox;
    private javax.swing.JToggleButton ok;
    private javax.swing.JTable usuarios;
    // End of variables declaration//GEN-END:variables
}
