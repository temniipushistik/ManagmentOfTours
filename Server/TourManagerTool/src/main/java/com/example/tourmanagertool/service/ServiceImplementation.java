package com.example.tourmanagertool.service;

import java.lang.annotation.Annotation;
import java.util.List;

import com.example.tourmanagertool.entities.EntityTour;
import com.example.tourmanagertool.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceImplementation implements com.example.tourmanagertool.service.Service {
    @Autowired
    Repository repository;

    //как проверять на уникальность? По номеру телефона?лучше почта
    @Override
    public EntityTour addClient(EntityTour client) {
        return repository.save(client);
    }

    @Override
    public void delete(String username) {
        //находим по почте и удаляем в дальнейшем
        repository.deleteByUsername(username);
    }

    @Override
    public EntityTour getClient(String username) {
        List<EntityTour> result = repository.findByUsername(username);
        if (result.size() != 0) {
            //Что делать, если несколько позиций(возвращаем список)
            //тащить с номером телефона или почтой - поменять аргумент
            return result.get(0);
        } else {
            return null;
        }

    }
}

