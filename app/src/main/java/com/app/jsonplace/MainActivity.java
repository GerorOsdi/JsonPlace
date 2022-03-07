package com.app.jsonplace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.app.jsonplace.Interface.interfaces;
import com.app.jsonplace.models.usuarios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView lstUsuarios;
    EditText txtDato;
    Button btnbuscar;
    ArrayList<String> lstData = new ArrayList<>();
    ArrayAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstUsuarios = (ListView) findViewById(R.id.lstLista);
        txtDato = (EditText) findViewById(R.id.txtDat);
        btnbuscar = (Button) findViewById(R.id.btnBuscar);

        obtenerData();

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetUsuario(txtDato.getText().toString());
            }
        });

    }

    public void obtenerData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        interfaces interFaceUser = retrofit.create(interfaces.class);

        Call<List<usuarios>> call = interFaceUser.getUsuarios();

        call.enqueue(new Callback<List<usuarios>>() {
            @Override
            public void onResponse(Call<List<usuarios>> call, Response<List<usuarios>> response) {
                for(usuarios user : response.body()){
                    Log.i("On Response", user.getTitle());
                    lstData.add(user.getId()+" - " + user.getTitle());
                }
                adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,lstData);
                lstUsuarios.setAdapter(adp);
                adp.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<usuarios>> call, Throwable t) {

            }
        });
    }

    public void GetUsuario(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        interfaces interFaceUser = retrofit.create(interfaces.class);

        Call<usuarios> call = interFaceUser.getUsuario(id);

        call.enqueue(new Callback<usuarios>() {
            @Override
            public void onResponse(Call<usuarios> call, Response<usuarios> response) {
                if(response.isSuccessful()){
                usuarios user = response.body();

                lstData = new ArrayList<>();
                lstData.add(user.getId()+" - " + user.getTitle());


                adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,lstData);
                lstUsuarios.setAdapter(adp);
                //adp.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<usuarios> call, Throwable t) {

            }
        });
    }
}