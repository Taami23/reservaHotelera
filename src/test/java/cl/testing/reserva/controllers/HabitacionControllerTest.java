package cl.testing.reserva.controllers;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import exceptions.HabitacionEmptyListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.service.HabitacionService;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;

@ExtendWith(MockitoExtension.class)
public class HabitacionControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private HabitacionService habitacionService;

	private JacksonTester<List<Habitacion>> jsonListaHabitacion;
	private JacksonTester<Habitacion> jsonHabitacion;
	
	@InjectMocks
	private HabitacionController habitacionController;
	
	@BeforeEach
	void setup() {
		JacksonTester.initFields(this,new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(habitacionController).build();	
	}
	
	
	
	@Test
	void SiSeInvocaGetAllHabitacionesYExistenDebeRetornarListaConHabitaciones() throws Exception{
		//Given
		ArrayList<Habitacion> habitaciones=new ArrayList<Habitacion>();
		habitaciones.add(new Habitacion("1",15000,2,0));
		habitaciones.add(new Habitacion("6",17000,2,0));
		habitaciones.add(new Habitacion("11",18000,2,0));
		
		given(habitacionService.getAllHabitaciones()).willReturn(habitaciones);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/habitaciones/")
							.accept(MediaType.APPLICATION_JSON))
							.andReturn()
							.getResponse();
					
		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonListaHabitacion.write(habitaciones).getJson());
	}
	
	@Test 
	void siSeInvocaGetAllHabitacionesyLaListaEstaVaciaArrojaExceptionHabitacionEmptyList() throws Exception{
		doThrow(new HabitacionEmptyListException()).when(habitacionService).getAllHabitaciones();
		
		MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/habitaciones/")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	
	//agregar
	@Test
	void SiDeseaAgregarUnaHAbitacionYSePuede() throws Exception {
		Habitacion habitacionAgregar = new Habitacion("31",15000,2,0);
		
		MockHttpServletResponse response= mockMvc.perform(post("/ReservaHotelera/habitaciones/agregar")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonHabitacion.write(habitacionAgregar)
				.getJson())).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonHabitacion.write(habitacionAgregar).getJson());
		
	}
	
	
	@Test
	void siDeseaAgregarUnaHabitacionPeroYaExisteLanzaException() throws Exception, HabitacionAlreadyExistException{
		Habitacion habitacionAgregar = new Habitacion("1",15000,2,0);
		
		doThrow(new HabitacionAlreadyExistException()).when(habitacionService).agregarHabitacion(ArgumentMatchers.any(Habitacion.class));
		
				MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/habitaciones/agregar")
						.contentType(MediaType.APPLICATION_JSON).content(jsonHabitacion.write(habitacionAgregar).getJson())).andReturn().getResponse();

				assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());	
	}

	//eliminar
	@Test
	void siDeseaEliminarUnaHabitacionEntoncesSeBuscaLaHabitacionPorSuIdYSeElimina() throws Exception, HabitacionNotFoundException {
		
		Habitacion habitacionBuscada = new Habitacion("1",15000,2,0);
		MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/habitaciones/delete/1")
				.accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		verify(habitacionService, times(1)).eliminarHabitacion(1);
	}
	
	@Test
	void siDeseaEkiminarUnaHabitacionYNoLaEncuentraArrojaException() throws Exception{
		doThrow(new HabitacionNotFoundException()).when(habitacionService).eliminarHabitacion(1);
		
		MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/habitaciones/delete/1")
				.accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		verify(habitacionService, times(1)).eliminarHabitacion(1);
	}
	
	
	
	
	//editar
	@Test
	void siDeseaEditarLosDatosDeUnaHabitacionYLoEncuentraEntoncesDevuelveLaHabitacionActualizada() throws Exception {
		Habitacion habitacionBuscada = new Habitacion("1",15000,2,0);
		int nuevoPrecio = 12300;
		habitacionBuscada.setPrecioHabitacion(nuevoPrecio);

		//When
		MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/habitaciones/update/1")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHabitacion.write(habitacionBuscada).getJson()))
				.andReturn()
				.getResponse();

		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.equals(jsonHabitacion));
	}
	
	
	@Test
	void SiDeseaEditarLosDatosDeUnaHabitacionYNoLaEncuentraArrojaException() throws Exception, HabitacionNotFoundException, HabitacionAlreadyExistException{
		Habitacion habitacionBuscada= new Habitacion("1",15000,2,0);
		habitacionBuscada.setIdHabitacion(1);
		
		doThrow(new HabitacionNotFoundException()).when(habitacionService).editarHabitacion(ArgumentMatchers.any(Habitacion.class));
		
		MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/habitaciones/update/1")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHabitacion.write(habitacionBuscada).getJson()))
				.andReturn()
				.getResponse();

		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	
	
}
