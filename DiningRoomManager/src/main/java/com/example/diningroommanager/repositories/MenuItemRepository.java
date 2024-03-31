package com.example.diningroommanager.repositories;

import com.example.diningroommanager.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
    List<MenuItem> findByMenuId(int id);
}
