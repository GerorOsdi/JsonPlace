package com.app.jsonplace.Interface;

import com.app.jsonplace.models.usuarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface interfaces {

    String Ruta0 = "/posts";
    String Ruta1 = "/posts/{valor}";


    @GET(Ruta0)
    Call<List<usuarios>> getUsuarios();

    @GET(Ruta1)
    Call<usuarios> getUsuario (@Path("valor") String id);
}
