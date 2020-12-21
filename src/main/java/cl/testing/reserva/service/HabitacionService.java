package cl.testing.reserva.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;

public class HabitacionService {

	@Autowired
	private HabitacionRepository habitacionRepository;

	public List<Habitacion> getAllHabitaciones() throws HabitacionNotFoundException{
		if (habitacionRepository.findAll().isEmpty()) {
			throw new HabitacionNotFoundException();
		}
		return habitacionRepository.findAll();
		/*ArrayList<Habitacion> habitaciones1 = new ArrayList<Habitacion>();
		List<Habitacion> habitaciones =(List<Habitacion>) habitacionRepository.findAll();
		for (Habitacion habitacion : habitaciones) {
			habitaciones1.add(habitacion);
			}
		}
		return getAllHabitaciones();*/
	}

	public List<Habitacion> getAllHabitacionesByStatus(boolean b) throws HabitacionNotFoundException {
		if (habitacionRepository.findAll().isEmpty()) {
			throw new HabitacionNotFoundException();
		}else {
			ArrayList<Habitacion> habitaciones1= new ArrayList<Habitacion>();
			List<Habitacion> habitaciones=(List<Habitacion>) habitacionRepository.findAll();
			for(Habitacion habitacion: habitaciones) {
				if(habitacion.isEnUso()==true) {
					habitaciones1.add(habitacion);
				}
			}
			if(habitaciones1.isEmpty()) {
				throw new HabitacionNotFoundException();
			}
		return habitaciones1;
		}
	}
	
	
	//FALTAAAAAAA
	public List<Habitacion> getAllHabitacionesByNombreHotel(String nombreHotel) {
		ArrayList<Habitacion> habitacionesConNombreHotel = new ArrayList<Habitacion>();
		List<Habitacion> habitaciones = (List<Habitacion>) habitacionRepository.findAll();
		
		for(Habitacion habitacion : habitaciones) {
			habitacionesConNombreHotel.add(habitacion);
		}
		return habitacionesConNombreHotel;
	}


	public void agregarHabitacion(Habitacion habitacion) throws HabitacionAlreadyExistException {
		if (getHabitacionById(habitacion.getIdHabitacion())== null) {
			habitacionRepository.save(habitacion);
		}else {
			throw new HabitacionAlreadyExistException();
		}
	}
	
	//????????????
	public List<Habitacion> getAllHAbitacionesByNumber(Integer numero) {
		// TODO Auto-generated method stub
		return null;
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

 
   //???????????????????????
	public Object getHabitacionById(int idHabitacion) {
		// TODO Auto-generated method stub
		return null;
	}

	//????????????????????
	public List<Habitacion> getAllHabitacionesByNumber(Integer numero) {
		// TODO Auto-generated method stub
		return null;
	}
}
