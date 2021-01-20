package cl.testing.reserva.tdd.service;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ClienteRepository;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.repository.ReservaRepository;
import cl.testing.reserva.service.ReservaService;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	void siSeInvocaGetAllReservasYExistenDebeRetornarListaConReservas() throws ReservaEmptyListException {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		List<Reserva> resultados;
		Reserva r1 = new Reserva(new Date("2020/12/23"), 40000, new Date("2020/12/30"), 1, 1);
		Reserva r2 = new Reserva(new Date("2021/12/27"), 50000, new Date("2021/01/7"), 1, 2);
		reservas.add(r1);
		reservas.add(r2);

		when(reservaRepository.findAll()).thenReturn(reservas);

		resultados = reservaService.getAllReservas(1);
		assertNotNull(resultados);
		assertAll("resultados",
				()->assertEquals(new Date("2020/12/23"), resultados.get(0).getFechaInicio()),
				()->assertEquals(40000, resultados.get(0).getMontoFinal()),
				()->assertEquals(new Date("2020/12/30"), resultados.get(0).getFechaTermino()),
				()->assertEquals(1, resultados.get(0).getIdCliente()),
				()->assertEquals(1, resultados.get(0).getIdHabitacion()),
				()->assertEquals(new Date("2021/12/27"), resultados.get(1).getFechaInicio()),
				()->assertEquals(50000, resultados.get(1).getMontoFinal()),
				()->assertEquals(new Date("2021/01/7"), resultados.get(1).getFechaTermino()),
				()->assertEquals(1, resultados.get(1).getIdCliente()),
				()->assertEquals(2, resultados.get(1).getIdHabitacion())
				);

	}

	@Test
	void siSeInvocaGetAllReservasYNoExistenReservasArrojaException() throws ReservaEmptyListException  {
		assertThrows(ReservaEmptyListException.class, ()->reservaService.getAllReservas(1));

	}

	@Test
	void siDeseaListarReservasEntreFechasYExistenReservasEnEseRangoRetornaUnaLista() throws ReservaEmptyListException, ParseException {
		String fecha1 ="2020/12/23";
		String fecha2 ="2021/01/7";
		List<Reserva> resultados;
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		Reserva r1 = new Reserva(new Date("2020/12/23"), 40000, new Date("2020/12/30"), 1, 1);
		Reserva r2 = new Reserva(new Date("2020/12/24"), 50000, new Date("2021/01/7"), 1, 2);
		reservas.add(r1);
		reservas.add(r2);

		when(reservaRepository.findAll()).thenReturn(reservas);

		resultados = reservaService.searchByDates(fecha1, fecha2, 1);
		assertNotNull(resultados);
		assertAll("resultados",
				()->assertEquals(new Date("2020/12/23"), resultados.get(0).getFechaInicio()),
				()->assertEquals(40000, resultados.get(0).getMontoFinal()),
				()->assertEquals(new Date("2020/12/30"), resultados.get(0).getFechaTermino()),
				()->assertEquals(1, resultados.get(0).getIdCliente()),
				()->assertEquals(1, resultados.get(0).getIdHabitacion()),
				()->assertEquals(new Date("2020/12/24"), resultados.get(1).getFechaInicio()),
				()->assertEquals(50000, resultados.get(1).getMontoFinal()),
				()->assertEquals(new Date("2021/01/7"), resultados.get(1).getFechaTermino()),
				()->assertEquals(1, resultados.get(1).getIdCliente()),
				()->assertEquals(2, resultados.get(1).getIdHabitacion())
		);
	}

	@Test
	void siDeseaListarReservasEntreFechasYNoExistenReservasArrojaException(){
		assertThrows(ReservaEmptyListException.class, ()->reservaService.searchByDates("2020/12/23", "2021/01/20", 1));
	}

	//EDITAR

	@Test
	void siDeseaEditarLosDatosDeUnaReservaYNoLaEncuentraEntoncesSeArrojaException() throws Exception {
		//Arrange
		Reserva reserva = new Reserva(new Date("2020/12/23"), 40000, new Date("2020/12/30"), 1, 1);
		reserva.setIdReserva(1);
		when(reservaRepository.findById(1)).thenReturn(null);

		//Act + Assert
		assertThrows(ReservaNotFoundException.class, ()-> reservaService.editarReserva(reserva));
	}


	@Test
	void siDeseaEditarLosDatosDeUnaHabitacionYLaEncuentraEntoncesDevueleLaHabitacionActualizada() throws ReservaNotFoundException {
		//Arrange
		Reserva reserva = new Reserva(new Date("2020/12/23"), 40000, new Date("2020/12/30"), 1, 1);
		reserva.setIdReserva(1);
		Optional<Reserva> reserva2 = Optional.of(reserva);
		when(reservaRepository.findById(1)).thenReturn(reserva2);
		when(reservaRepository.save(reserva)).thenReturn(reserva);

		reserva.setMontoFinal(50000);

		//Act + Assert
		Reserva reserva1 = reservaService.editarReserva(reserva);
		assertNotNull(reserva1);
		assertAll("reserva1",
				()->assertEquals(new Date("2020/12/23"), reserva.getFechaInicio()),
				()->assertEquals(50000, reserva.getMontoFinal()),
				()->assertEquals(new Date("2020/12/30"), reserva.getFechaTermino()),
				()->assertEquals(1, reserva.getIdCliente()),
				()->assertEquals(1, reserva.getIdHabitacion())
		);
	}

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
