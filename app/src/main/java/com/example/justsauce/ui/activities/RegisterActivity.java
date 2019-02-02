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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nickname_editText, email_editText, password_editText, passwordConfirm_editText, nTelefono_editText;
    Button register_button;
    TextView effettuaLogin_textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nickname_editText = findViewById(R.id.nickname_editText);
        nTelefono_editText = findViewById(R.id.nTelefono_editText);
        email_editText = findViewById(R.id.email_editText);
        password_editText = findViewById(R.id.password_editText);
        passwordConfirm_editText = findViewById(R.id.passwordConfirm_editText);

        register_button = findViewById(R.id.register_button);
        effettuaLogin_textView = findViewById(R.id.effettuaLogin_textView);
        effettuaLogin_textView.setPaintFlags(effettuaLogin_textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        register_button.setOnClickListener(this);
        effettuaLogin_textView.setOnClickListener(this);


        nickname_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nickname = nickname_editText.getText().toString();
                if(!Utils.checkNickname(nickname))
                    nickname_editText.setError("Il nicknama deve essre di almeno 5 caratteri");
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterButtonEnabled();
            }
        });
        nTelefono_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nTelefono = nTelefono_editText.getText().toString();
                if(!Utils.checkNTelephone(nTelefono))
                    nTelefono_editText.setError("Numero di telefono errato");
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterButtonEnabled();
            }
        });
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
                checkRegisterButtonEnabled();
            }
        });
        password_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = password_editText.getText().toString();
                if(!Utils.checkPassword(password))
                    password_editText.setError("La password deve contenere almeno 6 caratteri");
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterButtonEnabled();
            }
        });
        passwordConfirm_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = password_editText.getText().toString();
                String passwordConfirm = passwordConfirm_editText.getText().toString();
                if(!Utils.confirmPassword(password,passwordConfirm))
                    passwordConfirm_editText.setError("Le password non coincidono");
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkRegisterButtonEnabled();
            }
        });
    }

    private void checkRegisterButtonEnabled(){

        String nickname = nickname_editText.getText().toString();
        String nTelefono = nTelefono_editText.getText().toString();
        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();
        String passwordConfirm = passwordConfirm_editText.getText().toString();

        if(Utils.checkNickname(nickname))
            if(Utils.verifyEmail(email))
                if(Utils.checkPassword(password))
                    if(Utils.confirmPassword(password,passwordConfirm))
                        if(Utils.checkNTelephone(nTelefono))
                            register_button.setEnabled(true);
                        else
                            register_button.setEnabled(false);
                    else
                        register_button.setEnabled(false);
                else
                    register_button.setEnabled(false);
            else
                register_button.setEnabled(false);
        else
            register_button.setEnabled(false);

    }

    private void doRegister(){

        Utils.showToast(this,"Registrazione effettuata, effettua il login");   //registrazione effettuata
        Intent intent = new Intent(this,LoginActivity.class);    //reindirizza all'activity del login
        startActivity(intent);


        /*
        String nickname = nickname_editText.getText().toString();
        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();
        String passwordConfirm = passwordConfirm_editText.getText().toString();
        String nTelefono = nTelefono_editText.getText().toString();

        if(Utils.verifyNickname(nickname)){
            if(Utils.verifyEmail(email)){
                if(Utils.verifyPassword(password)){
                    if(Utils.confirmPassword(password,passwordConfirm)){
                        if(Utils.verifyNTelephone(nTelefono)){
                            register_button.setEnabled(true);
                        }
                        else{
                            register_button.setEnabled(false);
                            nTelefono_editText.setError("Numero di telefono errato");
                        }
                    }
                    else {
                        register_button.setEnabled(false);
                        passwordConfirm_editText.setError("Le password non coincidono");
                    }
                }
                else {
                    register_button.setEnabled(false);
                    password_editText.setError("La password deve contenere almeno 6 caratteri");
                }
            }
            else {
                register_button.setEnabled(false);
                email_editText.setError("Email errata");
            }
        }
        else {
            register_button.setEnabled(false);
            nickname_editText.setError("Il nicknama deve essre di almeno 5 caratteri");
        }*/

    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.register_button:
                doRegister();
                break;
            case R.id.effettuaLogin_textView:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}
