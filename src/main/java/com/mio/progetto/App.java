package com.mio.progetto;

import com.mio.progetto.JavaFx.CreateBarChart;
import com.mio.progetto.Model.Categoria;
import com.mio.progetto.Model.Transazione;
import com.mio.progetto.Model.TransazioneEntity;
import com.mio.progetto.database.DataBaseCreator;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class App
{
    public static void main( String[] args ) throws SQLException {
        DataBaseCreator dataBaseCreator= new DataBaseCreator();
        dataBaseCreator.CreateTable();
        Transazione transazione = new Transazione();

        do {
            System.out.println("Che operazione vuoi fare? \n I - inserimento \n D - delete \n T- lista di tutte le transazioni \n G - visualizza grafico spese \n E - exit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("E")) {
                System.exit(0);
            }
            if (input.equalsIgnoreCase("I")) {
                System.out.println("Inserire Descrizione");
                scanner = new Scanner(System.in);
                input = scanner.nextLine();
                String descrizione = input;
//                System.out.println("Inserire Categoria");
//                scanner = new Scanner(System.in);
//                input = scanner.nextLine();
//                String categoria = input;

                System.out.println("Inserire Categoria tra le seguenti:");
                for (Categoria c : Categoria.values()) {
                    System.out.println("- " + c);
                }
                scanner = new Scanner(System.in);
                input = scanner.nextLine();

                Categoria categoria;
                try {
                    categoria = Categoria.valueOf(input.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Categoria non valida. Riprova.");
                    continue;
                }



                System.out.println("Inserire Importo");
                scanner = new Scanner(System.in);
                input = scanner.nextLine();
                double importo = Double.parseDouble(input);
                System.out.println("Inserire Data in formato YYYY-MM-DD");
                scanner = new Scanner(System.in);
                input = scanner.nextLine();
                String data = input;
                transazione.insertTransizione(new TransazioneEntity(0, descrizione, categoria, importo, data));
            }
            if(input.equalsIgnoreCase("D")){
                System.out.println("Che tipo di delete vuoi fare? /n Id - tramite ID /n C - tramite categoria /n Data - tramite data");
                if (input.equalsIgnoreCase("Id")) {
                    System.out.println("inserire ID");
                    scanner= new Scanner(System.in);
                    int id= Integer.parseInt(scanner.nextLine());
                    transazione.deleteTransazioneById(id);
                }
                if (input.equalsIgnoreCase("C")) {
                    System.out.println("inserire categoria");
                    scanner= new Scanner(System.in);
                    String categoria= scanner.nextLine();
                    transazione.deleteTransazioniByCategoria(categoria);
                }
                if (input.equalsIgnoreCase("Data")) {
                    System.out.println("inserire Data in formato YYYY-MM-DD");
                    scanner= new Scanner(System.in);
                    String data= scanner.nextLine();
                    transazione.deleteTransazioniBeforeDate(data);
                }
            }
            if (input.equalsIgnoreCase("T")) {
                System.out.println("Lista di tutte le operazioni");
                transazione.getAllTransazioni().forEach(t -> System.out.println(t.getDescrizione() + " - " + t.getImporto() + "€"));
            }
            if (input.equalsIgnoreCase("G")){
                CreateBarChart.main(args);
            }
        } while (true);
    }
}
