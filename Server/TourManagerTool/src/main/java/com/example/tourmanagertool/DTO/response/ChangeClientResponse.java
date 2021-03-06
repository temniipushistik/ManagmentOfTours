package com.example.tourmanagertool.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class ChangeClientResponse {
    //мы выводим всю имеющуюся информацию после изменения данных о клиенте. Для всех респонсов будем возвращать юник репонс
    private String userName;
    private String email;
    private String phoneNumber;
    private String sourceOfTraffic;
    private  int id;
}
