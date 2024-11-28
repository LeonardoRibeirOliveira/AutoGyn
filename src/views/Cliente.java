package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class Cliente extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public Cliente() {
        initComponents();
        database = ConnectionModule.conector();
        PesquisarCliente();
        
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
                    
                    LimparCampos();
                }
            }
            
            PesquisarCliente();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void PesquisarCliente(){
        String sql;
        if(fieldSearch.getText() != null){
            sql = "select * from clientes where Nome like ?";
        }
        else{
            sql = "select * from clientes";
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
    
    public void SelecionarCliente(){
        try{
            int cliente = tableClient.getSelectedRow();
            
            fieldName.setText(tableClient.getModel().getValueAt(cliente, 1).toString());
            String tipoCliente = tableClient.getModel().getValueAt(cliente, 2).toString();
                if (tipoCliente.equalsIgnoreCase("F")) {
                    fieldType.setSelectedItem("Física"); // Seleciona 'Física' na JComboBox
                } else if (tipoCliente.equalsIgnoreCase("J")) {
                    fieldType.setSelectedItem("Jurídica"); // Seleciona 'Jurídica' na JComboBox
                } else {
                    JOptionPane.showMessageDialog(null, "Tipo de cliente inválido!");
                    return; // Sai do método
                }
            fieldCPF.setText(tableClient.getModel().getValueAt(cliente, 3).toString());
            fieldTelephone.setText(tableClient.getModel().getValueAt(cliente, 4).toString());
            fieldEmail.setText(tableClient.getModel().getValueAt(cliente, 5).toString());
            fieldAdress.setText(tableClient.getModel().getValueAt(cliente, 6).toString());
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void AlterarCliente(){
        String sql = "UPDATE clientes SET Nome =?, TipoCliente =?, CPF_CNPJ =?, Telefone =?, Email =?, Endereco =? WHERE CPF_CNPJ =?";
        
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
            pst.setString(7, fieldCPF.getText());
            
            if(fieldName.getText().isEmpty() || fieldType.getSelectedItem().toString().isEmpty() ||fieldCPF.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            }
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
                    
                    LimparCampos();
                }
            }
            
            PesquisarCliente();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void DeletarCliente() {
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja confirmar a exclusão do cliente?", "Atenção!", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String cpfCnpj = fieldCPF.getText();

            // Exibir informações relacionadas antes de excluir
            try {
                String consultaVinculos = """
                    SELECT
                        (SELECT COUNT(*) FROM Veiculos v INNER JOIN Clientes c ON v.IdCliente = c.IdCliente WHERE c.CPF_CNPJ = ?) AS TotalVeiculos,
                        (SELECT COUNT(*) FROM OrdensDeServico os INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo 
                            INNER JOIN Clientes c ON v.IdCliente = c.IdCliente WHERE c.CPF_CNPJ = ?) AS TotalOrdensServico,
                        (SELECT COUNT(*) FROM ExecucaoOS ex INNER JOIN OrdensDeServico os ON ex.IdOS = os.IdOS 
                            INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo 
                            INNER JOIN Clientes c ON v.IdCliente = c.IdCliente WHERE c.CPF_CNPJ = ?) AS TotalExecucoes,
                        (SELECT COUNT(*) FROM Pagamentos p INNER JOIN OrdensDeServico os ON p.IdOS = os.IdOS 
                            INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo 
                            INNER JOIN Clientes c ON v.IdCliente = c.IdCliente WHERE c.CPF_CNPJ = ?) AS TotalPagamentos,
                        (SELECT COUNT(*) FROM ProdutosOS pos INNER JOIN OrdensDeServico os ON pos.IdOS = os.IdOS 
                            INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo 
                            INNER JOIN Clientes c ON v.IdCliente = c.IdCliente WHERE c.CPF_CNPJ = ?) AS TotalProdutosOS
                    """;

                pst = database.prepareStatement(consultaVinculos);
                for (int i = 1; i <= 5; i++) {
                    pst.setString(i, cpfCnpj);
                }

                rs = pst.executeQuery();

                if (rs.next()) {
                    int totalVeiculos = rs.getInt("TotalVeiculos");
                    int totalOrdensServico = rs.getInt("TotalOrdensServico");
                    int totalExecucoes = rs.getInt("TotalExecucoes");
                    int totalPagamentos = rs.getInt("TotalPagamentos");
                    int totalProdutosOS = rs.getInt("TotalProdutosOS");

                    String mensagem = String.format(
                        "Este cliente possui os seguintes vínculos:\n" +
                        "- Veículos: %d\n" +
                        "- Ordens de Serviço: %d\n" +
                        "- Execuções de OS: %d\n" +
                        "- Pagamentos: %d\n" +
                        "- Produtos de OS: %d\n\n" +
                        "Todos os dados vinculados serão excluídos.\nDeseja continuar?",
                        totalVeiculos, totalOrdensServico, totalExecucoes, totalPagamentos, totalProdutosOS
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

            // Excluir em cascata
            try {
                database.setAutoCommit(false);

                String deleteProdutosOS = """
                    DELETE pos FROM ProdutosOS pos
                    INNER JOIN OrdensDeServico os ON pos.IdOS = os.IdOS
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    INNER JOIN Clientes c ON v.IdCliente = c.IdCliente
                    WHERE c.CPF_CNPJ = ?
                """;
                pst = database.prepareStatement(deleteProdutosOS);
                pst.setString(1, cpfCnpj);
                pst.executeUpdate();

                String deletePagamentos = """
                    DELETE p FROM Pagamentos p
                    INNER JOIN OrdensDeServico os ON p.IdOS = os.IdOS
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    INNER JOIN Clientes c ON v.IdCliente = c.IdCliente
                    WHERE c.CPF_CNPJ = ?
                """;
                pst = database.prepareStatement(deletePagamentos);
                pst.setString(1, cpfCnpj);
                pst.executeUpdate();

                String deleteExecucoesOS = """
                    DELETE ex FROM ExecucaoOS ex
                    INNER JOIN OrdensDeServico os ON ex.IdOS = os.IdOS
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    INNER JOIN Clientes c ON v.IdCliente = c.IdCliente
                    WHERE c.CPF_CNPJ = ?
                """;
                pst = database.prepareStatement(deleteExecucoesOS);
                pst.setString(1, cpfCnpj);
                pst.executeUpdate();

                String deleteOrdensServico = """
                    DELETE os FROM OrdensDeServico os
                    INNER JOIN Veiculos v ON os.IdVeiculo = v.IdVeiculo
                    INNER JOIN Clientes c ON v.IdCliente = c.IdCliente
                    WHERE c.CPF_CNPJ = ?
                """;
                pst = database.prepareStatement(deleteOrdensServico);
                pst.setString(1, cpfCnpj);
                pst.executeUpdate();

                String deleteVeiculos = """
                    DELETE v FROM Veiculos v
                    INNER JOIN Clientes c ON v.IdCliente = c.IdCliente
                    WHERE c.CPF_CNPJ = ?
                """;
                pst = database.prepareStatement(deleteVeiculos);
                pst.setString(1, cpfCnpj);
                pst.executeUpdate();

                String deleteCliente = "DELETE FROM Clientes WHERE CPF_CNPJ = ?";
                pst = database.prepareStatement(deleteCliente);
                pst.setString(1, cpfCnpj);
                int apagado = pst.executeUpdate();

                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente e todos os dados relacionados foram removidos com sucesso!");
                }

                database.commit();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao excluir cliente: " + ex.getMessage());
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
            PesquisarCliente();
        }
    }

    
    private void LimparCampos(){
        fieldName.setText(null);
        fieldCPF.setText(null);
        fieldTelephone.setText(null);
        fieldEmail.setText(null);
        fieldAdress.setText(null);
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
        setForeground(java.awt.Color.gray);
        setTitle("Clientes");
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setPreferredSize(new java.awt.Dimension(908, 604));

        SignalField.setText("* Campos Obrigatórios");

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
                                .addGap(420, 420, 420)
                                .addComponent(Search)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                .addGap(46, 46, 46)
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
        AlterarCliente();
    }//GEN-LAST:event_editClientActionPerformed

    private void addClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClientActionPerformed
        AdicionarCliente();
    }//GEN-LAST:event_addClientActionPerformed

    private void deleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteClientActionPerformed
        DeletarCliente();
    }//GEN-LAST:event_deleteClientActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        PesquisarCliente();
    }//GEN-LAST:event_SearchActionPerformed

    private void tableClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientMouseClicked
        SelecionarCliente();
    }//GEN-LAST:event_tableClientMouseClicked

    private void fieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSearchActionPerformed
        PesquisarCliente();
    }//GEN-LAST:event_fieldSearchActionPerformed


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
