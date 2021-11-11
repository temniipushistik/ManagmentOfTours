package com.example.tourmanagertool.service;

import java.util.List;

import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.entities.EntityTour;
import com.example.tourmanagertool.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImplementation implements ClientService {
    @Autowired
    Repository repository;


    @Override
    public EntityTour addClient(CreateClientRequest client) {
        return null;
    }

    @Override
    public void delete(String email) {

        repository.deleteByEmail(email);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public EntityTour getClient(String email) {
        List<EntityTour> result = repository.findByEmail(email);
        if (result.size() != 0) {

            return result.get(0);
        } else {
            return null;
        }

    }
}

