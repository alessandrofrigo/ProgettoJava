package com.mio.progetto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseCreator {
    private static final String URL= "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME= "gestione_spese";
    private static final String USER= "root";
    private static final String PASSWORD= "test1234";

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
