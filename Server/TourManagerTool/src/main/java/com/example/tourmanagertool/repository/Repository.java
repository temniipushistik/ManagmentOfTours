package com.example.tourmanagertool.repository;

import com.example.tourmanagertool.entities.EntityTour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Repository extends JpaRepository<EntityTour,Integer> {
    List<EntityTour> findByUsername(String username);
    void deleteByUsername(String username);

}
