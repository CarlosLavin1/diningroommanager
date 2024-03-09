package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.Layout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LayoutRepository extends JpaRepository<Layout, Integer> {
}
