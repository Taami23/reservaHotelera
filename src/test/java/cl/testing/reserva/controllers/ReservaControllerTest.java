package cl.testing.reserva.controllers;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ClienteService;
import cl.testing.reserva.service.ReservaService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ReservaControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ReservaService reservaService;

    private JacksonTester<List<Reserva>> jsonListaReservas;

    @InjectMocks
    private ReservaController reservaController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(reservaController).build();
    }

    @Test
    void siSeInvocaGetAllClienteDebeRetornarTodosLosClientes() throws Exception {
        //Given
    	ArrayList<Reserva> reservas = new ArrayList<Reserva>();
    	List<Reserva> resultado;
        reservas.add(new Reserva(new Date("2020/11/09"), 297000, new Date("2020/11/20"), 1, 1));
        reservas.add(new Reserva(new Date("2020/11/05"), 270000, new Date("2020/11/15"), 2, 2));
        
        
        given(reservaService.getAllReservas()).willReturn(reservas);
        //When
        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/reservas/")
                                            .accept(MediaType.APPLICATION_JSON))
                                            .andReturn()
                                            .getResponse();
        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListaReservas.write(reservas).getJson());
    }

}
