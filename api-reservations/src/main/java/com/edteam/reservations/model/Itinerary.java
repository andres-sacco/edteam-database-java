package com.edteam.reservations.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Transient
    private List<Segment> segment;

    @Transient
    private Price price;

    public List<Segment> getSegment() {
        return segment;
    }

    public void setSegment(List<Segment> segment) {
        this.segment = segment;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itinerary itinerary = (Itinerary) o;
        return Objects.equals(id, itinerary.id) && Objects.equals(segment, itinerary.segment) && Objects.equals(price, itinerary.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, segment, price);
    }

    @Override
    public String toString() {
        return "Itinerary{" +
                "id=" + id +
                ", segment=" + segment +
                ", price=" + price +
                '}';
    }
}
