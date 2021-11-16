package com.example.tourmanagertool.service;

import com.example.tourmanagertool.DTO.request.ChangeClientRequest;
import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.DTO.request.DeleteClientRequest;
import com.example.tourmanagertool.DTO.request.ObtainClientRequest;
import com.example.tourmanagertool.DTO.response.UniqueResponse;
import com.example.tourmanagertool.entities.EntityTour;

public interface ClientService {
    UniqueResponse createClient(CreateClientRequest request);

    UniqueResponse changeClient(ChangeClientRequest request);

    UniqueResponse deleteClient(DeleteClientRequest request);

    UniqueResponse obtainClient(ObtainClientRequest request);

    UniqueResponse obtainAllClients();


}
