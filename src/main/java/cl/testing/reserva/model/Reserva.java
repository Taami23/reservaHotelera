package cl.testing.reserva.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Reserva {

    @Id
    @GeneratedValue
    private Integer idReserva;
    private Date fechaInicio;
    private Integer montoFinal;
    private Date fechaTermino;
    private Integer idCliente;
    private Integer idHabitacion;

    public Reserva() {
    }

    public Reserva(Date fechaInicio, Integer montoFinal, Date fechaTermino, Integer idCliente, Integer idHabitacion) {
        this.fechaInicio = fechaInicio;
        this.montoFinal = montoFinal;
        this.fechaTermino = fechaTermino;
    	this.idCliente = idCliente;
    	this.idHabitacion = idHabitacion;
    }

    

    public Integer getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Integer idReserva) {
		this.idReserva = idReserva;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
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

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getIdHabitacion() {
		return idHabitacion;
	}

	public void setIdHabitacion(Integer idHabitacion) {
		this.idHabitacion = idHabitacion;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", fechaInicio=" + fechaInicio + ", montoFinal=" + montoFinal
				+ ", fechaTermino=" + fechaTermino + ", idCliente=" + idCliente + ", idHabitacion=" + idHabitacion
				+ "]";
	}

	/*@Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idReserva +
                ", fechaInicio='" + fechaInicio + '\'' +
                ", montoFinal='" + montoFinal + '\'' +
                ", fechaTermino=" + fechaTermino +
                ", idCliente='" + idCliente + '\'' +
                ", idHabitacion='" + idHabitacion + '\'' +
                '}';
    }
    */
}
