package cl.testing.reserva.service;

import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public  List<Hotel> getAllHotelsByName(String name) {
        ArrayList<Hotel> hotelesConName = new ArrayList<>();
        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll();

        for (Hotel hotel : hotels){
            if (hotel.getNombre().toLowerCase().indexOf(name.toLowerCase()) !=-1){
                hotelesConName.add(hotel);
            }
        }
        return hotelesConName;
    }
}
