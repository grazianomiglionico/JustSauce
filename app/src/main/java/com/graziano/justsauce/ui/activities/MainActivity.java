package com.graziano.justsauce.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.graziano.justsauce.R;
import com.graziano.justsauce.ui.SharedPreferencesManager;
import com.graziano.justsauce.ui.Utils;
import com.graziano.justsauce.ui.adapters.RestaurantAdapter;
import com.graziano.justsauce.ui.datamodels.Restaurant;
import com.graziano.justsauce.ui.datamodels.User;
import com.graziano.justsauce.ui.services.RestController;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    //
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LAYOUT_MODE_KEY = "LAYOUT_MODE";                        //chiave di isGridLayout
    /*SharedPreferences sharedPreferences;
    private static final String SharedPrefs = "com.example.justsauce.general_prefs";*/

    //
    private RecyclerView restaurantRV;
    private RecyclerView.LayoutManager layoutManager;
    private RestaurantAdapter adapter;
    private ProgressBar progressBarCyclic;

    private ArrayList<Restaurant> arrayList = new ArrayList<>();

    private RestController restController;

    private Menu menu;

    private LoginLogoutReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sharedPreferences = getSharedPreferences(SharedPrefs,MODE_PRIVATE);

        restaurantRV = findViewById(R.id.places_rv);
        progressBarCyclic = findViewById(R.id.progressBarCyclic);

        adapter = new RestaurantAdapter(this);
        adapter.setIsGridLayout(getSavedLayoutManager());

        layoutManager = getLayoutManager(getSavedLayoutManager());


        restaurantRV.setLayoutManager(layoutManager);
        restaurantRV.setAdapter(adapter);

        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT,this,this);

        //set Broadcast listener by action
        receiver = new LoginLogoutReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,new IntentFilter(Utils.LOGIN_ACTION));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,new IntentFilter(Utils.LOGOUT_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        boolean isLoggedSharedPrefs = SharedPreferencesManager.getIsLoggedSharedPrefs(this);

        getMenuInflater().inflate(R.menu.menu_main,menu);

        menu.findItem(R.id.changeLayout).setIcon(adapter.getIsGridLayout() ? R.drawable.baseline_view_module_black_24 : R.drawable.baseline_view_list_black_24);
        menu.findItem(R.id.checkout_menu).setVisible(false);
        manageMenuByLogin(isLoggedSharedPrefs);

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
        } else if(item.getItemId() == R.id.profile_menu){
            startActivity(new Intent(this,ProfileActivity.class));
            return true;
        } else if(item.getItemId() == R.id.logout_menu){
            SharedPreferencesManager.putValue(this,User.LOGIN_TOKEN_KEY,null);
            SharedPreferencesManager.putValue(this,User.ID_USER_KEY,null);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Utils.LOGOUT_ACTION));
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        /*
        sharedPreferences = getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(LAYOUT_MODE_KEY,isGridLayout);
        editor.apply();*/
        SharedPreferencesManager.putValue(this,LAYOUT_MODE_KEY,isGridLayout);
    }

    private boolean getSavedLayoutManager(){
        /*
        sharedPreferences = getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        return sharedPreferences.getBoolean(LAYOUT_MODE_KEY,false);*/
        return SharedPreferencesManager.getBooleanValue(this,LAYOUT_MODE_KEY);
    }

    private void manageMenuByLogin(boolean isLogged){
        if(isLogged) {
            menu.findItem(R.id.login_menu).setVisible(false);
            menu.findItem(R.id.profile_actionSettings).setVisible(true);
        }
        else {
            menu.findItem(R.id.profile_actionSettings).setVisible(false);
            menu.findItem(R.id.login_menu).setVisible(true);
        }
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG,response);

        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0;i<jsonArray.length(); i++){
                Restaurant restaurant = new Restaurant(jsonArray.getJSONObject(i));
                arrayList.add(restaurant);
            }
            adapter.setData(arrayList);
            progressBarCyclic.setVisibility(View.GONE);
        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG,error.getMessage());
        Utils.showToast(this,error.getMessage());
    }


    public class LoginLogoutReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"onReceive MainActivity");
            manageMenuByLogin(intent.getAction().equals(Utils.LOGIN_ACTION));
        }
    }

}
