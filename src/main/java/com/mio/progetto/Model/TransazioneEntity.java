package com.mio.progetto.Model;

//classe per gestire una spesa o entrata
public class TransazioneEntity {
    private int id;
    private String descrizione;
    private Categoria categoria;
    private String sottocategoria;
    private double importo;
    private String data;

    // No-args constructor for Jackson
    public TransazioneEntity() {}

//    public TransazioneEntity(int Id, String descrizione, Categoria categoria, Sottocategoria sottocategoria, double importo, String data){
//        this.Id= Id;
//        this.descrizione= descrizione;
//        this.categoria= Categoria.valueOf(String.valueOf(categoria));
//        this.sottocategoria = String.valueOf(sottocategoria);
//        this.importo= importo;
//        this.data= data;
//    }

    public TransazioneEntity(int id, String descrizione, String categoriaStr, String sottocategoriaStr, double importo, String data) {
        this.id = id;
        this.descrizione = descrizione;
        this.categoria = Categoria.valueOf(categoriaStr.toUpperCase());
        this.sottocategoria = String.valueOf(new Sottocategoria(sottocategoriaStr, this.categoria));
        this.importo = importo;
        this.data = data;
    }

    public TransazioneEntity(int id, String descrizione, Categoria categoria, String sottocategoriaStr, double importo, String data) {
        this.id = id;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.sottocategoria = sottocategoriaStr;
        this.importo = importo;
        this.data = data;
    }


    public int getId() {return id;}
    public void setId(int id) { this.id = id; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public void setSottocategoria(String sottocategoria) { this.sottocategoria = sottocategoria; }
    public void setImporto(double importo) { this.importo = importo; }
    public void setData(String data) { this.data = data; }

    @Override
    public String toString() {
        return "TransazioneEntity{" +
                "id=" + id +
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
