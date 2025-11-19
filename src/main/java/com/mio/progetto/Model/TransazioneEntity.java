package com.mio.progetto.Model;

//classe per gestire una spesa o entrata
public class TransazioneEntity {
    private int Id;
    private String descrizione;
    private Categoria categoria;
    private String sottocategoria;
    private double importo;
    private String data;


//    public TransazioneEntity(int Id, String descrizione, Categoria categoria, Sottocategoria sottocategoria, double importo, String data){
//        this.Id= Id;
//        this.descrizione= descrizione;
//        this.categoria= Categoria.valueOf(String.valueOf(categoria));
//        this.sottocategoria = String.valueOf(sottocategoria);
//        this.importo= importo;
//        this.data= data;
//    }

    public TransazioneEntity(int id, String descrizione, String categoriaStr, String sottocategoriaStr, double importo, String data) {
        this.Id = Id;
        this.descrizione = descrizione;
        this.categoria = Categoria.valueOf(String.valueOf(Categoria.valueOf(categoriaStr)));
        this.sottocategoria = String.valueOf(new Sottocategoria(sottocategoriaStr, this.categoria));
        this.importo = importo;
        this.data = data;
    }

    public TransazioneEntity(int id, String descrizione, Categoria categoria, String sottocategoriaStr, double importo, String data) {
        this.Id = Id;
        this.descrizione = descrizione;
        this.categoria = Categoria.valueOf(String.valueOf(categoria));
        this.sottocategoria = sottocategoriaStr;
        this.importo = importo;
        this.data = data;
    }


    public int getId() {return Id;}

    @Override
    public String toString() {
        return "TransazioneEntity{" +
                "id=" + Id +
                ", descrizione='" + descrizione + '\'' +
                ", categoria='" + categoria + '\'' +
                ", sottocategoria='" + sottocategoria + '\'' +
                ", importo=" + importo +
                ", data='" + data + '\'' +
                '}';
    }

    public String getDescrizione() { return descrizione;}
    public Categoria getCategoria(){ return categoria;}
    public String getSottocategoria() { return sottocategoria;}
    public Double getImporto() {return importo;}
    public String getData() { return data;}
}
