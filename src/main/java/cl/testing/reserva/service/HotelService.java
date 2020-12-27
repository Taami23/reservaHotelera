package cl.testing.reserva.service;

import cl.testing.reserva.model.Hotel;
import cl.testing.reserva.repository.HotelRepository;
import exceptions.HotelAlreadyExistsException;
import exceptions.HotelNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public  List<Hotel> getAllHotelsByName(String name) throws HotelNotFoundException {
        ArrayList<Hotel> hotelesConName = new ArrayList<>();
        List<Hotel> hotels = (List<Hotel>) hotelRepository.findAll();
        for (Hotel hotel : hotels){
            if (hotel.getNombre().toLowerCase().indexOf(name.toLowerCase()) !=-1){
                hotelesConName.add(hotel);
            }
        }

        if(hotelesConName.isEmpty()){
            throw new HotelNotFoundException();
        }
        return hotelesConName;
    }

    public List<Hotel> getAllHotel() throws HotelNotFoundException {
        if (hotelRepository.findAll().isEmpty()){
            throw new HotelNotFoundException();
        }
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

    public Hotel getHotelByCorreo(String correo) throws HotelNotFoundException {
        Hotel hotelConCorreo = null;
        List<Hotel> hotels = hotelRepository.findAll();
        if (!hotels.isEmpty()){
            for (int i = 0; i < hotels.size(); i++){
                if (hotels.get(i).getContactoCorreo().equalsIgnoreCase(correo)){
                    return hotels.get(i);
                }
            }
        }
        return hotelConCorreo;
    }

	public void agregarHotel(Hotel hotel) throws HotelAlreadyExistsException, HotelNotFoundException {
        if (getHotelByCorreo(hotel.getContactoCorreo()) == null){
            hotelRepository.save(hotel);
        }else {
            throw new HotelAlreadyExistsException();
        }
    }
}
