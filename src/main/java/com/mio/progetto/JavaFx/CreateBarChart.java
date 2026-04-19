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

import javax.swing.*;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class CreateBarChart extends Application {

    /**
     * Ottiene una connessione al database leggendo le configurazioni da application.properties.
     */
    private static Connection getDbConnection() throws Exception {
        Properties props = new Properties();
        try (InputStream input = CreateBarChart.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException("application.properties non trovato");
            }
            props.load(input);
        }
        String url = props.getProperty("spring.datasource.url");
        String user = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");
        return DriverManager.getConnection(url, user, password);
    }

    private JFreeChart createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection conn = getDbConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT categoria, SUM(importo) AS totale FROM transazioni GROUP BY categoria")) {

            while (rs.next()) {
                dataset.addValue(rs.getDouble("totale"), rs.getString("categoria"), "Spese");
            }

        } catch (Exception e) {
            System.err.println("Errore DB: " + e.getMessage());
        }

        return ChartFactory.createBarChart("Spese per Categoria", "Categoria", "Euro", dataset);
    }

    @Override
    public void start(Stage primaryStage) {
        SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);

        StackPane root = new StackPane(swingNode);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Grafico Spese");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            JFreeChart chart = createBarChart();
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
            swingNode.setContent(chartPanel);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
