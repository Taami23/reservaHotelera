package cl.testing.reserva.integrationTests.stepsDefs;

import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.repository.ReservaRepository;
import cl.testing.reserva.service.ReservaService;
import exceptions.ReservaAlreadyExistsException;
import exceptions.ReservaNotFoundException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class reservaStepDefs {
    @LocalServerPort
    private int port;

    Reserva reserva;
    List<Reserva> reservas = new ArrayList<Reserva>();
    Reserva reservaEditada;

    private ResponseEntity<Reserva> responseReserva;
    private ResponseEntity<List<Reserva>> responseReservas;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Given("existe una reserva; fechaInicio {string}, fechaTermino {string}, montoFinal {int}, idCliente {int}, idHabitacion {int}")
    public void existe_una_reserva_fecha_inicio_fecha_termino_monto_final_id_cliente_id_habitacion(String fechaInicio, String fechaTermino, Integer montoFinal, Integer idCliente, Integer idHabitacion) throws ParseException {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        String sFechaInicioDate= fechaInicio;
        Date fechaInicioDate = new SimpleDateFormat("yyyy/MM/dd").parse(sFechaInicioDate);

        String sFechaTerminoDate= fechaTermino;
        Date fechaTerminoDate = new SimpleDateFormat("yyyy/MM/dd").parse(sFechaTerminoDate);

        //reserva = new Reserva(new Date(fechaInicio),new Date(fechaTermino),montoFinal,idCliente,idHabitacion);
        reserva = new Reserva(fechaInicioDate,montoFinal,fechaTerminoDate,idCliente,idHabitacion);
        reserva.setIdReserva(1);
    }

    @When("deseo editar la fecha de termino a {string} de la reserva")
    public void deseo_editar_la_fecha_de_termino_a_de_la_reserva(String fechaTerminoNueva) throws ParseException {
        // Write code here that turns the phrase above into concrete actions

        String sFechaTerminoNueva = fechaTerminoNueva;
        Date fechaTerminoDateNueva = new SimpleDateFormat("yyyy/MM/dd").parse(sFechaTerminoNueva);
        reserva.setFechaTermino(fechaTerminoDateNueva);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Reserva> entity = new HttpEntity<>(reserva,httpHeaders);
        testRestTemplate = new TestRestTemplate();
        responseReserva = testRestTemplate.exchange(createURLWithPort("/ReservaHotelera/reservas/update"),
                HttpMethod.POST,
                entity,
                Reserva.class);
    }

    @Then("me aseguro que los campos tengan datos correctos y obtengo el estado {string}")
    public void me_aseguro_que_los_campos_tengan_datos_correctos_y_obtengo_el_estado(String estado) {
        // Write code here that turns the phrase above into concrete actions
        reserva = responseReserva.getBody();
        assertNotNull(reserva);
        System.out.println(reserva.getIdReserva());
        System.out.println(reserva.getFechaInicio());
        assertEquals(1,reserva.getIdReserva());
        assertEquals(new Date("2020/01/01"), reservaEditada.getFechaInicio());
        assertEquals(new Date("2020/03/03"), reservaEditada.getFechaTermino());
        assertEquals(20000, reservaEditada.getMontoFinal());
        assertEquals(1, reservaEditada.getIdCliente());
        assertEquals(1, reservaEditada.getIdHabitacion());
        assertEquals(estado.toUpperCase(), responseReserva.getStatusCode().name().toString());
        System.out.println(reservaEditada.getIdReserva());
        //reservaRepository.delete(reserva);
    }


    private String createURLWithPort(String s) {
        return "http://localhost:" +port+s;
    }

}
