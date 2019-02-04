package com.example.justsauce.ui.datamodels;

public class Restaurant {

    private String nome;
    private String indirizzo;
    private double ordineMinimo;
    private String categoria;
    private String piattoClassico;
    private String tempoConsegna;
    private double valutazione;


    public Restaurant(){}

    public Restaurant(String nome,String indirizzo,double ordineMinimo,String categoria,String piattoClassico, String tempoConsegna, double valutazione){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.ordineMinimo = ordineMinimo;
        this.categoria = categoria;
        this.piattoClassico = piattoClassico;
        this.tempoConsegna = tempoConsegna;
        this.valutazione = valutazione;
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

    public String getPiattoClassico() {
        return piattoClassico;
    }

    public void setPiattoClassico(String piattoClassico) {
        this.piattoClassico = piattoClassico;
    }

    public String getTempoConsegna() {
        return tempoConsegna;
    }

    public void setTempoConsegna(String tempoConsegna) {
        this.tempoConsegna = tempoConsegna;
    }

    public double getValutazione() {
        return valutazione;
    }

    public void setValutazione(double valutazione) {
        this.valutazione = valutazione;
    }
}
