package com.example.appmobilestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appmobilestore.Utilities.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class EditProductActivity extends AppCompatActivity implements View.OnClickListener{
    EditText editDesc,editStock,editPrecio;
    ImageView imageFoto;
    Button btnActualizar,btnEliminar;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        loadComponents();
        Intent intentProduct = getIntent();
        if (intentProduct.getExtras() != null ){
            getProduct(intentProduct.getExtras().getString("id"));
            id = intentProduct.getExtras().getString("id");
        }else{
            Toast.makeText(this, "Error al recibir parametros", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void loadComponents() {
        //producto
        // editStock = findViewById(R.id
        // editStock);

        editDesc = findViewById(R.id.editDesc);
        editPrecio = findViewById(R.id.editPrecio);
        editStock = findViewById(R.id.editStock);
        imageFoto = findViewById(R.id.imageFoto);


        btnEliminar = findViewById(R.id.btnEliminar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar.setOnClickListener(this);
        btnActualizar.setOnClickListener(this);

    }

    private void getProduct(String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Data.URL_PRODUCT + id,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    if (response.getString("_id") != null){
                        setData(response);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(EditProductActivity.this, errorResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(EditProductActivity.this, "Exception on Failure method", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }

    private void setData(JSONObject obj) throws JSONException {
        editDesc.setText(obj.getString("descripcion"));
        editStock.setText(obj.getString("stock"));
        editPrecio.setText(obj.getString("precio"));

        Glide.with(this).load(Data.HOST + obj.getString("foto" )).into(imageFoto);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnActualizar){
            patchData();
        }else{
            deleteData();

        }

    }

    private void deleteData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.delete(Data.URL_PRODUCT+id,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    if (response.getString("message") != null){

                        Toast.makeText(EditProductActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditProductActivity.this,SellerHomeActivity.class);

                        startActivity(intent);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(EditProductActivity.this, errorResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(EditProductActivity.this, "Exception on Failure method", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

    }

    private void patchData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        ///
        if(editDesc.getText().toString().isEmpty() ){

        }
        params.put("descripcion",editDesc.getText().toString());
        params.put("stock",editStock.getText().toString());
        params.put("precio",editPrecio.getText().toString());
        client.patch(Data.URL_PRODUCT + id,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    if (response.getString("message") != null){

                        Toast.makeText(EditProductActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(EditProductActivity.this,SellerHomeActivity.class);

                        startActivity(intent);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(EditProductActivity.this, errorResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(EditProductActivity.this, "Exception on Failure method", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
}