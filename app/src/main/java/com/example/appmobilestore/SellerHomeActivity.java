package com.example.appmobilestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellerHomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnPublicar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        loadComponents();

    }

    private void loadComponents() {
        btnPublicar = findViewById(R.id.btnPublicar);
        //los otros botones

        btnPublicar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnPublicar: intent = new Intent(this,PostProduct.class);break;
            default: intent = new Intent(this,SellerHomeActivity.class);break;

        }

        startActivity(intent);
    }
}
