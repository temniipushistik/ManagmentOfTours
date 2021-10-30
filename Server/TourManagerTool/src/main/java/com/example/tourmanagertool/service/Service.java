package com.example.tourmanagertool.service;

import com.example.tourmanagertool.entities.EntityTour;

public interface Service {
    EntityTour addClient(EntityTour client);
    void delete(String username);
    EntityTour getClient(String username);


}
