package com.example.justsauce.ui;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static final int LOGIN_REQUEST_CODE = 1001;
    public static final String LOGIN_ACTION = "LOGIN_ACTION";
    public static final String LOGOUT_ACTION = "LOGOUT_ACTION";

    public static boolean verifyEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean checkPassword(String password){
        if(password.length()>=6) return true; else return false;
    }

    public static boolean confirmPassword(String password,String password1){
        if(password.equals(password1)) return true; else return false;
    }

    public static boolean checkNTelephone(String n_telefono){
        if(n_telefono.length()==10) return true; else return false;
    }

    public static boolean checkUsername(String nickname){
        if(nickname.length()>=5) return true; else return false;
    }

    public static void showToast(Context contesto, String contenuto){
        Toast.makeText(contesto, contenuto,Toast.LENGTH_LONG).show();
    }

}