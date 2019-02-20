package com.example.justsauce.ui.activities;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.justsauce.R;
import com.example.justsauce.ui.SharedPreferencesManager;
import com.example.justsauce.ui.Utils;
import com.example.justsauce.ui.datamodels.User;
import com.example.justsauce.ui.services.RestController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    //STATIC FINAL VARIABLE
    private static final String TAG = MainActivity.class.getSimpleName();

    //UI Components
    private EditText email_editText, password_editText;
    private TextView creaAccount_textView;
    private Button login_button;

    //DATA MODEL Components
    private boolean isEmailValid,isPasswordValid;

    //REST CONTROLLER Components
    private RestController restController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //
        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);
        login_button = findViewById(R.id.login_button);
        creaAccount_textView = findViewById(R.id.creaAccount_textView);
        creaAccount_textView.setPaintFlags(creaAccount_textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        //click listener
        login_button.setOnClickListener(this);
        creaAccount_textView.setOnClickListener(this);

        //
        email_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isEmailValid = Utils.verifyEmail(s.toString().trim());
                if(!isEmailValid) email_editText.setError("Email errata");
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButtonEnabled();
            }
        });
        password_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isPasswordValid = Utils.checkPassword(s.toString().trim());
                if(!isPasswordValid) password_editText.setError("La password deve contenere almeno 6 caratteri");
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginButtonEnabled();
            }
        });

        //
        restController = new RestController(this);
    }

    private void loginButtonEnabled(){
        if(isEmailValid && isPasswordValid)
            login_button.setEnabled(true);
        else
            login_button.setEnabled(false);
    }

    private void doLogin(){

        Map<String,String> params = new HashMap<>();
        params.put("identifier",email_editText.getText().toString().trim());
        params.put("password",password_editText.getText().toString().trim());

        restController.postRequest(User.LOGIN_ENDPOINT,params,this,this);
    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.login_button){
            doLogin();
        }
        else if(v.getId() == R.id.creaAccount_textView){
            startActivity(new Intent(this,RegisterActivity.class));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG,error.getMessage());
        Utils.showToast(this,error.getMessage());
    }

    @Override
    public void onResponse(String response) {
        Log.i(TAG,"Accesso effettuato");
        Log.d(TAG,response);
        Utils.showToast(this,"Accesso effettuato");

        try {
            JSONObject responseJson = new JSONObject(response);

            String loginToken = responseJson.getString("jwt");
            SharedPreferencesManager.putValue(this,User.LOGIN_TOKEN_KEY,loginToken);

            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Utils.LOGIN_ACTION));
            finish();

        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
        }

        /* Activity for result */
        /*
        Intent i = new Intent();
        i.putExtra("response",response);
        setResult(Activity.RESULT_OK,i);
        finish();
        */

        //TODO hardcoded
    }
}
