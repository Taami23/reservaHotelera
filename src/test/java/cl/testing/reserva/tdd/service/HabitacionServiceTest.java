package cl.testing.reserva.tdd.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import cl.testing.reserva.service.HabitacionService;
import exceptions.HabitacionEmptyListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;

@ExtendWith(MockitoExtension.class)
public class HabitacionServiceTest {
	
	
	@Mock
	private HabitacionRepository habitacionRepository;
	
	@InjectMocks
	private HabitacionService habitacionService;
	
	@Test
	void siSeInvocaListarTodasLasHabitacionesLasEntrega() throws Exception {
		//Arrange 
		ArrayList<Habitacion> habitaciones=new ArrayList<Habitacion>();
		List<Habitacion> resultado;
		habitaciones.add(new Habitacion("1",15000,2,0));
		habitaciones.add(new Habitacion("6",15000,2,0));
		habitaciones.add(new Habitacion("11",15000,2,0));
		
		when(habitacionRepository.findAll()).thenReturn(habitaciones);
		
		//Act
		resultado=habitacionService.getAllHabitaciones();
		
		//Assert
		assertNotNull(resultado);
		assertAll("resultado",
				()-> assertEquals("1",resultado.get(0).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(0).getPrecioHabitacion()),
				()-> assertEquals(2,resultado.get(0).getPisoHabitacion()),
				()-> assertEquals(0,resultado.get(0).isEnUso()),
				
				()-> assertEquals("6",resultado.get(1).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(1).getPrecioHabitacion()),
				()-> assertEquals(2,resultado.get(1).getPisoHabitacion()),
				()-> assertEquals(0,resultado.get(1).isEnUso()),
				
				()-> assertEquals("11",resultado.get(2).getNumeroHabitacion()),
				()-> assertEquals(15000,resultado.get(2).getPrecioHabitacion()),
				()-> assertEquals(2,resultado.get(2).getPisoHabitacion()),
				()-> assertEquals(0,resultado.get(2).isEnUso())
				);
	}
	
	@Test
	void siSeInvocaGetAllHabitacionesYNoExistenHabitacionesArrojaException() throws HabitacionEmptyListException{
		assertThrows(HabitacionEmptyListException.class, ()-> habitacionService.getAllHabitaciones());
	}

	
	@Test
	void siDeseaAgregarUnaHabitacionEntoncesLaPuedeAgregar() throws HabitacionAlreadyExistException, HabitacionNotFoundException {
    	//Arrange
		List<Habitacion> habitaciones = new ArrayList<>();
		Habitacion habitacionAgregar = new Habitacion("4",20000,2,0);
		Habitacion habitacionExistente = new Habitacion("1",15000,2,0);
		Habitacion habitacionExistente1 = new Habitacion("6",15000,2,0);
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
		Habitacion habitacionAgregar = new Habitacion("4",20000,2,0);
		Habitacion habitacionExistente = new Habitacion("4",20000,2,0);
		Habitacion habitacionExistente1 = new Habitacion("4",20000,2,0);
		habitaciones.add(habitacionExistente);
		habitaciones.add(habitacionExistente1);
		when(habitacionRepository.findAll()).thenReturn(habitaciones);
		
		assertThrows(HabitacionAlreadyExistException.class, ()-> habitacionService.agregarHabitacion(habitacionAgregar));
	}

	@Test 
	void siDeseaEliminarUnaHabitacionEntoncesSeBuscaPorSuIdYSeElimina() throws HabitacionNotFoundException{
		//Arrange
		Habitacion habitacionBuscada = new Habitacion("4",20000,2,0);
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
		 Habitacion habitacionBuscada = new Habitacion("4",20000,2,0);
		 habitacionBuscada.setIdHabitacion(1);
		 when(habitacionRepository.getOne(1)).thenReturn(null);
		 
		 //Act + Assert
		 assertThrows(HabitacionNotFoundException.class, ()-> habitacionService.editarHabitacion(habitacionBuscada));
	 }
	
			
	 @Test 
	 void siDeseaEditarLosDatosDeUnaHabitacionYLaEncuentraEntoncesDevueleLaHabitacionActualizada() throws HabitacionNotFoundException{
		 //Arrange
		 Habitacion habitacionBuscada = new Habitacion("4",20000,2,0);

		 habitacionBuscada.setIdHabitacion(1);
		 when(habitacionRepository.getOne(1)).thenReturn(habitacionBuscada);
		 when(habitacionRepository.save(habitacionBuscada)).thenReturn(habitacionBuscada);

		 habitacionBuscada.setPrecioHabitacion(25000);
		 
		 //Act + Assert
		 Habitacion habitacionResultado = habitacionService.editarHabitacion(habitacionBuscada);
		 assertNotNull(habitacionResultado);
		 assertAll("habitacionResultado",
				 ()-> assertEquals("4",habitacionResultado.getNumeroHabitacion()),
				 ()-> assertEquals(25000,habitacionResultado.getPrecioHabitacion()),
				 ()-> assertEquals(2,habitacionResultado.getPisoHabitacion()),
				 ()-> assertEquals(0,habitacionResultado.isEnUso()));
	 }





}
