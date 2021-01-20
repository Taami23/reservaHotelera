package cl.testing.reserva.controllers;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ReservaService;
import exceptions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/ReservaHotelera/reservas")
public class ReservaController {
	
	@Autowired
    private ReservaService reservaService;

	@GetMapping("/{id}")
	public ResponseEntity<List<Reserva>> getAllReservas(@PathVariable int id) throws Exception{
		try {
			List<Reserva> reservas = reservaService.getAllReservas(id);
			return new ResponseEntity<>(reservas, HttpStatus.OK);
		}catch (ReservaEmptyListException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/update")
	public ResponseEntity<Reserva> update(@RequestBody Reserva reserva){
		try{
			return new ResponseEntity<Reserva>(reservaService.editarReserva(reserva),HttpStatus.OK);
		} catch (ReservaNotFoundException e) {
			return new ResponseEntity<Reserva>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/search/{id}")
	public ResponseEntity<List<Reserva>> searchByDates(@PathVariable int id, @RequestParam(name = "fecha1") String fecha1, @RequestParam(name = "fecha2") String fecha2){
		try{
			List<Reserva> reservas = reservaService.searchByDates(fecha1, fecha2, id);
			return new ResponseEntity<>(reservas, HttpStatus.OK);
		} catch (ReservaEmptyListException | ParseException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

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
