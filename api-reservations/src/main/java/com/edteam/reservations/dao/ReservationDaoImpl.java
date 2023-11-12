package com.edteam.reservations.dao;

import com.edteam.reservations.dto.SearchReservationCriteriaDTO;
import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.specification.ReservationSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Reservation> findAll(SearchReservationCriteriaDTO criteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = criteriaBuilder.createQuery(Reservation.class);
        Root<Reservation> root = query.from(Reservation.class);

        Predicate predicate = ReservationSpecification.withSearchCriteria(criteria).toPredicate(root, query, criteriaBuilder);
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        return Optional.ofNullable(reservation);
    }

    @Override
    public Reservation save(Reservation reservation) {
        if(reservation.getId() != null) {
            entityManager.merge(reservation);
        } else {
            entityManager.persist(reservation);
        }
        entityManager.flush();
        return reservation;
    }


    @Override
    public void deleteById(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation != null) {
            entityManager.remove(reservation);
            entityManager.flush();
        }
    }

    @Override
    public boolean existsById(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if(Objects.isNull(reservation)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}