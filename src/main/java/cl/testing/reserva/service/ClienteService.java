package cl.testing.reserva.service;

import java.util.List;

import cl.testing.reserva.model.Cliente;
import cl.testing.reserva.repository.ClienteRepository;
import exceptions.ClienteAlreadyExistsException;
import exceptions.ClienteNotFoundException;
import exceptions.ClientesEmptyListException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAllClientes() throws ClientesEmptyListException {
        if(clienteRepository.findAll().isEmpty()) {
            throw new ClientesEmptyListException();
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

    public Cliente getClienteByCorreo(String correo) throws ClienteNotFoundException {
        Cliente cliente = null;
        List<Cliente> clientes = clienteRepository.findAll();
        if (!clientes.isEmpty()){
            for (int i = 0; i < clientes.size(); i++){
                if (clientes.get(i).getCorreoElectrinico().equalsIgnoreCase(correo)){
                    return clientes.get(i);
                }
            }
        }
        return cliente;
    }

    public void agregarCliente(Cliente cliente) throws ClienteAlreadyExistsException, ClienteNotFoundException {
        if (getClienteByCorreo(cliente.getCorreoElectrinico()) == null){
            clienteRepository.save(cliente);
        }else {
            throw new ClienteAlreadyExistsException();
        }
    }

    public void eliminarCliente(int id) throws ClienteNotFoundException {
        Cliente cliente = getById(id);
        if(cliente == null) {
            throw new ClienteNotFoundException();
        }
        clienteRepository.delete(cliente);
    }

}
