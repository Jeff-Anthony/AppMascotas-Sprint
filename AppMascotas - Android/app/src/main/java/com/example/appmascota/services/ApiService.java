package com.example.appmascota.services;


import com.example.appmascota.models.Mascotas;
import com.example.appmascota.models.Usuarios;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "http://192.168.1.16:8090";

    @GET("/mascotas")  // http://10.0.2.2:8090/mascotas
    Call<List<Mascotas>> findAll();

    @FormUrlEncoded
    @POST("/mascotas")
    Call<Mascotas> createMascotas(@Field("nombre") String nombre,
                                  @Field("raza") String raza,
                                  @Field("edad") String edad);
    @Multipart
    @POST("/mascotas")
    Call<Mascotas> createMascotas(@Part("nombre") RequestBody nombre,
                                  @Part("raza") RequestBody raza,
                                  @Part("edad") RequestBody edad,
                                  @Part MultipartBody.Part foto
    );

    @Multipart
    @POST("/auth/usuarios")
    Call<Usuarios> createUsuario(@Part("nombre_usu") RequestBody nombre_usu,
                                 @Part("correo_usu") RequestBody correo_usu,
                                 @Part("password_usu") RequestBody password_usu
    );

    @FormUrlEncoded
    @POST("/auth/usuarios")
    Call<Usuarios> createUsuario(@Field("nombre_usu") String nombre_usu,
                                 @Field("correo_usu") String correo_usu,
                                 @Field("password_usu") String password_usu

    );






    @DELETE("/mascotas/{id}")
    Call<String> destroyMascotas(@Path("id") Long id);
    @GET("/mascotas/{id}")
    Call<Mascotas> showMascotas(@Path("id") Long id);


    @FormUrlEncoded
    @POST("/auth/login")
    Call<Usuarios> login(@Field("correo_usu") String correo_usu,
                        @Field("password_usu") String password_usu);

    @GET("/api/profile")
    Call<Usuarios> getProfile();


}
