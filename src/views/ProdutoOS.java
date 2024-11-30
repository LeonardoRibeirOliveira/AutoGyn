package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
public class ProdutoOS extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    SimpleDateFormat userInputFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public ProdutoOS() {
        initComponents();
        database = ConnectionModule.conector();
        PesquisarOSEstoque();
        
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
    
    private void AdicionarProdutoOS() {
        String sql = "insert into ProdutosOS (IdOS, IdProduto, Quantidade, PrecoTotal) values(?,?,?,?)";

        try {
            
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldIdos.getText());
            pst.setString(2, fieldIdProduto.getText());
            pst.setString(3, quantidade.getText());
            pst.setString(4, precoTotal.getText());

            if (fieldIdos.getText().isEmpty() || quantidade.getText().isEmpty() || fieldIdProduto.getText().isEmpty() || precoTotal.getText().isEmpty()) {
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
    
    
    
    
    
    //================================OS & Estoque
    
    private void PesquisarOSEstoque(){
        String sql, sql2;
        ResultSet rs;

        sql = "SELECT os.IdOS AS IdOS, v.Placa AS Placa, c.CPF_CNPJ AS CpfCliente, " +
              "os.DataAbertura AS DataAbertura, os.DataConclusao AS DataConclusao, os.Status AS Status, os.Total AS Total " +
              "FROM OrdensDeServico os " +
              "JOIN veiculos v ON os.IdVeiculo = v.IdVeiculo " +
              "JOIN clientes c ON v.IdCliente = c.IdCliente";
        
        sql2 = "SELECT * FROM Estoque";


        try {
            pst = database.prepareStatement(sql);

            rs = pst.executeQuery();

            tableOS.setModel(DbUtils.resultSetToTableModel(rs));
            
            pst = database.prepareStatement(sql2);

            rs = pst.executeQuery();

            tableProduto.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void SelecionarIdOS(){
        String sql;
        ResultSet rs;
        
        sql = "SELECT * FROM ProdutosOS WHERE IdOS = ?";
        
        try{
            int cliente = tableOS.getSelectedRow();
            
            fieldIdos.setText(tableOS.getModel().getValueAt(cliente, 0).toString());
            
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldIdos.getText());
            
            rs = pst.executeQuery();
            
            tableProdutoOS.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void SelecionarIdProduto(){
        try{
            int cliente = tableProduto.getSelectedRow();
            
            fieldIdProduto.setText(tableProduto.getModel().getValueAt(cliente, 0).toString());
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    //====================================
    
    
    private void LimparCampos(){
        quantidade.setText(null);
        precoTotal.setText(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addOS = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SignalField = new javax.swing.JLabel();
        fieldIdos = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableProdutoOS = new javax.swing.JTable();
        quantidade = new javax.swing.JTextField();
        idOS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        precoTotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableOS = new javax.swing.JTable();
        SignalField4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProduto = new javax.swing.JTable();
        SignalField1 = new javax.swing.JLabel();
        fieldIdProduto = new javax.swing.JTextField();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(908, 604));

        addOS.setText("Adicionar");
        addOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOSActionPerformed(evt);
            }
        });

        jLabel1.setText("* Id os");

        jLabel2.setText("* Preço total");

        SignalField.setText("* Campos Obrigatórios");

        fieldIdos.setEditable(false);
        fieldIdos.setBackground(new java.awt.Color(255, 255, 255));
        fieldIdos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldIdos.setBorder(new javax.swing.border.MatteBorder(null));
        fieldIdos.setName(""); // NOI18N

        tableProdutoOS = new javax.swing.JTable(){     public boolean isCellEditable(int rowIndex, int colIndex){         return false;     } };
        tableProdutoOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableProdutoOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProdutoOSMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableProdutoOS);

        quantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quantidadeActionPerformed(evt);
            }
        });

        idOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idOSActionPerformed(evt);
            }
        });

        jLabel5.setText("* Id produto");

        precoTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precoTotalActionPerformed(evt);
            }
        });

        jLabel6.setText("* Quantidade");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("OS"));

        tableProduto = new javax.swing.JTable(){
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
        jScrollPane5.setViewportView(tableOS);

        SignalField4.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        SignalField4.setText("(Selecione alguma OS para mostrar seus pagamentos ou cadastrar um novo)");

        tableProduto = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tableProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableProduto.getTableHeader().setReorderingAllowed(false);
        tableProduto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableProdutoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableProduto);

        SignalField1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        SignalField1.setText("(Selecione algum produto para cadastro)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(SignalField4)
                        .addGap(41, 41, 41)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(SignalField1)
                        .addGap(0, 244, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SignalField4)
                    .addComponent(SignalField1))
                .addGap(19, 19, 19)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        fieldIdProduto.setEditable(false);
        fieldIdProduto.setBackground(new java.awt.Color(255, 255, 255));
        fieldIdProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldIdProduto.setBorder(new javax.swing.border.MatteBorder(null));
        fieldIdProduto.setName(""); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SignalField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addOS))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(467, 467, 467)
                                    .addComponent(idOS, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(65, 65, 65)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(precoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fieldIdos, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(fieldIdProduto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)))
                                .addComponent(jLabel6)
                                .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldIdos, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(precoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addOS)
                    .addComponent(SignalField))
                .addGap(12, 12, 12)
                .addComponent(idOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOSActionPerformed
        AdicionarProdutoOS();
    }//GEN-LAST:event_addOSActionPerformed

    private void tableProdutoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProdutoMouseClicked
       SelecionarIdProduto();
    }//GEN-LAST:event_tableProdutoMouseClicked

    private void tableProdutoOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableProdutoOSMouseClicked
        
    }//GEN-LAST:event_tableProdutoOSMouseClicked

    private void quantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_quantidadeActionPerformed

    private void idOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idOSActionPerformed

    private void precoTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precoTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precoTotalActionPerformed

    private void tableOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOSMouseClicked
        SelecionarIdOS();
    }//GEN-LAST:event_tableOSMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SignalField;
    private javax.swing.JLabel SignalField1;
    private javax.swing.JLabel SignalField2;
    private javax.swing.JLabel SignalField3;
    private javax.swing.JLabel SignalField4;
    private javax.swing.JButton addOS;
    private javax.swing.JTextField fieldIdProduto;
    private javax.swing.JTextField fieldIdos;
    private javax.swing.JTextField idOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextField precoTotal;
    private javax.swing.JTextField quantidade;
    private javax.swing.JTable tableOS;
    private javax.swing.JTable tableOS1;
    private javax.swing.JTable tableOS2;
    private javax.swing.JTable tableProduto;
    private javax.swing.JTable tableProdutoOS;
    // End of variables declaration//GEN-END:variables
}
