package com.example.justsauce.ui.services;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.justsauce.ui.SharedPreferencesManager;
import com.example.justsauce.ui.activities.CheckoutActivity;
import com.example.justsauce.ui.datamodels.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RestController {

    private final static String BASE_URL = "http://138.68.86.70/";
    private final static String VERSION = "";
    private static final String TAG = RestController.class.getSimpleName();

    RequestQueue queue;

    public RestController (Context context){
        queue = Volley.newRequestQueue(context);
    }

    public void getRequest(String endpoint, Response.Listener<String> success, Response.ErrorListener error){

        final String url = BASE_URL.concat(VERSION).concat(endpoint);
        StringRequest request = new StringRequest(Request.Method.GET, url, success, error);

        queue.add(request);
    }

    public void postRequest(String endpoint, final Map<String,String> params, Response.Listener<String> success, Response.ErrorListener error){

        final String url = BASE_URL.concat(VERSION).concat(endpoint);
        StringRequest request = new StringRequest(Request.Method.POST, url, success, error){

            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        queue.add(request);
    }

    public void postRequestSendOrder(String endpoint, final JSONObject jsonObject, Response.Listener<JSONObject> success, Response.ErrorListener error){

        final String url = BASE_URL.concat(VERSION).concat(endpoint);

        Log.d(TAG,url);
        Log.d(TAG,jsonObject.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, success, error){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header = new HashMap<>();
                header.put("Content-Type:","application/json");
                header.put("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzZiYzgyZmNlYjQ3NjYzMGY2Y2Q5YmEiLCJpYXQiOjE1NTA3NTY1MTcsImV4cCI6MTU1MzM0ODUxN30.AZp9mT-8u77gI4pHdj1E_uNdmYGoRtK1JIybEzrY0Ko");

                return header;
            }
        };
        queue.add(request);
    }

}
