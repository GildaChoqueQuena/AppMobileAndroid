package com.example.appmobilestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.appmobilestore.Utilities.Data;

public class BuyerHomeActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnPerfil, btnCitas, btnTienda, btnCompras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadComponents();
    }

    private void loadComponents() {
        btnCitas = findViewById(R.id.btnCitas);
        btnPerfil = findViewById(R.id.btnPerfil);
        btnTienda = findViewById(R.id.btnTienda);
        btnCompras = findViewById(R.id.btnCompras);
        btnCitas.setOnClickListener(this);
        btnPerfil.setOnClickListener(this);
        btnTienda.setOnClickListener(this);
        btnCompras.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.example, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            logout();


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Data.ID_USER = "";
        Data.TOKEN = "";
        finish();

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btnPerfil: intent = new Intent(this, PerfilActivity.class);break ;
            case R.id.btnCitas: intent = new Intent(this, CitasActivity.class);break ;
            case R.id.btnTienda: intent = new Intent(this, TiendaActivity.class);break ;
            case R.id.btnCompras: intent = new Intent(this, ComprasActivity.class);break ;
            default: intent = new Intent(this, BuyerHomeActivity.class);break ;
        }

        startActivity(intent);
    }
}
