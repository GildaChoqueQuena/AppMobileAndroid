package com.example.appmobilestore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmobilestore.Utilities.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class PerfilActivity extends AppCompatActivity {
    EditText nombreEdit;
    TextView correoText, tipoText;
    ImageView avatarImage;
    Button btnGuardar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        loadComponents();
        getData();
    }

    private void getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Data.URL_USERS + Data.ID_USER,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("_id") != null){
                        Toast.makeText(PerfilActivity.this,"ok",Toast.LENGTH_LONG ).show();
                        setData(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(PerfilActivity.this, errorResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setData(JSONObject obj) {
        try {
            nombreEdit.setText(obj.getString("name"));
            correoText.setText(obj.getString("email"));
            tipoText.setText(obj.getString("tipo").toUpperCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void loadComponents() {
        nombreEdit = findViewById(R.id.nombreEdit);
        correoText = findViewById(R.id.correoText);
        tipoText = findViewById(R.id.tipoText);
        avatarImage = findViewById(R.id.avatarImage);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

    }

    private void updateData() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        String nombre = nombreEdit.getText().toString();
        if (nombreEdit.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo no puede estar vacio", Toast.LENGTH_SHORT).show();
        }
        params.put("name",nombre);

        client.patch(Data.URL_USERS + Data.ID_USER,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("message") != null){
                        Toast.makeText(PerfilActivity.this,response.getString("message"),Toast.LENGTH_LONG ).show();
                        PerfilActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Toast.makeText(PerfilActivity.this, errorResponse.getString("error"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
