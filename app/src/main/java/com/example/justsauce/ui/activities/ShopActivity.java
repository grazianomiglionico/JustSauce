package com.example.justsauce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.justsauce.R;
import com.example.justsauce.ui.adapters.ProductAdapter;
import com.example.justsauce.ui.datamodels.Product;
import com.example.justsauce.ui.datamodels.Restaurant;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements ProductAdapter.OnQuantityChangedListener, View.OnClickListener {

    private ImageView image;
    private TextView restaurantName_textView,restaurantAddress_textView,restaurantOrderMinimum_textView,totalOrder_textView;
    private Button checkout_button;
    private ProgressBar progressBar;

    private RecyclerView product_rv;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter adapter;

    private Restaurant restaurant;
    private double total;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        image = findViewById(R.id.image);
        restaurantName_textView = findViewById(R.id.restaurantName_textView);
        restaurantAddress_textView = findViewById(R.id.restaurantAddress_textView);
        restaurantOrderMinimum_textView = findViewById(R.id.restaurantOrderMinimum_textView);
        totalOrder_textView = findViewById(R.id.totalOrder_textView);

        product_rv = findViewById(R.id.product_rv);
        checkout_button = findViewById(R.id.checkout_button);
        progressBar = findViewById(R.id.progressBar);

        restaurant = getRestaurant();

        //img
        restaurant.setImage("https://chandigarhofficial.com/wp-content/uploads/2018/11/Mcdonals-Logo.png");
        Glide.with(this).load(restaurant.getImage()).into(image);

        restaurantName_textView.setText(restaurant.getNome());
        restaurantAddress_textView.setText(restaurant.getIndirizzo());
        restaurantOrderMinimum_textView.setText(restaurant.getOrdineMinimo() + "€");

        progressBar.setMax((int)(restaurant.getOrdineMinimo()) * 100);

        layoutManager = new LinearLayoutManager(this);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL);    //DIVIDER
        product_rv.addItemDecoration(decoration);

        adapter = new ProductAdapter(this,restaurant.getMenu());

        adapter.setOnQuantityChangedListener(this);

        product_rv.setLayoutManager(layoutManager);
        product_rv.setAdapter(adapter);

        checkout_button.setOnClickListener(this);

    }


    private Restaurant getRestaurant() {
        Restaurant restaurant = new Restaurant("MC Donald's","Via Tiburtina 515",10,"Fast Food","15-20min");
        restaurant.setMenu(getMenu());

        return restaurant;
    }

    private ArrayList<Product> getMenu(){
        ArrayList<Product> menu = new ArrayList<>();

        menu.add(new Product("Big mac",5));
        menu.add(new Product("Mc menu",7));
        menu.add(new Product("Mc chicken",8));
        menu.add(new Product("Mc wrappen",9));
        menu.add(new Product("Mc sugo",10));

        return menu;
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
}
