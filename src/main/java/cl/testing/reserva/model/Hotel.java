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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contactoCorreo == null) ? 0 : contactoCorreo.hashCode());
		result = prime * result + ((contactoTelefono == null) ? 0 : contactoTelefono.hashCode());
		result = prime * result + ((contrasena == null) ? 0 : contrasena.hashCode());
		result = prime * result + ((direccion == null) ? 0 : direccion.hashCode());
		result = prime * result + ((idHotel == null) ? 0 : idHotel.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numeroHabitaiciones == null) ? 0 : numeroHabitaiciones.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (contactoCorreo == null) {
			if (other.contactoCorreo != null)
				return false;
		} else if (!contactoCorreo.equals(other.contactoCorreo))
			return false;
		if (contactoTelefono == null) {
			if (other.contactoTelefono != null)
				return false;
		} else if (!contactoTelefono.equals(other.contactoTelefono))
			return false;
		if (contrasena == null) {
			if (other.contrasena != null)
				return false;
		} else if (!contrasena.equals(other.contrasena))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (idHotel == null) {
			if (other.idHotel != null)
				return false;
		} else if (!idHotel.equals(other.idHotel))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroHabitaiciones == null) {
			if (other.numeroHabitaiciones != null)
				return false;
		} else if (!numeroHabitaiciones.equals(other.numeroHabitaiciones))
			return false;
		return true;
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
