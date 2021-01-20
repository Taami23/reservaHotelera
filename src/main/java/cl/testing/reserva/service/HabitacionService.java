package cl.testing.reserva.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.testing.reserva.model.Reserva;
import exceptions.HabitacionEmptyListException;
import exceptions.ReservaEmptyListException;
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
		if(habitacion.getIdHabitacion()==null){
			habitacionRepository.save(habitacion);
		}else{
			if (habitacionRepository.getOne(habitacion.getIdHabitacion()) == null) {
				habitacionRepository.save(habitacion);
			}else{
				throw new HabitacionAlreadyExistException();
			}
		}

	}

	public List<Habitacion> searchByPrice(int price1, int price2) throws HabitacionEmptyListException{
		List<Habitacion> habitacionesCliente = new ArrayList<Habitacion>();
		List<Habitacion> habitaciones = habitacionRepository.findAll();
		if (habitaciones.isEmpty()){
			throw new HabitacionEmptyListException();
		}
		for (Habitacion habitacion : habitaciones){
			if ((habitacion.getPrecioHabitacion() <= price2) && (habitacion.getPrecioHabitacion()>= price1)){
				habitacionesCliente.add(habitacion);
			}
		}
		if (habitacionesCliente.isEmpty()){
			throw new HabitacionEmptyListException();
		}
		return habitacionesCliente;
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
