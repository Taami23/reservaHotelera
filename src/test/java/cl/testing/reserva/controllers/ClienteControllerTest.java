package cl.testing.reserva.controllers;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.service.ClienteService;
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
public class ClienteControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    private JacksonTester<List<Cliente>> jsonListaCliente;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setup(){
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void siSeInvocaGetAllClienteDebeRetornarTodosLosClientes() throws Exception {
        //Given
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        clientes.add(new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi"));
        clientes.add(new Cliente("Juanito Alcachofa", "198762543", new Date("1996/04/24"), "+56912345678", "correito@gmail.com","holi2"));

        given(clienteService.getAllClientes()).willReturn(clientes);
        //When
        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/clientes/")
                                            .accept(MediaType.APPLICATION_JSON))
                                            .andReturn()
                                            .getResponse();
        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonListaCliente.write(clientes).getJson());
    }

}
