package cl.testing.reserva.service;

import java.util.ArrayList;
import java.util.List;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.repository.ClienteRepository;
import exceptions.ClienteNotFoundException;
import exceptions.ClientesEmptyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() throws ClientesEmptyList {
        if(clienteRepository.findAll() == null) {
            throw new ClientesEmptyList();
        }
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente getById(int id) throws ClienteNotFoundException {
        if(clienteRepository.getOne(id) == null) {
            throw new ClienteNotFoundException();
        }else{
            return clienteRepository.getOne(id);
        }
    }

    public Cliente updateCliente(Cliente cliente) throws ClienteNotFoundException {
        getById(cliente.getIdCliente());
        return clienteRepository.save(cliente);
    }


}
