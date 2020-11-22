package cl.testing.reserva.controllers;

import java.util.List;

import cl.testing.reserva.model.Reserva;
import cl.testing.reserva.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/ReservaHotelera/reservas")
public class ReservaController {
	
	@Autowired
    private ReservaService reservaService;
	
	@GetMapping("/")
    public ResponseEntity<List<Reserva>> getAllReservas() {
        List<Reserva> reservas = reservaService.getAllReservas();
        return new ResponseEntity<List<Reserva>>(reservas,HttpStatus.OK);
    }

	
	
}
