package cl.testing.reserva.model;

public class Habitacion {

	private int idHabitacion;
	private int numeroHabitacion;
	private int precioHabitacion;
	private char pisoHabitacion;
	private boolean enUso;
	
	public Habitacion() {
		
	}
	
	public Habitacion(int idHabitacion,int numeroHabitacion, int precioHabitacion, char pisoHabitacion,boolean enUso) {
		this.idHabitacion=idHabitacion;
		this.numeroHabitacion=numeroHabitacion;
		this.precioHabitacion=precioHabitacion;
		this.pisoHabitacion=pisoHabitacion;
		this.enUso=enUso;
	}
	
	
	
	public int getIdHabitacion() {
		return idHabitacion;
	}
	public void setIdHabitacion(int idHabitacion) {
		this.idHabitacion = idHabitacion;
	}
	public int getNumeroHabitacion() {
		return numeroHabitacion;
	}
	public void setNumeroHabitacion(int numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}
	public int getPrecioHabitacion() {
		return precioHabitacion;
	}
	public void setPrecioHabitacion(int precioHabitacion) {
		this.precioHabitacion = precioHabitacion;
	}
	public char getPisoHabitacion() {
		return pisoHabitacion;
	}
	public void setPisoHabitacion(char pisoHabitacion) {
		this.pisoHabitacion = pisoHabitacion;
	}
	public boolean isEnUso() {
		return enUso;
	}
	public void setEnUso(boolean enUso) {
		this.enUso = enUso;
	}
	
	//public void listAllHabitaciones() {
	//}

	@Override
	public String toString() {
		return "Habitacion [id="+idHabitacion +", numeroHabitacion=" +numeroHabitacion+", precioHabitacion=" +precioHabitacion+ ". pisoHabitacion=" +pisoHabitacion+", estado="+ enUso+ "]";
	}
	
	
	
	
	
}