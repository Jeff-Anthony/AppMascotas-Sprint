package pe.edu.tecsup.Resmascota.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_usu;
	
	private String nombre_usu;
	
	private String correo_usu;
	
	private String password_usu;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name=("id_usuario"),referencedColumnName="id_usu")
    private List<Mascota> mascota=new ArrayList<>();

	public Long getId_usu() {
		return id_usu;
	}

	public String getNombre_usu() {
		return nombre_usu;
	}

	public String getCorreo_usu() {
		return correo_usu;
	}

	public String getPassword_usu() {
		return password_usu;
	}

	public List<Mascota> getMascota() {
		return mascota;
	}

	public void setId_usu(Long id_usu) {
		this.id_usu = id_usu;
	}

	public void setNombre_usu(String nombre_usu) {
		this.nombre_usu = nombre_usu;
	}

	public void setCorreo_usu(String correo_usu) {
		this.correo_usu = correo_usu;
	}

	public void setPassword_usu(String password_usu) {
		this.password_usu = password_usu;
	}

	public void setMascota(List<Mascota> mascota) {
		this.mascota = mascota;
	}

	@Override
	public String toString() {
		return "Usuario [id_usu=" + id_usu + ", nombre_usu=" + nombre_usu + ", correo_usu=" + correo_usu
				+ ", password_usu=" + password_usu + ", mascota=" + mascota + "]";
	}

	
	
	
	
}
