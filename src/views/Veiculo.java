package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
public class Veiculo extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    
    public Veiculo() {
        initComponents();
        database = ConnectionModule.conector();
        PesquisarCliente();
        PesquisarVeiculo();
        
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
    
    private void AdicionarVeiculo(){
        String sql = "insert into veiculos (Placa, Modelo, Marca, Ano, IdCliente) values(?,?,?,?,?)";
        
        try{
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldAno.getText());
            pst.setString(2, fieldModelo.getText());
            pst.setString(3, fieldMarca.getText());
            pst.setString(4, fieldAno.getText());
            pst.setString(5, fieldIdcliente.getText());
            
            if(fieldAno.getText().isEmpty() || fieldModelo.getText().isEmpty() || fieldMarca.getText().isEmpty() || fieldAno.getText().isEmpty() || fieldIdcliente.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            }
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Veiculo adicionado com sucesso!");
                    
                    LimparCampos();
                }
            }
            
            PesquisarVeiculo();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        } 
    }
    
    private void PesquisarVeiculo(){
        String sql;
        ResultSet rs;
        if (fieldSearch.getText() != null && !fieldSearch.getText().isEmpty()) {
            sql = "SELECT v.IdVeiculo AS Id, v.Placa AS Placa, v.Modelo AS Modelo, v.Marca AS Marca, v.Ano AS Ano, c.IdCliente AS IdCliente, c.Nome AS NomeCliente, c.CPF_CNPJ AS CpfCliente" +
                  "FROM veiculos v " +
                  "JOIN clientes c ON v.IdCliente = c.IdCliente " +
                  "WHERE v.Placa LIKE ?";
        } else {
            sql = "SELECT v.IdVeiculo AS Id, v.Placa AS Placa, v.Modelo AS Modelo, v.Marca AS Marca, v.Ano AS Ano, c.IdCliente AS IdCliente, c.Nome AS NomeCliente, c.CPF_CNPJ AS CpfCliente " +
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
    
    public void SelecionarVeiculo(){
        try{
            int veiculo = tableVeiculo.getSelectedRow();
            
            fieldPlaca.setText(tableVeiculo.getModel().getValueAt(veiculo, 1).toString());
            fieldModelo.setText(tableVeiculo.getModel().getValueAt(veiculo, 2).toString());
            fieldMarca.setText(tableVeiculo.getModel().getValueAt(veiculo, 3).toString());
            fieldAno.setText(tableVeiculo.getModel().getValueAt(veiculo, 4).toString());
            fieldIdcliente.setText(tableVeiculo.getModel().getValueAt(veiculo, 5).toString());
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void DeletarVeiculo() {
        ResultSet rs;
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja confirmar a exclusão do veículo?", "Atenção!", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String placa = fieldAno.getText();

            try {
                String consultaVinculos = """
                    SELECT
                        (SELECT COUNT(*) FROM OrdensDeServico os INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo WHERE v.Placa = ?) AS TotalOrdensServico,
                        (SELECT COUNT(*) FROM ExecucaoOS ex INNER JOIN OrdensDeServico os ON ex.IdOS = os.IdOS INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo WHERE v.Placa = ?) AS TotalExecucoes,
                        (SELECT COUNT(*) FROM Pagamentos p INNER JOIN OrdensDeServico os ON p.IdOS = os.IdOS INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo WHERE v.Placa = ?) AS TotalPagamentos,
                        (SELECT COUNT(*) FROM ProdutosOS pos INNER JOIN OrdensDeServico os ON pos.IdOS = os.IdOS INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo WHERE v.Placa = ?) AS TotalProdutosOS
                    """;

                pst = database.prepareStatement(consultaVinculos);
                for (int i = 1; i <= 4; i++) {
                    pst.setString(i, placa);
                }

                rs = pst.executeQuery();

                if (rs.next()) {
                    int totalOrdensServico = rs.getInt("TotalOrdensServico");
                    int totalExecucoes = rs.getInt("TotalExecucoes");
                    int totalPagamentos = rs.getInt("TotalPagamentos");
                    int totalProdutosOS = rs.getInt("TotalProdutosOS");

                    String mensagem = String.format(
                        "Este veículo possui os seguintes vínculos:\n" +
                        "- Ordens de Serviço: %d\n" +
                        "- Execuções de OS: %d\n" +
                        "- Pagamentos: %d\n" +
                        "- Produtos de OS: %d\n\n" +
                        "Todos os dados vinculados serão excluídos.\nDeseja continuar?",
                        totalOrdensServico, totalExecucoes, totalPagamentos, totalProdutosOS
                    );

                    int confirmarExclusao = JOptionPane.showConfirmDialog(null, mensagem, "Confirmação", JOptionPane.YES_NO_OPTION);

                    if (confirmarExclusao == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar vínculos: " + ex.getMessage());
                return;
            }

            try {
                database.setAutoCommit(false);

                String deleteProdutosOS = """
                    DELETE pos FROM ProdutosOS pos
                    INNER JOIN OrdensDeServico os ON pos.IdOS = os.IdOS
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    WHERE v.Placa = ?
                """;
                pst = database.prepareStatement(deleteProdutosOS);
                pst.setString(1, placa);
                pst.executeUpdate();

                String deletePagamentos = """
                    DELETE p FROM Pagamentos p
                    INNER JOIN OrdensDeServico os ON p.IdOS = os.IdOS
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    WHERE v.Placa = ?
                """;
                pst = database.prepareStatement(deletePagamentos);
                pst.setString(1, placa);
                pst.executeUpdate();

                String deleteExecucoesOS = """
                    DELETE ex FROM ExecucaoOS ex
                    INNER JOIN OrdensDeServico os ON ex.IdOS = os.IdOS
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    WHERE v.Placa = ?
                """;
                pst = database.prepareStatement(deleteExecucoesOS);
                pst.setString(1, placa);
                pst.executeUpdate();

                String deleteOrdensServico = """
                    DELETE os FROM OrdensDeServico os
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    WHERE v.Placa = ?
                """;
                pst = database.prepareStatement(deleteOrdensServico);
                pst.setString(1, placa);
                pst.executeUpdate();

                String deleteVeiculo = "DELETE FROM Veiculos WHERE Placa = ?";
                pst = database.prepareStatement(deleteVeiculo);
                pst.setString(1, placa);
                int apagado = pst.executeUpdate();

                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Veículo e todos os dados relacionados foram removidos com sucesso!");
                }

                database.commit();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir veículo: " + ex.getMessage());
                try {
                    database.rollback();
                } catch (Exception rollbackEx) {
                    JOptionPane.showMessageDialog(null, "Erro ao desfazer alterações: " + rollbackEx.getMessage());
                }
            } finally {
                try {
                    database.setAutoCommit(true);
                } catch (Exception autoCommitEx) {
                    JOptionPane.showMessageDialog(null, "Erro ao redefinir transação: " + autoCommitEx.getMessage());
                }
            }

            LimparCampos();
            PesquisarVeiculo();
        }
    }
 
    private void AlterarVeiculo(){
        String sql = "UPDATE veiculos SET Placa =?, Modelo =?, Marca =?, Ano =?, IdCliente =? WHERE Placa =? ";
        
        try{
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldAno.getText());
            pst.setString(2, fieldModelo.getText());
            pst.setString(3, fieldMarca.getText());
            pst.setString(4, fieldAno.getText());
            pst.setString(5, fieldIdcliente.getText());
            pst.setString(6, fieldAno.getText());
            
            if(fieldAno.getText().isEmpty() || fieldModelo.getText().isEmpty() || fieldMarca.getText().isEmpty() || fieldAno.getText().isEmpty() || fieldIdcliente.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            }
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Veículo editado com sucesso!");
                    
                    LimparCampos();
                }
            }
            
            PesquisarVeiculo();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    //================================Cliente
    
    private void PesquisarCliente(){
        String sql;
        ResultSet rs;
        if(fieldSearch.getText() != null){
            sql = "select IdCliente, Nome, CPF_CNPJ from clientes where Nome like ?";
        }
        else{
            sql = "select IdCliente, Nome, CPF_CNPJ from clientes";
        }
        
        try{
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldSearch.getText() + "%");
            
            rs = pst.executeQuery();
            
            tableClient.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void SelecionarIdCliente(){
        try{
            int cliente = tableClient.getSelectedRow();
            
            fieldIdcliente.setText(tableClient.getModel().getValueAt(cliente, 0).toString());
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    //====================================
    
    
    private void LimparCampos(){
        fieldIdcliente.setText(null);
        fieldAno.setText(null);
        fieldModelo.setText(null);
        fieldAno.setText(null);
        fieldMarca.setText(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableClient = new javax.swing.JTable();
        fieldSearch = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        fieldModelo = new javax.swing.JTextField();
        addClient = new javax.swing.JButton();
        fieldAno = new javax.swing.JTextField();
        deleteVeiculo = new javax.swing.JButton();
        fieldMarca = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        SignalField = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fieldIdcliente = new javax.swing.JTextField();
        editVeiculo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableVeiculo = new javax.swing.JTable();
        SearchVeiculo = new javax.swing.JButton();
        fieldSearchVeiculo = new javax.swing.JTextField();
        fieldPlaca = new javax.swing.JTextField();

        setBorder(null);
        setPreferredSize(new java.awt.Dimension(908, 604));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        tableClient = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tableClient.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableClient.getTableHeader().setReorderingAllowed(false);
        tableClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableClient);

        fieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSearchActionPerformed(evt);
            }
        });

        Search.setText("Buscar pelo nome");
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

        addClient.setText("Adicionar");
        addClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClientActionPerformed(evt);
            }
        });

        fieldAno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldAnoActionPerformed(evt);
            }
        });

        deleteVeiculo.setText("Deletar");
        deleteVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteVeiculoActionPerformed(evt);
            }
        });

        jLabel1.setText("* Id cliente");

        jLabel2.setText("* Placa");

        jLabel3.setText("* Ano");

        jLabel4.setText("* Modelo");

        SignalField.setText("* Campos Obrigatórios");

        jLabel6.setText("* Marca");

        fieldIdcliente.setEditable(false);
        fieldIdcliente.setBackground(new java.awt.Color(255, 255, 255));
        fieldIdcliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldIdcliente.setBorder(new javax.swing.border.MatteBorder(null));
        fieldIdcliente.setName(""); // NOI18N

        editVeiculo.setText("Editar");
        editVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editVeiculoActionPerformed(evt);
            }
        });

        tableVeiculo = new javax.swing.JTable(){     public boolean isCellEditable(int rowIndex, int colIndex){         return false;     } };
        tableVeiculo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableVeiculo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableVeiculoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableVeiculo);

        SearchVeiculo.setText("Buscar pela placa");
        SearchVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchVeiculoActionPerformed(evt);
            }
        });

        fieldSearchVeiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldSearchVeiculoActionPerformed(evt);
            }
        });

        fieldPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPlacaActionPerformed(evt);
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 802, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 68, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(deleteVeiculo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editVeiculo)
                                .addGap(18, 18, 18)
                                .addComponent(addClient))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fieldSearchVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(SearchVeiculo)
                                        .addGap(288, 288, 288)
                                        .addComponent(SignalField))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(fieldAno, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel1)
                                            .addComponent(fieldIdcliente)
                                            .addComponent(fieldPlaca))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(fieldModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(fieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))))
                                .addGap(0, 22, Short.MAX_VALUE)))
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldSearchVeiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SearchVeiculo)
                    .addComponent(SignalField))
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldIdcliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fieldAno, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteVeiculo)
                    .addComponent(editVeiculo)
                    .addComponent(addClient))
                .addGap(87, 87, 87))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClientActionPerformed
        AdicionarVeiculo();
    }//GEN-LAST:event_addClientActionPerformed

    private void fieldAnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldAnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldAnoActionPerformed

    private void deleteVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteVeiculoActionPerformed
        DeletarVeiculo();
    }//GEN-LAST:event_deleteVeiculoActionPerformed

    private void fieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSearchActionPerformed
        PesquisarCliente();
    }//GEN-LAST:event_fieldSearchActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        PesquisarCliente();
    }//GEN-LAST:event_SearchActionPerformed

    private void tableClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientMouseClicked
        SelecionarIdCliente();
    }//GEN-LAST:event_tableClientMouseClicked

    private void editVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editVeiculoActionPerformed
        AlterarVeiculo();
    }//GEN-LAST:event_editVeiculoActionPerformed

    private void SearchVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchVeiculoActionPerformed
        PesquisarVeiculo();
    }//GEN-LAST:event_SearchVeiculoActionPerformed

    private void fieldSearchVeiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSearchVeiculoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldSearchVeiculoActionPerformed

    private void tableVeiculoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableVeiculoMouseClicked
        SelecionarVeiculo();
    }//GEN-LAST:event_tableVeiculoMouseClicked

    private void fieldPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldPlacaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Search;
    private javax.swing.JButton SearchVeiculo;
    private javax.swing.JLabel SignalField;
    private javax.swing.JButton addClient;
    private javax.swing.JButton deleteVeiculo;
    private javax.swing.JButton editVeiculo;
    private javax.swing.JTextField fieldAno;
    private javax.swing.JTextField fieldIdcliente;
    private javax.swing.JTextField fieldMarca;
    private javax.swing.JTextField fieldModelo;
    private javax.swing.JTextField fieldPlaca;
    private javax.swing.JTextField fieldSearch;
    private javax.swing.JTextField fieldSearchVeiculo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tableClient;
    private javax.swing.JTable tableVeiculo;
    // End of variables declaration//GEN-END:variables
}
