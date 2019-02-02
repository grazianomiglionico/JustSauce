package com.example.justsauce.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.justsauce.R;
import com.example.justsauce.ui.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email_editText, password_editText;
    Button login_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);
        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(this);
    }

    private void doLogin(){


        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        if(!Utils.verifyEmail(email)){
            email_editText.setError("Email errata");
        }else if(!Utils.verifyPassword(password)){
            password_editText.setError("Password errata");
        }else{
            Utils.showToast(this,"Accesso effettuato");         //login effettuato,
            Intent intent = new Intent(this,MainActivity.class);    //reindirizza all'activity dei locali
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v){

        if(v.getId()==R.id.login_button){
            doLogin();
        }
    }
}
