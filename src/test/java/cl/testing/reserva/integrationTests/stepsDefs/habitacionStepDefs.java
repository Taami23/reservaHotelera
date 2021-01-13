package cl.testing.reserva.integrationTests.stepsDefs;

import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.repository.HabitacionRepository;
import cl.testing.reserva.service.HabitacionService;
import exceptions.HabitacionAlreadyExistException;
import exceptions.HabitacionEmptyListException;
import exceptions.HabitacionNotFoundException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class habitacionStepDefs {
    @LocalServerPort
    private int port;

    Habitacion habitacion;
    List<Habitacion> habitaciones = new ArrayList<>();
    List<Habitacion> habitacionesFiltradas = new ArrayList<>();
    Habitacion agregada;

    private ResponseEntity<Habitacion> responseHabitacion;
    private ResponseEntity<List<Habitacion>> responseHabitaciones;

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Given("existe una nueva habitacion; nroHabitacion {string}, precio {int}, pisoHabitacion {int}, enUso {int}")
    public void existe_una_nueva_habitacion_id_nro_habitacion_precio_piso_habitacion_en_uso(String nroHabitacion, Integer precio, Integer pisoHabitacion, Integer enUso){
//        System.out.println("Size "+habitacionRepository.findAll().size());
        habitacion = new Habitacion(nroHabitacion, precio,pisoHabitacion,enUso);
    }

    @When("deseo agregar una habitacion")
    public void deseo_agregar_una_habitacion_a_un_hotel() throws HabitacionNotFoundException, HabitacionAlreadyExistException {
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
//        List<Habitacion> list = habitacionRepository.findAll();
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("Habitacion"+list.get(i).getNumeroHabitacion());
//        }
        System.out.println(habitacion.getIdHabitacion());
        assertNotNull(habitacion);
        assertEquals(nroHabitacion, habitacion.getNumeroHabitacion());
        habitacionRepository.delete(habitacion);

    }

    @Given("una lista de habitaciones")
    public void una_lista_de_habitaciones() throws HabitacionNotFoundException, HabitacionAlreadyExistException {
        Habitacion habitacion1 = new Habitacion("H1", 15000, 1,0);
        Habitacion habitacion2 = new Habitacion("H2", 17000,1,0);
        Habitacion habitacion3 = new Habitacion("H3", 95000,1,0);
        Habitacion habitacion4 = new Habitacion("H4", 19000,2,0);
        habitaciones.add(habitacion1);
        habitaciones.add(habitacion2);
        habitaciones.add(habitacion3);
        habitaciones.add(habitacion4);
        habitacionRepository.save(habitacion1);
        habitacionRepository.save(habitacion2);
        habitacionRepository.save(habitacion3);
        habitacionRepository.save(habitacion4);
    }

    @When("deseo filtrar habitaciones segun su precio entre {int} y {int}")
    public void deseo_filtrar_habitaciones_segun_su_precio_entre_y(Integer precio1, Integer precio2) throws HabitacionEmptyListException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        HttpEntity<List<Habitacion>> entity = new HttpEntity<>(null,httpHeaders);

        testRestTemplate = new TestRestTemplate();
        responseHabitaciones = testRestTemplate.exchange(createURLWithPort("/ReservaHotelera/habitaciones/search?price1=" + precio1 + "&price2=" + precio2),
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<Habitacion>>(){});
    }
    @Then("obtengo el estado {string} y una lista de habitaciones dentro del rango")
    public void obtengo_el_estado_y_una_lista_de_habitaciones_dentro_del_rango(String estado) {
        assertEquals(estado.toUpperCase(), responseHabitaciones.getStatusCode().name().toString());
//        List<Habitacion> list = habitacionRepository.findAll();
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println("Habitacion"+list.get(i).getNumeroHabitacion());
//        }
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

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

