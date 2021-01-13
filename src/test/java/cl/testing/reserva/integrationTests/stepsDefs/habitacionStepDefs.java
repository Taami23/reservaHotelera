package cl.testing.reserva.integrationTests.stepsDefs;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.service.HabitacionService;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class habitacionStepDefs {
    @LocalServerPort
    private int port;

    Habitacion habitacion;
    Habitacion agregada;

    private ResponseEntity<Habitacion> responseHabitacion;

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @After
    public  void tearDown(){
        habitacionRepository.deleteAll();
    }

    @Given("existe una nueva habitacion; nroHabitacion {string}, precio {int}, pisoHabitacion {int}, enUso {int}")
    public void existe_una_nueva_habitacion_id_nro_habitacion_precio_piso_habitacion_en_uso(String nroHabitacion, Integer precio, Integer pisoHabitacion, Integer enUso){
        System.out.println(nroHabitacion);
        habitacion = new Habitacion(nroHabitacion, precio,pisoHabitacion,enUso);
    }

    @When("deseo agregar una habitacion")
    public void deseo_agregar_una_habitacion_a_un_hotel() throws HabitacionNotFoundException, HabitacionAlreadyExistException {
        System.out.println(habitacion.getNumeroHabitacion());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<Habitacion> entity = new HttpEntity<>(habitacion,httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseHabitacion = testRestTemplate.exchange(createURLWithPort("/ReservaHotelera/habitaciones/agregar"), HttpMethod.POST,entity,Habitacion.class);
    }

    @Then("obtengo el estado {string} y la habitacion agregada tiene como numero de Habitacion {string}")
    public void otendo_el_estado_true_y_la_habitacion_agregada_tiene_como_numero_de_habitacion(String estado, String nroHabitacion) {
        assertEquals(estado.toUpperCase(), responseHabitacion.getStatusCode().name().toString());

        habitacion = responseHabitacion.getBody();

        assertNotNull(habitacion);
        assertEquals(nroHabitacion, habitacion.getNumeroHabitacion());

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
