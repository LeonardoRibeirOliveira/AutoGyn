/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import javax.swing.JOptionPane;

public class CadastroCliente extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public CadastroCliente() {
        initComponents();
        database = ConnectionModule.conector();
        
    }
    
    private void AdicionarCliente(){
        String sql = "insert into clientes (Nome, TipoCliente, CPF_CNPJ, Telefone, Email, Endereco) values(?,?,?,?,?,?)";
        
        try{
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldName.getText());
            String tipoCliente = fieldType.getSelectedItem().toString();
                if (tipoCliente.equalsIgnoreCase("Física")) {
                    pst.setString(2, "F"); // Define 'F' para Pessoa Física
                } else if (tipoCliente.equalsIgnoreCase("Jurídica")) {
                    pst.setString(2, "J"); // Define 'J' para Pessoa Jurídica
                } else {
                    JOptionPane.showMessageDialog(null, "Tipo de cliente inválido!");
                    return; // Sai do método
                }
            pst.setString(3, fieldCPF.getText());
            pst.setString(4, fieldTelephone.getText());
            pst.setString(5, fieldEmail.getText());
            pst.setString(6, fieldAdress.getText());
            
            if(fieldName.getText().isEmpty() || fieldType.getSelectedItem().toString().isEmpty() ||fieldCPF.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            }
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
                    
                    fieldName.setText(null);
                    fieldCPF.setText(null);
                    fieldTelephone.setText(null);
                    fieldEmail.setText(null);
                    fieldAdress.setText(null);
                }
            }
        }
        catch(Exception ex){
            System.err.println("Erro: " + ex.getMessage());
        }
        
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        SignalField = new javax.swing.JLabel();
        fieldName = new javax.swing.JTextField();
        fieldTelephone = new javax.swing.JTextField();
        fieldCPF = new javax.swing.JTextField();
        fieldAdress = new javax.swing.JTextField();
        fieldEmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        editClient = new javax.swing.JButton();
        addClient = new javax.swing.JButton();
        deleteClient = new javax.swing.JButton();
        fieldSearch = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableClient = new javax.swing.JTable();
        fieldType = new javax.swing.JComboBox<>();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setBorder(null);
        setTitle("Clientes");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setPreferredSize(new java.awt.Dimension(908, 604));

        SignalField.setText("* Campos Obirgatórios");

        fieldName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNameActionPerformed(evt);
            }
        });

        fieldCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldCPFActionPerformed(evt);
            }
        });

        fieldEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldEmailActionPerformed(evt);
            }
        });

        jLabel1.setText("* Nome");

        jLabel2.setText("* CPF/CNPJ");

        jLabel3.setText("Email");

        jLabel4.setText("* Tipo Cliente");

        jLabel5.setText("Telefone");

        jLabel6.setText("Endereço");

        editClient.setText("Editar");
        editClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editClientActionPerformed(evt);
            }
        });

        addClient.setText("Adicionar");
        addClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClientActionPerformed(evt);
            }
        });

        deleteClient.setText("Deletar");
        deleteClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteClientActionPerformed(evt);
            }
        });

        Search.setText("Buscar");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        tableClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableClient);

        fieldType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Física", "Jurídica" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(Search)
                                .addGap(204, 204, 204)
                                .addComponent(SignalField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(deleteClient)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editClient)
                                .addGap(18, 18, 18)
                                .addComponent(addClient)))
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1)
                                .addComponent(fieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                .addComponent(fieldCPF)
                                .addComponent(fieldEmail)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(fieldTelephone, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                            .addComponent(fieldAdress)
                            .addComponent(fieldType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(72, 72, 72))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SignalField)
                    .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(fieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(fieldType))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldTelephone, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldAdress, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteClient)
                    .addComponent(editClient)
                    .addComponent(addClient))
                .addGap(68, 68, 68))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNameActionPerformed

    private void fieldCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldCPFActionPerformed

    private void fieldEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldEmailActionPerformed

    private void editClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editClientActionPerformed

    private void addClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClientActionPerformed
        AdicionarCliente();
    }//GEN-LAST:event_addClientActionPerformed

    private void deleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteClientActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SearchActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Search;
    private javax.swing.JLabel SignalField;
    private javax.swing.JButton addClient;
    private javax.swing.JButton deleteClient;
    private javax.swing.JButton editClient;
    private javax.swing.JTextField fieldAdress;
    private javax.swing.JTextField fieldCPF;
    private javax.swing.JTextField fieldEmail;
    private javax.swing.JTextField fieldName;
    private javax.swing.JTextField fieldSearch;
    private javax.swing.JTextField fieldTelephone;
    private javax.swing.JComboBox<String> fieldType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tableClient;
    // End of variables declaration//GEN-END:variables
}
