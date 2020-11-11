package cl.testing.reserva.service;

import java.util.ArrayList;
import java.util.List;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() {
        return (List<Cliente>) clienteRepository.findAll();
    }


}
