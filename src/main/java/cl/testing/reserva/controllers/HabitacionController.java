package cl.testing.reserva.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.service.HabitacionService;

@RestController
@RequestMapping("/ReservaHotelera/habitaciones")
public class HabitacionController {
	
	@Autowired
	private HabitacionService habitacionService;
	
	@GetMapping("/")
	public ResponseEntity<List<Habitacion>> getAllHabitaciones(){
		List<Habitacion> habitaciones = habitacionService.getAllHabitaciones();
		return new ResponseEntity<List<Habitacion>>(habitaciones,HttpStatus.OK);
	}

}
