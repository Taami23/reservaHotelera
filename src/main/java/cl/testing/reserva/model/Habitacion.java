package cl.testing.reserva.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Habitacion {
	@Id
	@GeneratedValue
	private Integer idHabitacion;
	private String numeroHabitacion;
	private Integer precioHabitacion;
	private Integer pisoHabitacion;
	private Integer enUso;
	
	public Habitacion() {
		
	}
	
	public Habitacion(String numeroHabitacion, Integer precioHabitacion, Integer pisoHabitacion, Integer enUso) {
		this.idHabitacion=idHabitacion;
		this.numeroHabitacion=numeroHabitacion;
		this.precioHabitacion=precioHabitacion;
		this.pisoHabitacion=pisoHabitacion;
		this.enUso=enUso;
	}
	
	
	
	public Integer getIdHabitacion() {
		return idHabitacion;
	}
	public void setIdHabitacion(Integer idHabitacion) {
		this.idHabitacion = idHabitacion;
	}
	public String getNumeroHabitacion() {
		return numeroHabitacion;
	}
	public void setNumeroHabitacion(String numeroHabitacion) {
		this.numeroHabitacion = numeroHabitacion;
	}
	public Integer getPrecioHabitacion() {
		return precioHabitacion;
	}
	public void setPrecioHabitacion(Integer precioHabitacion) {
		this.precioHabitacion = precioHabitacion;
	}
	public Integer getPisoHabitacion() {
		return pisoHabitacion;
	}
	public void setPisoHabitacion(Integer pisoHabitacion) {
		this.pisoHabitacion = pisoHabitacion;
	}
	public Integer isEnUso() {
		return enUso;
	}
	public void setEnUso(Integer enUso) {
		this.enUso = enUso;
	}

	@Override
	public String toString() {
		return "Habitacion [id="+idHabitacion +", numeroHabitacion=" +numeroHabitacion+", precioHabitacion=" +precioHabitacion+ ". pisoHabitacion=" +pisoHabitacion+", estado="+ enUso+ "]";
	}
	
	
	
	
	
}
