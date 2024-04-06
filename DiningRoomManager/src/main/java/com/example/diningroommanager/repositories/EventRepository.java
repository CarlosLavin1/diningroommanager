package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.Event;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Modifying
    @Transactional
    @Query("update Event e set e.name = :name, e.startDate = :startDate, e.endDate = :endDate, " +
            "e.seatingDuration = :seatingDuration, e.description = :description, e.price = :price, " +
            "e.layout = (select l from Layout l where l.id = :layoutId), " +
            "e.menu = (select m from Menu m where m.id = :menuId)" +
            "where e.id = :id ")

    void update(@Param("id")int id,
                @Param("name") String name,
                @Param("startDate") LocalDate startDate,
                @Param("endDate") LocalDate endDate,
                @Param("seatingDuration") Integer seatingDuration,
                @Param("description") String description,
                @Param("price") Double price,
                @Param("layoutId") int layoutId,
                @Param("menuId") int menuId);
}
