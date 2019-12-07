package com.example.appmascota.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmascota.R;
import com.example.appmascota.models.Mascotas;
import com.example.appmascota.services.ApiService;
import com.example.appmascota.services.ApiServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoMascotaActivity extends AppCompatActivity {

    private static final String TAG = InfoMascotaActivity.class.getSimpleName();

    private Long id;

    private ImageView fotoImage;
    private TextView nombreText;
    private TextView razaText;
    private TextView edadText;
    private TextView dueñoText;
    private TextView correodueñoText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_mascota);

        fotoImage = findViewById(R.id.info_imagen);
        nombreText = findViewById(R.id.info_nombre);
        razaText = findViewById(R.id.info_raza);
        edadText = findViewById(R.id.info_edad);
        dueñoText = findViewById(R.id.info_dueño);
        correodueñoText = findViewById(R.id.info_correo);

        id = getIntent().getExtras().getLong("ID");
        Log.e(TAG, "id:" + id);

        initialize();

    }


    private void initialize(){

        ApiService service = ApiServiceGenerator.createService(this, ApiService.class);

        Call<Mascotas> call = service.showMascotas(id);

        call.enqueue(new Callback<Mascotas>() {
            @Override
            public void onResponse(@NonNull Call<Mascotas> call, @NonNull Response<Mascotas> response) {
                try {

                    if (response.isSuccessful()) {

                        Mascotas mascotas = response.body();
                        Log.d(TAG, "mascota: " + mascotas);

                        nombreText.setText(mascotas.getNombre());
                        razaText.setText(mascotas.getRaza());
                        edadText.setText(mascotas.getEdad());


                        String url = ApiService.API_BASE_URL + "/mascotas/images/" + mascotas.getFoto();
                        ApiServiceGenerator.createPicasso(InfoMascotaActivity.this).load(url).into(fotoImage);

                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(InfoMascotaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Mascotas> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(InfoMascotaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }


}
