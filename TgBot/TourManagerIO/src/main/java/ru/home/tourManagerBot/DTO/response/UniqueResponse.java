package ru.home.tourManagerBot.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UniqueResponse {
    //в этот класс можно добавлять любые объекты
    private String message;//сообщение об ошибке
    private Object dto;// сюда передается в качестве параметра другие ответы DTO (передачу будет делать сервис!)
    //создал клиента - этот класс отвечает что все ок, в качестве подтверждения возращает информацию о созаднном клиенте
    //респопс дто на подтверждение действия - например
    //на каждый запрос ответ написать свой класс.
}
