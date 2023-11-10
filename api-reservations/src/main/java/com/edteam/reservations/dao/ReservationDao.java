package com.edteam.reservations.dao;

import com.edteam.reservations.dto.SearchReservationCriteriaDTO;
import com.edteam.reservations.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationDao {

    public List<Reservation> findAll(SearchReservationCriteriaDTO criteria);

    Optional<Reservation> findById(Long id);

    Reservation save(Reservation reservation);

    void deleteById(Long id);

    boolean existsById(Long id);
}