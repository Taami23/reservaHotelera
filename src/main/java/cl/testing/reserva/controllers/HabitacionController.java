package cl.testing.reserva.controllers;

import java.util.List;

import exceptions.HabitacionEmptyListException;
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
		try{
			List<Habitacion> habitaciones = habitacionService.getAllHabitaciones();
			return new ResponseEntity<>(habitaciones,HttpStatus.OK);
		}catch (HabitacionEmptyListException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{name}")
    public ResponseEntity<Habitacion> getHabitacionById(@PathVariable int id) throws HabitacionNotFoundException {
        Habitacion habitacion = habitacionService.buscarHabitacion(id);
		System.out.println("caca");
        return new ResponseEntity<Habitacion>(habitacion, HttpStatus.OK);
    }
	
	@PostMapping("/agregar")
	public ResponseEntity<Habitacion> agregarHabitacion(@RequestBody Habitacion habitacion) throws
	HabitacionNotFoundException{
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
