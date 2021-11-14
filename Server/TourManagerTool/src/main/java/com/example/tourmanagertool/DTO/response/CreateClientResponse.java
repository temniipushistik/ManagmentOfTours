package com.example.tourmanagertool.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class CreateClientResponse {
    //мы выводим всю имеющуюся информацию после изменения данных о клиенте. Для всех респонсов будем возвращать юник репонс
    private String userName;
    private String email;
    private String phoneNumber;
    private String sourceOfTraffic;
    private  int id;
}
