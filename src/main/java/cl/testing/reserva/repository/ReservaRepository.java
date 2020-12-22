package cl.testing.reserva.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.testing.reserva.model.Reserva;

@Repository
@Profile("jpa")
public interface ReservaRepository extends JpaRepository <Reserva, Integer> {

}
