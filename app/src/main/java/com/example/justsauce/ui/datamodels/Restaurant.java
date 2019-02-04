package com.example.justsauce.ui.datamodels;

public class Restaurant {

    private String nome;
    private String indirizzo;
    private double ordineMinimo;

    public Restaurant(){}

    public Restaurant(String nome,String indirizzo,double ordineMinimo){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.ordineMinimo = ordineMinimo;
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
}
