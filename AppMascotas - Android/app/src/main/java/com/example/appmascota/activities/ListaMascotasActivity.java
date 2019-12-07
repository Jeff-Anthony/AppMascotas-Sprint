package com.example.appmascota.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmascota.R;
import com.example.appmascota.adapters.MascotasAdapter;
import com.example.appmascota.models.ApiError;
import com.example.appmascota.models.Mascotas;
import com.example.appmascota.models.Usuarios;
import com.example.appmascota.repositories.UsuarioRepository;
import com.example.appmascota.services.ApiService;
import com.example.appmascota.services.ApiServiceGenerator;

import java.util.List;
import java.util.ResourceBundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaMascotasActivity extends AppCompatActivity {

    private static final String TAG = ListaMascotasActivity.class.getSimpleName();

    private RecyclerView mascotasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);



        mascotasList = findViewById(R.id.Recyclerview_mascotas);

        mascotasList.setLayoutManager(new LinearLayoutManager(this));
        mascotasList.setAdapter(new MascotasAdapter());

        initialize();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void initialize() {
        ApiService service = ApiServiceGenerator.createService(this, ApiService.class);

        service.findAll().enqueue(new Callback<List<Mascotas>>() {
            @Override
            public void onResponse(Call<List<Mascotas>> call, Response<List<Mascotas>> response) {
                if(response.isSuccessful()) {

                    List<Mascotas> mascotas = response.body();
                    Log.d(TAG, "mascotas: " + mascotas);

                    MascotasAdapter adapter = (MascotasAdapter) mascotasList.getAdapter();
                    adapter.setMascotas(mascotas);
                    adapter.notifyDataSetChanged();

                } else {
                    ApiError error = ApiServiceGenerator.parseError(response);
                    Toast.makeText(ListaMascotasActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mascotas>> call, Throwable t) {
                Toast.makeText(ListaMascotasActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    private static final int REQUEST_REGISTER_FORM = 100;

    public void showRegister(View view){
        startActivityForResult(new Intent(this, RegistroMascotasActivity.class), REQUEST_REGISTER_FORM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_REGISTER_FORM) {
            initialize();   // refresh data from rest service
        }
    }

    private void logout(){

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


}
