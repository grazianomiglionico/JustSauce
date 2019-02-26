package com.graziano.justsauce.ui.activities;

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
import com.graziano.justsauce.R;
import com.graziano.justsauce.ui.SharedPreferencesManager;
import com.graziano.justsauce.ui.adapters.OrderAdapter;
import com.graziano.justsauce.ui.database.AppDatabase;
import com.graziano.justsauce.ui.datamodels.Order;
import com.graziano.justsauce.ui.datamodels.Product;
import com.graziano.justsauce.ui.datamodels.User;
import com.graziano.justsauce.ui.services.RestController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        new LoadTask().execute();
    }

    private void bindData(){
        restaurantName_textView.setText(order.getRestaurant().getNome());
        restaurantAddress_textView.setText(order.getRestaurant().getIndirizzo());
        totalOrder_textView.setText(String.valueOf(order.getTotal()));
        restaurantOrderMinimum_textView.setText(String.valueOf(order.getRestaurant().getOrdineMinimo()));

        layoutManager = new LinearLayoutManager(this);
        adapter = new OrderAdapter(CheckoutActivity.this,order);
        adapter.setOnRemovedItemListener(CheckoutActivity.this);

        order_rv.setLayoutManager(layoutManager);
        order_rv.setAdapter(adapter);
    }

    public void updateTotal(double prezzo){
        order.setTotal(order.getTotal() - prezzo);
        totalOrder_textView.setText(String.valueOf(order.getTotal()));
    }

    @Override
    public void onRemove(Product product) {
        order.getProducts().remove(product);
        updateTotal(product.getPrezzo() * product.getQuantita());
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

        jsonObject.put("restaurant",order.getRestaurant().getId());
        jsonObject.put("user", SharedPreferencesManager.getStringValue(this, User.ID_USER_KEY));
        jsonObject.put("amount",order.getTotal());

        List<Product> products = order.getProducts();

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


    private class LoadTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO GET ORDER BY LOCAL DATABASE
            AppDatabase dbInstance = AppDatabase.getInstance(CheckoutActivity.this);
            List<Order> dbOrders = dbInstance.orderDao().getAll();
            order = dbOrders.get(0);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //TODO bindData
            bindData();
        }
    }
}
