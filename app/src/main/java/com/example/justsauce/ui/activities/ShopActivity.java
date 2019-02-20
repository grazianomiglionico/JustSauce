package com.example.justsauce.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.justsauce.R;
import com.example.justsauce.ui.SharedPreferencesManager;
import com.example.justsauce.ui.Utils;
import com.example.justsauce.ui.adapters.ProductAdapter;
import com.example.justsauce.ui.datamodels.Product;
import com.example.justsauce.ui.datamodels.Restaurant;
import com.example.justsauce.ui.datamodels.User;
import com.example.justsauce.ui.services.RestController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.OnQuantityChangedListener, View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    //STATIC FINAL VARIABLE
    private static final String TAG = ShopActivity.class.getSimpleName();


    //UI Components
    private ImageView image;
    private TextView restaurantName_textView,restaurantAddress_textView,restaurantOrderMinimum_textView,totalOrder_textView;
    private Button checkout_button;
    private ProgressBar progressBar, progressBarCyclic;

    //RECYCLER VIEW Components
    private RecyclerView product_rv;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter adapter;

    //DATA MODELS Components
    private Restaurant restaurant;
    private double total;

    //REST CONTROLLER Components
    private String id;
    private RestController restController;

    //
    private Menu menu;

    //
    private LoginReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        //Initialize UI controllers
        progressBarCyclic = findViewById(R.id.progressBarCyclic);
        image = findViewById(R.id.image);
        restaurantName_textView = findViewById(R.id.restaurantName_textView);
        restaurantAddress_textView = findViewById(R.id.restaurantAddress_textView);
        restaurantOrderMinimum_textView = findViewById(R.id.restaurantOrderMinimum_textView);
        totalOrder_textView = findViewById(R.id.totalOrder_textView);

        product_rv = findViewById(R.id.product_rv);
        checkout_button = findViewById(R.id.checkout_button);
        progressBar = findViewById(R.id.progressBar);
        checkout_button.setOnClickListener(this);


        //Create layout manager, adapter and set recycler view
        layoutManager = new LinearLayoutManager(this);
        product_rv.setLayoutManager(layoutManager);

        adapter = new ProductAdapter(this);
        adapter.setOnQuantityChangedListener(this);
        product_rv.setAdapter(adapter);

        //Remove animator
        ((SimpleItemAnimator) product_rv.getItemAnimator()).setSupportsChangeAnimations(false);

        //Add divider
        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);    //DIVIDER
        product_rv.addItemDecoration(decoration);


        //Manage REST CONTROLLER
        id = getIntent().getStringExtra(Restaurant.RESTAURANT_ID);
        restController = new RestController(this);
        restController.getRequest(Restaurant.ENDPOINT.concat(id),this,this);

        //Set Broadcast listener by action
        receiver = new LoginReceiver();
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

        menu.findItem(R.id.changeLayout).setVisible(false);
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
        } else if(item.getItemId() == R.id.profile_menu){
            startActivity(new Intent(this,ProfileActivity.class));
            return true;
        } else if(item.getItemId() == R.id.logout_menu){
            SharedPreferencesManager.putValue(this,User.LOGIN_TOKEN_KEY,null);
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Utils.LOGOUT_ACTION));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG,"requestCode " + requestCode);
        Log.d(TAG,"resultCode " + resultCode);


        if(requestCode == Utils.LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            menu.findItem(R.id.login_menu).setTitle("Profile").setIcon(R.drawable.baseline_person_outline_black_24)
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    startActivity(new Intent(ShopActivity.this,ProfileActivity.class));
                    return true;
                }
            });
        }
    }*/

    private void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }

    private void bindData(){
        Glide.with(this).load(restaurant.getImage()).into(image);
        restaurantName_textView.setText(restaurant.getNome());
        restaurantAddress_textView.setText(restaurant.getIndirizzo());
        restaurantOrderMinimum_textView.setText(String.valueOf(restaurant.getOrdineMinimo()) + "€");
        progressBar.setMax((int)(restaurant.getOrdineMinimo()) * 100);

        adapter.setData(restaurant.getProducts());
    }

    private void checkCheckoutEnabled(){
        if(total>=restaurant.getOrdineMinimo())
            checkout_button.setEnabled(true);
        else
            checkout_button.setEnabled(false);
    }

    private void updateTotal(double itemPrezzo){
        total+=itemPrezzo;
        totalOrder_textView.setText(String.valueOf(total));
    }

    private void updateProgress(int progress){
        progressBar.setProgress(progress);
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
    public void onChange(double prezzo) {
        updateTotal(prezzo);
        updateProgress((int)total * 100);
        checkCheckoutEnabled();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.checkout_button){
            startActivity(new Intent(this,CheckoutActivity.class));
        }
    }

    @Override
    public void onResponse(String response) {
        Log.d(TAG,response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            setRestaurant(new Restaurant(jsonObject));

            JSONArray jsonArray = jsonObject.getJSONArray("products");
            for (int i = 0;i<jsonArray.length(); i++){
                Product product = new Product(jsonArray.getJSONObject(i));
                restaurant.getProducts().add(product);
            }

            bindData();
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

    public class LoginReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"onReceive ShopActivity");
            manageMenuByLogin(intent.getAction().equals(Utils.LOGIN_ACTION));
        }
    }

}
