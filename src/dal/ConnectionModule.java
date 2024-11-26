package dal;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionModule {
    
    public static Connection conector() {
        Connection database = null;
        
        // Nome do driver JDBC correto
        String driver = "com.mysql.cj.jdbc.Driver";
        
        // Dados da conexão
        String url = "jdbc:mysql://localhost:3306/auto_gyn_database";
        String user = "root";
        String password = "root";
        
        try {
            // Carrega o driver JDBC
            Class.forName(driver);
            
            // Estabelece a conexão
            database = DriverManager.getConnection(url, user, password);
            return database;
        } catch (Exception e) {
            System.err.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}
