package cl.testing.reserva.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.service.HabitacionService;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;

@ExtendWith(MockitoExtension.class)
public class HabitacionServiceTest {

	//listar todas las habitaciones
	//listar habitaciones por nombre  hotel
	//listar habitacion por id
	//listar habitacion por estado(usada,desocupada)
	//ERROR listar todas habitaciones pero no hay 
	
	
	@Mock
	private HabitacionRepository habitacionRepository;
	
	@InjectMocks
	private HabitacionService habitacionService;
	
	@Test
	void siSeInvocaListarTodasLasHabitacionesLasEntrega() throws Exception {
		//Arrange 
		ArrayList<Habitacion> habitaciones=new ArrayList<Habitacion>();
		List<Habitacion> resultado;
		habitaciones.add(new Habitacion(1,1,15000,'2',false));
		habitaciones.add(new Habitacion(6,6,15000,'2',false));
		habitaciones.add(new Habitacion(11,11,15000,'2',false));
		
		when(habitacionRepository.findAll()).thenReturn(habitaciones);
		
		//Act
		resultado=habitacionService.getAllHabitaciones();
		
		//Assert
		assertNotNull(resultado);
		assertAll("resultado",
				()-> assertEquals(1,resultado.get(0).getIdHabitacion()),
				()-> assertEquals(1,resultado.get(0).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(0).getPrecioHabitacion()),
				()-> assertEquals('2',resultado.get(0).getPisoHabitacion()),
				()-> assertEquals(false,resultado.get(0).isEnUso()),
				
				()-> assertEquals(6,resultado.get(1).getIdHabitacion()),
				()-> assertEquals(6,resultado.get(1).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(1).getPrecioHabitacion()),
				()-> assertEquals('2',resultado.get(1).getPisoHabitacion()),
				()-> assertEquals(false,resultado.get(1).isEnUso()),
				
				()-> assertEquals(11,resultado.get(2).getIdHabitacion()),
				()-> assertEquals(11,resultado.get(2).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(2).getPrecioHabitacion()),
				()-> assertEquals('2',resultado.get(2).getPisoHabitacion()),
				()-> assertEquals(false,resultado.get(2).isEnUso())				
				);
	}
	
	@Test
	void siSeInvocaGetAllHabitacionesYNoExistenHabitacionesArrojaException() throws HabitacionNotFoundException{
		assertThrows(HabitacionNotFoundException.class, ()-> habitacionService.getAllHabitaciones());
	}
	
	
	@Test
	void siSeInvocaListarHabitacionesPorNombreDeHotelLasEntrega() {

		//Arrange
		ArrayList<Habitacion> habitaciones=new ArrayList<Habitacion>();
		List<Habitacion> resultado;
		habitaciones.add(new Habitacion(21,21,15000,'2',false));
		habitaciones.add(new Habitacion(22,22,17000,'2',false));
		habitaciones.add(new Habitacion(23,23,18000,'2',false));
		
		when(habitacionRepository.findAll()).thenReturn(habitaciones);
		
		//act
		resultado = habitacionService.getAllHabitacionesByNombreHotel("Hotel California");
		//assert
		assertNotNull(resultado);
		assertAll("resultado",
				()-> assertEquals(21,resultado.get(0).getIdHabitacion()),
				()-> assertEquals(21,resultado.get(0).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(0).getPrecioHabitacion()),
				()-> assertEquals('2',resultado.get(0).getPisoHabitacion()),
				()-> assertEquals(false,resultado.get(0).isEnUso()),
				
				()-> assertEquals(22,resultado.get(1).getIdHabitacion()),
				()-> assertEquals(22,resultado.get(1).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(1).getPrecioHabitacion()),
				()-> assertEquals('2',resultado.get(1).getPisoHabitacion()),
				()-> assertEquals(false,resultado.get(1).isEnUso()),
				
				()-> assertEquals(23,resultado.get(2).getIdHabitacion()),
				()-> assertEquals(23,resultado.get(2).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(2).getPrecioHabitacion()),
				()-> assertEquals('2',resultado.get(2).getPisoHabitacion()),
				()-> assertEquals(false,resultado.get(2).isEnUso())				
				);
	}
	
