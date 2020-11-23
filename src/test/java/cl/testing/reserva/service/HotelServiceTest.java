package cl.testing.reserva.service;

import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.repository.HotelRepository;
import exceptions.HotelAlreadyExistsException;
import exceptions.HotelNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void siSeInvocaGetAllByNamesYExistenHotelesConEseNombreDebeRetornarUnaListaConLosHoteles() throws HotelNotFoundException {
        //Arrange
        ArrayList<Hotel> hotels = new ArrayList<Hotel>();
        List<Hotel> resultados;
        hotels.add(new Hotel("Hotel mi casa", 2, "Carrera 952", "65987456321", "contacto@contacto.cl", "password"));
        hotels.add(new Hotel("Hotel la casa del terror", 7, "Libertad 390", "65987456321", "contacto@contacto.cl", "password"));
        hotels.add(new Hotel("Hotel de hoteles", 1, "Arturo Prat 103", "65987456321", "contacto@contacto.cl", "password"));
        hotels.add(new Hotel("Hotel casa blanca", 4, "El roble 952", "65987456321", "contacto@contacto.cl", "password"));

        when(hotelRepository.findAll()).thenReturn(hotels);

        resultados = hotelService.getAllHotelsByName("Casa");
        assertNotNull(resultados);
        assertAll("resultados",
                ()-> assertEquals("Hotel mi casa", resultados.get(0).getNombre()),
                ()-> assertEquals(2, resultados.get(0).getNumeroHabitaiciones()),
                ()-> assertEquals("Carrera 952", resultados.get(0).getDireccion()),
                ()-> assertEquals("65987456321", resultados.get(0).getContactoTelefono()),
                ()-> assertEquals("Hotel la casa del terror", resultados.get(1).getNombre()),
                ()-> assertEquals(7, resultados.get(1).getNumeroHabitaiciones()),
                ()-> assertEquals("Libertad 390", resultados.get(1).getDireccion()),
                ()-> assertEquals("65987456321", resultados.get(1).getContactoTelefono()),
                ()-> assertEquals("Hotel casa blanca", resultados.get(2).getNombre()),
                ()-> assertEquals(4, resultados.get(2).getNumeroHabitaiciones()),
                ()-> assertEquals("El roble 952", resultados.get(2).getDireccion()),
                ()-> assertEquals("65987456321", resultados.get(2).getContactoTelefono())
        );
    }

    @Test
	void siSeInvocaGetAllByNamesYNoExistenHotelesConEseNombreArrojaExcepcion() throws HotelNotFoundException{

    	assertThrows(HotelNotFoundException.class, () -> hotelService.getAllHotelsByName("Casa"));
	}

	@Test
	void siSeInvocaGetAllHotelYExistenHotelesDebeRetornarUnaListaConLosHoteles() throws HotelNotFoundException{
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		List<Hotel> resultados;
		hotels.add(new Hotel("Hotel mi casa", 2, "Carrera 952", "65987456321", "contacto@contacto.cl", "password"));
		hotels.add(new Hotel("Hotel la casa del terror", 7, "Libertad 390", "65987456321", "contacto@contacto.cl", "password"));

		when(hotelRepository.findAll()).thenReturn(hotels);

		resultados = hotelService.getAllHotel();
		assertNotNull(resultados);
		assertAll("resultados",
				()-> assertEquals("Hotel mi casa", resultados.get(0).getNombre()),
				()-> assertEquals(2, resultados.get(0).getNumeroHabitaiciones()),
				()-> assertEquals("Carrera 952", resultados.get(0).getDireccion()),
				()-> assertEquals("65987456321", resultados.get(0).getContactoTelefono()),
				()-> assertEquals("Hotel la casa del terror", resultados.get(1).getNombre()),
				()-> assertEquals(7, resultados.get(1).getNumeroHabitaiciones()),
				()-> assertEquals("Libertad 390", resultados.get(1).getDireccion()),
				()-> assertEquals("65987456321", resultados.get(1).getContactoTelefono())
		);
	}

	@Test
	void siSeInvocaGetAllHotelYNoExistenHotelesArrojaExcepcion() throws HotelNotFoundException{
		assertThrows(HotelNotFoundException.class, () -> hotelService.getAllHotel());
	}

	@Test
	void siDeseaAgregarUnHotelEntoncesLoPuedeAgregar() throws HotelAlreadyExistsException, HotelNotFoundException {
    	//Arrange
		List<Hotel> hotels = new ArrayList<>();
		Hotel hotelAAgregar = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		when(hotelService.getHotelByCorreo(hotelAAgregar.getContactoCorreo())).thenReturn(null);
		Hotel hotelExistente = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan2@gmail.com", "hotelchillan2020");
		Hotel hotelExistente1 = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan1@gmail.com", "hotelchillan2020");
		hotels.add(hotelExistente);
		hotels.add(hotelExistente1);
		when(hotelRepository.findAll()).thenReturn(hotels);



		hotelService.agregarHotel(hotelAAgregar);

		verify(hotelRepository, times(1)).save(hotelAAgregar);
	}

	@Test
	void siDeseaAgregarUnHotelYEsteYaExisteArrojaExcepcion() throws HotelAlreadyExistsException, HotelNotFoundException {
		//Arrange
		List<Hotel> hotels = new ArrayList<Hotel>();
		Hotel hotelAAgregar = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		Hotel hotelExistente = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		Hotel hotelExistente1 = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan1@gmail.com", "hotelchillan2020");
		hotels.add(hotelExistente);
		hotels.add(hotelExistente1);
		when(hotelRepository.findAll()).thenReturn(hotels);
		//doReturn(hotelExistente).when(hotelService).getHotelByCorreo(hotelAAgregar.getContactoCorreo());



		assertThrows(HotelAlreadyExistsException.class, ()-> hotelService.agregarHotel(hotelAAgregar));
	}
    @Test
	void siDeseaEliminarUnHotelEntoncesSeBuscaElHotelPorSuIdYSeElimina() throws HotelNotFoundException {
		//Arrange
		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		when(hotelRepository.getOne(1)).thenReturn(hotelBuscado);
		
		//Act
		hotelService.eliminarHotel(1);
		
		//Assert
		verify(hotelRepository,times(1)).delete(hotelBuscado);
		
		
		
		/*
		assertNotNull(hotelEncontrado);
		assertAll("hotelEncontrado",
				() -> assertEquals(1, hotelEncontrado.getIdHotel()),
				() -> assertEquals("Hotel Chillan".toLowerCase(), hotelEncontrado.getNombre().toLowerCase()),
				() -> assertEquals(50, hotelEncontrado.getNumeroHabitaiciones()),
				() -> assertEquals("Avenida Libertad 658".toLowerCase(), hotelEncontrado.getDireccion()),
				() -> assertEquals("+56945768572", hotelEncontrado.getContactoTelefono()),
				() -> assertEquals("hotelchillan@gmail.com", hotelEncontrado.getContactoCorreo()),
				() -> assertEquals("hotelchillan2020", hotelEncontrado.getContrasena()));
				*/
	}
    @Test
	void siDeseaEliminarUnHotelYNoLoEncuentraEntoncesSeArrojaLaExcepcion() throws HotelNotFoundException {
    	// Arrange
		when(hotelRepository.getOne(1)).thenReturn(null);
		
		// Act + Assert
		assertThrows(HotelNotFoundException.class, () -> 
				hotelService.eliminarHotel(1));
	}

    @Test
	void siDeseaEditarLosDatosDeUnHotelYNoLoEncuentraEntoncesSeArrojaLaExcepcion() throws HotelNotFoundException {
    	// Arrange    	
		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
		hotelBuscado.setIdHotel(1);
		when(hotelRepository.getOne(1)).thenReturn(null);
		
		// Act + Assert
		assertThrows(HotelNotFoundException.class, () -> hotelService.editarHotel(hotelBuscado));
		
		
	}
    @Test
   	void siDeseaEditarLosDatosDeUnHotelYLoEncuentraEntoncesDevuelveElHotelActualizado() throws HotelNotFoundException {
       	// Arrange    	
   		Hotel hotelBuscado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56945768572", "hotelchillan@gmail.com", "hotelchillan2020");
   		Hotel hotelActualizado = new Hotel("Hotel Chillan", 50, "Avenida Libertdad 658", "+56978547865", "hotelchillan@gmail.com", "hotelchillan2020");

   		hotelBuscado.setIdHotel(1);
   		hotelActualizado.setIdHotel(1);
   		when(hotelRepository.getOne(1)).thenReturn(hotelBuscado);
   		when(hotelRepository.save(hotelActualizado)).thenReturn(hotelActualizado);
   		
   		  		
   		// Act + Assert
   		Hotel hotelResultado = hotelService.editarHotel(hotelActualizado);
        assertNotNull(hotelResultado);
        assertAll("hotelResultado",
                ()-> assertEquals("Hotel Chillan", hotelResultado.getNombre()),
                ()-> assertEquals(50, hotelResultado.getNumeroHabitaiciones()),
                ()-> assertEquals("Avenida Libertdad 658", hotelResultado.getDireccion()),
                ()-> assertEquals("+56978547865", hotelResultado.getContactoTelefono()),
                ()-> assertEquals("hotelchillan@gmail.com", hotelResultado.getContactoCorreo()),
                ()-> assertEquals("hotelchillan2020", hotelResultado.getContrasena()));   		
   		
   	} 
    
}
