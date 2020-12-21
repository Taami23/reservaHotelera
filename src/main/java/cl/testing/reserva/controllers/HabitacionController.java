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

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.service.HabitacionService;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;

@RestController
@RequestMapping("/ReservaHotelera/habitaciones")
public class HabitacionController {
	
	@Autowired
	private HabitacionService habitacionService;
	
	@GetMapping("/")
	public ResponseEntity<List<Habitacion>> getAllHabitaciones() throws Exception{
		List<Habitacion> habitaciones = habitacionService.getAllHabitaciones();
		return new ResponseEntity<List<Habitacion>>(habitaciones,HttpStatus.OK);
	}
	
	@GetMapping("/{name}")
    public ResponseEntity<List<Habitacion>> getAllHabitacionessByNumber(@PathVariable Integer numero) throws HabitacionNotFoundException {
        List<Habitacion> habitaciones = habitacionService.getAllHabitacionesByNumber(numero);
        return new ResponseEntity<List<Habitacion>>(habitaciones, HttpStatus.OK);
    }
	
	@GetMapping("/agregar")
	public ResponseEntity<Habitacion> agregarHabitacion(@RequestBody Habitacion habitacion) throws
	HabitacionNotFoundException, HabitacionAlreadyExistException{
		try {
			habitacionService.agregarHabitacion(habitacion);
			return new ResponseEntity<>(habitacion, HttpStatus.CREATED);
		}catch (HabitacionAlreadyExistException e) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/delete/{id}")
    public ResponseEntity<Habitacion> delete(@PathVariable int id) throws HabitacionNotFoundException {
        try {
            habitacionService.eliminarHabitacion(id);
        } catch (HabitacionNotFoundException e) {
            return new ResponseEntity<Habitacion>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Habitacion>(habitacionService.buscarHabitacion(id), HttpStatus.OK);
    }
	
	
	 @PostMapping("/update/{id}")
	    public ResponseEntity<Habitacion> update(@PathVariable int id, Habitacion habitacion) {
	        try {
	        	habitacion.setIdHabitacion(id);
	            return new ResponseEntity<Habitacion>(habitacionService.editarHabitacion(habitacion),HttpStatus.CREATED);
	        } catch (HabitacionNotFoundException e) {
	            return new ResponseEntity<Habitacion>(HttpStatus.NOT_FOUND);
	        }

	    }

	
	
	
	
}
