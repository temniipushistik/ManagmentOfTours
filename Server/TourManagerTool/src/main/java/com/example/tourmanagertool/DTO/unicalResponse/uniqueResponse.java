package com.example.tourmanagertool.DTO.unicalResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class uniqueResponse {
    //в этот класс можно добавлять любые объекты
    private String message;//сообщение об ошибке
    private Object dto;// сюда передается в качестве параметра другие ответы DTO
    //создал клиента - этот класс отвечает что все ок, в качестве подтверждения возращает информацию о созаднном клиенте
    //респопс дто на подтверждение действия - например
    //на каждый запрос ответ писать свой с классами.
}
