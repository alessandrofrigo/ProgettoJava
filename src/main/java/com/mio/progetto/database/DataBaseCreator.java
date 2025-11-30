package com.mio.progetto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreator {
    private static String URL;
    private static String DB_NAME;
    private static String USER;
    private static String PASSWORD;

    static {
        try (java.io.InputStream input = DataBaseCreator.class.getClassLoader().getResourceAsStream("application.properties")) {
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
        }
        catch (SQLException e) {
            System.out.println("Errore nella creazione del database: " + e.getMessage());
        }
    }

    public void CreateTable(){
        //URL per accedere al db specifico
        String URLDB= "jdbc:mysql://localhost:3306/gestione_spese";

        try (Connection conn = DriverManager.getConnection(URL +DB_NAME, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Crea la tabella "transazioni"
             String sql = "CREATE TABLE IF NOT EXISTS transazioni ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "descrizione VARCHAR(255),"
                + "importo DECIMAL(10,2),"
                + "categoria VARCHAR(50),"
                + "sottocategoria VARCHAR(50),"
                + "data DATE)";
            stmt.executeUpdate(sql);
            System.out.println("Tabella creata con successo!");
        }
        catch (SQLException e) {
            System.out.println("Errore nella creazione della tabella: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
    }
}
