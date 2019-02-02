package com.example.justsauce.ui.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.justsauce.R;
import com.example.justsauce.ui.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email_editText, password_editText;
    TextView creaAccount_textView;
    Button login_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);

        login_button = findViewById(R.id.login_button);
        creaAccount_textView = findViewById(R.id.creaAccount_textView);
        creaAccount_textView.setPaintFlags(creaAccount_textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        login_button.setOnClickListener(this);
        creaAccount_textView.setOnClickListener(this);



        email_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String email = email_editText.getText().toString();
                if(!Utils.verifyEmail(email))
                    email_editText.setError("Email errata");
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginEnabled();
            }
        });
        password_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = password_editText.getText().toString();
                if(!Utils.checkPassword(password))
                    password_editText.setError("Password errata");
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkLoginEnabled();
            }
        });
    }

    private void checkLoginEnabled(){

        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        if(!Utils.verifyEmail(email))
            login_button.setEnabled(false);
        else if(!Utils.checkPassword(password))
            login_button.setEnabled(false);
        else
            login_button.setEnabled(true);

    }

    private void doLogin(){

        Utils.showToast(this,"Accesso effettuato");         //login effettuato,
        Intent intent = new Intent(this,MainActivity.class);    //reindirizza all'activity dei locali
        startActivity(intent);


        /*
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
        }*/
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.login_button:
                doLogin();
                break;
            case R.id.creaAccount_textView:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
        }
    }
}
