package cl.testing.reserva.integrationTests.stepsDefs;


import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.repository.ReservaRepository;
import cl.testing.reserva.service.ClienteService;
import cl.testing.reserva.service.HabitacionService;
import cl.testing.reserva.service.ReservaService;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionAlreadyInUse;
import exceptions.HabitacionNotFoundException;
import exceptions.ReservaNotFoundClienteOHabitacion;
import cl.testing.reserva.model.Reserva;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import exceptions.ClienteAlreadyExistsException;
import exceptions.ClienteNotFoundException;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.springframework.core.ParameterizedTypeReference;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class reservaStepDefs {
    @LocalServerPort
    private int port;

    Cliente cliente;
    Habitacion habitacion;
    Habitacion habitacion2;
    Reserva reserva;
    List<Reserva> reservas = new ArrayList<>();
    List<Reserva> reservasEncontradas = new ArrayList<>();

    private ResponseEntity<Reserva> responseReserva;
    private ResponseEntity<List<Reserva>> responseReservas;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Given("una lista de reservas")
    public void una_lista_de_reservas() {
        Reserva r1 = new Reserva(new Date("2020/12/23"), 40000, new Date("2020/12/30"), 1, 1);
        Reserva r2 = new Reserva(new Date("2020/12/24"), 50000, new Date("2021/01/7"), 1, 2);
        Reserva r3 = new Reserva(new Date("2021/01/08"), 100000, new Date("2021/01/20"), 1, 3);
        reservas.add(r1);
        reservas.add(r2);
        reservas.add(r3);
        reservaRepository.save(r1);
        reservaRepository.save(r2);
        reservaRepository.save(r3);
    }

    @When("deseo buscar las reservas entre las fechas {string} y {string}")
    public void deseo_buscar_las_reservas_entre_las_fechas_y(String fecha1, String fecha2) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<List<Reserva>> entity = new HttpEntity<>(null,httpHeaders);
        
        testRestTemplate = new TestRestTemplate();
        responseReservas = testRestTemplate.exchange(createURLWithPort("/ReservaHotelera/reservas/search/1?fecha1=" + fecha1 + "&fecha2=" + fecha2),
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Reserva>>(){});
    }

    @Then("obtengo el estado {string} y una lista de las habitaciones detro de las fechas ingresadas")
    public void obtengo_el_estado_y_una_lista_de_las_habitaciones_detro_de_las_fechas_ingresadas(String estado) {
        assertEquals(estado.toUpperCase(), responseReservas.getStatusCode().name().toString());
        reservasEncontradas = responseReservas.getBody();
        assertNotNull(reservas);
        assertAll("reservas",
                ()->assertEquals(new Date("2020/12/23"), reservasEncontradas.get(0).getFechaInicio()),
                ()->assertEquals(40000, reservasEncontradas.get(0).getMontoFinal()),
                ()->assertEquals(new Date("2020/12/30"), reservasEncontradas.get(0).getFechaTermino()),
                ()->assertEquals(1, reservasEncontradas.get(0).getIdCliente()),
                ()->assertEquals(1, reservasEncontradas.get(0).getIdHabitacion()),
                ()->assertEquals(new Date("2020/12/24"), reservasEncontradas.get(1).getFechaInicio()),
                ()->assertEquals(50000, reservasEncontradas.get(1).getMontoFinal()),
                ()->assertEquals(new Date("2021/01/7"), reservasEncontradas.get(1).getFechaTermino()),
                ()->assertEquals(1, reservasEncontradas.get(1).getIdCliente()),
                ()->assertEquals(2, reservasEncontradas.get(1).getIdHabitacion())
        );
    }

    @Given("existe un cliente; id {int}, nombre {string}, rut {string}, fechaNaci {string}, telefono {string}, email {string}, contrasena {string}")
    public void existe_un_cliente_id_nombre_rut_fecha_naci_telefono_email_contrasena(Integer id, String nombre, String rut, String fechaNac, String telefono, String email, String contrasena) throws ParseException, ClienteAlreadyExistsException, ClienteNotFoundException {
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date fechaN = objSDF.parse(fechaNac);
        cliente = new Cliente(nombre, rut, fechaN, telefono, email, contrasena);
        cliente.setIdCliente(100);
        clienteService.agregarCliente(cliente);


    }
