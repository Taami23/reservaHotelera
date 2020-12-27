package cl.testing.reserva.controllers;

import java.util.List;

import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ReservaService;
import exceptions.ReservaAlreadyExistsException;
import exceptions.ReservaEmptyListException;
import exceptions.ReservaNotFoundClienteOHabitacion;
import exceptions.ReservaNotFoundException;

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
	
	@GetMapping("/")
    public ResponseEntity<List<Reserva>> getAllReservas() throws ReservaEmptyListException {
        List<Reserva> reservas = reservaService.getAllReservas();
        return new ResponseEntity<List<Reserva>>(reservas,HttpStatus.OK);
    }
	
	@PostMapping("/agregar")
	public ResponseEntity<Reserva> agregarReserva(@RequestBody Reserva reserva) throws ReservaNotFoundClienteOHabitacion{
		try {
			reservaService.agregarReserva(reserva);
			return new ResponseEntity<>(reserva, HttpStatus.CREATED);
		}catch (ReservaNotFoundClienteOHabitacion e) {
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
        return new ResponseEntity<Reserva>(reservaService.buscarReserva(id), HttpStatus.OK);
    }	
	
}
