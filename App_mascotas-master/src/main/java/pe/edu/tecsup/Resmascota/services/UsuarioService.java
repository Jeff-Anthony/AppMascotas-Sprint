package pe.edu.tecsup.Resmascota.services;

import pe.edu.tecsup.Resmascota.entities.Usuario;

public interface UsuarioService {
	
Usuario findByUsernameAndPassword(String nombre_usu, String password_usu);
	
	Usuario findByUsername(String nombre_usu);


}
