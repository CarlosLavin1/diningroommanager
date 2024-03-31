package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
