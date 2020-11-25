package cl.testing.reserva.controllers;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import cl.testing.reserva.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.HotelAlreadyExistsException;
import exceptions.HotelNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.web.bind.annotation.RequestBody;

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
	void siSeInvocaGetAllByNameYLaListaEstaVaciaArrojaExcepcionHotelNotFound() throws Exception{
		doThrow(new HotelNotFoundException()).when(hotelService).getAllHotelsByName("Casa");

		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/Casa")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
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
	void siSeInvocaGetAllHotelYLaListaEstaVaciaArrojaHotelNotFoundExcepcion() throws Exception {
		//Given
		doThrow(new HotelNotFoundException()).when(hotelService).getAllHotel();

		//When
		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();

		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
	}
	@Test
	void siDeseaAgregarUnHotelEntoncesLoPuedeAgregar() throws Exception{
		Hotel hotelAAgregar = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");

		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/agregar")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelAAgregar).getJson())).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getContentAsString()).isEqualTo(jsonHotel.write(hotelAAgregar).getJson());
	}

	@Disabled
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

	@Test
	void siDeseaEliminarUnHotelEntoncesSeBuscaElHotelPorSuIdYSeElimina() throws Exception, HotelNotFoundException {
		//Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");

		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/delete/1")
				.accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		verify(hotelService, times(1)).eliminarHotel(1);
	}

	@Test
	void siDeseaEliminarUnHotelYNoLoEncuentraArrojaExcepcion() throws Exception {

		doThrow(new HotelNotFoundException()).when(hotelService).eliminarHotel(1);

		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/delete/1")
				.accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		verify(hotelService, times(1)).eliminarHotel(1);
	}

	@Test
	void siDeseaEditarLosDatosDeUnHotelYLoEncuentraEntoncesDevuelveElHotelActualizado() throws Exception {
		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		String nuevoNombre = "Hotel Central Chillan";
		hotelBuscado.setNombre(nuevoNombre);

		//When
		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/update/1")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelBuscado).getJson()))
				.andReturn()
				.getResponse();

		//Then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.equals(jsonHotel));
	}

	@Disabled
	@Test
	void siDeseaEditarLosDatosDeUnHotelYNoLoEncuentraArrojaExcepcion() throws Exception, HotelNotFoundException, HotelAlreadyExistsException {
		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		//String nombre = "Hotel Chillancito";
		hotelBuscado.setIdHotel(1);
		//hotelBuscado.setNombre(nombre);

		doThrow(new HotelNotFoundException()).when(hotelService).editarHotel(hotelBuscado);

		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/update/1")
				.contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelBuscado).getJson()))
				.andReturn()
				.getResponse();


		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		verify(hotelService, times(1)).editarHotel(hotelBuscado);
	}
}
