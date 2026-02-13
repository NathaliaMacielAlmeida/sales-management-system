package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoSQLite {

    private static final String URL = "jdbc:sqlite:banco_vendas.db";

    public static Connection conectar() {
        Connection conn = null;

        try {
            // 👇 FORÇA carregar o driver
            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(URL);
            System.out.println("Conexão com SQLite realizada com sucesso!");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver SQLite não encontrado!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }

        return conn;
    }
}
