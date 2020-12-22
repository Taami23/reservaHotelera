package cl.testing.reserva.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reserva {
	@Id
    @GeneratedValue
    private Integer idReserva;
	private Date fecha;
	private Integer montoFinal;
	private Date fechaTermino;
	
	public Reserva() {
		
	}
	
	public Reserva(Date fecha, Integer montoFinal, Date fechaTermino) {
		this.fecha = fecha;
		this.montoFinal = montoFinal;
		this.fechaTermino = fechaTermino;		
	}

	public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Integer getMontoFinal() {
		return montoFinal;
	}

	public void setMontoFinal(Integer montoFinal) {
		this.montoFinal = montoFinal;
	}

	public Date getFechaTermino() {
		return fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", fecha=" + fecha + ", montoFinal=" + montoFinal + ", fechaTermino="
				+ fechaTermino + "]";
	}

	
	
	
}
