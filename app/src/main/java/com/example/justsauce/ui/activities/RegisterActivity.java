package com.example.justsauce.ui.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    //STATIC FINAL VARIABLE
    private static final String TAG = MainActivity.class.getSimpleName();


    //UI Components
    private EditText username_editText, email_editText, password_editText, passwordConfirm_editText, nTelefono_editText;
    private Button register_button;
    private TextView effettuaLogin_textView;

    //DATA MODEL Components
    private boolean isUsernameValid,isTelephoneValid,isEmailValid,isPasswordValid,isPasswordConfirmValid;

    //REST CONTROLLER Components
    private RestController restController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //
        username_editText = findViewById(R.id.username_editText);
        nTelefono_editText = findViewById(R.id.nTelefono_editText);
        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);
        passwordConfirm_editText = findViewById(R.id.passwordConfirm_editText);
        register_button = findViewById(R.id.register_button);
        effettuaLogin_textView = findViewById(R.id.effettuaLogin_textView);
        effettuaLogin_textView.setPaintFlags(effettuaLogin_textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        //click listener
        register_button.setOnClickListener(this);
        effettuaLogin_textView.setOnClickListener(this);

        //
        username_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isUsernameValid = Utils.checkUsername(s.toString().trim());
                if(!isUsernameValid) username_editText.setError("Il nicknama deve essere di almeno 5 caratteri");
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerButtonEnabled();
            }
        });
        nTelefono_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isTelephoneValid = Utils.checkNTelephone(s.toString().trim());
                if(!isTelephoneValid) nTelefono_editText.setError("Numero di telefono errato");
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerButtonEnabled();
            }
        });
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
                registerButtonEnabled();
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
                registerButtonEnabled();
            }
        });
        passwordConfirm_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = password_editText.getText().toString().trim();
                isPasswordConfirmValid = Utils.confirmPassword(password,s.toString().trim());
                if(!isPasswordConfirmValid) passwordConfirm_editText.setError("Le password non coincidono");
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerButtonEnabled();
            }
        });

        //
        restController = new RestController(this);
    }

    private void registerButtonEnabled(){
        if (isUsernameValid && isTelephoneValid && isEmailValid && isPasswordValid && isPasswordConfirmValid)
            register_button.setEnabled(true);
        else
            register_button.setEnabled(false);
    }

    private void doRegister(){
        Map<String, String> params = new HashMap<>();
        params.put("username",username_editText.getText().toString().trim());
        params.put("email",email_editText.getText().toString().trim());
        params.put("password",password_editText.getText().toString().trim());

        restController.postRequest(User.REGISTER_ENDPOINT,params,this,this);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.register_button){
            doRegister();
        }
        else if(v.getId() == R.id.effettuaLogin_textView){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e(TAG,error.getMessage());
        Utils.showToast(this,error.getMessage());
    }

    @Override
    public void onResponse(String response) {
        Log.i(TAG,"REGISTRAZIONE EFFETTUATA");
        Log.d(TAG,response);
        Utils.showToast(this,"REGISTRAZIONE EFFETTUATA");

        try {
            JSONObject responseJson = new JSONObject(response);

            String accessToken = responseJson.getString("jwt");
            SharedPreferencesManager.putValue(this,User.ACCESS_TOKEN_KEY,accessToken);

            User user = new User(responseJson.getJSONObject("user"),accessToken);

        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
        }
    }

}
