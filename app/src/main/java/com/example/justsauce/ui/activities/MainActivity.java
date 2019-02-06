package com.example.justsauce.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

    SharedPreferences sharedPreferences;
    private static final String SharedPrefs = "com.example.justsauce.general_prefs";
    private static final String LAYOUT_MODE = "LAYOUT_MODE";                                //chiave di isGridLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restaurantRV = findViewById(R.id.places_rv);

        adapter = new RestaurantAdapter(this,getData());
        layoutManager = getLayoutManager(getSavedLayoutManager());


        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        menu.findItem(R.id.changeLayout).setIcon(adapter.getIsGridLayout() ? R.drawable.baseline_view_module_black_24 : R.drawable.baseline_view_list_black_24);

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
        } else if(item.getItemId() == R.id.changeLayout){
            setLayoutManager();
            item.setIcon(adapter.getIsGridLayout() ? R.drawable.baseline_view_module_black_24 : R.drawable.baseline_view_list_black_24);
            saveLayoutManager(adapter.getIsGridLayout());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private ArrayList<Restaurant> getData(){
        arrayList = new ArrayList();

        Restaurant mcDonald = new Restaurant("MC Donald's","Via Tiburtina 515",10,"Fast Food","15-20min");
        Restaurant burgerKing = new Restaurant("Burger King","Via Tiburtina 474",8,"Fast Food","15-20min");
        Restaurant kfc = new Restaurant("KFC","Via Sandro sandri 79",15,"Fast Food","10-15min");
        Restaurant rossoPomodoro = new Restaurant("Rosso Pomodoro","Via Sandro sandri 81",15,"Italiano","20-30min");


        mcDonald.setImage("https://chandigarhofficial.com/wp-content/uploads/2018/11/Mcdonals-Logo.png");
        burgerKing.setImage("https://c8.alamy.com/compit/p2cgt2/burger-king-logo-p2cgt2.jpg");
        kfc.setImage("https://pbs.twimg.com/profile_images/907647041207099392/vr20oP_Q_400x400.jpg");
        rossoPomodoro.setImage("https://i.vimeocdn.com/video/472128650_1280x720.jpg");


        arrayList.add(mcDonald);
        arrayList.add(burgerKing);
        arrayList.add(kfc);
        arrayList.add(rossoPomodoro);

        return arrayList;
    }

    private void setLayoutManager(){
        adapter.setIsGridLayout(!adapter.getIsGridLayout());
        layoutManager = getLayoutManager(adapter.getIsGridLayout());

        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    private RecyclerView.LayoutManager getLayoutManager(boolean isGridLayout){
        return isGridLayout ? new GridLayoutManager(this,2) : new LinearLayoutManager(this);
    }

    private void saveLayoutManager(boolean isGridLayout){
        sharedPreferences = getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LAYOUT_MODE,isGridLayout);
        editor.apply();
    }

    private boolean getSavedLayoutManager(){
        sharedPreferences = getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        adapter.setIsGridLayout(sharedPreferences.getBoolean(LAYOUT_MODE,false));
        return sharedPreferences.getBoolean(LAYOUT_MODE,false);
    }

}
