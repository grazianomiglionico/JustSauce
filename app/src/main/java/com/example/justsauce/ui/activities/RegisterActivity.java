package com.example.justsauce.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.justsauce.R;
import com.example.justsauce.ui.Utils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nickname_editText, email_editText, password_editText, passwordConfirm_editText, nTelefono_editText;
    Button register_button;
    TextView registerLogin_textView;

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
        registerLogin_textView = findViewById(R.id.registerLogin_textView);

        register_button.setOnClickListener(this);
    }

    public void doRegister(){

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
            email_editText.setError("Il nicknama deve essre di almeno 5 caratteri");
        }

    }

    @Override
    public void onClick(View v){

        if(v.getId()==R.id.register_button){
            doRegister();
            Utils.showToast(this,"Registrazione effettuata");
        }
    }
}
