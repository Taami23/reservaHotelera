package cl.testing.reserva.service;


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
