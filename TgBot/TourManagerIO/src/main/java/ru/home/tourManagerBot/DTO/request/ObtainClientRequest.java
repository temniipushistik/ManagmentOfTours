package ru.home.tourManagerBot.DTO.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ObtainClientRequest {
//получаем желаемого клиента по почте
    private String email;
}
