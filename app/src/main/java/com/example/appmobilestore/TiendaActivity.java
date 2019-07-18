package com.example.appmobilestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appmobilestore.Adaptors.ProductAdapter;
import com.example.appmobilestore.Items.ItemProduct;
import com.example.appmobilestore.Utilities.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TiendaActivity extends AppCompatActivity {
    RecyclerView recyclerProduct;
    Spinner spinnerCategoria;
    ArrayList<ItemProduct> listData,listSearchedData;
    String categoria;
    androidx.appcompat.widget.SearchView buscador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        loadComponents();
        getData();


    }



    private void loadComponents() {
        listData = new ArrayList<>();
        listSearchedData = new ArrayList<>();

        spinnerCategoria = findViewById( R.id.spinnerCategoria);
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoria = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buscador = findViewById( R.id.buscador);

        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscar(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    loadData(false);
                }
                return false;
            }
        });

        recyclerProduct = findViewById(R.id.recyclerProducts);

        recyclerProduct.setLayoutManager(
                new LinearLayoutManager(TiendaActivity.this,RecyclerView.VERTICAL,false));

    }

    private void buscar(String query) {
        listSearchedData.clear();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Data.URL_PRODUCT + "?descripcion="+query,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        ItemProduct item = new ItemProduct();
                        item.setDescripcion(obj.getString("descripcion"));
                        item.setId(obj.getString("_id"));

                        item.setStock(obj.getInt("stock"));
                        item.setPrecio(obj.getDouble("precio"));
                        item.setFoto(obj.getString("foto"));
                        listSearchedData.add(item);
                    }

                    loadData(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(TiendaActivity.this, errorResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(TiendaActivity.this, "Exception on Failure method", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Data.URL_PRODUCT,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject obj = array.getJSONObject(i);
                        ItemProduct item = new ItemProduct();
                        item.setDescripcion(obj.getString("descripcion"));
                        item.setId(obj.getString("_id"));

                        item.setStock(obj.getInt("stock"));
                        item.setPrecio(obj.getDouble("precio"));
                        item.setFoto(obj.getString("foto"));
                        listData.add(item);
                    }

                    loadData(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(TiendaActivity.this, errorResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(TiendaActivity.this, "Exception on Failure method", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }


    private void loadData(boolean b) {
        if (b){
            ProductAdapter adapter = new ProductAdapter(this, listSearchedData);
            recyclerProduct.setAdapter(adapter);
        }else  {
            ProductAdapter adapter = new ProductAdapter(this, listData);
            recyclerProduct.setAdapter(adapter);
        }


    }
}