	/*
	@Test
	void siSeInvocaListarHabitacionesPorIdYExisteEntoncesLaEntrega() {
		//Arrange
		List<Habitacion> habitaciones = new ArrayList<>();
		
		
		
		Habitacion habitacionAgregar = new Habitacion(4,4,20000,'2',false);
		when(habitacionService.getHabitacionById(idHabitacion))
	}
	*/
	
	
	@Test
	void siDeseaAgregarUnaHabitacionEntoncesLaPuedeAgregar() throws HabitacionAlreadyExistException, HabitacionNotFoundException {
    	//Arrange
		List<Habitacion> habitaciones = new ArrayList<>();
		Habitacion habitacionAgregar = new Habitacion(4,4,20000,'2',false);
		when(habitacionService.getHabitacionById(habitacionAgregar.getIdHabitacion())).thenReturn(null);
		Habitacion habitacionExistente = new Habitacion(1,1,15000,'2',false);
		Habitacion habitacionExistente1 = new Habitacion(6,6,15000,'2',false);
		habitaciones.add(habitacionExistente);
		habitaciones.add(habitacionExistente1);
		when(habitacionRepository.findAll()).thenReturn(habitaciones);
		
		habitacionService.agregarHabitacion(habitacionAgregar);

		verify(habitacionRepository, times(1)).save(habitacionAgregar);
	}
	
	@Test
	void siDeseaAgregarUnaHAbitacionYYaExisteArrojaException() throws HabitacionAlreadyExistException, HabitacionNotFoundException{
		//Arrange
		List<Habitacion> habitaciones = new ArrayList<Habitacion>();
		Habitacion habitacionAgregar = new Habitacion(4,4,20000,'2',false);
		Habitacion habitacionExistente = new Habitacion(4,4,20000,'2',false);
		Habitacion habitacionExistente1 = new Habitacion(4,4,20000,'2',false);
		habitaciones.add(habitacionExistente);
		habitaciones.add(habitacionExistente1);
		when(habitacionRepository.findAll()).thenReturn(habitaciones);
		
		assertThrows(HabitacionAlreadyExistException.class, ()-> habitacionService.agregarHabitacion(habitacionAgregar));
	}

	@Test 
	void siDeseaEliminarUnaHabitacionEntoncesSeBuscaPorSuIdYSeElimina() throws HabitacionNotFoundException{
		//Arrange
		Habitacion habitacionBuscada = new Habitacion(4,4,20000,'2',false);
		when(habitacionRepository.getOne(1)).thenReturn(habitacionBuscada);
		
		//Act
		habitacionService.eliminarHabitacion(1);
		
		//Assert
		verify(habitacionRepository,times(1)).delete(habitacionBuscada);
	}
	
	 @Test 
	 void SiDeseaEliminarUnaHabitacionYNoLoEncuentraArrojaException() throws HabitacionNotFoundException{
		 //Arrange
		 when(habitacionRepository.getOne(1)).thenReturn(null);
		 
		 //Act + Assert
		 assertThrows(HabitacionNotFoundException.class, ()->habitacionService.eliminarHabitacion(1));
	 }
	 
	 @Test
	 void siDeseaEditarLosDatosDeUnaHabitacionYNoLaEncuentraEntoncesSeArrojaException() throws HabitacionNotFoundException{
		 //Arrange
		 Habitacion habitacionBuscada = new Habitacion(4,4,20000,'2',false);
		 habitacionBuscada.setIdHabitacion(1);
		 when(habitacionRepository.getOne(1)).thenReturn(null);
		 
		 //Act + Assert
		 assertThrows(HabitacionNotFoundException.class, ()-> habitacionService.editarHabitacion(habitacionBuscada));
	 }
	
			
	 @Test 
	 void siDeseaEditarLosDatosDeUnaHabitacionYLaEncuentraEntoncesDevueleLaHabitacionActualizada() throws HabitacionNotFoundException{
		 //Arrange
		 Habitacion habitacionBuscada = new Habitacion(4,4,20000,'2',false);
		 Habitacion habitacionActualizada= new Habitacion(4,4,25000,'2',false);
		 
		 habitacionBuscada.setIdHabitacion(4);
		 habitacionActualizada.setIdHabitacion(4);
		 when(habitacionRepository.getOne(1)).thenReturn(habitacionBuscada);
		 when(habitacionRepository.save(habitacionActualizada)).thenReturn(habitacionActualizada);
		 
		 //Act + Assert
		 Habitacion habitacionResultado = habitacionService.editarHabitacion(habitacionActualizada);
		 assertNotNull(habitacionResultado);
		 assertAll("habitacionResultado",
				 ()-> assertEquals(4,habitacionResultado.getIdHabitacion()),
				 ()-> assertEquals(4,habitacionResultado.getNumeroHabitacion()),
				 ()-> assertEquals(25000,habitacionResultado.getPrecioHabitacion()),
				 ()-> assertEquals('2',habitacionResultado.getPisoHabitacion()),
				 ()-> assertEquals(false,habitacionResultado.isEnUso()));
	 }





}
