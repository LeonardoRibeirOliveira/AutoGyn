package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
public class PagamentoOS extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    SimpleDateFormat userInputFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public PagamentoOS() {
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
    
    private void AdicionarPagamentoOS() {
        String sql = "insert into Pagamentos (IdOS, ValorPago, DataPagamento, MeioPagamento) values(?,?,?,?)";

        try {
            java.util.Date parsedDatapgto = userInputFormat.parse(dataPagamento.getText());

            String formatDatapgto = databaseFormat.format(parsedDatapgto);
            
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldIdos.getText());
            pst.setString(2, valorPago.getText());
            pst.setString(3, formatDatapgto);
            pst.setString(4, meioPagamento.getSelectedItem().toString());

            if (fieldIdos.getText().isEmpty() || valorPago.getText().isEmpty() || formatDatapgto.isEmpty()|| meioPagamento.getSelectedItem().toString().isEmpty()) {
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
        
        sql = "SELECT * FROM Pagamentos WHERE IdOS = ?";
        
        try{
            int cliente = tableOS.getSelectedRow();
            
            fieldIdos.setText(tableOS.getModel().getValueAt(cliente, 0).toString());
            
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldIdos.getText());
            
            rs = pst.executeQuery();
            
            tablePagamento.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    //====================================
    
    
    private void LimparCampos(){
        valorPago.setText(null);
        valorPago.setText(null);
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
        tablePagamento = new javax.swing.JTable();
        valorPago = new javax.swing.JTextField();
        idOS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        dataPagamento = new javax.swing.JTextField();
        meioPagamento = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

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

        SignalField1.setText("(Selecione alguma OS para mostrar seus pagamentos ou cadastrar um novo)");

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

        jLabel2.setText("* Data Pagamento (dd/mm/yyyy)");

        SignalField.setText("* Campos Obrigatórios");

        fieldIdos.setEditable(false);
        fieldIdos.setBackground(new java.awt.Color(255, 255, 255));
        fieldIdos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldIdos.setBorder(new javax.swing.border.MatteBorder(null));
        fieldIdos.setName(""); // NOI18N

        tablePagamento = new javax.swing.JTable(){     public boolean isCellEditable(int rowIndex, int colIndex){         return false;     } };
        tablePagamento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablePagamento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePagamentoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablePagamento);

        valorPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                valorPagoActionPerformed(evt);
            }
        });

        idOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idOSActionPerformed(evt);
            }
        });

        jLabel5.setText("* Meio Pagamento");

        dataPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataPagamentoActionPerformed(evt);
            }
        });

        meioPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dinheiro", "Cartão de crédito", "Cartão de débito", "PIX", "Boleto"  }));

        jLabel6.setText("* Valor pago");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(443, 443, 443)
                        .addComponent(idOS, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(SignalField))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel2)
                                        .addComponent(dataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(fieldIdos, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel1))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5)
                                                .addComponent(meioPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jLabel6)
                                        .addComponent(valorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(addOS, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SignalField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(meioPagamento)
                            .addComponent(fieldIdos, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dataPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 30, Short.MAX_VALUE)
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
        AdicionarPagamentoOS();
    }//GEN-LAST:event_addOSActionPerformed

    private void tableOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOSMouseClicked
        SelecionarIdOS();
    }//GEN-LAST:event_tableOSMouseClicked

    private void tablePagamentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePagamentoMouseClicked
        
    }//GEN-LAST:event_tablePagamentoMouseClicked

    private void valorPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_valorPagoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_valorPagoActionPerformed

    private void idOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idOSActionPerformed

    private void dataPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataPagamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataPagamentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SignalField;
    private javax.swing.JLabel SignalField1;
    private javax.swing.JButton addOS;
    private javax.swing.JTextField dataPagamento;
    private javax.swing.JTextField fieldIdos;
    private javax.swing.JTextField idOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> meioPagamento;
    private javax.swing.JTable tableOS;
    private javax.swing.JTable tablePagamento;
    private javax.swing.JTextField valorPago;
    // End of variables declaration//GEN-END:variables
}
