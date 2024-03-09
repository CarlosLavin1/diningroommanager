package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatingRepository extends JpaRepository<Seating, Integer> {
}
