package cl.testing.reserva.controllers;


import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.service.HotelService;
import exceptions.HotelAlreadyExistsException;
import exceptions.HotelNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservaHoteles/hoteles")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{name}")
    public ResponseEntity<List<Hotel>> getAllHotelsByName(@PathVariable String name) throws HotelNotFoundException {
        try{
            List<Hotel> hotels = hotelService.getAllHotelsByName(name);
            return new ResponseEntity<List<Hotel>>(hotels, HttpStatus.OK);
        }catch (HotelNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<Hotel> agregarHotel(@RequestBody Hotel hotel) throws HotelNotFoundException, HotelAlreadyExistsException {
        try {
            hotelService.agregarHotel(hotel);
            return new ResponseEntity<>(hotel, HttpStatus.CREATED);
        } catch (HotelAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }


    }

    @GetMapping("/")
    public ResponseEntity<List<Hotel>> getAllHotel() throws HotelNotFoundException {
        try{
            List<Hotel> hotels = hotelService.getAllHotel();
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        }catch (HotelNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Hotel> delete(@PathVariable int id) throws HotelNotFoundException {
        try {
            hotelService.eliminarHotel(id);
        } catch (HotelNotFoundException e) {
            return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Hotel>(hotelService.buscarHotel(id), HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Hotel> update(@PathVariable int id, Hotel hotel) {
        try {
            hotel.setIdHotel(id);
            return new ResponseEntity<Hotel>(hotelService.editarHotel(hotel),HttpStatus.CREATED);
        } catch (HotelNotFoundException e) {
            return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
        }

    }
}
