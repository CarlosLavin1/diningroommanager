package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    boolean existsByValue(String value);
    Status getStatusByValue(String value);
}
