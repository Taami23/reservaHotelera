package cl.testing.reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ReservaRepository;
import exceptions.ClientesEmptyListException;
import exceptions.ReservasEmptyListException;

@Service
public class ReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	public List<Reserva> getAllReservas() throws ReservasEmptyListException {
        if(reservaRepository.findAll().isEmpty()) {
            throw new ReservasEmptyListException();
        }
        return reservaRepository.findAll();
    }
	
	public Reserva save(Reserva reserva){
        return reservaRepository.save(reserva);
    }
	
	
	
	
	public void agregarReserva(Reserva reserva) {
		// TODO Auto-generated method stub

	}
}
