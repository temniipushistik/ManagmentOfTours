package com.example.tourmanagertool.service;

import java.util.List;

import com.example.tourmanagertool.DTO.request.ChangeClientRequest;
import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.DTO.request.DeleteClientRequest;
import com.example.tourmanagertool.DTO.request.ObtainClientRequest;
import com.example.tourmanagertool.DTO.response.UniqueResponse;
import com.example.tourmanagertool.entities.EntityTour;
import com.example.tourmanagertool.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImplementation implements ClientService {
    @Autowired
    Repository repository;

    @Override
    public UniqueResponse createClient(CreateClientRequest request) {
        return null;
    }

    @Override
    public UniqueResponse changeClient(ChangeClientRequest request) {
        return null;
    }

    @Override
    public UniqueResponse deleteClient(DeleteClientRequest request) {
        return null;
    }

    @Override
    public UniqueResponse obtainClient(ObtainClientRequest request) {
        return null;
    }

    @Override
    public UniqueResponse obtainAllClients() {
        return null;
    }


//    @Override
//    public EntityTour addClient(CreateClientRequest client) {
//
//        return null;
//    }
//
//    @Override
//    public void delete(String email) {
//
//        repository.deleteByEmail(email);
//    }
//
//    @Override
//    public void deleteAll() {
//        repository.deleteAll();
//    }
//
//    @Override
//    public EntityTour getClient(String email) {
//        List<EntityTour> result = repository.findByEmail(email);
//        if (result.size() != 0) {
//
//            return result.get(0);
//        } else {
//            return null;
//        }
//
//    }
}

