package com.mio.progetto.Model;

//classe per gestire una spesa o entrata
public class TransazioneEntity {
    private int Id;
    private String descrizione;
    private String categoria;
    private double importo;
    private String data;


    public TransazioneEntity(int Id, String descrizione, Categoria categoria, double importo, String data){
        this.Id= Id;
        this.descrizione= descrizione;
        this.categoria= String.valueOf(categoria);
        this.importo= importo;
        this.data= data;
    }

    public int getId() {return Id;}

    @Override
    public String toString() {
        return "TransazioneEntity{" +
                "descrizione= " + descrizione + '\'' +
                '}';
    }

    public String getDescrizione() { return descrizione;}
    public String getCategoria(){ return categoria;}
    public Double getImporto() {return importo;}
    public String getData() { return data;}
}
