package pe.edu.tecsup.Resmascota.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import pe.edu.tecsup.Resmascota.entities.Mascota;

public interface MascotaRepository extends CrudRepository<Mascota, Long>  {

	@Override
	List<Mascota> findAll();

}
