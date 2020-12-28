package cl.testing.reserva.service;
import java.util.ArrayList;
import java.util.List;

import exceptions.HabitacionEmptyListException;
import org.springframework.beans.factory.annotation.Autowired;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class HabitacionService {

	@Autowired
	private HabitacionRepository habitacionRepository;

	public List<Habitacion> getAllHabitaciones() throws HabitacionEmptyListException {
		if (habitacionRepository.findAll().isEmpty()) {
			throw new HabitacionEmptyListException();
		}
		return habitacionRepository.findAll();
	}


	public void agregarHabitacion(Habitacion habitacion) throws HabitacionAlreadyExistException, HabitacionNotFoundException {
		if (getHabitacionByNumero(habitacion.getNumeroHabitacion()) == null) {
			habitacionRepository.save(habitacion);
		}else{
			throw new HabitacionAlreadyExistException();
		}
	}

	public Habitacion getHabitacionByNumero(String numeroHabitacion){
		Habitacion habitacion = null;
		List<Habitacion> habitaciones = habitacionRepository.findAll();
		if(!habitaciones.isEmpty()){
			for (int i = 0; i < habitaciones.size(); i++) {
				if(habitaciones.get(i).getNumeroHabitacion().equalsIgnoreCase(numeroHabitacion)){
					return habitaciones.get(i);
				}
			}
		}
		return habitacion;
	}


	public void eliminarHabitacion(int id) throws HabitacionNotFoundException {
		Habitacion habitacionAEliminar = buscarHabitacion(id);
		if(habitacionAEliminar == null) {
			throw new HabitacionNotFoundException();
		}
		habitacionRepository.delete(habitacionAEliminar);	
	}


	public Habitacion buscarHabitacion(int id) throws HabitacionNotFoundException {
		Habitacion habitacionEncontrada = habitacionRepository.getOne(id);
		if	(habitacionEncontrada == null) {
			throw new HabitacionNotFoundException();
		}
		return habitacionEncontrada;
	}

	
	public Habitacion editarHabitacion(Habitacion habitacion) throws HabitacionNotFoundException {
		if	(buscarHabitacion(habitacion.getIdHabitacion()) ==null) {
			throw new HabitacionNotFoundException();
		}
		return habitacionRepository.save(habitacion);
	}


//	public List<Habitacion> getAllHabitacionesByStatus(boolean b) throws HabitacionNotFoundException {
//		if (habitacionRepository.findAll().isEmpty()) {
//			throw new HabitacionNotFoundException();
//		}else {
//			ArrayList<Habitacion> habitaciones1= new ArrayList<Habitacion>();
//			List<Habitacion> habitaciones=(List<Habitacion>) habitacionRepository.findAll();
//			for(Habitacion habitacion: habitaciones) {
//				if(habitacion.isEnUso()==1) {
//					habitaciones1.add(habitacion);
//				}
//			}
//			if(habitaciones1.isEmpty()) {
//				throw new HabitacionNotFoundException();
//			}
//		return habitaciones1;
//		}
//	}
//

	//FALTAAAAAAA
//	public List<Habitacion> getAllHabitacionesByNombreHotel(String nombreHotel) {
//		ArrayList<Habitacion> habitacionesConNombreHotel = new ArrayList<Habitacion>();
//		List<Habitacion> habitaciones = (List<Habitacion>) habitacionRepository.findAll();
//
//		for(Habitacion habitacion : habitaciones) {
//			habitacionesConNombreHotel.add(habitacion);
//		}
//		return habitacionesConNombreHotel;
//	}


}
