package com.example.tourmanagertool.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ObtainAllClientsResponse {
    //поле коллекция с типом данных - оптайн блаблабла
private Collection<ObtainClientResponse> allClients;
}
