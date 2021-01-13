package cl.testing.reserva.tdd.controllers;

import cl.testing.reserva.controllers.ReservaController;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ReservaService;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;


@ExtendWith(MockitoExtension.class)
public class ReservaControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ReservaService reservaService;

    private JacksonTester<List<Reserva>> jsonListaReservas;
    private JacksonTester<Reserva> jsonReserva;

    @InjectMocks
    private ReservaController reservaController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();
    }

    @Test
    void siSeInvocaAgregarReservaEntoncesLaPuedeAgregar() throws Exception{
        Reserva reserva = new Reserva(new Date("2020/12/23"), 300000, new Date("2020/12/30"), 1,1);

        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/reservas/agregar")
                .contentType(MediaType.APPLICATION_JSON).content(jsonReserva.write(reserva).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
    @Test
    void siSeInvocaAgregarReservaPeroEstaHabitacionOClienteNoExistenLanzaExcepcion() throws  Exception{
        Reserva reserva = new Reserva(new Date("2020/12/23"), 300000, new Date("2020/12/30"), 1,1);
        doThrow(new ReservaNotFoundClienteOHabitacion()).when(reservaService).agregarReserva(ArgumentMatchers.any(Reserva.class));
        
        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/reservas/agregar")
                .contentType(MediaType.APPLICATION_JSON).content(jsonReserva.write(reserva).getJson()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void siSeInvocaAgregarReservaPeroHabitacionYaEstaEnUsoLanzaExcepcion() throws  Exception{
        Reserva reserva = new Reserva(new Date("2020/12/23"), 300000, new Date("2020/12/30"), 1,1);
        doThrow(new HabitacionAlreadyInUse()).when(reservaService).agregarReserva(ArgumentMatchers.any(Reserva.class));

        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/reservas/agregar")
                .contentType(MediaType.APPLICATION_JSON).content(jsonReserva.write(reserva).getJson()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void siDeseaEliminarUnaReservaEntoncesSeBuscaPorSuIdYSeElimina() throws Exception, ReservaNotFoundException {
        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/reservas/delete/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(reservaService, times(1)).eliminarReserva(1);
    }
    @Test
    void siDeseaEliminarUnaReservaYNoExisteLanzaExcepcionReservaNotFound() throws Exception, ReservaNotFoundException {
        doThrow(new ReservaNotFoundException()).when(reservaService).eliminarReserva(1);

        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/reservas/delete/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }



}
