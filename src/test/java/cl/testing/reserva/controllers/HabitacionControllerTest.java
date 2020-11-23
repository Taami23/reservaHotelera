package cl.testing.reserva.controllers;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.testing.reserva.controllers.HabitacionController;
import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.service.HabitacionService;

@ExtendWith(MockitoExtension.class)
public class HabitacionControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private HabitacionService habitacionService;
	
	private JacksonTester<List<Habitacion>> jsonListaHabitacion;
	
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
		habitaciones.add(new Habitacion(21,21,15000,'2',false));
		habitaciones.add(new Habitacion(22,22,17000,'2',false));
		habitaciones.add(new Habitacion(23,23,18000,'2',false));
		
		given(habitacionService.getAllHabitaciones().willReturn(habitaciones));
		habitaciones.remove(1);
		
		//When
		MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/habitaciones/"))
							.accept(MediaType.APPLICATION_JSON))
							.andReturn()
							.getResponse();
					
		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(
				jsonListaHabitacion.write(habitaciones).getJson()
		);
	}
}
