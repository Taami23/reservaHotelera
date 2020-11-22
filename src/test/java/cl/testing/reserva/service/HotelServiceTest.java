package cl.testing.reserva.service;

import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void siSeInvocaGetAllByNamesYExistenHotelesConEseNombreDebeRetornarUnaListaConLosHoteles(){
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
}
