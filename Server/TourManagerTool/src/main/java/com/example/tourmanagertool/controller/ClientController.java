package com.example.tourmanagertool.controller;

import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tourmanagertool.entities.EntityTour;


@RestController
@RequestMapping("/api/client")
//

public class ClientController {

    @Autowired
    ClientService service;

    //фронт отправляет запрос по ссылке с create. еще надо добавить в этом классе delete change
    // get01(возвращает по почте клиента)
    // , get02 - возвращает весь список клиентов

    //api запрос через протокол http был типа post
    @PostMapping("/create")
    //ResponseEntity - обертка для ответа, некий уверсальный объект, <HttpStatus> - класс,
    // которые предоставляем коды ответов/ошибок(200, 404 и пр)
    //сreateClient - имя метода
    // в аргумент передаем наше DTO сюда помечаем аннотацией
    // (@аннотация преобразует json из "/api/client/create в DTO объект класса createclientrequest)
    public ResponseEntity<HttpStatus> createClient(@RequestBody CreateClientRequest request){

        EntityTour result = service.addClient(request);

        if (result != null){
            return new ResponseEntity(result,HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }



}
