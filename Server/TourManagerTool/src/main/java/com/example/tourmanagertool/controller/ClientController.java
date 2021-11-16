package com.example.tourmanagertool.controller;

import com.example.tourmanagertool.DTO.request.*;
import com.example.tourmanagertool.DTO.response.*;
import com.example.tourmanagertool.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<HttpStatus> createClient(@RequestBody CreateClientRequest request) {

        // создаем объект(потому что у нас Object Dto в юнике), который будет передаваться в ответ на создание клиента, готовый респонс сервис уже сформировал
        UniqueResponse serviceResult = service.createClient(request);
        //получаем поле из юник респонса объект и приводим его к конкретному типу респонса - создание клиента
        CreateClientResponse resultDTO = (CreateClientResponse) serviceResult.getDto();

        if (resultDTO != null) {

            //в первом аргументе джексон переведет объект UniqueResponse'a в строку, т.е. мы передаем стринг и объект и джексон переведет
            //их сам в строку
            return new ResponseEntity(serviceResult, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(serviceResult, HttpStatus.CONFLICT);
        }


        //EntityTour result = service.addClient(request);


    }

    @PostMapping("/change")

    public ResponseEntity<HttpStatus> changeClient(@RequestBody ChangeClientRequest request) {

        UniqueResponse serviceResult = service.changeClient(request);
        ChangeClientResponse resultDTO = (ChangeClientResponse) serviceResult.getDto();
        if (resultDTO != null) {
            return new ResponseEntity(serviceResult, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(serviceResult, HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteClient(@RequestBody DeleteClientRequest request) {
        UniqueResponse serviceResult = service.deleteClient(request);
        ChangeClientResponse resultDTO = (ChangeClientResponse) serviceResult.getDto();

//в самом сообщении(который стринг в UniqueResponse из serviceResult) будем передавать успешно или нет
        //параметр ДТО для данного запроса будет null т.к. у нас нет такого респонс ДТО т.к. не понятно
        // что возвращать
        return new ResponseEntity(serviceResult, HttpStatus.ACCEPTED);


    }

    @GetMapping("/obtain")
    public ResponseEntity<HttpStatus> obtainClient(@RequestBody ObtainClientRequest request) {
        UniqueResponse serviceResult = service.obtainClient(request);
        ObtainClientResponse resultDTO = (ObtainClientResponse) serviceResult.getDto();
        if (resultDTO != null) {
            return new ResponseEntity(serviceResult, HttpStatus.FOUND);
        } else {
            return new ResponseEntity(serviceResult, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/obtainAll")
    public ResponseEntity<HttpStatus> obtainAllClients(@RequestBody ObtainAllClientsRequest request) {
        UniqueResponse serviceResult = service.obtainAllClients();
        ObtainAllClientsResponse resultDTO = (ObtainAllClientsResponse) serviceResult.getDto();
        if (resultDTO != null) {
            return new ResponseEntity(serviceResult, HttpStatus.FOUND);
        } else {
            return new ResponseEntity(serviceResult, HttpStatus.NOT_FOUND);
        }

    }


}
