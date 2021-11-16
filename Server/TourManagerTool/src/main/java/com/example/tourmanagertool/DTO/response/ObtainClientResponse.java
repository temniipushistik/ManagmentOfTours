package com.example.tourmanagertool.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObtainClientResponse {
    //мы выводим всю имеющуюся информацию после изменения данных о клиенте. Для всех респонсов будем возвращать юник репонс
    private String userName;
    private String email;
    private String phoneNumber;
    private String sourceOfTraffic;
    //id не нужен менеджеру, когда выводим данные о конкретном клиенте, типо нахуа, лишнее


}
