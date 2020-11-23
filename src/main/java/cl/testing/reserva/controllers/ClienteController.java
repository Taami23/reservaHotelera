package cl.testing.reserva.controllers;

import java.util.List;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.service.ClienteService;
import exceptions.ClienteNotFoundException;
import exceptions.ClientesEmptyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ReservaHotelera/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public ResponseEntity<List<Cliente>> getAllClientes() throws ClientesEmptyList {
        try{
            List<Cliente> clientes = clienteService.getAllClientes();
            return new ResponseEntity<>(clientes,HttpStatus.OK);
        }catch (ClientesEmptyList e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = {"/update/{id}"})
    public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id") int id,@RequestBody Cliente cliente) {
        try {
            cliente.setIdCliente(id);
            clienteService.updateCliente(cliente);
            return new ResponseEntity<>(clienteService.updateCliente(cliente),HttpStatus.CREATED);
        }catch (ClienteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
