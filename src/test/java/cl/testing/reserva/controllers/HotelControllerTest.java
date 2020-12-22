package cl.testing.reserva.controllers;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cl.testing.reserva.service.HotelService;
import exceptions.HotelNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.query.criteria.internal.expression.SearchedCaseExpression.WhenClause;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.doThrow;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import cl.testing.reserva.model.Hotel;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
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
	
	//------------------------------------------------------------------------------------------------------------------------------------
	@Test 
	void SiSeInvocaDeleteYExisteElHotelDebeEliminarAlHotel() throws Exception{
		//Given
		ArrayList<Hotel> hoteles = new ArrayList<>();
		hoteles.add(new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020"));
		
		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/delete/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		//Then
		verify(hotelService, times(1)).eliminarHotel(1);
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
						
	}	
	@Test 
	void SiSeInvocaDeleteYNoExisteElHotelDebeLazarseLaException() throws Exception{
		//Given
		doThrow(new HotelNotFoundException()).when(hotelService).eliminarHotel(2);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/delete/2").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		verify(hotelService, times(1)).eliminarHotel(2);
						
	}
	@Test
	void SiDeseaEditarLosDatosDeUnHotelYLoEncuentraDevuelveElHotelActualizado() throws Exception{
		//Given
		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		String nuevoNombre = "Hotel Central de Chillan";
		hotelBuscado.setNombre(nuevoNombre);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/update/1").contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelBuscado).getJson())).andReturn().getResponse();
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.equals(jsonHotel));
	}	
	
	//@Disabled
	@Test 
	void SiSeInvocaUpdateYNoExisteElHotelDebeLazarseLaException() throws Exception{
		//Given
		/*
		Hotel hotelAActualizar = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		String nombre = "Hotel Chillan Central";
		hotelAActualizar.setIdHotel(1);
		hotelAActualizar.setNombre(nombre);
		given(hotelService.editarHotel(hotelAActualizar)).willThrow(new HotelNotFoundException());
		
		//when
		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/update/1").contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelAActualizar).getJson())).andReturn().getResponse();
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		*/
		
		// given
		/*
		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		doThrow(new HotelNotFoundException()).when(hotelService).editarHotel(hotelBuscado);
				
		// when
		MockHttpServletResponse response = mockMvc.perform(get("/reservaHoteles/hoteles/update/1")
						.accept(MediaType.APPLICATION_JSON))
						.andReturn().getResponse();
				
		// then 
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		verify(hotelService, times(1)).editarHotel(hotelBuscado);		
		*/
		
		/*
		Hotel hotelAActualizar = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		String nuevoNombre = "Hotel Central de Chillan";
		hotelAActualizar.setNombre(nuevoNombre);
		given(hotelService.buscarHotel(1)).willReturn(null);
		//doThrow(new HotelNotFoundException()).when(hotelService).editarHotel(hotelAActualizar);
				
		//when
		MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/update/1").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
		
		//then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(response.equals(hotelAActualizar));
		
		*/
		
		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		hotelBuscado.setIdHotel(1);
        //String nombre = "Hotel Chillancito";
        //hotelBuscado.setNombre(nombre);

        //assertThrows(new HotelNotFoundException()).when(hotelService).editarHotel(hotelBuscado);
        when(hotelService.buscarHotel(hotelBuscado.getIdHotel())).thenReturn(null);
        MockHttpServletResponse response = mockMvc.perform(post("/reservaHoteles/hoteles/update/1")
                .contentType(MediaType.APPLICATION_JSON).content(jsonHotel.write(hotelBuscado).getJson()))
                .andReturn()
                .getResponse();

        //assertThrows(HotelNotFoundException.class, ()-> hotelService.editarHotel(hotelBuscado));
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        verify(hotelService, times(1)).editarHotel(hotelBuscado);
		
	}
	
	

}
