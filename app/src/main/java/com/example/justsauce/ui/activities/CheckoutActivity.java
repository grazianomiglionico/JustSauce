package com.example.justsauce.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justsauce.R;
import com.example.justsauce.ui.adapters.OrderAdapter;
import com.example.justsauce.ui.database.AppDatabase;
import com.example.justsauce.ui.datamodels.Order;
import com.example.justsauce.ui.datamodels.Product;
import com.example.justsauce.ui.datamodels.Restaurant;
import com.example.justsauce.ui.services.RestController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener, OrderAdapter.OnRemovedItemListener, Response.Listener<JSONObject>, Response.ErrorListener {

    //STATIC FINAL VARIABLE
    private static final String TAG = CheckoutActivity.class.getSimpleName();
    private TextView restaurantName_textView,restaurantAddress_textView,totalOrder_textView,restaurantOrderMinimum_textView;
    private Button pay_button;

    //
    private RecyclerView order_rv;
    private RecyclerView.LayoutManager layoutManager;
    private OrderAdapter adapter;

    //
    private Order order;
    private double total = 39;

    //
    private RestController restController;

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
        pay_button.setOnClickListener(this);

        order = getOrder(); //todo modifica
        bindData();

        layoutManager = new LinearLayoutManager(this);
        adapter = new OrderAdapter(this,order);
        adapter.setOnRemovedItemListener(this);

        order_rv.setLayoutManager(layoutManager);
        order_rv.setAdapter(adapter);
    }

    private void bindData(){
        restaurantName_textView.setText(order.getRestaurant().getNome());
        restaurantAddress_textView.setText(order.getRestaurant().getIndirizzo());
        totalOrder_textView.setText(String.valueOf(order.getTotal()));
        restaurantOrderMinimum_textView.setText(String.valueOf(order.getRestaurant().getOrdineMinimo()));
    }

    //TODO hardcoded
    private Order getOrder(){
        Order order = new Order();

        order.setRestaurant(getRestaurant());
        order.setProducts(getMenu());
        order.setTotal(total);

        return order;
    }

    //todo modifica
    private Restaurant getRestaurant() {
        Restaurant restaurant = new Restaurant("MC Donald's","Via Tiburtina 515",10,"Fast Food","15-20min");
        restaurant.setProducts(getMenu());

        return restaurant;
    }

    //todo modifica
    private ArrayList<Product> getMenu(){
        ArrayList<Product> menu = new ArrayList<>();

        menu.add(new Product("Big mac",5));
        menu.add(new Product("Mc menu",7));
        menu.add(new Product("Mc chicken",8));
        menu.add(new Product("Mc wrappen",9));
        menu.add(new Product("Mc sugo",10));

        return menu;
    }

    public void updateTotal(double prezzo){
        order.setTotal(order.getTotal() - prezzo);
        totalOrder_textView.setText(String.valueOf(order.getTotal()));
    }

    @Override
    public void onRemove(Product product) {
        order.getProducts().remove(product);
        updateTotal(product.getPrezzo());
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pay_button){
            restController = new RestController(this);
            try {
                restController.postRequestSendOrder(Order.ENDPOINT,buildJsonObjectToSend(),this,this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d(TAG,"Ordine inviato");
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d(TAG,error.networkResponse.data.toString());
    }

    private JSONObject buildJsonObjectToSend() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("restaurant","5c6570061a39104f0ba41134");    //id mc donald
        jsonObject.put("user","5c6bc82fceb476630f6cd9ba");          //id user simonemarino
        jsonObject.put("amount",50.00);

        List<Product> products = new ArrayList<Product>();
        products.add(new Product("1","Pizza",10,2));
        products.add(new Product("2","Birra",10,3));

        JSONArray jsonArrayProducts = new JSONArray();
        for(int i = 0; i<products.size(); i++) {
            JSONObject jsonObjectSingleProduct = new JSONObject();
            jsonObjectSingleProduct.put("id", products.get(i).getId());
            jsonObjectSingleProduct.put("quantity",products.get(i).getQuantita());

            jsonArrayProducts.put(i,jsonObjectSingleProduct);
        }
        jsonObject.put("products",jsonArrayProducts);

        return jsonObject;
    }


    private class getSavedTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO GET ORDER BY LOCAL DATABASE
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //TODO QUALCOSA
        }
    }
}
