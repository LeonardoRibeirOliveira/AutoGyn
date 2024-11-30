package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
public class ExecucaoOS extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    SimpleDateFormat userInputFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public ExecucaoOS() {
        initComponents();
        database = ConnectionModule.conector();
        PesquisarOS();
        
        javax.swing.plaf.InternalFrameUI ui = this.getUI();
        if (ui instanceof javax.swing.plaf.basic.BasicInternalFrameUI) {
            javax.swing.plaf.basic.BasicInternalFrameUI basicUI = (javax.swing.plaf.basic.BasicInternalFrameUI) ui;

            // Remover o MouseMotionListener da barra de título
            basicUI.getNorthPane().removeMouseMotionListener(
                basicUI.getNorthPane().getMouseMotionListeners()[0]
            );

            // Remover a barra de título
            basicUI.getNorthPane().setVisible(false);

            // Remover a borda do internal frame (opcional)
            this.setBorder(null);
        }
    }
    
    private void AdicionarExecucaoOS() {
        String sql = "insert into ExecucaoOS (IdOS, DescricaoExecucao, DataExecucao) values(?,?,?)";

        try {
            pst = database.prepareStatement(sql);

            java.util.Date parsedDataAbertura = userInputFormat.parse(dataAbertura.getText());

            String formattedDataAbertura = databaseFormat.format(parsedDataAbertura);

            pst.setString(1, fieldIdos.getText());
            pst.setString(2, descOs.getText());
            pst.setString(3, formattedDataAbertura);

            if (fieldIdos.getText().isEmpty() || dataAbertura.getText().isEmpty() || formattedDataAbertura.isEmpty()|| descOs.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço adicionada com sucesso!");
                    
                    SelecionarIdOS();
                    LimparCampos();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar ordem de serviço: " + ex.getMessage());
        }
    }
    
    
    
    
    
    //================================OS
    
    private void PesquisarOS(){
        String sql;
        ResultSet rs;

        sql = "SELECT os.IdOS AS IdOS, v.Placa AS Placa, c.CPF_CNPJ AS CpfCliente, " +
              "os.DataAbertura AS DataAbertura, os.DataConclusao AS DataConclusao, os.Status AS Status, os.Total AS Total " +
              "FROM OrdensDeServico os " +
              "JOIN veiculos v ON os.IdVeiculo = v.IdVeiculo " +
              "JOIN clientes c ON v.IdCliente = c.IdCliente";


        try {
            pst = database.prepareStatement(sql);

            rs = pst.executeQuery();

            tableOS.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void SelecionarIdOS(){
        String sql;
        ResultSet rs;
        
        sql = "SELECT * FROM ExecucaoOS WHERE IdOS = ?";
        
        try{
            int cliente = tableOS.getSelectedRow();
            
            fieldIdos.setText(tableOS.getModel().getValueAt(cliente, 0).toString());
            
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldIdos.getText());
            
            rs = pst.executeQuery();
            
            tableExecucao.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    //====================================
    
    
    private void LimparCampos(){
        dataAbertura.setText(null);
        dataAbertura.setText(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOS = new javax.swing.JTable();
        SignalField1 = new javax.swing.JLabel();
        addOS = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SignalField = new javax.swing.JLabel();
        fieldIdos = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableExecucao = new javax.swing.JTable();
        dataAbertura = new javax.swing.JTextField();
        idOS = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        descOs = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(908, 604));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("OS"));

        tableOS = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tableOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableOS.getTableHeader().setReorderingAllowed(false);
        tableOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableOS);

        SignalField1.setText("(Selecione alguma OS para mostrar suas execuções ou cadastrar uma nova)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 583, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(SignalField1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(SignalField1)
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        addOS.setText("Adicionar");
        addOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOSActionPerformed(evt);
            }
        });

        jLabel1.setText("* Id os");

        jLabel2.setText("* Data Execucao (dd/mm/yyyy)");

        SignalField.setText("* Campos Obrigatórios");

        fieldIdos.setEditable(false);
        fieldIdos.setBackground(new java.awt.Color(255, 255, 255));
        fieldIdos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldIdos.setBorder(new javax.swing.border.MatteBorder(null));
        fieldIdos.setName(""); // NOI18N

        tableExecucao = new javax.swing.JTable(){     public boolean isCellEditable(int rowIndex, int colIndex){         return false;     } };
        tableExecucao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableExecucao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableExecucaoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableExecucao);

        dataAbertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataAberturaActionPerformed(evt);
            }
        });

        idOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idOSActionPerformed(evt);
            }
        });

        descOs.setColumns(20);
        descOs.setRows(5);
        jScrollPane3.setViewportView(descOs);

        jLabel5.setText("* Descricao Execucao");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(617, 617, 617)
                                .addComponent(SignalField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1)
                                            .addComponent(dataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fieldIdos, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))))
                        .addGap(0, 79, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(443, 443, 443)
                                .addComponent(idOS, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(addOS, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(59, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SignalField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldIdos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(addOS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOSActionPerformed
        AdicionarExecucaoOS();
    }//GEN-LAST:event_addOSActionPerformed

    private void tableOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOSMouseClicked
        SelecionarIdOS();
    }//GEN-LAST:event_tableOSMouseClicked

    private void tableExecucaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableExecucaoMouseClicked
        
    }//GEN-LAST:event_tableExecucaoMouseClicked

    private void dataAberturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataAberturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataAberturaActionPerformed

    private void idOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idOSActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SignalField;
    private javax.swing.JLabel SignalField1;
    private javax.swing.JButton addOS;
    private javax.swing.JTextField dataAbertura;
    private javax.swing.JTextArea descOs;
    private javax.swing.JTextField fieldIdos;
    private javax.swing.JTextField idOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tableExecucao;
    private javax.swing.JTable tableOS;
    // End of variables declaration//GEN-END:variables
}
