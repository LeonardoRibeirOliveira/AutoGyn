package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
public class OrdemServico extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    SimpleDateFormat userInputFormat = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public OrdemServico() {
        initComponents();
        database = ConnectionModule.conector();
        PesquisarVeiculo();
        PesquisarOrdemServico();
        
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
    
    private void AdicionarOrdemServico() {
        String sql = "insert into OrdensDeServico (IdVeiculo, DataAbertura, DataConclusao, Status, Total) values(?,?,?,?,?)";

        try {
            pst = database.prepareStatement(sql);

            java.util.Date parsedDataAbertura = userInputFormat.parse(dataAbertura.getText());
            java.util.Date parsedDataConclusao = userInputFormat.parse(dataConclusao.getText());

            String formattedDataAbertura = databaseFormat.format(parsedDataAbertura);
            String formattedDataConclusao = databaseFormat.format(parsedDataConclusao);

            pst.setString(1, fieldIdveiculo.getText());
            pst.setString(2, formattedDataAbertura);
            pst.setString(3, formattedDataConclusao);
            pst.setString(4, statusOS.getSelectedItem().toString());
            pst.setString(5, valorTotal.getText());

            if (fieldIdveiculo.getText().isEmpty() || dataAbertura.getText().isEmpty() || statusOS.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço adicionada com sucesso!");

                    LimparCampos();
                }
            }

            PesquisarOrdemServico();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar ordem de serviço: " + ex.getMessage());
        }
    }

    private void PesquisarOrdemServico() {
        String sql;
        ResultSet rs;

        sql = "SELECT os.IdOS AS IdOS, os.IdVeiculo AS IdVeiculo, v.Placa AS Placa, c.Nome AS NomeCliente, c.CPF_CNPJ AS CpfCliente, " +
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
    
    public void SelecionarOrdemServico() {
        try {
            int os = tableOS.getSelectedRow();
            
            idOS.setText(tableOS.getModel().getValueAt(os, 0).toString());
            fieldIdveiculo.setText(tableOS.getModel().getValueAt(os, 1).toString());
            String dataAberturaDB = tableOS.getModel().getValueAt(os, 5).toString();
            String dataConclusaoDB = tableOS.getModel().getValueAt(os, 6).toString();
            
            java.util.Date parsedDataAbertura = databaseFormat.parse(dataAberturaDB);
            java.util.Date parsedDataConclusao = databaseFormat.parse(dataConclusaoDB);

            String formattedDataAbertura = userInputFormat.format(parsedDataAbertura);
            String formattedDataConclusao = userInputFormat.format(parsedDataConclusao);

            dataAbertura.setText(formattedDataAbertura);
            dataConclusao.setText(formattedDataConclusao);
            
            statusOS.setSelectedItem(tableOS.getModel().getValueAt(os, 7).toString());
            valorTotal.setText(tableOS.getModel().getValueAt(os, 8).toString());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void AlterarOrdemServico(){
        String sql = "UPDATE OrdensDeServico SET IdVeiculo =?, DataAbertura =?, DataConclusao =?, Status =?, Total =? WHERE IdOS =? ";
        
        try{
            pst = database.prepareStatement(sql);

            java.util.Date parsedDataAbertura = userInputFormat.parse(dataAbertura.getText());
            java.util.Date parsedDataConclusao = userInputFormat.parse(dataConclusao.getText());

            String formattedDataAbertura = databaseFormat.format(parsedDataAbertura);
            String formattedDataConclusao = databaseFormat.format(parsedDataConclusao);

            pst.setString(1, fieldIdveiculo.getText());
            pst.setString(2, formattedDataAbertura);
            pst.setString(3, formattedDataConclusao);
            pst.setString(4, statusOS.getSelectedItem().toString());
            pst.setString(5, valorTotal.getText());
            pst.setString(6, idOS.getText());

            if (fieldIdveiculo.getText().isEmpty() || dataAbertura.getText().isEmpty() || statusOS.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de serviço adicionada com sucesso!");

                    LimparCampos();
                }
            }

            PesquisarOrdemServico();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    
    //================================Veiculo
    
    private void PesquisarVeiculo(){
        String sql;
        ResultSet rs;
        if (fieldSearch.getText() != null && !fieldSearch.getText().isEmpty()) {
            sql = "SELECT v.IdVeiculo AS Id, v.Placa AS Placa, c.Nome AS NomeCliente, c.CPF_CNPJ AS CpfCliente " +
                  "FROM veiculos v " +
                  "JOIN clientes c ON v.IdCliente = c.IdCliente " +
                  "WHERE v.Placa LIKE ?";
        } else {
            sql = "SELECT v.IdVeiculo AS Id, v.Placa AS Placa, c.Nome AS NomeCliente, c.CPF_CNPJ AS CpfCliente " +
                  "FROM veiculos v " +
                  "JOIN clientes c ON v.IdCliente = c.IdCliente";
        }
        
        try{
            pst = database.prepareStatement(sql);
            
            if (sql.contains("?")) {
                pst.setString(1, fieldSearch.getText() + "%");
            }
            
            rs = pst.executeQuery();
            
            tableVeiculo.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void SelecionarIdVeiculo(){
        try{
            int cliente = tableVeiculo.getSelectedRow();
            
            fieldIdveiculo.setText(tableVeiculo.getModel().getValueAt(cliente, 0).toString());
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    //====================================
    
    
    private void LimparCampos(){
        fieldIdveiculo.setText(null);
        dataConclusao.setText(null);
        dataAbertura.setText(null);
        valorTotal.setText(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVeiculo = new javax.swing.JTable();
        fieldSearch = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        addOS = new javax.swing.JButton();
        dataConclusao = new javax.swing.JTextField();
        valorTotal = new javax.swing.JTextField("0.00");
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        SignalField = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fieldIdveiculo = new javax.swing.JTextField();
        editOS = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableOS = new javax.swing.JTable();
        dataAbertura = new javax.swing.JTextField();
        statusOS = new javax.swing.JComboBox<>();
        idOS = new javax.swing.JTextField();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(908, 604));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Veículo"));

        tableVeiculo = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tableVeiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableVeiculo.getTableHeader().setReorderingAllowed(false);
        tableVeiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableVeiculoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableVeiculo);

        fieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSearchActionPerformed(evt);
            }
        });

        Search.setText("Buscar pela placa");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fieldSearch)
                        .addGap(18, 18, 18)
                        .addComponent(Search))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Search))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addContainerGap())
        );

        addOS.setText("Adicionar");
        addOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addOSActionPerformed(evt);
            }
        });

        dataConclusao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataConclusaoActionPerformed(evt);
            }
        });

        jLabel1.setText("* Id veículo");

        jLabel2.setText("* Data abertura (dd/mm/yyyy)");

        jLabel3.setText("* Data conclusão (dd/mm/yyyy)");

        jLabel4.setText("* Status");

        SignalField.setText("* Campos Obrigatórios");

        jLabel6.setText("Valor total");

        fieldIdveiculo.setEditable(false);
        fieldIdveiculo.setBackground(new java.awt.Color(255, 255, 255));
        fieldIdveiculo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldIdveiculo.setBorder(new javax.swing.border.MatteBorder(null));
        fieldIdveiculo.setName(""); // NOI18N

        editOS.setText("Editar");
        editOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOSActionPerformed(evt);
            }
        });

        tableOS = new javax.swing.JTable(){     public boolean isCellEditable(int rowIndex, int colIndex){         return false;     } };
        tableOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableOSMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableOS);

        dataAbertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dataAberturaActionPerformed(evt);
            }
        });

        statusOS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aberta", "Em Execução", "Concluída", "Cancelada" }));

        idOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SignalField)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(editOS)
                                .addGap(18, 18, 18)
                                .addComponent(addOS)))
                        .addGap(0, 45, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dataConclusao)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(dataAbertura)
                                    .addComponent(fieldIdveiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(valorTotal)
                                    .addComponent(statusOS, 0, 193, Short.MAX_VALUE)))
                            .addComponent(idOS, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(SignalField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(statusOS)
                            .addComponent(fieldIdveiculo, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dataAbertura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(valorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataConclusao, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editOS)
                    .addComponent(addOS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(idOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addOSActionPerformed
        AdicionarOrdemServico();
    }//GEN-LAST:event_addOSActionPerformed

    private void dataConclusaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataConclusaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataConclusaoActionPerformed

    private void fieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSearchActionPerformed
        PesquisarVeiculo();
    }//GEN-LAST:event_fieldSearchActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        PesquisarVeiculo();
    }//GEN-LAST:event_SearchActionPerformed

    private void tableVeiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableVeiculoMouseClicked
        SelecionarIdVeiculo();
    }//GEN-LAST:event_tableVeiculoMouseClicked

    private void editOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editOSActionPerformed
        AlterarOrdemServico();
    }//GEN-LAST:event_editOSActionPerformed

    private void tableOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableOSMouseClicked
        SelecionarOrdemServico();
    }//GEN-LAST:event_tableOSMouseClicked

    private void dataAberturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dataAberturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dataAberturaActionPerformed

    private void idOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idOSActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Search;
    private javax.swing.JLabel SignalField;
    private javax.swing.JButton addOS;
    private javax.swing.JTextField dataAbertura;
    private javax.swing.JTextField dataConclusao;
    private javax.swing.JButton editOS;
    private javax.swing.JTextField fieldIdveiculo;
    private javax.swing.JTextField fieldSearch;
    private javax.swing.JTextField idOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> statusOS;
    private javax.swing.JTable tableOS;
    private javax.swing.JTable tableVeiculo;
    private javax.swing.JTextField valorTotal;
    // End of variables declaration//GEN-END:variables
}
