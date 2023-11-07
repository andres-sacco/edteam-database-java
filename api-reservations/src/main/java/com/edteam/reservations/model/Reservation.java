package com.edteam.reservations.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Transient
    private List<Passenger> passengers;

    @Transient
    private Itinerary itinerary;

    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(passengers, that.passengers) && Objects.equals(itinerary, that.itinerary) && Objects.equals(creationDate, that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passengers, itinerary, creationDate);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", passengers=" + passengers +
                ", itinerary=" + itinerary +
                ", creationDate=" + creationDate +
                '}';
    }
}
