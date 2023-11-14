package com.edteam.reservations.dto;

import java.time.LocalDate;

public class SearchReservationCriteriaDTO {
    private Long itineraryId;
    private String firstName;
    private String lastName;
    private LocalDate reservationDate;
    private String sortField;
    private String sortingDirection;
    private Integer pageActual = 0;
    private Integer pageSize = 10;

    public Long getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Long itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortingDirection() {
        return sortingDirection;
    }

    public void setSortingDirection(String sortingDirection) {
        this.sortingDirection = sortingDirection;
    }

    public Integer getPageActual() {
        return pageActual;
    }

    public void setPageActual(Integer pageActual) {
        this.pageActual = pageActual;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
