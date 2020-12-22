package cl.testing.reserva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ReservaService;
import exceptions.ClienteAlreadyExistsException;
import exceptions.ClienteNotFoundException;
import exceptions.ClientesEmptyListException;
import exceptions.HabitacionNotFoundException;
import exceptions.ReservaAlreadyExistsException;
import exceptions.ReservaNotFoundException;
import exceptions.ReservasEmptyListException;

@RestController
@RequestMapping("/ReservaHotelera/reservas")
public class ReservaController {
	 @Autowired
	 private ReservaService reservaService;
	 
	 @GetMapping("/")
	 public ResponseEntity<List<Reserva>> getAllReservas() throws ReservasEmptyListException {
		 
	    try{
	        List<Reserva> reservas = reservaService.getAllReservas();
	        return new ResponseEntity<>(reservas,HttpStatus.OK);
	    }catch (ReservasEmptyListException e){
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	 
	@PostMapping("/agregar")
	public ResponseEntity<Reserva> agregarReserva(@RequestBody Reserva reserva)
			throws ReservaNotFoundException, ReservaAlreadyExistsException {
		try {
			reservaService.agregarReserva(reserva);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (ReservaAlreadyExistsException e) {
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
