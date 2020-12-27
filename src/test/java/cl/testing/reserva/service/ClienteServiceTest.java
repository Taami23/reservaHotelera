package cl.testing.reserva.service;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.repository.ClienteRepository;
import exceptions.ClienteAlreadyExistsException;
import exceptions.ClienteNotFoundException;
import exceptions.ClientesEmptyListException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;


    //    private String nombre;
    //    private String rut;
    //    private Date fechaNacimiento;
    //    private String telefono;
    //    private String correoElectrinico;
    //    private String contrasena;

    @Test
    void siSeInvocaGetAllClientesDebeRetornarTodosLosClientes() throws ClientesEmptyListException {
        //Arrange
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        List<Cliente> resultado;
        clientes.add(new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi"));
        clientes.add(new Cliente("Juanito Alcachofa", "198762543", new Date("1996/04/24"), "+56912345678", "correito@gmail.com","holi2"));
        when(clienteRepository.findAll()).thenReturn(clientes);

        //Act
        resultado = clienteService.getAllClientes();

        //Assert
        assertNotNull(resultado);
        assertAll("resultado",
                () -> assertEquals("Tamara Salgado",resultado.get(0).getNombre()),
                () -> assertEquals("19415903k", resultado.get(0).getRut()),
                () -> assertEquals(new Date("1997/01/19"), resultado.get(0).getFechaNacimiento()),
                () -> assertEquals("+56975845747", resultado.get(0).getTelefono()),
                () -> assertEquals("tvale.sv@gmail.com", resultado.get(0).getCorreoElectrinico()),
                () -> assertEquals("holi", resultado.get(0).getContrasena()),
                () -> assertEquals("Juanito Alcachofa",resultado.get(1).getNombre()),
                () -> assertEquals("198762543", resultado.get(1).getRut()),
                () -> assertEquals(new Date("1996/04/24"), resultado.get(1).getFechaNacimiento()),
                () -> assertEquals("+56912345678", resultado.get(1).getTelefono()),
                () -> assertEquals("correito@gmail.com", resultado.get(1).getCorreoElectrinico()),
                () -> assertEquals("holi2", resultado.get(1).getContrasena())
        );
    }

    @Test
    void siSeInvocaGetAllClientesYNoExiteNingunClienteRetornaLaExcepcionClientesEmptyList(){
//        //Arrange
//        when(clienteRepository.findAll()).thenReturn(null);

        //Act + Assert
        assertThrows(ClientesEmptyListException.class, () -> clienteService.getAllClientes());
    }

    @Test
    void siDeseaEditarLosDatosDeUnClienteYNoExisteEntoncesSeArrojaLaExcepcionClienteNotFound() {
        // Arrange
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        cliente.setIdCliente(1);
        when(clienteRepository.getOne(1)).thenReturn(null);

        // Act + Assert
        assertThrows(ClienteNotFoundException.class, () -> clienteService.updateCliente(cliente));
    }

    @Test
    void siDeseaEditarLosDatosDeUnClienteYLoExisteEntoncesDevuelveElHotelActualizado() throws ClienteNotFoundException {
        // Arrange
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");

        cliente.setIdCliente(1);
        when(clienteRepository.getOne(1)).thenReturn(cliente);
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        cliente.setNombre("Tamara Valentina Salgado");

        // Act
        Cliente clienteResultado = clienteService.updateCliente(cliente);

        //Assert
        assertNotNull(clienteResultado);
        assertAll("clienteResultado",
                () -> assertEquals("Tamara Valentina Salgado",clienteResultado.getNombre()),
                () -> assertEquals("19415903k", clienteResultado.getRut()),
                () -> assertEquals(new Date("1997/01/19"), clienteResultado.getFechaNacimiento()),
                () -> assertEquals("+56975845747", clienteResultado.getTelefono()),
                () -> assertEquals("tvale.sv@gmail.com", clienteResultado.getCorreoElectrinico()),
                () -> assertEquals("holi", clienteResultado.getContrasena())
                );
    }

    @Test
    void siDeseaAgregarUnClienteEntoncesLoPuedeAgregar() throws ClienteAlreadyExistsException, ClienteNotFoundException {
        //Arrange
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        Cliente clienteExistente = new Cliente("Tamara Valentina Salgado", "19415903k", new Date("1997/01/19"), "+56977845747", "tvale.sv1@gmail.com","holi");
        clientes.add(clienteExistente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        clienteService.agregarCliente(cliente);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void siDeseaAgregarUnClienteYExisteEntoncesLanzaExcepcionClienteAlreadyExists() throws ClienteNotFoundException {
        //Arrange
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        when(clienteService.getClienteByCorreo(cliente.getCorreoElectrinico())).thenReturn(null);
        Cliente clienteExistente = new Cliente("Tamara Valentina Salgado", "19415903k", new Date("1997/01/19"), "+56977845747", "tvale.sv@gmail.com","holi");
        clientes.add(clienteExistente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        //Act + Assert
        assertThrows(ClienteAlreadyExistsException.class, ()-> clienteService.agregarCliente(cliente));
    }

    @Test
    void siDeseaEliminarUnClienteSeBuscaYSeElimina() throws  ClienteNotFoundException {
        // Arrange
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        cliente.setIdCliente(1);
        when(clienteRepository.getOne(1)).thenReturn(cliente);

        // Act
        clienteService.eliminarCliente(cliente.getIdCliente());

        //Assert
        verify(clienteRepository,times(1)).delete(cliente);
    }

    @Test
    void siDeseaEliminarUnClienteSeBuscaYNoExiste() throws  ClienteNotFoundException {
        // Arrange
        Cliente cliente = new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi");
        cliente.setIdCliente(1);
        when(clienteRepository.getOne(1)).thenReturn(null);

        //Act + Assert
        assertThrows(ClienteNotFoundException.class,() -> clienteService.eliminarCliente(1) );
    }


}
