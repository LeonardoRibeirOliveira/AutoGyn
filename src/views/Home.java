
package views;

import java.sql.*;
import dal.ConnectionModule;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



public class Home extends javax.swing.JFrame {

    Connection database = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    public Home() {
        initComponents();
        database = ConnectionModule.conector();
        BoasVindas view = new BoasVindas();
        view.setVisible(true);    
        painelHome.removeAll();
        painelHome.add(view);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelHome = new javax.swing.JDesktopPane();
        menuBarHome = new javax.swing.JMenuBar();
        register = new javax.swing.JMenu();
        registerClient = new javax.swing.JMenuItem();
        registerStock = new javax.swing.JMenuItem();
        registerVeicles = new javax.swing.JMenuItem();
        registerService = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        execucaoOs = new javax.swing.JMenuItem();
        pagamentoOs = new javax.swing.JMenuItem();
        produtoOs = new javax.swing.JMenuItem();
        Report = new javax.swing.JMenu();
        reportService = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        painelHome.setAlignmentX(0.0F);
        painelHome.setAlignmentY(0.0F);
        painelHome.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout painelHomeLayout = new javax.swing.GroupLayout(painelHome);
        painelHome.setLayout(painelHomeLayout);
        painelHomeLayout.setHorizontalGroup(
            painelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 908, Short.MAX_VALUE)
        );
        painelHomeLayout.setVerticalGroup(
            painelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 602, Short.MAX_VALUE)
        );

        register.setText("Cadastro");

        registerClient.setText("Clientes");
        registerClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerClientActionPerformed(evt);
            }
        });
        register.add(registerClient);

        registerStock.setText("Estoque");
        registerStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerStockActionPerformed(evt);
            }
        });
        register.add(registerStock);

        registerVeicles.setText("Veículos");
        registerVeicles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerVeiclesActionPerformed(evt);
            }
        });
        register.add(registerVeicles);

        registerService.setText("Ordens de Serviço");
        registerService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerServiceActionPerformed(evt);
            }
        });
        register.add(registerService);

        menuBarHome.add(register);

        jMenu1.setText("Controle");

        execucaoOs.setText("Execução OS");
        execucaoOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execucaoOsActionPerformed(evt);
            }
        });
        jMenu1.add(execucaoOs);

        pagamentoOs.setText("Pagamento OS");
        pagamentoOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagamentoOsActionPerformed(evt);
            }
        });
        jMenu1.add(pagamentoOs);

        produtoOs.setText("Produto OS");
        produtoOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                produtoOsActionPerformed(evt);
            }
        });
        jMenu1.add(produtoOs);

        menuBarHome.add(jMenu1);

        Report.setText("Relatório");

        reportService.setText("Serviços");
        reportService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportServiceActionPerformed(evt);
            }
        });
        Report.add(reportService);

        menuBarHome.add(Report);

        setJMenuBar(menuBarHome);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelHome)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(936, 647));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção!", JOptionPane.YES_NO_OPTION);
        
        if(sair == JOptionPane.YES_OPTION){
            System.exit(0);
        }
        else{
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    private void registerClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerClientActionPerformed
        Cliente cliente = new Cliente();
        cliente.setVisible(true);  
        painelHome.removeAll();
        painelHome.add(cliente);
    }//GEN-LAST:event_registerClientActionPerformed

    private void registerStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerStockActionPerformed
        Estoque estoque = new Estoque();
        estoque.setVisible(true);  
        painelHome.removeAll();
        painelHome.add(estoque);
    }//GEN-LAST:event_registerStockActionPerformed

    private void registerVeiclesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerVeiclesActionPerformed
        Veiculo veiculo = new Veiculo();
        veiculo.setVisible(true);  
        painelHome.removeAll();
        painelHome.add(veiculo);
    }//GEN-LAST:event_registerVeiclesActionPerformed

    private void registerServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerServiceActionPerformed
        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setVisible(true);  
        painelHome.removeAll();
        painelHome.add(ordemServico);
    }//GEN-LAST:event_registerServiceActionPerformed

    private void execucaoOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execucaoOsActionPerformed
        ExecucaoOS execucaoOS = new ExecucaoOS();
        execucaoOS.setVisible(true);  
        painelHome.removeAll();
        painelHome.add(execucaoOS);
    }//GEN-LAST:event_execucaoOsActionPerformed

    private void pagamentoOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagamentoOsActionPerformed
        PagamentoOS pagamentoOS = new PagamentoOS();
        pagamentoOS.setVisible(true);  
        painelHome.removeAll();
        painelHome.add(pagamentoOS);
    }//GEN-LAST:event_pagamentoOsActionPerformed

    private void produtoOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_produtoOsActionPerformed
        ProdutoOS produtoOS = new ProdutoOS();
        produtoOS.setVisible(true);  
        painelHome.removeAll();
        painelHome.add(produtoOS);
    }//GEN-LAST:event_produtoOsActionPerformed

    private void reportServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportServiceActionPerformed
        String outputPath = System.getProperty("user.home") + "/Downloads/relatorio_ordem_servico.csv";

        // Query SQL
        String sql = """
                SELECT 
                    os.IdOS AS OrdemDeServicoID,
                    os.DataAbertura,
                    os.DataConclusao,
                    os.Status,
                    os.Total,
                    v.Placa,
                    v.Modelo,
                    v.Marca,
                    v.Ano,
                    c.Nome AS NomeCliente,
                    c.CPF_CNPJ,
                    c.Telefone,
                    c.Email,
                    c.Endereco
                FROM 
                    OrdensDeServico os
                INNER JOIN 
                    Veiculos v ON os.IdVeiculo = v.IdVeiculo
                INNER JOIN 
                    Clientes c ON v.IdCliente = c.IdCliente
            """;

        // Criando o JFrame para exibir os dados
        JFrame frame = new JFrame("Exportar Relatório");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Tabela para exibir os dados
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
             PreparedStatement pst = database.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            // Escreve o cabeçalho no CSV e na tabela
            String[] columns = {"OrdemDeServicoID", "DataAbertura", "DataConclusao", "Status", "Total", "Placa", "Modelo", "Marca", "Ano", "NomeCliente", "CPF_CNPJ", "Telefone", "Email", "Endereco"};
            model.setColumnIdentifiers(columns);
            writer.write(String.join(",", columns));
            writer.newLine();

            // Itera pelos resultados e escreve cada linha no CSV e na tabela
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("OrdemDeServicoID"),
                        rs.getTimestamp("DataAbertura"),
                        rs.getTimestamp("DataConclusao"),
                        rs.getString("Status"),
                        rs.getDouble("Total"),
                        rs.getString("Placa"),
                        rs.getString("Modelo"),
                        rs.getString("Marca"),
                        rs.getInt("Ano"),
                        rs.getString("NomeCliente"),
                        rs.getString("CPF_CNPJ"),
                        rs.getString("Telefone"),
                        rs.getString("Email"),
                        rs.getString("Endereco")
                };

                // Adiciona a linha na tabela
                model.addRow(row);

                // Adiciona a linha no CSV
                writer.write(String.format("%d,%s,%s,%s,%.2f,%s,%s,%s,%d,%s,%s,%s,%s,%s",
                        rs.getInt("OrdemDeServicoID"),
                        rs.getTimestamp("DataAbertura"),
                        rs.getTimestamp("DataConclusao"),
                        rs.getString("Status"),
                        rs.getDouble("Total"),
                        rs.getString("Placa"),
                        rs.getString("Modelo"),
                        rs.getString("Marca"),
                        rs.getInt("Ano"),
                        rs.getString("NomeCliente"),
                        rs.getString("CPF_CNPJ"),
                        rs.getString("Telefone"),
                        rs.getString("Email"),
                        rs.getString("Endereco")
                ));
                writer.newLine();
            }

            // Estilo do cabeçalho da tabela
            table.getTableHeader().setBackground(Color.LIGHT_GRAY);

            // Mensagem de sucesso
            JOptionPane.showMessageDialog(frame, "Relatório exportado com sucesso para " + outputPath);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao executar a consulta: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao salvar o arquivo: " + e.getMessage());
        }

        // Exibe o JFrame
        frame.setVisible(true);
        
    }//GEN-LAST:event_reportServiceActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Report;
    private javax.swing.JMenuItem execucaoOs;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar menuBarHome;
    private javax.swing.JMenuItem pagamentoOs;
    private javax.swing.JDesktopPane painelHome;
    private javax.swing.JMenuItem produtoOs;
    private javax.swing.JMenu register;
    private javax.swing.JMenuItem registerClient;
    private javax.swing.JMenuItem registerService;
    private javax.swing.JMenuItem registerStock;
    private javax.swing.JMenuItem registerVeicles;
    private javax.swing.JMenuItem reportService;
    // End of variables declaration//GEN-END:variables
}
