package pe.edu.tecsup.Resmascota.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.tecsup.Resmascota.entities.Mascota;
import pe.edu.tecsup.Resmascota.repositories.MascotaRepository;


@Service
@Transactional
public class MascotaServiceImpl implements MascotaService{

	@Autowired
	private MascotaRepository mascotaRepository;
	
	@Override
	public List<Mascota> findAll() {
		return mascotaRepository.findAll();
	}

	@Override
	public Mascota findById(Long id) {
		return mascotaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe registro"));
	}

	@Override
	public void save(Mascota producto) {
		mascotaRepository.save(producto);
		
	}

	@Override
	public void deleteById(Long id) {
		mascotaRepository.deleteById(id);
		
	}

}
