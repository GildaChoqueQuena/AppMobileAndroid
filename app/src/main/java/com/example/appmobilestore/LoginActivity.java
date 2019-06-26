package com.example.appmobilestore;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmobilestore.Utilities.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText emailEdit, passwordEdit;
    Button loginButton, registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadComponents();

    }


    private void loadComponents() {
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);

        loginButton = findViewById(R.id.login);
        registerButton = findViewById(R.id.register);

        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);


    }

    private void login() {

        //controlar no vacios
        String email = emailEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("email", email);
        params.put("password", password);

        client.post(Data.URL_USERS_LOGIN, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    if (response.getString("message") != null){
                        Toast.makeText(LoginActivity.this, "Acceso Correcto", Toast.LENGTH_SHORT).show();
                        Data.TOKEN = response.getString("token");
                        if (response.getString("tipo") == "comprador"){
                            Intent intent = new Intent(LoginActivity.this, BuyerHomeActivity.class);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(LoginActivity.this, SellerHomeActivity.class);
                            startActivity(intent);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }
    private void register() {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login){
            login();
        }else{
            register();
        }

    }


}
