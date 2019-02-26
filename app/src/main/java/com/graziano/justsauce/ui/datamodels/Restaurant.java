package com.graziano.justsauce.ui.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Entity(tableName = "Restaurant")
public class Restaurant {

    public static final String RESTAURANT_ID = "ID";
    public static final String ENDPOINT = "restaurants/";


    @ColumnInfo(name = "id_restaurant")
    private String id;
    @ColumnInfo(name = "nome")
    private String nome;
    @ColumnInfo(name = "indirizzo")
    private String indirizzo;
    @ColumnInfo(name = "ordine_minimo")
    private double ordineMinimo;
    @ColumnInfo(name = "categoria")
    private String categoria;
    @ColumnInfo(name = "tempo_consegna")
    private String tempoConsegna;
    @ColumnInfo(name = "image")
    private String image;
    @Ignore
    private ArrayList<Product> products;




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
