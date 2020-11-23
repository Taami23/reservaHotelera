package cl.testing.reserva.controllers;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import cl.testing.reserva.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.HotelAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.testing.reserva.model.Hotel;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {

	private MockMvc mockMvc;

	@Mock
	private HotelService hotelService;

	private JacksonTester<List<Hotel>> jsonListaHoteles;

	private JacksonTester<Hotel> jsonHotel;

	@InjectMocks
	private HotelController hotelController;

	@BeforeEach
	void setup(){
		JacksonTester.initFields(this, new ObjectMapper());
		mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
	}

	@Test
	void SiSeInvocaGetAllByNamesYExistenHotelesConEseNombreDebeRetornarListaConLosHoteles() throws Exception{

		//Given
		ArrayList<Hotel> hotels = new ArrayList<>();
		hotels.add(new Hotel("Hotel mi casa", 2, "Carrera 952", "65987456321", "contacto@contacto.cl", "password"));
		hotels.add(new Hotel("Hotel la casa del terror", 7, "Libertad 390", "65987456321", "contacto@contacto.cl", "password"));
		hotels.add(new Hotel("Hotel de hoteles", 1, "Arturo Prat 103", "65987456321", "contacto@contacto.cl", "password"));
		hotels.add(new Hotel("Hotel casa blanca", 4, "El roble 952", "65987456321", "contacto@contacto.cl", "password"));

		given(hotelService.getAllHotelsByName("Casa")).willReturn(hotels);


		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/Casa")
				.accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonListaHoteles.write(hotels).getJson());
	}

	@Test
	void SiSeInvocaGetAllHotelYExistenHotelesDebeRetornarListaConLosHoteles() throws Exception {
		//Given
		ArrayList<Hotel> hotels = new ArrayList<>();
		hotels.add(new Hotel("Hotel mi casa", 2, "Carrera 952", "65987456321", "contacto@contacto.cl", "password"));
		hotels.add(new Hotel("Hotel la casa del terror", 7, "Libertad 390", "65987456321", "contacto@contacto.cl", "password"));
		hotels.add(new Hotel("Hotel de hoteles", 1, "Arturo Prat 103", "65987456321", "contacto@contacto.cl", "password"));
		hotels.add(new Hotel("Hotel casa blanca", 4, "El roble 952", "65987456321", "contacto@contacto.cl", "password"));

		given(hotelService.getAllHotel()).willReturn(hotels);

		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/")
				.accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonListaHoteles.write(hotels).getJson());

	}

	@Test
	void siDeseaAgregarUnHotelEntoncesLoPuedeAgregar() throws Exception{
		Hotel hotelAAgregar = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");

		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/agregar")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelAAgregar).getJson())).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonHotel.write(hotelAAgregar).getJson());
	}

	@Test
	void siDeseaAgregarUnHotelPeroEsteYaExiste() throws Exception, HotelAlreadyExistsException {
		Hotel hotelAAgregar = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");

		//given(hotelService.getHotelByCorreo(hotelAAgregar.getContactoCorreo())).willThrow(new HotelAlreadyExistsException());
		//given(hotelService.getHotelByCorreo(hotelAAgregar.getContactoCorreo())).willReturn(hotelAAgregar);
		//when(hotelService.getHotelByCorreo(hotelAAgregar.getContactoCorreo())).thenReturn(hotelAAgregar);
		//doThrow(new HotelAlreadyExistsException()).when(hotelService).agregarHotel(hotelAAgregar);

		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/agregar")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelAAgregar).getJson())).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
		//assertThat(response.getContentAsString()).isEqualTo(jsonHotel.write(hotelAAgregar).getJson());

	}

}
