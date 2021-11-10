package com.example.tourmanagertool.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ChangeClientRequest {
    private String userName;
    private String email;
    private String phoneNumber;
    private String sourceOfTraffic;
}
