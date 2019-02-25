package com.example.justsauce.ui.typeconverter;

import android.arch.persistence.room.TypeConverter;

import com.example.justsauce.ui.datamodels.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class ProductTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Product> stringToList(String productsString) {
        if (productsString == null)
            return null;

        Type type = new TypeToken<List<Product>>(){}.getType();

        return gson.fromJson(productsString, type);
    }

    @TypeConverter
    public static String listToString(List<Product> productsList) {
        if (productsList == null)
            return null;

        Type type = new TypeToken<List<Product>>(){}.getType();

        return gson.toJson(productsList, type);
    }


}