//    @Given("una lista de reservas")
//    public void una_lista_de_reservas() throws ParseException, HabitacionAlreadyExistException, HabitacionNotFoundException, ReservaNotFoundClienteOHabitacion, HabitacionAlreadyInUse {
//
//
//        habitacion = new Habitacion("H50", 300000, 4, 0);
//        habitacion2 = new Habitacion("H60", 150000, 3, 0);
//        habitacion.setIdHabitacion(1);
//        habitacion2.setIdHabitacion(2);
//        habitacionService.agregarHabitacion(habitacion);
//        habitacionService.agregarHabitacion(habitacion2);
//
//
//        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
//        Date fecIngreso = objSDF.parse("15-08-2020");
//        Date fecSalida = objSDF.parse("15-09-2020");
//
//        reserva = new Reserva(fecIngreso, 300000, fecSalida, cliente.getIdCliente(), habitacion.getIdHabitacion());
//        reservaService.agregarReserva(reserva);
//        reservas.add(reserva);
//
//        fecIngreso = objSDF.parse("15-09-2020");
//        fecSalida = objSDF.parse("15-10-2020");
//
//        reserva = new Reserva(fecIngreso, 300000, fecSalida, cliente.getIdCliente(), habitacion2.getIdHabitacion());
//        reservaService.agregarReserva(reserva);
//        reservas.add(reserva);
//    }



    @When("deseo filtrar reservas segun un cliente con id {int}")
    public void deseo_listar_las_reservas(Integer idCliente) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<List<Reserva>> entity = new HttpEntity<>(null,httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseReservas = testRestTemplate.exchange(createURLWithPort("/ReservaHotelera/reservas/"+idCliente), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<Reserva>>() {});
    }

    @Then("obtengo el estado {string} y una lista de reservas del cliente")
    public void obtengo_el_estado_y_una_lista_de_reservas_del_cliente_con_id(String status) {
        assertEquals(status.toUpperCase(), responseReservas.getStatusCode().name().toString());

        reservasEncontradas = responseReservas.getBody();
        assertNotNull(reservas);
        assertAll("reservas",
                ()->assertEquals(new Date("2020/12/23"), reservasEncontradas.get(0).getFechaInicio()),
                ()->assertEquals(40000, reservasEncontradas.get(0).getMontoFinal()),
                ()->assertEquals(new Date("2020/12/30"), reservasEncontradas.get(0).getFechaTermino()),
                ()->assertEquals(1, reservasEncontradas.get(0).getIdCliente()),
                ()->assertEquals(1, reservasEncontradas.get(0).getIdHabitacion()),
                ()->assertEquals(new Date("2020/12/24"), reservasEncontradas.get(1).getFechaInicio()),
                ()->assertEquals(50000, reservasEncontradas.get(1).getMontoFinal()),
                ()->assertEquals(new Date("2021/01/7"), reservasEncontradas.get(1).getFechaTermino()),
                ()->assertEquals(1, reservasEncontradas.get(1).getIdCliente()),
                ()->assertEquals(2, reservasEncontradas.get(1).getIdHabitacion())
        );
        /*
        habitacionesFiltradas = responseHabitaciones.getBody();
        assertNotNull(habitaciones);
        assertAll("habitaciones",
                () -> assertEquals("H1", habitacionesFiltradas.get(0).getNumeroHabitacion()),
                () -> assertEquals(15000, habitacionesFiltradas.get(0).getPrecioHabitacion()),
                () -> assertEquals("H2", habitacionesFiltradas.get(1).getNumeroHabitacion()),
                () -> assertEquals(17000, habitacionesFiltradas.get(1).getPrecioHabitacion()),
                () -> assertEquals("H4", habitacionesFiltradas.get(2).getNumeroHabitacion()),
                () -> assertEquals(19000, habitacionesFiltradas.get(2).getPrecioHabitacion())
        );
        */

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
