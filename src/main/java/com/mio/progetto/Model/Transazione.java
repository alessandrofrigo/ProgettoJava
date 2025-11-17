package com.mio.progetto.Model;

import com.mio.progetto.database.DataBaseCreator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Transazione {

    public void insertTransizione(TransazioneEntity t) {
        String sql = "INSERT INTO transazioni (descrizione, importo, categoria, sottocategoria, data) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseCreator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, t.getDescrizione());
            pstmt.setDouble(2, t.getImporto());
            pstmt.setString(3, t.getCategoria().toString());          // salva enum come nome (STRING)
            pstmt.setString(4, t.getSottocategoria());  // salva nome della sottocategoria
            pstmt.setString(5, t.getData());

            pstmt.executeUpdate();
            System.out.println("Transazione inserita con successo!");

        } catch (SQLException e) {
            System.out.println("Errore nell'inserimento della transazione: " + e.getMessage());
        }
    }

    public List<TransazioneEntity> getAllTransazioni() {
        List<TransazioneEntity> lista = new ArrayList<>();
        String sql = "SELECT * FROM transazioni";

        try (Connection conn = DataBaseCreator.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // leggo le colonne come stringhe/primitive dal DB
                int id = rs.getInt("id");
                String descrizione = rs.getString("descrizione");
                String categoriaStr = rs.getString("categoria");
                String sottocategoriaStr = rs.getString("sottocategoria");
                double importo = rs.getDouble("importo");
                String data = rs.getString("data");

                // costruisco l'oggetto TransazioneEntity usando il costruttore di comodo
                TransazioneEntity te = new TransazioneEntity(
                        id,
                        descrizione,
                        categoriaStr,
                        sottocategoriaStr,
                        importo,
                        data
                );

                lista.add(te);
            }

        } catch (SQLException e) {
            System.out.println("Errore nel recupero delle transazioni: " + e.getMessage());
        }

        return lista;
    }

    // rimanenti metodi delete/reset come li hai già (li lascio invariati)
    public void deleteTransazioneById(int id) {
        String sql = "DELETE FROM transazioni WHERE id = ?";
        try (Connection conn = DataBaseCreator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transazione eliminata con successo!");
                resetAutoIncrement();
            } else {
                System.out.println("Nessuna transazione trovata con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Errore nell'eliminazione della transazione: " + e.getMessage());
        }
    }

    public void deleteTransazioniByCategoria(String categoria) {
        String sql = "DELETE FROM transazioni WHERE categoria = ?";
        try (Connection conn = DataBaseCreator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoria);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " transazioni eliminate nella categoria: " + categoria);
            resetAutoIncrement();
        } catch (SQLException e) {
            System.out.println("Errore nell'eliminazione delle transazioni: " + e.getMessage());
        }
    }

    public void deleteTransazioniBeforeDate(String data) {
        String sql = "DELETE FROM transazioni WHERE data < ?";
        try (Connection conn = DataBaseCreator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " transazioni eliminate prima del " + data);
            resetAutoIncrement();
        } catch (SQLException e) {
            System.out.println("Errore nell'eliminazione delle transazioni: " + e.getMessage());
        }
    }

    public void resetAutoIncrement() {
        String sql = "ALTER TABLE transazioni AUTO_INCREMENT = 1";
        try (Connection conn = DataBaseCreator.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("AUTO_INCREMENT resettato!");
        } catch (SQLException e) {
            System.out.println("Errore nel reset di AUTO_INCREMENT: " + e.getMessage());
        }
    }
}
