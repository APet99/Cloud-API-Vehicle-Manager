package com.example.demo;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional
public class VehicleDao {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
    }

    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public Vehicle update(Vehicle vehicle) {
        return entityManager.merge(vehicle);
    }

    public void delete(int id) {
        Vehicle vehicle = getById(id);
        if (vehicle != null) {
            entityManager.remove(vehicle);
        }
    }

    public List<Vehicle> getLatest() {
        Query q = entityManager.createNativeQuery("Select * FROM vehicles order by id desc limit 10", Vehicle.class);
        return q.getResultList();
    }
}
