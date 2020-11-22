package cl.testing.reserva.controllers;


import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.service.HotelService;
import exceptions.HotelNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservaHoteles/hoteles")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{name}")
    public ResponseEntity<List<Hotel>> getAllHotelsByName(@PathVariable String name) {
        List<Hotel> hotels = hotelService.getAllHotelsByName(name);
        return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Hotel>> getAllHotel() {
        List<Hotel> hotels = hotelService.getAllHotel();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
    
  	@GetMapping("/delete/{id}")
  	 public ResponseEntity<Hotel> delete(@PathVariable int id){
  		 try {
  			 hotelService.eliminarHotel(id);
  		 }catch (HotelNotFoundException e) {
  			 return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
  		 }
  		 return new ResponseEntity<Hotel>(HttpStatus.OK);
  	 }
  	
  	@PostMapping("/update/{id}")
  	public ResponseEntity<Hotel> update(@PathVariable int id, Hotel hotel){
  		try {
  			hotel.setIdHotel(id);
  			hotelService.editarHotel(hotel);
  		}catch (HotelNotFoundException e) {
 			 return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
 		}
 		 return new ResponseEntity<Hotel>(HttpStatus.OK);
  	}
}
