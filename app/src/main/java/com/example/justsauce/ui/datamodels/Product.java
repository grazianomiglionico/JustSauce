package com.example.justsauce.ui.datamodels;

import java.util.ArrayList;

public class Product {

    private String nome;
    private double prezzo;
    private int quantita;


    public Product(){}
    public Product(String nome, double prezzo){
        this.nome = nome;
        this.prezzo = prezzo;
    }

    public Product(String nome, double prezzo, int quantita) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }



    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void increaseQuantity(){
        this.quantita++;
    }

    public void decreaseQuantity(){
        if(quantita == 0) return;
        this.quantita--;
    }
}
