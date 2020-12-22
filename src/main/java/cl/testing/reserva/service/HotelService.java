package cl.testing.reserva.service;

import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.repository.HotelRepository;
import exceptions.HotelNotFoundException;

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

    public List<Hotel> getAllHotel(){
        return hotelRepository.findAll();
    }
    
    public Hotel buscarHotel(int id) throws HotelNotFoundException {
		Hotel hotelEncontrado = hotelRepository.getOne(id);
		if(hotelEncontrado == null) {
			throw new HotelNotFoundException();
		}
		return hotelEncontrado;
	}	
	public void eliminarHotel(int id) throws HotelNotFoundException {
        Hotel hotelAEliminar = buscarHotel(id);
        if(hotelAEliminar == null) {
			throw new HotelNotFoundException();
		}
        hotelRepository.delete(hotelAEliminar);
    }
	public Hotel editarHotel(Hotel hotel) throws HotelNotFoundException {        
		if(buscarHotel(hotel.getIdHotel()) == null) {
          
			throw new HotelNotFoundException();

		}
		return hotelRepository.save(hotel);
		
	}
}
