package cl.testing.reserva.model;

public class Hotel {

	private Integer idHotel;
	private String nombre;
	private String numeroHabitaiciones;
	private String direccion;
	private String contactoTelefono;
	private String contactoCorreo;
	private String contrasena;
	
	public Hotel(Integer idHotel, String nombre, String numeroHabitaiciones, String direccion, String contactoTelefono,
			String contactoCorreo, String contrasena) {
		this.idHotel = idHotel;
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

	public String getNumeroHabitaiciones() {
		return numeroHabitaiciones;
	}

	public void setNumeroHabitaiciones(String numeroHabitaiciones) {
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
	
	
	
	
	
}
