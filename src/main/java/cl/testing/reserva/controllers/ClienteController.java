package cl.testing.reserva.controllers;

import java.util.List;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/ReservaHotelera/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return new ResponseEntity<List<Cliente>>(clientes,HttpStatus.OK);
    }

}
