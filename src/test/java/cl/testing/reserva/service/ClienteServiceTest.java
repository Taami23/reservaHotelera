package cl.testing.reserva.service;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
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
    void siSeInvocaGetAllClientesDebeRetornarTodosLosClientes(){
        //Arrange
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        List<Cliente> resultado;
        clientes.add(new Cliente("Tamara Salgado", "19415903k", new Date("1997/01/19"), "+56975845747", "tvale.sv@gmail.com","holi"));
        clientes.add(new Cliente("Juanito Alcachofa", "198762543", new Date("1996/04/24"), "+56912345678", "correito@gmail.com","holi2"));
        when(clienteRepository.findAll()).thenReturn(clientes);

        //Act
        resultado = clienteService.getAllClientes();

        //Asseert
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
}
