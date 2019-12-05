package pe.edu.tecsup.Resmascota.services;

import java.util.List;

import pe.edu.tecsup.Resmascota.entities.Mascota;

public interface MascotaService {
	
	public List<Mascota> findAll();
	
	public Mascota findById(Long id);
	
	public void save(Mascota producto);
	
	public void deleteById(Long id);


}
