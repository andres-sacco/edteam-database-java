package com.edteam.reservations.dao;

import com.edteam.reservations.dto.SearchReservationCriteriaDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.specification.ReservationSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public List<Reservation> findAll(SearchReservationCriteriaDTO criteria) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> query = criteriaBuilder.createQuery(Reservation.class);
        Root<Reservation> root = query.from(Reservation.class);

        Predicate predicate = ReservationSpecification.withSearchCriteria(criteria).toPredicate(root, query,
                criteriaBuilder);
        query.where(predicate);

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    // Uncomment when you want to check what happens with this approach
    //@Lock(LockModeType.PESSIMISTIC_READ)
    public Optional<Reservation> findById(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        // Uncomment when you want to check what happens with this approach
        //Reservation reservation = entityManager.find(Reservation.class, id, LockModeType.PESSIMISTIC_READ);
        return Optional.ofNullable(reservation);
    }

    @Override
    public Reservation save(Reservation reservation) {
        transactionTemplate.execute(status -> {
            try {
                if (reservation.getId() != null) {
                    entityManager.merge(reservation);
                } else {
                    entityManager.persist(reservation);
                }
                entityManager.flush();
                // Uncomment the following line to check the rollback
                // throw new EdteamException(APIError.BAD_FORMAT);
            } catch (Exception e) {
                status.setRollbackOnly(); // Mark the transaction for rollback
                throw e; // Re-throw the exception to ensure the rollback occurs
            }
            return null; // Return value is required for the TransactionTemplate
        });

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
        if (Objects.isNull(reservation)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}