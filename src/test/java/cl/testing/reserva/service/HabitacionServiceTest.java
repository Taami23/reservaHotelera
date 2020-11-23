package cl.testing.reserva.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.service.HabitacionService;

@ExtendWith(MockitoExtension.class)
public class HabitacionServiceTest {

	
	@Mock
	private HabitacionRepository habitacionRepository;
	
	@InjectMocks
	private HabitacionService habitacionService;
	
	@Test
	void siSeInvocaListarTodasLasHabitacionesLasEntrega() {
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
	
	@Test
	void SiSeInvocaListarHabitacionesPorEstado









}
