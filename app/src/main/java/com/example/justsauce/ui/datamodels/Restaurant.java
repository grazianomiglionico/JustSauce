package com.example.justsauce.ui.datamodels;

public class Restaurant {

    private String nome;
    private String indirizzo;
    private double ordineMinimo;
    private String categoria;
    private String tempoConsegna;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Restaurant(){}

    public Restaurant(String nome,String indirizzo,double ordineMinimo,String categoria, String tempoConsegna){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.ordineMinimo = ordineMinimo;
        this.categoria = categoria;
        this.tempoConsegna = tempoConsegna;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public double getOrdineMinimo() {
        return ordineMinimo;
    }

    public void setOrdineMinimo(double ordineMinimo) {
        this.ordineMinimo = ordineMinimo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTempoConsegna() {
        return tempoConsegna;
    }

    public void setTempoConsegna(String tempoConsegna) {
        this.tempoConsegna = tempoConsegna;
    }

}
