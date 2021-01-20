package cl.testing.reserva.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ClienteRepository;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.repository.ReservaRepository;
import exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;
	private ClienteRepository clienteRepository;
	private HabitacionRepository habitacionRepository;

	public List<Reserva> getAllReservas(int idCliente) throws ReservaEmptyListException{
		List<Reserva> reservasCliente = new ArrayList<Reserva>();
		List<Reserva> reservas = reservaRepository.findAll();
		if (reservas.isEmpty()){
			throw new ReservaEmptyListException();
		}
		for (Reserva reserva : reservas){
			if (reserva.getIdCliente() == idCliente){
				reservasCliente.add(reserva);
			}
		}
		if (reservasCliente.isEmpty()){
			throw new ReservaEmptyListException();
		}
		return reservasCliente;
	}

	public List<Reserva> searchByDates(String fecha1, String fecha2, int idCliente) throws ReservaEmptyListException, ParseException {
		List<Reserva> reservasCliente = new ArrayList<Reserva>();
		List<Reserva> reservas = reservaRepository.findAll();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");
		Date fechaI = format.parse(fecha1);
		Date fechaT = format.parse(fecha2);
		if (reservas.isEmpty()){
			throw new ReservaEmptyListException();
		}
		for (Reserva reserva : reservas){
			if ((reserva.getIdCliente() == idCliente) && (reserva.getFechaInicio().getTime() <= fechaT.getTime()) && (reserva.getFechaInicio().getTime()>= fechaI.getTime())){
				reservasCliente.add(reserva);
			}
		}
		if (reservasCliente.isEmpty()){
			throw new ReservaEmptyListException();
		}
		return reservasCliente;
	}

	public Reserva editarReserva(Reserva reserva) throws ReservaNotFoundException {
		if	(reservaRepository.findById(reserva.getIdReserva()) == null) {
			throw new ReservaNotFoundException();
		}
		return reservaRepository.save(reserva);
	}

	public void agregarReserva(Reserva reserva) throws ReservaNotFoundClienteOHabitacion, HabitacionAlreadyInUse {
		if((clienteRepository.getOne(reserva.getIdCliente()) != null) && (habitacionRepository.getOne(reserva.getIdHabitacion()) != null)) {
			if(habitacionRepository.getOne(reserva.getIdHabitacion()).isEnUso() == 0){
				reservaRepository.save(reserva);
			}else {
				throw new HabitacionAlreadyInUse();
			}
		}else {
			throw new ReservaNotFoundClienteOHabitacion();
		}
		
	}

	public void eliminarReserva(int id) throws ReservaNotFoundException {
		Reserva reservaAEliminar = reservaRepository.getOne(id);
		if(reservaAEliminar == null) {
			throw new ReservaNotFoundException();
		}
		reservaRepository.delete(reservaAEliminar);
	}

}
