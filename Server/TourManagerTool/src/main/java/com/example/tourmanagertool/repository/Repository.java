package com.example.tourmanagertool.repository;

import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.DTO.response.UniqueResponse;
import com.example.tourmanagertool.entities.EntityTour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Repository extends JpaRepository<EntityTour,Integer> {
    List<EntityTour> findByEmail(String email);
    void deleteByEmail(String email);
    List<EntityTour> findAll();
    //  UniqueResponse changeClient(CreateClientRequest request);

}


