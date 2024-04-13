package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.Seating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatingRepository extends JpaRepository<Seating, Integer> {
    public List<Seating> getSeatingsByEvent_Id(int id);
    @Query("SELECT s FROM Seating s JOIN FETCH s.event e JOIN FETCH e.layout l JOIN FETCH l.diningTables WHERE s.id = :id")
    Optional<Seating> findByIdWithEventAndTables(@Param("id") int id);
}
