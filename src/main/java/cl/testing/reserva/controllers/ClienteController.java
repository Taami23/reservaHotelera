package cl.testing.reserva.controllers;

import java.sql.SQLOutput;
import java.util.List;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.service.ClienteService;
import exceptions.ClienteAlreadyExistsException;
import exceptions.ClienteNotFoundException;
import exceptions.ClientesEmptyListException;
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
    public ResponseEntity<List<Cliente>> getAllClientes() throws ClientesEmptyListException {
        try{
            List<Cliente> clientes = clienteService.getAllClientes();
            return new ResponseEntity<>(clientes,HttpStatus.OK);
        }catch (ClientesEmptyListException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = {"/update/{id}"})
    public ResponseEntity<Cliente> updateCliente(@PathVariable(value = "id") int id,@RequestBody Cliente cliente) {
        try {
            cliente.setIdCliente(id);
            clienteService.updateCliente(cliente);
            System.out.println(cliente.toString());
            return new ResponseEntity<>(clienteService.updateCliente(cliente),HttpStatus.CREATED);
        }catch (ClienteNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Cliente> agregarHotel(@RequestBody Cliente cliente) throws ClienteNotFoundException, ClienteAlreadyExistsException {
        try {
            clienteService.agregarCliente(cliente);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (ClienteAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Cliente> delete(@PathVariable int id) throws ClienteNotFoundException {
        try {
            clienteService.eliminarCliente(id);
            return new ResponseEntity<Cliente>(HttpStatus.OK);
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<Cliente>(HttpStatus.NOT_FOUND);
        }
    }


}
