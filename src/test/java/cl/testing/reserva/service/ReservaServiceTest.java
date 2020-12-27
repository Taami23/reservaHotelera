package cl.testing.reserva.service;


import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ClienteRepository;
import cl.testing.reserva.repository.ReservaRepository;
import exceptions.ReservaAlreadyExistsException;
import exceptions.ReservaNotFoundException;
import exceptions.ReservaEmptyListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {
    
    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private ReservaService reservaService;


    @Test 
    void siSeinvocaGetAllReservasDebeRetornarTodasLasReservas() throws ReservaEmptyListException {
    	//Arrange
    	ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    	List<Reserva> resultado;
        reservas.add(new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1));
        reservas.add(new Reserva(new Date("2020/11/05"), 270000, new Date("2020/11/15"), 2, 2));
        when(reservaRepository.findAll()).thenReturn(reservas);
        
        //act
        resultado = reservaService.getAllReservas();
        
        //Assert
        assertNotNull(resultado);
        assertAll("resultado",
        		//reserva1
        		()-> assertEquals(new Date("2020/11/09"), resultado.get(0).getFechaInicio()),
        		()-> assertEquals(297000, resultado.get(0).getMontoFinal()),
        		()-> assertEquals(new Date("2020/11/20"), resultado.get(0).getFechaTermino()),
				()-> assertEquals(1, resultado.get(0).getIdCliente()),
				()-> assertEquals(1, resultado.get(0).getIdHabitacion()),
				//reserva2
				()-> assertEquals(new Date("2020/11/05"), resultado.get(1).getFechaInicio()),
        		()-> assertEquals(270000, resultado.get(1).getMontoFinal()),
        		()-> assertEquals(new Date("2020/11/15"), resultado.get(1).getFechaTermino()),
				()-> assertEquals(2, resultado.get(1).getIdCliente()),
				()-> assertEquals(2, resultado.get(1).getIdHabitacion())
        );
    }
    
    @Test
    void siDeseaAgregarUnaReservaEntoncesLaPuedeAgregar() throws ReservaAlreadyExistsException, ReservaNotFoundException {
        //Arrange
        List<Reserva> reservas = new ArrayList<>();
        Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
        //Reserva reservaExistente = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
        //reservas.add(reservaExistente);
        when(reservaRepository.findAll()).thenReturn(reservas);

        reservaService.agregarReserva(reserva);
        verify(reservaRepository, times(1)).save(reserva);
    }
    
    @Test
    void siDeseaAgregarUnaReservaYExisteEntoncesLanzaExcepcionReservaAlreadyExists() throws ReservaNotFoundException {
        //Arrange
        List<Reserva> reservas = new ArrayList<>();
        Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
        when(reservaService.buscarReserva(reserva.getIdReserva())).thenReturn(null);
        Reserva reservaExistente = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
        reservas.add(reservaExistente);
        when(reservaRepository.findAll()).thenReturn(reservas);

        //Act + Assert
        assertThrows(ReservaAlreadyExistsException.class, ()-> reservaService.agregarReserva(reserva));
    }

    @Test
    void siDeseaEliminarUnaReservaSeBuscaYSeElimina() throws ReservaNotFoundException {
        // Arrange
        Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
        reserva.setIdReserva(1);
        when(reservaRepository.getOne(1)).thenReturn(reserva);

        // Act
        reservaService.eliminarReserva(reserva.getIdReserva());

        //Assert
        verify(reservaRepository,times(1)).delete(reserva);
    }
    @Test
    void siDeseaEliminarUnClienteSeBuscaYNoExiste() throws  ReservaNotFoundException {
        // Arrange
        Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
        reserva.setIdReserva(1);
        when(reservaRepository.getOne(1)).thenReturn(null);

        //Act + Assert
        assertThrows(ReservaNotFoundException.class,() -> reservaService.eliminarReserva(1) );
    }
}
