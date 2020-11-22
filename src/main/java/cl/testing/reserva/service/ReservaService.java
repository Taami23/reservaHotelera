package cl.testing.reserva.service;


import java.util.List;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {
	
	@Autowired
	private ReservaRepository reservaRepository;	
	
	public List<Reserva> getAllReservas() {		
		return (List<Reserva>) reservaRepository.findAll();
	}

}
