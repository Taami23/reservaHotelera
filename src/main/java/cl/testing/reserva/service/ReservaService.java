package cl.testing.reserva.service;


import java.util.List;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ClienteRepository;
import cl.testing.reserva.repository.ReservaRepository;
import exceptions.ReservaAlreadyExistsException;
import exceptions.ReservaEmptyListException;
import exceptions.ReservaNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;



public class ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;
	private ClienteRepository clienteRepository;
	
	public List<Reserva> getAllReservas() throws ReservaEmptyListException{		
		if(reservaRepository.findAll().isEmpty()) {
            throw new ReservaEmptyListException();
        }
        return reservaRepository.findAll();
	}	

	public void agregarReserva(Reserva reserva) throws ReservaNotFoundException, ReservaAlreadyExistsException {
		if(clienteRepository.getOne(reserva.getIdCliente()) != null) {
			reservaRepository.save(reserva);
		}
		//if (buscarReserva(reserva.getIdReserva()) == null) {
		///	reservaRepository.save(reserva);
		//}
		throw new ReservaAlreadyExistsException();
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
