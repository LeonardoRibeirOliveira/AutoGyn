package views;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dal.ConnectionModule;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

public class Estoque extends javax.swing.JInternalFrame {

    Connection database = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public Estoque() {
        initComponents();
        database = ConnectionModule.conector();
        PesquisarEstoque();
        
        
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
    
    private void AdicionarEstoque(){
        String sql = "insert into estoque (NomeProduto, Descricao, Quantidade, PrecoUnitario) values(?,?,?,?)";
        
        try{
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldName.getText());
            pst.setString(2, fieldDescricao.getText());
            pst.setString(3, fieldQuantidade.getText());
            pst.setString(4, fieldPreco.getText());
            
            if(fieldName.getText().isEmpty() || fieldQuantidade.getText().isEmpty() || fieldPreco.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            }
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
                    
                    LimparCampos();
                }
            }
            
            PesquisarEstoque();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void PesquisarEstoque(){
        String sql;
        ResultSet rs;
        if (fieldSearch.getText() != null && !fieldSearch.getText().isEmpty()) {
            sql = "select * from estoque where NomeProduto like ?";
        } else {
            sql = "select * from estoque";
        }
        
        try{
            pst = database.prepareStatement(sql);
            
            if (sql.contains("?")) {
                pst.setString(1, fieldSearch.getText() + "%");
            }
            
            rs = pst.executeQuery();
            
            tableEstoque.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
     
    public void SelecionarEstoque(){
        try{
            int veiculo = tableEstoque.getSelectedRow();
            
            fieldId.setText(tableEstoque.getModel().getValueAt(veiculo, 0).toString());
            fieldName.setText(tableEstoque.getModel().getValueAt(veiculo, 1).toString());
            fieldDescricao.setText(tableEstoque.getModel().getValueAt(veiculo, 2).toString());
            fieldQuantidade.setText(tableEstoque.getModel().getValueAt(veiculo, 3).toString());
            fieldPreco.setText(tableEstoque.getModel().getValueAt(veiculo, 4).toString());
            
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void AlterarEstoque(){
        String sql = "UPDATE estoque SET NomeProduto =?, Descricao =?, Quantidade =?, PrecoUnitario =? WHERE IdProduto =? ";       
        try{
            pst = database.prepareStatement(sql);
            pst.setString(1, fieldName.getText());
            pst.setString(2, fieldDescricao.getText());
            pst.setString(3, fieldQuantidade.getText());
            pst.setString(4, fieldPreco.getText());
            pst.setString(5, fieldId.getText());
            
            if(fieldName.getText().isEmpty() || fieldDescricao.getText().isEmpty() || fieldQuantidade.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
            }
            else{
                int adicionado = pst.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Estoque editado com sucesso!");
                    
                    LimparCampos();
                }
            }
            
            PesquisarEstoque();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    private void DeletarEstoque(){
        int confirma = JOptionPane.showConfirmDialog(null, "Deseja confirmar a exclução do estoque selecionado?", "Atenção!", JOptionPane.YES_NO_OPTION);
        
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "delete from estoque WHERE IdProduto =?";
        
            try{
                pst = database.prepareStatement(sql);
                pst.setString(1, fieldId.getText());

                int apagado = pst.executeUpdate();

                if(apagado > 0){
                    JOptionPane.showMessageDialog(null, "Estoque removido com sucesso!");

                    
                }
                
                LimparCampos();

                PesquisarEstoque();
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
    
    private void LimparCampos(){
        fieldName.setText(null);
        fieldDescricao.setText(null);
        fieldQuantidade.setText(null);
        fieldPreco.setText(null);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        SignalField = new javax.swing.JLabel();
        fieldName = new javax.swing.JTextField();
        fieldQuantidade = new javax.swing.JTextField();
        fieldPreco = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        editEstoque = new javax.swing.JButton();
        addEstoque = new javax.swing.JButton();
        deleteEstoque = new javax.swing.JButton();
        fieldSearch = new javax.swing.JTextField();
        Search = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEstoque = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        fieldDescricao = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        fieldId = new javax.swing.JTextField();

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

        fieldQuantidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldQuantidadeActionPerformed(evt);
            }
        });

        fieldPreco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPrecoActionPerformed(evt);
            }
        });

        jLabel1.setText("* Nome produto");

        jLabel2.setText("* Quantidade");

        jLabel3.setText("* Preço");

        editEstoque.setText("Editar");
        editEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editEstoqueActionPerformed(evt);
            }
        });

        addEstoque.setText("Adicionar");
        addEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEstoqueActionPerformed(evt);
            }
        });

        deleteEstoque.setText("Deletar");
        deleteEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEstoqueActionPerformed(evt);
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

        tableEstoque = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tableEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tableEstoque.getTableHeader().setReorderingAllowed(false);
        tableEstoque.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableEstoqueMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableEstoque);

        fieldDescricao.setColumns(20);
        fieldDescricao.setRows(5);
        jScrollPane2.setViewportView(fieldDescricao);

        jLabel7.setText("Descrição");

        fieldId.setEditable(false);
        fieldId.setBackground(new java.awt.Color(204, 204, 204));
        fieldId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fieldId.setPreferredSize(new java.awt.Dimension(0, 0));
        fieldId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldIdActionPerformed(evt);
            }
        });

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                                .addComponent(SignalField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(deleteEstoque)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editEstoque)
                                .addGap(18, 18, 18)
                                .addComponent(addEstoque)))
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(fieldName, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
                                .addComponent(fieldQuantidade)
                                .addComponent(fieldPreco)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(69, 69, 69))))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(fieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldPreco, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteEstoque)
                    .addComponent(editEstoque)
                    .addComponent(addEstoque))
                .addGap(68, 68, 68))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fieldNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldNameActionPerformed

    private void fieldQuantidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldQuantidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldQuantidadeActionPerformed

    private void fieldPrecoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPrecoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldPrecoActionPerformed

    private void editEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editEstoqueActionPerformed
        AlterarEstoque();
    }//GEN-LAST:event_editEstoqueActionPerformed

    private void addEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEstoqueActionPerformed
        AdicionarEstoque();
    }//GEN-LAST:event_addEstoqueActionPerformed

    private void deleteEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEstoqueActionPerformed
        DeletarEstoque();
    }//GEN-LAST:event_deleteEstoqueActionPerformed

    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
        PesquisarEstoque();
    }//GEN-LAST:event_SearchActionPerformed

    private void tableEstoqueMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEstoqueMouseClicked
        SelecionarEstoque();
    }//GEN-LAST:event_tableEstoqueMouseClicked

    private void fieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldSearchActionPerformed
        PesquisarEstoque();
    }//GEN-LAST:event_fieldSearchActionPerformed

    private void fieldIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldIdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Search;
    private javax.swing.JLabel SignalField;
    private javax.swing.JButton addEstoque;
    private javax.swing.JButton deleteEstoque;
    private javax.swing.JButton editEstoque;
    private javax.swing.JTextArea fieldDescricao;
    private javax.swing.JTextField fieldId;
    private javax.swing.JTextField fieldName;
    private javax.swing.JTextField fieldPreco;
    private javax.swing.JTextField fieldQuantidade;
    private javax.swing.JTextField fieldSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tableEstoque;
    // End of variables declaration//GEN-END:variables
}
