package com.example.tourmanagertool.service;

import java.util.List;

import com.example.tourmanagertool.DTO.request.ChangeClientRequest;
import com.example.tourmanagertool.DTO.request.CreateClientRequest;
import com.example.tourmanagertool.DTO.request.DeleteClientRequest;
import com.example.tourmanagertool.DTO.request.ObtainClientRequest;
import com.example.tourmanagertool.DTO.response.ChangeClientResponse;
import com.example.tourmanagertool.DTO.response.CreateClientResponse;
import com.example.tourmanagertool.DTO.response.UniqueResponse;
import com.example.tourmanagertool.entities.EntityTour;
import com.example.tourmanagertool.repository.Repository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImplementation implements ClientService {
    @Autowired
    Repository repository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public UniqueResponse createClient(CreateClientRequest request) {
        UniqueResponse response;
        //берем репозиторий и ищем почту по нему
        //list используем т.к. репозиторий возвращает лист
        List<EntityTour> resultSearchByEmail = repository.findByEmail(request.getEmail());
        if(resultSearchByEmail.size()!=0){
            response = new UniqueResponse("Клиент с такой почтой уже существует",null);
        }else{//второй аргумент - тип данных в который надо перевести, первый аргумент - из чего перевести
            EntityTour modelToSaveFromDTO = modelMapper.map(request,EntityTour.class);
            //метод saveAndFlush - относится к jpa (часть спринга) и в случае экспешина он выдаст ошибку
            EntityTour result = repository.saveAndFlush(modelToSaveFromDTO);//-сохраняем в БД
            if (result!=null ){


                //конвертируем обратно из ентите в ДТО:
            CreateClientResponse responseDTOFromEntity = modelMapper.map(result,CreateClientResponse.class);
                //отправляем это на фронт
            response = new UniqueResponse("Всё успешно добавлено в БД",responseDTOFromEntity);
            }else{//null - контакт с БД рухнул
                response = new UniqueResponse("Что-то не так на стороне БД",null);
            }
        }


        return response;
    }

    @Override
    public UniqueResponse changeClient(ChangeClientRequest request) {
        UniqueResponse response;
        //дял упрощения  запрос изменения клиента используется в одно действие -
        // т.е. можно менять только телефон и имя
        List<EntityTour> resultSearchByEmail = repository.findByEmail(request.getEmail());
        if (resultSearchByEmail.size() == 0) {
            response = new UniqueResponse("Клиент с такой почтой не существует", null);}
            else{
            //получаем объект с указанной почтой

                EntityTour clientForChanging = resultSearchByEmail.get(0);
                clientForChanging.setPhoneNumber(request.getPhoneNumber());
                clientForChanging.setUsername(request.getUserName());
                EntityTour result = repository.save(clientForChanging);


                if (result!=null  ){

                    //конвертируем обратно из ентите в ДТО:
                    ChangeClientResponse responseDTOFromEntity = modelMapper.map(result,ChangeClientResponse.class);
                    //отправляем это на фронт
                    response = new UniqueResponse("Всё успешно добавлено в БД",responseDTOFromEntity);
                }else{//null - контакт с БД рухнул
                    response = new UniqueResponse("Что-то не так на стороне БД",null);
                }

            }
            return response;
        }


    @Override
    public UniqueResponse deleteClient(DeleteClientRequest request) {
        return null;
    }

    @Override
    public UniqueResponse obtainClient(ObtainClientRequest request) {
        return null;
    }

    @Override
    public UniqueResponse obtainAllClients() {
        return null;
    }


//    @Override
//    public EntityTour addClient(CreateClientRequest client) {
//
//        return null;
//    }
//
//    @Override
//    public void delete(String email) {
//
//        repository.deleteByEmail(email);
//    }
//
//    @Override
//    public void deleteAll() {
//        repository.deleteAll();
//    }
//
//    @Override
//    public EntityTour getClient(String email) {
//        List<EntityTour> result = repository.findByEmail(email);
//        if (result.size() != 0) {
//
//            return result.get(0);
//        } else {
//            return null;
//        }
//
//    }
}

