package com.example.justsauce.ui.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.justsauce.ui.datamodels.Order;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM 'Order' ")
    List<Order> getAll();

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);
}
