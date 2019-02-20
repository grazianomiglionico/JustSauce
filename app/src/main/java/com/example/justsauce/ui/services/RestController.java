package com.example.justsauce.ui.services;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.justsauce.ui.datamodels.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RestController {

    private final static String BASE_URL = "http://138.68.86.70/";
    private final static String VERSION = "";

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

}
