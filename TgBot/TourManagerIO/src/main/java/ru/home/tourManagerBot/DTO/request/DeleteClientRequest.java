package ru.home.tourManagerBot.DTO.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteClientRequest {
    private String email;
    //всего одно поле - удаление по емайл


}
