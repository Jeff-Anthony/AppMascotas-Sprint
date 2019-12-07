package com.example.appmascota.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appmascota.R;
import com.example.appmascota.models.Usuarios;
import com.example.appmascota.services.ApiService;
import com.example.appmascota.services.ApiServiceGenerator;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private static final String TAG = RegistroActivity.class.getSimpleName();

    private EditText nombreUsuario;
    private EditText correoUsuario;
    private EditText passwordUsuario;
    private Button Btnregistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombreUsuario = findViewById(R.id.edit_nombre);
        correoUsuario = findViewById(R.id.edit_correo);
        passwordUsuario = findViewById(R.id.edit_contraseña);
        Btnregistro = findViewById(R.id.registro_usuario_button);

    }

    private Bitmap bitmap;

    public void RegistroUsuario(View view){



        String nombre = nombreUsuario.getText().toString();
        String correo = correoUsuario.getText().toString();
        String pass = passwordUsuario.getText().toString();

        if(nombre.isEmpty() || correo.isEmpty() || pass.isEmpty()){

            Toast.makeText(this,"Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            return;
        }


        ApiService service = ApiServiceGenerator.createService(this, ApiService.class);


        Call<Usuarios> call;



        if(bitmap == null){
            call = service.createUsuario(nombre, correo, pass);
        } else {

            RequestBody nombrePart = RequestBody.create(MultipartBody.FORM, nombre);
            RequestBody correoPart = RequestBody.create(MultipartBody.FORM, correo);
            RequestBody passPart = RequestBody.create(MultipartBody.FORM, pass);

            call = service.createUsuario(nombrePart, correoPart, passPart);
        }

        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {

                try{

                    if(response.isSuccessful()){

                        Usuarios usuarios = response.body();
                        Log.d(TAG , "Usuario: " + usuarios);

                        Toast.makeText(RegistroActivity.this, "Registro Realizado Correctamente", Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);

                        finish();
                    }else{
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }


                }catch(Throwable t){
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(RegistroActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(RegistroActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }


}
