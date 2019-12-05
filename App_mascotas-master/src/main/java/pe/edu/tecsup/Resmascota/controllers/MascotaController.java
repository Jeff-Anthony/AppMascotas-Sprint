package pe.edu.tecsup.Resmascota.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.tecsup.Resmascota.entities.Mascota;
import pe.edu.tecsup.Resmascota.services.MascotaService;


@RestController
public class MascotaController {

private static final Logger logger = LoggerFactory.getLogger(MascotaController.class);
	
	@Value("${app.storage.path}")
	private String STORAGEPATH;
	
	@Autowired
	private MascotaService mascotaService;
	
	@GetMapping("/mascotas")
	public List<Mascota> productos() {
		logger.info("call productos");
		
		List<Mascota> productos = mascotaService.findAll();
		logger.info("productos: " + productos);
		
		return productos;
	}
	
	@GetMapping("/mascotas/images/{filename:.+}")
	public ResponseEntity<Resource> files(@PathVariable String filename) throws Exception{
		logger.info("call images: " + filename);
		
		Path path = Paths.get(STORAGEPATH).resolve(filename);
		logger.info("Path: " + path);
		
		if(!Files.exists(path)) {
			return ResponseEntity.notFound().build();
		}
		
		Resource resource = new UrlResource(path.toUri());
		logger.info("Resource: " + resource);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+resource.getFilename()+"\"")
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(STORAGEPATH).resolve(filename)))
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
				.body(resource);
	}
	
	@PostMapping("/mascotas")
	public Mascota crear(@RequestParam(name="foto", required=false) MultipartFile foto, @RequestParam("nombre_mas") String nombre_mas,@RequestParam("raza_mas") String raza_mas,@RequestParam("edad_mas") int edad_mas,@RequestParam("id_usu") Long id_usuario) throws Exception {
		logger.info("call crear(" + nombre_mas + ", " + raza_mas + ", " + edad_mas + ", " + foto + ", " + id_usuario + ")");
		
		Mascota mascota = new Mascota();
		mascota.setNombre_mas(nombre_mas);
		mascota.setRaza_mas(raza_mas);
		mascota.setEdad_mas(edad_mas);
		mascota.setId_usuario(id_usuario);
		
		if (foto != null && !foto.isEmpty()) {
			String filename = System.currentTimeMillis() + foto.getOriginalFilename().substring(foto.getOriginalFilename().lastIndexOf("."));
			mascota.setFoto(filename);
			if(Files.notExists(Paths.get(STORAGEPATH))){
		        Files.createDirectories(Paths.get(STORAGEPATH));
		    }
			Files.copy(foto.getInputStream(), Paths.get(STORAGEPATH).resolve(filename));
			mascota.setFoto(foto.getOriginalFilename());
		}
		
		mascotaService.save(mascota);
		
		return mascota;
	}
	
	@DeleteMapping("/mascotas/{id_mas}")
	public ResponseEntity<String> eliminar(@PathVariable Long id_mas) {
		logger.info("call eliminar: " + id_mas);
		
		mascotaService.deleteById(id_mas);
		
		return ResponseEntity.ok().body("Registro eliminado");
	}
	
	@GetMapping("/mascotas/{id_mas}")
	public Mascota obtener(@PathVariable Long id_mas) {
		logger.info("call obtener: " + id_mas);
		
		Mascota producto = mascotaService.findById(id_mas);
		
		return producto;
	}


}

