package cl.testing.reserva.repository;

import cl.testing.reserva.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository <Hotel, Integer> {

}
