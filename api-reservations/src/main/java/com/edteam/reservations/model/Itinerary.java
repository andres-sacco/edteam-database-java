package com.edteam.reservations.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;
import java.util.Objects;

@Entity
public class Itinerary extends Base {

    @Valid
    @NotEmpty(message = "You need at least one segment")
    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "itinerary_id")
    private List<Segment> segment;

    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "itinerary_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Itinerary itinerary = (Itinerary) o;
        return Objects.equals(getId(), itinerary.getId()) && Objects.equals(segment, itinerary.segment)
                && Objects.equals(price, itinerary.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), segment, price);
    }

    @Override
    public String toString() {
        return "Itinerary{" + "id=" + getId() + ", segment=" + segment + ", price=" + price + '}';
    }
}
