
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
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelHome = new javax.swing.JDesktopPane();
        menuBarHome = new javax.swing.JMenuBar();
        register = new javax.swing.JMenu();
        registerClient = new javax.swing.JMenuItem();
        registerService = new javax.swing.JMenuItem();
        registerStock = new javax.swing.JMenuItem();
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
            .addGap(0, 604, Short.MAX_VALUE)
        );

        register.setText("Cadastro");

        registerClient.setText("Cliente");
        registerClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerClientActionPerformed(evt);
            }
        });
        register.add(registerClient);

        registerService.setText("Serviços");
        register.add(registerService);

        registerStock.setText("Estoque");
        register.add(registerStock);

        menuBarHome.add(register);

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
                .addContainerGap()
                .addComponent(painelHome)
                .addContainerGap())
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
        CadastroCliente cliente = new CadastroCliente();
        cliente.setVisible(true);    
        painelHome.add(cliente);
    }//GEN-LAST:event_registerClientActionPerformed

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
    private javax.swing.JMenuBar menuBarHome;
    private javax.swing.JMenuItem optionsExit;
    private javax.swing.JMenuItem optionsHelp;
    private javax.swing.JDesktopPane painelHome;
    private javax.swing.JMenu register;
    private javax.swing.JMenuItem registerClient;
    private javax.swing.JMenuItem registerService;
    private javax.swing.JMenuItem registerStock;
    private javax.swing.JMenuItem reportService;
    // End of variables declaration//GEN-END:variables
}
