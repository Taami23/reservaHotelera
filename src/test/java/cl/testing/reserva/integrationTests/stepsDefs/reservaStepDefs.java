package cl.testing.reserva.integrationTests.stepsDefs;


import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.repository.ReservaRepository;
import cl.testing.reserva.service.ClienteService;
import cl.testing.reserva.service.ReservaService;
import cl.testing.reserva.model.Reserva;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class reservaStepDefs {
    @LocalServerPort
    private int port;

    Cliente cliente;
    Habitacion habitacion;
    Habitacion habitacion2;
    Reserva reserva;
    Reserva reservaEditada;
    List<Reserva> reservas = new ArrayList<>();
    List<Reserva> reservasEncontradas = new ArrayList<>();

    private ResponseEntity<Reserva> responseReserva;
    private ResponseEntity<List<Reserva>> responseReservas;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private HabitacionRepository habitacionRepository;

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
        reservaRepository.deleteAll();
    }

    @Given("existe un cliente; id {int}, nombre {string}, rut {string}, fechaNaci {string}, telefono {string}, email {string}, contrasena {string}")
    public void existe_un_cliente_id_nombre_rut_fecha_naci_telefono_email_contrasena(Integer id, String nombre, String rut, String fechaNac, String telefono, String email, String contrasena) throws ParseException, ClienteAlreadyExistsException, ClienteNotFoundException {
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date fechaN = objSDF.parse(fechaNac);
        cliente = new Cliente(nombre, rut, fechaN, telefono, email, contrasena);
        cliente.setIdCliente(100);
        clienteService.agregarCliente(cliente);


    }



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
        reservaRepository.deleteAll();
    }

    @Given("existe una reserva; fechaInicio {string}, fechaTermino {string}, montoFinal {int}, idCliente {int}, idHabitacion {int}")
    public void existe_una_reserva_fecha_inicio_fecha_termino_monto_final_id_cliente_id_habitacion(String fechaInicio, String fechaTermino, Integer montoFinal, Integer idCliente, Integer idHabitacion) throws ParseException {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        String sFechaInicioDate= fechaInicio;
        Date fechaInicioDate = new SimpleDateFormat("yyyy/MM/dd").parse(sFechaInicioDate);

        String sFechaTerminoDate= fechaTermino;
        Date fechaTerminoDate = new SimpleDateFormat("yyyy/MM/dd").parse(sFechaTerminoDate);

        reserva = new Reserva(fechaInicioDate,montoFinal,fechaTerminoDate,idCliente,idHabitacion);
        reservaRepository.save(reserva);

    }

    @When("deseo editar la fecha de termino a {string} de la reserva")
    public void deseo_editar_la_fecha_de_termino_a_de_la_reserva(String fechaTerminoNueva) throws ParseException {
        // Write code here that turns the phrase above into concrete actions

        String sFechaTerminoNueva = fechaTerminoNueva;
        Date fechaTerminoDateNueva = new SimpleDateFormat("yyyy/MM/dd").parse(sFechaTerminoNueva);
        reserva.setFechaTermino(fechaTerminoDateNueva);
        reserva.setIdReserva(13);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Reserva> entity = new HttpEntity<>(reserva,httpHeaders);
        testRestTemplate = new TestRestTemplate();
        responseReserva = testRestTemplate.exchange(createURLWithPort("/ReservaHotelera" +
                        "/reservas/update"),
                HttpMethod.POST,
                entity,
                Reserva.class);
    }

    @Then("me aseguro que los campos tengan datos correctos y obtengo el estado {string}")
    public void me_aseguro_que_los_campos_tengan_datos_correctos_y_obtengo_el_estado(String estado) {
        // Write code here that turns the phrase above into concrete actions
        reserva = responseReserva.getBody();
        assertNotNull(reserva);
        assertEquals(13,reserva.getIdReserva());
        assertEquals(new Date("2020/01/01"), reserva.getFechaInicio());
        assertEquals(new Date("2020/03/03"), reserva.getFechaTermino());
        assertEquals(20000, reserva.getMontoFinal());
        assertEquals(1, reserva.getIdCliente());
        assertEquals(1, reserva.getIdHabitacion());
        assertEquals(estado.toUpperCase(), responseReserva.getStatusCode().name().toString());
        //reservaRepository.delete(reserva);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
