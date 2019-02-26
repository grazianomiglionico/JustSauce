package com.graziano.justsauce.ui.datamodels;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    //
    public static final String REGISTER_ENDPOINT = "auth/local/register";
    public static final String LOGIN_ENDPOINT = "auth/local";
    public static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN_KEY";
    public static final String LOGIN_TOKEN_KEY = "LOGIN_TOKEN_KEY";
    public static final String ID_USER_KEY = "ID_USER_KEY";

    //
    private String id, username, email, nTelefono, accessToken;

    //Constructor
    public User(JSONObject jsonUser, String accessToken) throws JSONException {
        id = jsonUser.getString("id");
        username = jsonUser.getString("username");
        email = jsonUser.getString("email");
        this.accessToken = accessToken;
    }

    //GETTER and SETTER
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getnTelefono() {
        return nTelefono;
    }

    public void setnTelefono(String nTelefono) {
        this.nTelefono = nTelefono;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
