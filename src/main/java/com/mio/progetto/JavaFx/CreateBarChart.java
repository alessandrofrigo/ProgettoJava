package com.mio.progetto.JavaFx;
import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import com.mio.progetto.database.DataBaseCreator;

import javax.swing.*;
import java.sql.*;

public class CreateBarChart extends Application {
    private JFreeChart createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = DataBaseCreator.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT categoria, SUM(importo) AS totale FROM transazioni GROUP BY categoria");) {

            while (rs.next()) {
                dataset.addValue(rs.getDouble("totale"), rs.getString("categoria"), "Spese");
            }

        } catch (SQLException e) {
            System.out.println("Errore DB: " + e.getMessage());
        }

        return ChartFactory.createBarChart("Spese per Categoria", "Categoria", "Euro", dataset);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode); // Integra JFreeChart in SwingNode

        StackPane root = new StackPane(swingNode);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Grafico Spese");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metodo per inserire il grafico Swing in JavaFX
    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JFreeChart chart = createBarChart();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
            swingNode.setContent(chartPanel);
        });
    }

    // Metodo main per avviare l'applicazione
    public static void main(String[] args) {
        launch(args); // Avvia JavaFX
    }
}
