package pe.edu.tecsup.Resmascota.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mascota")
public class Mascota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_mas;
	
	private String nombre_mas;
	
	private String raza_mas;
	
	private int edad_mas;
	
	private String foto;
	
	private Long id_usuario;

	public Long getId_mas() {
		return id_mas;
	}

	public String getNombre_mas() {
		return nombre_mas;
	}

	public String getRaza_mas() {
		return raza_mas;
	}

	public int getEdad_mas() {
		return edad_mas;
	}

	public String getFoto() {
		return foto;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_mas(Long id_mas) {
		this.id_mas = id_mas;
	}

	public void setNombre_mas(String nombre_mas) {
		this.nombre_mas = nombre_mas;
	}

	public void setRaza_mas(String raza_mas) {
		this.raza_mas = raza_mas;
	}

	public void setEdad_mas(int edad_mas) {
		this.edad_mas = edad_mas;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

	@Override
	public String toString() {
		return "Mascota [id_mas=" + id_mas + ", nombre_mas=" + nombre_mas + ", raza_mas=" + raza_mas + ", edad_mas="
				+ edad_mas + ", foto=" + foto + ", id_usuario=" + id_usuario + "]";
	}

	
}
