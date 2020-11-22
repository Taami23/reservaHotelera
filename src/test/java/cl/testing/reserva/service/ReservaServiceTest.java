package cl.testing.reserva.service;


import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ReservaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
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

    /*private int idReserva;
    private Date fechaInicio;
    private int montoFinal;
    private Date fechaTermino;
    private int idCliente;
    private int idHabitacion;
    */

    @Test 
    void siSeinvocaGetAllReservasDebeRetornarTodasLasReservas() {
    	//Arrange
    	//ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    	//clientes.add(new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi"));
    	//clientes.add(new Cliente("Juanito Alcachofa", "198762543", new Date("1996/04/24"), "+56912345678", "correito@gmail.com","holi2"));
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

    
}
