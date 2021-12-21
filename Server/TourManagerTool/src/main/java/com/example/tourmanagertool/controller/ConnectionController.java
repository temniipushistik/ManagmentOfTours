package com.example.tourmanagertool.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Checking connection between view and server via REST
@RestController

@RequestMapping("/api/connection")

public class    ConnectionController {
    //obtain responses via GET
    @GetMapping("check")
    public ResponseEntity<HttpStatus> check() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
