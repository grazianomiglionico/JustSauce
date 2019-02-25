package com.example.justsauce.ui.datamodels;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.example.justsauce.ui.typeconverter.ProductTypeConverter;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "Order")
public class Order {

    public static final String ENDPOINT = "orders/";


    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private Restaurant restaurant;

    @ColumnInfo(name = "products")
    @TypeConverters(ProductTypeConverter.class)
    private List<Product> products;

    @ColumnInfo(name = "total")
    private double total;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
