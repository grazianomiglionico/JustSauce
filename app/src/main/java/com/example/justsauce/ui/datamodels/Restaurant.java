package com.example.justsauce.ui.datamodels;

import android.print.PrinterId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Restaurant {

    public static final String RESTAURANT_ID = "ID";
    private String id;
    private String nome;
    private String indirizzo;
    private double ordineMinimo;
    private String categoria;
    private String tempoConsegna;
    private String image;
    private ArrayList<Product> products;

    public static final String ENDPOINT = "restaurants/";


    public Restaurant(){}
    public Restaurant(String nome,String indirizzo,double ordineMinimo,String categoria, String tempoConsegna){
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.ordineMinimo = ordineMinimo;
        this.categoria = categoria;
        this.tempoConsegna = tempoConsegna;
        products = new ArrayList<>();
    }

    public Restaurant(JSONObject jsonRestaurant) throws JSONException {
        id = jsonRestaurant.getString("id");
        image = jsonRestaurant.getString("image_url");
        nome = jsonRestaurant.getString("name");
        indirizzo = jsonRestaurant.getString("address");
        ordineMinimo = jsonRestaurant.getDouble("min_order");
        products = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
