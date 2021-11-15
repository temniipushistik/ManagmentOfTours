package com.example.tourmanagertool;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TourManagerToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourManagerToolApplication.class, args);
    }
//бин для создания маппера между DTO и Entity объектами
        @Bean
        public ModelMapper modelMapper(){
            return new ModelMapper();
        }
    }


