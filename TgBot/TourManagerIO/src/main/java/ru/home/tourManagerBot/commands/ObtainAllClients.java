package ru.home.tourManagerBot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.API.ObtainAllService;
import ru.home.tourManagerBot.API.ObtainService;
import ru.home.tourManagerBot.BotImplementation;
import ru.home.tourManagerBot.DTO.request.ObtainAllClientsRequest;
import ru.home.tourManagerBot.DTO.request.ObtainClientRequest;
import ru.home.tourManagerBot.DTO.response.ObtainClientResponse;
import ru.home.tourManagerBot.DTO.response.UniqueResponse;

import java.util.ArrayList;

public class ObtainAllClients {

    static int sizeOfBD = 0;

    public SendMessage run(Update update) throws JsonProcessingException {
        sizeOfBD = BotImplementation.mainClientBD.size();

        if (update.getMessage().getText().equals("Получить всех пользователей") && (sizeOfBD != 0)) {

            return finish(update);

        } else if (update.getMessage().getText().equals("Получить всех пользователей") && (sizeOfBD == 0)) {

           /* SendMessage sendMessage = new Start().run(update);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("В базе ничего нет. Чтобы что-то получить нужно сначала что-то создать");
            return sendMessage;

            */
            return finish(update);

        } else {
            return bullshit(update);
        }
    }


    private SendMessage bullshit(Update update) {
        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("вылетел из цикла, косяк в логике");

        return sendMessage;
    }

    private SendMessage finish(Update update) throws JsonProcessingException {
        String textMessage;//= "Данные по всем клиентам:\n";// = client.get("sourceOfTraffic") + "";
        /*for (String name : BotImplementation.mainClientBD.keySet()) {
            textMessage += (name + " : " + BotImplementation.mainClientBD.get(name) + "\n");
        }

         */


        ObtainAllClientsRequest obtainAllClientsRequest = new ObtainAllClientsRequest();
       // obtainClientRequest.setEmail(BotImplementation.mainClientBD.get("email"));

        //передаем полученные данные в CreateService и получаем ответ от сервера
        UniqueResponse uniqueResponse = ObtainAllService.postJSon();

        if (uniqueResponse.getDto() == null) {
            //получаем из бэка ответа
            textMessage = uniqueResponse.getMessage();
        } else {
            textMessage = uniqueResponse.getMessage();
            //получаю объект, который записался в БД из бэка( т.е. часть DTO)
            ObtainClientResponse response = new ObjectMapper().convertValue(uniqueResponse.getDto(), ObtainClientResponse.class);
            //мапим в стринг и добавляем к тексту ответа
            textMessage += "\n" + new ObjectMapper().writeValueAsString(response);

        }

        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText(textMessage);
        return sendMessage;
    }


}


