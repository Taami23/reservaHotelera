package cl.testing.reserva.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;

public class HabitacionService {

	@Autowired
	private HabitacionRepository habitacionRepository;
	
	/*public List<Habitacion> getAllHabitaciones1(){
		return (List<Habitacion>) habitacionRepository.findAll();
	}
	
	*/
	
	public List<Habitacion> getAllHabitaciones(){
		ArrayList<Habitacion> habitaciones1 = new ArrayList<Habitacion>();
		List<Habitacion> habitaciones=(List<Habitacion>) habitacionRepository.findAll();
		
		for (Habitacion habitacion : habitaciones) {
			habitaciones1.add(habitacion);
		}
		return habitaciones;
	}
	
	
	

	public List<Habitacion> getAllHabitacionesByStatus(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Habitacion> getAllHabitacionesByNombreHotel(String nombreHotel) {
		ArrayList<Habitacion> habitacionesConNombreHotel = new ArrayList<Habitacion>();
		List<Habitacion> habitaciones = (List<Habitacion>) habitacionRepository.findAll();
		
		for(Habitacion habitacion : habitaciones) {
			habitacionesConNombreHotel.add(habitacion);
		}
		return habitacionesConNombreHotel;
	}
}
