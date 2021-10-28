package com.example.tourmanagertool.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


    // у него приватные поля всегда
//entities - структура базы данных
//лобок создает все гетеры сетеры, конструкторы с параметрами и без
    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor

    public class ExampleEntity {
        //создаем ключ
        @Id
        // алгоритм, по которому будет генерироваться айдишник
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String username;
        private String email;
        private String phoneNumber;
        //how does user find the company:
        private String sourceOfTraffic;

    }
