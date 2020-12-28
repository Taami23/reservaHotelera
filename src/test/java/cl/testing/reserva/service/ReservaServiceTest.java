package cl.testing.reserva.service;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ClienteRepository;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.repository.ReservaRepository;
import exceptions.*;

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

	@Mock
	private ClienteRepository clienteRepository;

	@Mock
	private HabitacionRepository habitacionRepository;

	@InjectMocks
	private ReservaService reservaService;

	@Test
	void siDeseaAgregarUnaReservaEntoncesLaPuedeAgregar() throws ReservaNotFoundClienteOHabitacion, HabitacionAlreadyInUse {
		// Arrange
		Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com", "holi");
		Habitacion habitacion = new Habitacion("4", 20000, 2, 0);
		Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/21"), 1, 1);
		when(clienteRepository.getOne(1)).thenReturn(cliente);
		when(habitacionRepository.getOne(1)).thenReturn(habitacion);

		// Act
		reservaService.agregarReserva(reserva);

		// Assert
		verify(reservaRepository, times(1)).save(reserva);
	}

	@Test
	void siDeseaAgregarUnaReservaYNoExisteElClienteOLaHabitacionEntoncesLanzaExcepcionReservaNotFoundClienteOHabitacion() throws ReservaNotFoundClienteOHabitacion {
		Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com", "holi");
		Habitacion habitacion = new Habitacion("4", 20000, 2, 0);
		Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/21"), 1, 1);
		when(clienteRepository.getOne(1)).thenReturn(cliente);
		when(habitacionRepository.getOne(1)).thenReturn(null);

		
		//Act + Assert
		assertThrows(ReservaNotFoundClienteOHabitacion.class, () -> reservaService.agregarReserva(reserva));
	}

	@Test
	void siDesaAgregarReservaYHabitacionYaEstaEnUsoLanzaExcepcion(){
		Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com", "holi");
		Habitacion habitacion = new Habitacion("4", 20000, 2, 1);
		Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/21"), 1, 1);
		when(clienteRepository.getOne(1)).thenReturn(cliente);
		when(habitacionRepository.getOne(1)).thenReturn(habitacion);

		assertThrows(HabitacionAlreadyInUse.class, () -> reservaService.agregarReserva(reserva));

	}

	@Test
	void siDeseaEliminarUnaReservaSeBuscaYSeElimina() throws ReservaNotFoundException {
		// Arrange
		Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
		reserva.setIdReserva(1);
		when(reservaRepository.getOne(1)).thenReturn(reserva);

		// Act
		reservaService.eliminarReserva(reserva.getIdReserva());

		// Assert
		verify(reservaRepository, times(1)).delete(reserva);
	}

	@Test
	void siDeseaEliminarUnReservaSeBuscaYNoExiste() throws ReservaNotFoundException {
		// Arrange
		Reserva reserva = new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1);
		reserva.setIdReserva(1);
		when(reservaRepository.getOne(1)).thenReturn(null);

		// Act + Assert
		assertThrows(ReservaNotFoundException.class, () -> reservaService.eliminarReserva(1));
	}
}
