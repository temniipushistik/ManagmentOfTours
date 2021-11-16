package com.example.tourmanagertool.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



//entities are structure of database
//lombok creates getters, setters, constructors with all args and without args below

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @javax.persistence.Entity

    public class EntityTour {
        //creat a key
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        int id;
        private String username;
        private String email;
        private String phoneNumber;
        //how does user find the company:
        private String sourceOfTraffic;



    }

