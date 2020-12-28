package cl.testing.reserva.controllers;

import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ReservaService;
import exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/ReservaHotelera/reservas")
public class ReservaController {
	
	@Autowired
    private ReservaService reservaService;
	
	@PostMapping("/agregar")
	public ResponseEntity<Reserva> agregarReserva(@RequestBody Reserva reserva) throws ReservaNotFoundClienteOHabitacion{
		try {
			reservaService.agregarReserva(reserva);
			return new ResponseEntity<>(reserva, HttpStatus.CREATED);
		}catch (ReservaNotFoundClienteOHabitacion | HabitacionAlreadyInUse e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/delete/{id}")
    public ResponseEntity<Reserva> delete(@PathVariable int id) throws ReservaNotFoundException {
        try {
            reservaService.eliminarReserva(id);
        } catch (ReservaNotFoundException e) {
            return new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Reserva>(HttpStatus.OK);
    }	
	
}
