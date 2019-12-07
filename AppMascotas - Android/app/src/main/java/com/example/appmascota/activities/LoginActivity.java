package com.example.appmascota.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmascota.R;
import com.example.appmascota.models.ApiError;
import com.example.appmascota.models.Mascotas;
import com.example.appmascota.models.Usuarios;
import com.example.appmascota.services.ApiService;
import com.example.appmascota.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText correo, contraseña;
    private Button btningreso, btnregistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correo_ingreso);
        contraseña = findViewById(R.id.contraseña_ingreso);

        btningreso = findViewById(R.id.button_ingreso);
        btnregistro = findViewById(R.id.button_registro);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               showRegistro();

            }
        });

        btningreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        loadLastCorreo();



    }

    public  void showRegistro(){

        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }




    private void login(){

        String Correo = correo.getText().toString();
        String Password = contraseña.getText().toString();

        if(Correo.isEmpty()){
            Toast.makeText(this, "Ingrese el correo de usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        if(Password.isEmpty()){
            Toast.makeText(this, "Ingrese el password", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuarios> call = service.login(Correo, Password);

        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if(response.isSuccessful()) { // code 200
                    Usuarios usuario = response.body();
                    Log.d(TAG, "usuario" + usuario);

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    sp.edit()
                            .putString("correo_usu", usuario.getCorreo_usu())
                            .putString("password_usu", usuario.getPassword_usu())
                            .putBoolean("islogged", true)
                            .commit();

                    // Go Main Activity
                    startActivity(new Intent(LoginActivity.this, ListaMascotasActivity.class));
                    finish();

                    Toast.makeText(LoginActivity.this, "Bienvenido " + usuario.getNombre_usu(), Toast.LENGTH_LONG).show();

                }else{
                    ApiError error = ApiServiceGenerator.parseError(response);
                    Toast.makeText(LoginActivity.this, "onError:" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "onFailure: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void loadLastCorreo(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String correo_usu = sp.getString("correo_usu", null);
        if(correo_usu != null){
            correo.setText(correo_usu);
        }
    }

    private void verifyLoginStatus(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean islogged = sp.getBoolean("islogged", false);

        if(islogged){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

    }



}
