package com.example.justsauce.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.justsauce.R;
import com.example.justsauce.ui.adapters.RestaurantAdapter;
import com.example.justsauce.ui.datamodels.Restaurant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView restaurantRV;
    RecyclerView.LayoutManager layoutManager;
    RestaurantAdapter adapter;
    ArrayList<Restaurant> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantRV = findViewById(R.id.places_rv);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RestaurantAdapter(this,getData());

        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);
    }

    private ArrayList<Restaurant> getData(){
        arrayList = new ArrayList();

        Restaurant mcDonald = new Restaurant("MC Donald's","Via Tiburtina",10,"Fast Food","Burgers","15-20 min",4.5);
        Restaurant burgerKing = new Restaurant("Burger King","Via Tiburtina",8,"Fast Food","Burgers","15.20 min",4.6);
        Restaurant rossoPomodoro = new Restaurant("Rosso Pomodoro","Via Sandro sandri 81",15,"Italiano","Pizza","20-30 min",4.8);

        arrayList.add(mcDonald);
        arrayList.add(burgerKing);
        arrayList.add(rossoPomodoro);

        return arrayList;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.login_menu){
            startActivity(new Intent(this,LoginActivity.class));
            return true;
        } else if(item.getItemId() == R.id.checkout_menu){
            startActivity(new Intent(this,CheckoutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
