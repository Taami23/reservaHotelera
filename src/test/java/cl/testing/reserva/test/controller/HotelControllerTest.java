package cl.testing.reserva.test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.testing.reserva.model.Hotel;

@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {
	
	Hotel hotel;
	
	@BeforeEach
	void setup() {
		hotel = new Hotel(1, "Hotel de Prueba", "2", "Calle falsa 123", "56987456123", 
				"contacto@hotel.cl", "hotelhotel123");
	}
	
	@Test
	void SiSolicitaMostrasTodasLasHabitacionesDelHotelDebePoderVerlas() {
		
	}
}
