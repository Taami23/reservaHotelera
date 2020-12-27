package cl.testing.reserva.service;


import java.util.List;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ClienteRepository;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.repository.ReservaRepository;
import exceptions.ClientesEmptyListException;
import exceptions.ReservaAlreadyExistsException;
import exceptions.ReservaEmptyListException;
import exceptions.ReservaNotFoundClienteOHabitacion;
import exceptions.ReservaNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;



public class ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;
	private ClienteRepository clienteRepository;
	private HabitacionRepository habitacionRepository;
	
	public List<Reserva> getAllReservas() throws ReservaEmptyListException{		
		if(reservaRepository.findAll().isEmpty()) {
            throw new ReservaEmptyListException();
        }
        return reservaRepository.findAll();
	}	

	public void agregarReserva(Reserva reserva) throws ReservaNotFoundClienteOHabitacion {
		if((clienteRepository.getOne(reserva.getIdCliente()) != null) && (habitacionRepository.getOne(reserva.getIdHabitacion()) != null)) {
			reservaRepository.save(reserva);
		}else {
			throw new ReservaNotFoundClienteOHabitacion();
		}
		
	}

	public void eliminarReserva(int id) throws ReservaNotFoundException {
		Reserva reservaAEliminar = buscarReserva(id);
		if(reservaAEliminar == null) {
			throw new ReservaNotFoundException();
		}
		reservaRepository.delete(reservaAEliminar);
	}

	public Reserva buscarReserva(int id) throws ReservaNotFoundException {
		Reserva reservaEncontrada = reservaRepository.getOne(id);
		if(reservaEncontrada == null) {
			throw new ReservaNotFoundException();
		}
		return reservaEncontrada;
	}
	

}
