package cl.testing.reserva.controllers;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.service.ClienteService;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import exceptions.ClienteAlreadyExistsException;
import exceptions.ClienteNotFoundException;
import exceptions.ClientesEmptyListException;
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
public class ClienteControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    private JacksonTester<List<Cliente>> jsonListaCliente;
    private JacksonTester<Cliente> jsonCliente;

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

    @Test
    void siSeInvocaGetAllClienteYLaListaEstaVaciaDebeRetornarExcepcionEmptyList() throws Exception {
        //Given
        doThrow(new ClientesEmptyListException()).when(clienteService).getAllClientes();

        //When
        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/clientes/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void siSeInvocaUpdateClienteYExisteDebeRetornarClienteActualizado() throws Exception {
        //Given
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        String nombre = "Tamara Valentina Salgado Villalobos";
        cliente.setIdCliente(1);
        cliente.setNombre(nombre);

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/clientes/update/1")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCliente.write(cliente).getJson()))
                .andReturn()
                .getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.equals(jsonCliente));
    }


    @Test
    void siSeInvocaUpdateClienteYNoExisteDebeRetornarExcepcionClienteNotFound() throws Exception {
        //Given
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        String nombre = "Tamara Valentina Salgado Villalobos";
        cliente.setIdCliente(1);
        cliente.setNombre(nombre);
        given(clienteService.updateCliente(ArgumentMatchers.any(Cliente.class))).willThrow(new ClienteNotFoundException());

        //When
        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/clientes/update/1")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCliente.write(cliente).getJson()))
                .andReturn()
                .getResponse();

        //Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void siDeseaAgregarUnClienteEntoncesLoPuedeAgregar() throws Exception{
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");

        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/clientes/add")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCliente.write(cliente).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }


    //ARREGLARLO

    @Test
    void siDeseaAgregarUnClientePeroEsteYaExiste() throws Exception, ClienteAlreadyExistsException {
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        doThrow(new ClienteAlreadyExistsException()).when(clienteService).agregarCliente(ArgumentMatchers.any(Cliente.class));

        MockHttpServletResponse response = mockMvc.perform(post("/ReservaHotelera/clientes/add")
                .contentType(MediaType.APPLICATION_JSON).content(jsonCliente.write(cliente).getJson()).accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void siDeseaEliminarUnClienteEntoncesSeBuscaPorIdPorSuIdYSeElimina() throws Exception, ClienteNotFoundException {
        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/clientes/delete/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        verify(clienteService, times(1)).eliminarCliente(1);
    }

    @Test
    void siDeseaEliminarUnClienteYNoExisteLanzaExcepcionClienteNotFound() throws Exception, ClienteNotFoundException {
        doThrow(new ClienteNotFoundException()).when(clienteService).eliminarCliente(1);

        MockHttpServletResponse response = mockMvc.perform(get("/ReservaHotelera/clientes/delete/1")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
