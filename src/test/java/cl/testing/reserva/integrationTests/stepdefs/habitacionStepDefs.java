package cl.testing.reserva.integrationTests.stepdefs;

import cl.testing.reserva.controllers.HabitacionController;
import cl.testing.reserva.integrationTests.CucumberSpringContextConfiguration;
import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.service.HabitacionService;
import cl.testing.reserva.service.HotelService;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;
import exceptions.HotelAlreadyExistsException;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class habitacionStepDefs extends CucumberSpringContextConfiguration {
    @LocalServerPort
    private int port;

    Habitacion habitacion;
    Habitacion agregada;
    List<Habitacion> listaHabitaciones;
    private ResponseEntity<Habitacion> responseHabitacion;
    private ResponseEntity<List<Habitacion>> responseHabitaciones;

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

//    @Autowired
//    HotelService hotelService;

    boolean estado;

    @After
    public void tearDown() {
        habitacionRepository.deleteAll();
    }

//    private Integer idHabitacion;
//    private String numeroHabitacion;
//    private Integer precioHabitacion;
//    private Integer pisoHabitacion;
//    private Integer enUso;

    @Given("existe una nueva habitacion; nroHabitacion {string}, precio {int}, pisoHabitacion {int}, enUso {int}")
    public void existe_una_nueva_habitacion_id_nro_habitacion_precio_piso_habitacion_en_uso(String nroHabitacion, Integer precio, Integer pisoHabitacion, Integer enUso) {
        habitacion = new Habitacion(nroHabitacion, precio, pisoHabitacion, enUso);
    }

    @When("deseo agregar una habitacion")
    public void deseo_agregar_una_habitacion_a_un_hotel() throws HabitacionNotFoundException, HabitacionAlreadyExistException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Habitacion> entity = new HttpEntity<>(habitacion, httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseHabitacion = testRestTemplate.exchange(createURLWithPort("/ReservaHotelera/habitaciones/agregar"), HttpMethod.POST, entity, Habitacion.class);
    }

    @Then("obtengo el estado {string} y la habitacion agregada tiene como numero de Habitacion {string}")
    public void otendo_el_estado_true_y_la_habitacion_agregada_tiene_como_numero_de_habitacion(String estado, String nroHabitacion) {
        assertEquals(estado.toUpperCase(), responseHabitacion.getStatusCode().name().toString());

        habitacion = responseHabitacion.getBody();

        assertNotNull(habitacion);
        assertEquals(nroHabitacion, habitacion.getNumeroHabitacion());

    }

    @Given("se tiene una lista de habitaciones")
    public void se_tiene_una_lista_de_habitaciones() {
        Habitacion habitacion1 = new Habitacion("H1", 10000, 1, 0);
        Habitacion habitacion2 = new Habitacion("H2", 13000, 1, 0);
        Habitacion habitacion3 = new Habitacion("H3", 9000, 2, 0);
        Habitacion habitacion4 = new Habitacion("H4", 23000, 2, 0);
        listaHabitaciones.add(habitacion1);
        listaHabitaciones.add(habitacion2);
        listaHabitaciones.add(habitacion3);
        listaHabitaciones.add(habitacion4);
    }

    @When("solicito filtrar las habitaciones por precio entre {int} y {int} pesos")
    public void solicito_filtrar_las_habitaciones_por_precio_entre_y_pesos(Integer int1, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("obtengo el estado {string} y la lista con las habitaciones dentro del rango")
    public void obtengo_el_estado_y_la_lista_con_las_habitaciones_dentro_del_rango(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
