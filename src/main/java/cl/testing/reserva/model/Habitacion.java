package cl.testing.reserva.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Habitacion that = (Habitacion) o;
		return Objects.equals(idHabitacion, that.idHabitacion) &&
				Objects.equals(numeroHabitacion, that.numeroHabitacion) &&
				Objects.equals(precioHabitacion, that.precioHabitacion) &&
				Objects.equals(pisoHabitacion, that.pisoHabitacion) &&
				Objects.equals(enUso, that.enUso);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idHabitacion, numeroHabitacion, precioHabitacion, pisoHabitacion, enUso);
	}

	@Override
	public String toString() {
		return "Habitacion [id="+idHabitacion +", numeroHabitacion=" +numeroHabitacion+", precioHabitacion=" +precioHabitacion+ ". pisoHabitacion=" +pisoHabitacion+", estado="+ enUso+ "]";
	}
	
	
	
	
	
}
