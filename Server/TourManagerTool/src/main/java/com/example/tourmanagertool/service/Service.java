package com.example.tourmanagertool.service;

import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.entities.EntityTour;

public interface Service {
    EntityTour addClient(CreateClientRequest client);
    void delete(String email);
    EntityTour getClient(String username);
    void deleteAll();



}
