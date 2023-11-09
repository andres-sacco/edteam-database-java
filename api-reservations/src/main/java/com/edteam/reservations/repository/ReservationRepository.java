package com.edteam.reservations.repository;

import com.edteam.reservations.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCreationDate(LocalDate creationDate);

    List<Reservation> findByCreationDateAndPassengersFirstName(LocalDate creationDate, String firstName);

    List<Reservation> findByCreationDateAndPassengersFirstNameAndPassengersLastName(LocalDate creationDate, String firstName, String lastName);
}