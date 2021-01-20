package cl.testing.reserva.tdd.controllers;

import cl.testing.reserva.controllers.ReservaController;
import cl.testing.reserva.model.Habitacion;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ReservaService;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
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
    void siSeInvocaGetAllReservasYExistenDebeRetornarListaConReservas() throws Exception{
        //Given
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();
        Reserva r1 = new Reserva(new Date("2020/12/23"), 40000, new Date("2020/12/30"), 1, 1);
        Reserva r2 = new Reserva(new Date("2021/12/27"), 50000, new Date("2021/01/7"), 1, 2);
        Reserva r3 = new Reserva(new Date("2021/01/08"), 100000, new Date("2021/01/20"), 1, 3);
        reservas.add(r1);
        reservas.add(r2);
        reservas.add(r3);


        given(reservaService.getAllReservas(1)).willReturn(reservas);

        //When
        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/reservas/1")
                            .accept(MediaType.APPLICATION_JSON))
                            .andReturn()
                            .getResponse();
        System.out.println("Response: "+ response.getContentAsString());
        System.out.println("Json: "+ jsonListaReservas.write(reservas).getJson());

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListaReservas.write(reservas).getJson());
    }

    @Test
    void siSeInvocaGetAllReservasyLaListaEstaVaciaArrojaExceptionReservaEmptyList() throws Exception{
        doThrow(new ReservaEmptyListException()).when(reservaService).getAllReservas(1);

        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/reservas/1")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void siDeseaListarReservasEntreFechasYExistenReservasEnEseRangoRetornaUnaLista() throws Exception {
        String fecha1 = "2020/12/23";
        String fecha2 ="2021/01/20";
        //Given
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();
        Reserva r1 = new Reserva(new Date("2020/12/23"), 40000, new Date("2020/12/30"), 1, 1);
        Reserva r2 = new Reserva(new Date("2020/12/24"), 50000, new Date("2021/01/7"), 1, 2);
        Reserva r3 = new Reserva(new Date("2021/01/08"), 100000, new Date("2021/01/20"), 1, 3);
        reservas.add(r1);
        reservas.add(r2);
        reservas.add(r3);


        given(reservaService.searchByDates(fecha1, fecha2, 1)).willReturn(reservas);

        //When
        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/reservas/search/1?fecha1="+fecha1+"&fecha2="+fecha2)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        System.out.println("Response: "+ response.getContentAsString());
        System.out.println("Json: "+ jsonListaReservas.write(reservas).getJson());

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListaReservas.write(reservas).getJson());
    }

    @Test
    void siDeseaListarReservasEntreFechasYNoExistenReservasArrojaException() throws Exception {
        String fecha1 = "2020/12/23";
        String fecha2 ="2021/01/20";
        doThrow(new ReservaEmptyListException()).when(reservaService).searchByDates(fecha1, fecha2, 1);

        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/reservas/search/1?fecha1="+fecha1+"&fecha2="+fecha2)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    @Test
    void siDeseaEditarLosDatosDeUnaReservaYLoEncuentraEntoncesDevuelveLaReservaActualizada() throws Exception {
        Reserva reservaBuscada = new Reserva(new Date("2021/01/08"), 100000, new Date("2021/01/20"), 3, 3);
        reservaBuscada.setIdReserva(1);
        int nuevoPrecio = 90000;

        given(reservaService.editarReserva(any(reservaBuscada.getClass()))).willReturn(reservaBuscada);
        reservaBuscada.setMontoFinal(nuevoPrecio);

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/reservas/update")
                .contentType(MediaType.APPLICATION_JSON).content(jsonReserva.write(reservaBuscada).getJson()))
                .andReturn()
                .getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.equals(jsonReserva));
    }

    @Test
    void SiDeseaEditarLosDatosDeUnaReservaYNoLaEncuentraArrojaException() throws Exception{
        Reserva reservaBuscada = new Reserva(new Date("2021/01/08"), 100000, new Date("2021/01/20"), 3, 3);
        reservaBuscada.setIdReserva(1);

        doThrow(new ReservaNotFoundException()).when(reservaService).editarReserva(ArgumentMatchers.any(Reserva.class));

        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/reservas/update")
                .contentType(MediaType.APPLICATION_JSON).content(jsonReserva.write(reservaBuscada).getJson()))
                .andReturn()
                .getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
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
