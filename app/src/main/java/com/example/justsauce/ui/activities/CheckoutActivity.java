package com.example.justsauce.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.justsauce.R;
import com.example.justsauce.ui.adapters.OrderAdapter;
import com.example.justsauce.ui.datamodels.Order;
import com.example.justsauce.ui.datamodels.Product;
import com.example.justsauce.ui.datamodels.Restaurant;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, OrderAdapter.OnRemovedItemListener {

    private TextView restaurantName_textView,restaurantAddress_textView,totalOrder_textView,restaurantOrderMinimum_textView;
    private Button pay_button;

    private RecyclerView order_rv;
    private RecyclerView.LayoutManager layoutManager;
    private OrderAdapter adapter;
    private Order order;

    private double total = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        restaurantName_textView = findViewById(R.id.restaurantName_textView);
        restaurantAddress_textView = findViewById(R.id.restaurantAddress_textView);
        order_rv = findViewById(R.id.order_rv);
        totalOrder_textView= findViewById(R.id.totalOrder_textView);
        restaurantOrderMinimum_textView = findViewById(R.id.restaurantOrderMinimum_textView);
        pay_button = findViewById(R.id.pay_button);

        order = getOrder();
        bindData();

        layoutManager = new LinearLayoutManager(this);
        adapter = new OrderAdapter(this,order.getProducts());
        adapter.setOnRemovedItemListener(this);
        order_rv.setLayoutManager(layoutManager);
        order_rv.setAdapter(adapter);

    }

    private void bindData(){
        restaurantName_textView.setText(order.getRestaurant().getNome());
        restaurantAddress_textView.setText(order.getRestaurant().getIndirizzo());
        totalOrder_textView.setText(String.valueOf(order.getTotal()));
        restaurantOrderMinimum_textView.setText(String.valueOf(20.00));
    }

    //TODO hardcoded
    private Order getOrder(){
        Order order = new Order();

        order.setRestaurant(getRestaurant());
        order.setProducts(getMenu());
        order.setTotal(total);

        return order;
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pay_button){
            //TODO manage click
        }
    }

    public void updateTotal(){
        order.setTotal(order.getTotal() - 5);
        totalOrder_textView.setText(String.valueOf(order.getTotal()));
        /*
        order.setTotal(order.getTotal() - prezzo);
        totalOrder_textView.setText(String.valueOf(total));
        */
    }

    @Override
    public void onRemove(Product product) {
        order.getProducts().remove(product);
        updateTotal();
        /*
        order.getProducts().remove(product);
        updateTotal(product.getPrezzo());
        */
    }
}
