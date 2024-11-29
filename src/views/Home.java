
package views;

import java.sql.*;
import dal.ConnectionModule;
import javax.swing.*;
import javax.swing.JOptionPane;



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
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        Report = new javax.swing.JMenu();
        reportService = new javax.swing.JMenuItem();
        Options = new javax.swing.JMenu();
        optionsHelp = new javax.swing.JMenuItem();
        optionsExit = new javax.swing.JMenuItem();

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

        jMenuItem1.setText("Execução OS");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Pagamento OS");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Produto OS");
        jMenu1.add(jMenuItem3);

        menuBarHome.add(jMenu1);

        Report.setText("Relatório");

        reportService.setText("Serviços");
        Report.add(reportService);

        menuBarHome.add(Report);

        Options.setText("Opções");

        optionsHelp.setText("Ajuda");
        Options.add(optionsHelp);

        optionsExit.setText("Sair");
        optionsExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsExitActionPerformed(evt);
            }
        });
        Options.add(optionsExit);

        menuBarHome.add(Options);

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

    private void optionsExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsExitActionPerformed
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção!", JOptionPane.YES_NO_OPTION);
        
        if(sair == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_optionsExitActionPerformed

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

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu Options;
    private javax.swing.JMenu Report;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuBar menuBarHome;
    private javax.swing.JMenuItem optionsExit;
    private javax.swing.JMenuItem optionsHelp;
    private javax.swing.JDesktopPane painelHome;
    private javax.swing.JMenu register;
    private javax.swing.JMenuItem registerClient;
    private javax.swing.JMenuItem registerService;
    private javax.swing.JMenuItem registerStock;
    private javax.swing.JMenuItem registerVeicles;
    private javax.swing.JMenuItem reportService;
    // End of variables declaration//GEN-END:variables
}
