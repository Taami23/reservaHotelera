package cl.testing.reserva.repository;

import cl.testing.reserva.model.Hotel;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface HotelRepository extends JpaRepository <Hotel, Integer> {

}
