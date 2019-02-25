package com.example.justsauce.ui.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.justsauce.ui.DAO.OrderDao;
import com.example.justsauce.ui.datamodels.Order;

@Database(entities = {Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static final String NAME_DB = "local-database";

    public abstract OrderDao orderDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null)
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, NAME_DB)
                    .build();

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
