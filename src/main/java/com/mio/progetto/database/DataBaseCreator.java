package com.mio.progetto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class DataBaseCreator {
    private static String URL;
    private static String DB_NAME;
    private static String USER;
    private static String PASSWORD;

    static {
        try (java.io.InputStream input = DataBaseCreator.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            java.util.Properties prop = new java.util.Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find application.properties");
                // Set default values or handle error
                URL = "jdbc:mysql://localhost:3306/";
                DB_NAME = "gestione_spese";
                USER = "root";
                PASSWORD = "test1234";
            } else {
                prop.load(input);
                URL = prop.getProperty("db.url");
                DB_NAME = prop.getProperty("db.name");
                USER = prop.getProperty("db.username");
                PASSWORD = prop.getProperty("db.password");
            }
        } catch (java.io.IOException ex) {
            ex.printStackTrace();
        }
    }

    public DataBaseCreator() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {

            // Crea il database "gestione_spese" se non esiste
            String sql = "CREATE DATABASE IF NOT EXISTS gestione_spese";
            stmt.executeUpdate(sql);
            System.out.println("Database creato con successo!");
        } catch (SQLException e) {
            System.out.println("Errore nella creazione del database: " + e.getMessage());
        }
    }

    @PostConstruct
    public void initDatabaseTables() {
        try (Connection conn = DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
                Statement stmt = conn.createStatement()) {

            // Crea la tabella "utenti" (deve essere creata PRIMA di transazioni)
            String sqlUtenti = "CREATE TABLE IF NOT EXISTS utenti ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(100) UNIQUE,"
                    + "email VARCHAR(150) UNIQUE,"
                    + "password_hash VARCHAR(255),"
                    + "ruolo VARCHAR(50),"
                    + "data_registrazione DATETIME)";
            stmt.executeUpdate(sqlUtenti);
            System.out.println("Tabella utenti creata/verificata con successo!");

            // Crea la tabella "transazioni" (con chiave esterna su utenti)
            String sqlTransazioni = "CREATE TABLE IF NOT EXISTS transazioni ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "descrizione VARCHAR(255),"
                    + "importo DECIMAL(10,2),"
                    + "categoria VARCHAR(50),"
                    + "sottocategoria VARCHAR(50),"
                    + "data DATE,"
                    + "utente_id INT,"
                    + "FOREIGN KEY (utente_id) REFERENCES utenti(id))";
            stmt.executeUpdate(sqlTransazioni);
            System.out.println("Tabella transazioni creata/verificata con successo!");

        } catch (SQLException e) {
            System.out.println("Errore nella creazione delle tabelle: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
    }
}
