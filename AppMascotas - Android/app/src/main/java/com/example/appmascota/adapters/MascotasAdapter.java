package com.example.appmascota.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appmascota.R;
import com.example.appmascota.activities.InfoMascotaActivity;
import com.example.appmascota.models.Mascotas;
import com.example.appmascota.services.ApiService;
import com.example.appmascota.services.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MascotasAdapter extends RecyclerView.Adapter<MascotasAdapter.ViewHolder> {

    private static final String TAG = MascotasAdapter.class.getSimpleName();
    private List<Mascotas> mascotas;

    public MascotasAdapter(){
        this.mascotas = new ArrayList<>();
    }


    public void setMascotas(List<Mascotas> mascotas){
        this.mascotas = mascotas;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fotoImage;
        TextView nombreText;
        TextView razaText;
        TextView edadText;
        ImageButton menuButton;

        ViewHolder(View itemView) {
            super(itemView);

            fotoImage = itemView.findViewById(R.id.imagen_cardview);
            nombreText = itemView.findViewById(R.id.nombre_cardview);
            razaText = itemView.findViewById(R.id.raza_cardview);
            edadText = itemView.findViewById(R.id.edad_cardview);
            menuButton = itemView.findViewById(R.id.menu_cardview);

        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mascota, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        final Context context = viewHolder.itemView.getContext();

        final Mascotas mascota = this.mascotas.get(position);

        viewHolder.nombreText.setText(mascota.getNombre());
        viewHolder.razaText.setText( mascota.getRaza());
        viewHolder.edadText.setText( mascota.getEdad());

        String url = ApiService.API_BASE_URL + "/mascotas/images/" + mascota.getFoto();

        ApiServiceGenerator.createPicasso(context).load(url).into(viewHolder.fotoImage);



        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:

                                ApiService service =ApiServiceGenerator.createService(context, ApiService.class);

                                service.destroyMascotas(mascota.getId_mas()).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                                        try {

                                            if (response.isSuccessful()) {

                                                String message = response.body();
                                                Log.d(TAG, "message: " + message);

                                                // Eliminar item del recyclerView y notificar cambios
                                                mascotas.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, mascotas.size());

                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                                            } else {
                                                throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                                            }

                                        } catch (Throwable t) {
                                            Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });

                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoMascotaActivity.class);
                intent.putExtra("ID", mascota.getId_mas());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.mascotas.size();
    }



}
