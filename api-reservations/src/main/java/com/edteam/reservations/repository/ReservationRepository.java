package com.edteam.reservations.repository;

import com.edteam.reservations.model.Reservation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    String QUERY_FIND_BY_CREATION_DATE = "SELECT r FROM Reservation r WHERE r.creationDate = :creationDate";
    String QUERY_FIND_BY_CREATION_DATE_AND_FIRSTNAME = "SELECT r FROM Reservation r JOIN r.passengers p WHERE r.creationDate = :creationDate AND p.firstName = :firstName";
    String QUERY_FIND_BY_CREATION_DATE_AND_FIRSTNAME_AND_LASTNAME = "SELECT r FROM Reservation r JOIN r.passengers p WHERE r.creationDate = :creationDate AND p.firstName = :firstName AND p.lastName = :lastName";

    @Query(value = QUERY_FIND_BY_CREATION_DATE)
    List<Reservation> findByCreationDate(@Param("creationDate") LocalDate creationDate);

    @Query(QUERY_FIND_BY_CREATION_DATE_AND_FIRSTNAME)
    List<Reservation> findByCreationDateAndPassengersFirstName(@Param("creationDate") LocalDate creationDate,
            @Param("firstName") String firstName);

    @Query(QUERY_FIND_BY_CREATION_DATE_AND_FIRSTNAME_AND_LASTNAME)
    List<Reservation> findByCreationDateAndPassengersFirstNameAndPassengersLastName(
            @Param("creationDate") LocalDate creationDate, @Param("firstName") String firstName,
            @Param("lastName") String lastName);

    @Transactional(readOnly = true, timeout = 30)
    List<Reservation> findAll(Specification<Reservation> specification);
}