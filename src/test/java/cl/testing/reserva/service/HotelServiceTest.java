package cl.testing.reserva.service;

import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.repository.HotelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public class HotelServiceTest {
    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelService hotelService;

    @Test
    void siSeInvocaGetAllByNamesYExistenHotelesConEseNombreDebeRetornarUnaListaConLosHoteles(){
        //Arrange
        ArrayList<Hotel> hotels = new ArrayList<>();
        List<Hotel> resultados;

    }
}
