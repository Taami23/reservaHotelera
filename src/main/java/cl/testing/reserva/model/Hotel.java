package cl.testing.reserva.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hotel {
	
	@Id
	@GeneratedValue
	private Integer idHotel;
	private String nombre;
	private Integer numeroHabitaiciones;
	private String direccion;
	private String contactoTelefono;
	private String contactoCorreo;
	private String contrasena;
	
	public Hotel(String nombre, Integer numeroHabitaiciones, String direccion, String contactoTelefono,
			String contactoCorreo, String contrasena) {
		this.nombre = nombre;
		this.numeroHabitaiciones = numeroHabitaiciones;
		this.direccion = direccion;
		this.contactoTelefono = contactoTelefono;
		this.contactoCorreo = contactoCorreo;
		this.contrasena = contrasena;
	}
	
	public Hotel() {
		
	}

	public Integer getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(Integer idHotel) {
		this.idHotel = idHotel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNumeroHabitaiciones() {
		return numeroHabitaiciones;
	}

	public void setNumeroHabitaiciones(Integer numeroHabitaiciones) {
		this.numeroHabitaiciones = numeroHabitaiciones;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getContactoTelefono() {
		return contactoTelefono;
	}

	public void setContactoTelefono(String contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

	public String getContactoCorreo() {
		return contactoCorreo;
	}

	public void setContactoCorreo(String contactoCorreo) {
		this.contactoCorreo = contactoCorreo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	@Override
	public String toString() {
		return "Hotel{" +
				"idHotel=" + idHotel +
				", nombre='" + nombre + '\'' +
				", numeroHabitaiciones=" + numeroHabitaiciones +
				", direccion='" + direccion + '\'' +
				", contactoTelefono='" + contactoTelefono + '\'' +
				", contactoCorreo='" + contactoCorreo + '\'' +
				", contrasena='" + contrasena + '\'' +
				'}';
	}
}
