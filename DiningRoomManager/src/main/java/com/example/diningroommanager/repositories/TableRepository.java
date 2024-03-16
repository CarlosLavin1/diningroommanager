package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.DiningTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<DiningTable, Integer> {
    List<DiningTable> findByLayout_Id(int layoutId);
}
