package pe.edu.tecsup.Resmascota.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import pe.edu.tecsup.Resmascota.entities.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>  {

	
	@Query("SELECT u FROM Usuario u WHERE u.nombre_usu=:nombre_usu AND u.password_usu=:password_usu")
	Usuario findByUsernameAndPassword(@Param("nombre_usu") String nombre_usu, @Param("password_usu") String password_usu);

	@Query("SELECT u FROM Usuario u WHERE u.nombre_usu=:nombre_usu")
	Usuario findByUsername(@Param("nombre_usu") String nombre_usu);


}
