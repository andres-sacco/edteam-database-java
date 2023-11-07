package com.edteam.reservations.repository;

import com.edteam.reservations.dto.SearchReservationCriteriaDTO;
import com.edteam.reservations.model.Itinerary;
import com.edteam.reservations.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ReservationRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRepository.class);
    private static final String JDBC_URL = "jdbc:mysql://localhost:3310/flights_reservation";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "muppet";

    public List<Reservation> getReservations(SearchReservationCriteriaDTO criteria) {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM reservation";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Reservation reservation = extractReservationFromResultSet(resultSet);
                    reservations.add(reservation);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ocurrs an error on getReservations(), {0}", e);
        }
        return reservations;
    }

    public Optional<Reservation> getReservationById(Long id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "SELECT * FROM reservation WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Reservation reservation = extractReservationFromResultSet(resultSet);
                        return Optional.of(reservation);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Ocurrs an error on getReservationById(), {0}", e);
        }
        return Optional.empty();
    }

    public Reservation save(Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO reservation (creation_date, itinerary_id) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                prepareReservationStatement(reservation, preparedStatement);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Ocurrs an error on save(), {0}", e);
        }
        return reservation;
    }

    public Reservation update(Long id, Reservation reservation) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "UPDATE reservation SET creation_date = ?, itinerary_id = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                prepareReservationStatement(reservation, preparedStatement);
                preparedStatement.setLong(3, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Ocurrs an error on update(), {0}", e);
        }
        return reservation;
    }

    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "DELETE FROM reservation WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Ocurrs an error on delete(), {0}", e);
        }
    }

    private Reservation extractReservationFromResultSet(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(resultSet.getLong("id"));
        reservation.setCreationDate(resultSet.getDate("creation_date").toLocalDate());

        Itinerary itinerary = new Itinerary();
        itinerary.setId(resultSet.getLong("itinerary_id"));
        reservation.setItinerary(itinerary);
        return reservation;
    }

    private void prepareReservationStatement(Reservation reservation, PreparedStatement preparedStatement)
            throws SQLException {
        preparedStatement.setDate(1, java.sql.Date.valueOf(reservation.getCreationDate()));
        preparedStatement.setLong(2, reservation.getItinerary().getId());
    }
}
