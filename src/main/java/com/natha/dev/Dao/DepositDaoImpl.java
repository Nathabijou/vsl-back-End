package com.natha.dev.Dao;

import com.natha.dev.Model.Deposit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DepositDaoImpl implements DepositDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Deposit> findAll() {
        String jpql = "SELECT d FROM Deposit d";
        TypedQuery<Deposit> query = entityManager.createQuery(jpql, Deposit.class);
        return query.getResultList();
    }

    @Override
    public Optional<Deposit> findById(String id) {
        return Optional.ofNullable(entityManager.find(Deposit.class, id));
    }

    @Override
    public Deposit save(Deposit deposit) {
        if (deposit.getId() == null) {
            entityManager.persist(deposit);
            return deposit;
        } else {
            return entityManager.merge(deposit);
        }
    }

    @Override
    public void deleteById(String id) {
        entityManager.createQuery("DELETE FROM Deposit d WHERE d.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void delete(Deposit deposit) {
        // Ensure the entity is managed before removing it
        if (!entityManager.contains(deposit)) {
            deposit = entityManager.merge(deposit);
        }
        entityManager.remove(deposit);
    }

    @Override
    public List<Deposit> findByAccountId(Long accountId) {
        String jpql = "SELECT d FROM Deposit d WHERE d.account.id = :accountId";
        TypedQuery<Deposit> query = entityManager.createQuery(jpql, Deposit.class);
        query.setParameter("accountId", accountId);
        return query.getResultList();
    }
    
    // Implement other methods from the interface as needed
}
