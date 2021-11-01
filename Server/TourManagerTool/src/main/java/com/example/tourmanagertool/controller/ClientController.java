package com.example.tourmanagertool.controller;

import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.tourmanagertool.entities.EntityTour;


@RestController

public class ClientController {

    @Autowired
    Service service;


    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createClient(@RequestBody CreateClientRequest request){
        // Демонстрация requestDTO

        EntityTour result = service.addClient(request);

        // Демонстрация requestDTO

        if (result != null){
            return new ResponseEntity(result,HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }



}
