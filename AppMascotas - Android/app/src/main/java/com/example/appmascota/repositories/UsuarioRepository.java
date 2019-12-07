package com.example.appmascota.repositories;

import com.example.appmascota.models.Usuarios;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {


    private static List<Usuarios> usuarios1= new ArrayList<>();

    public static Usuarios findUsuarios(String usuarionombre){



        for(Usuarios usuarios : usuarios1){

            if(usuarios.getNombre_usu().equalsIgnoreCase(usuarionombre)){

                return usuarios;

            }

        }
        return null;
    }

}
