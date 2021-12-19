package ru.home.tourManagerBot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.API.CreateService;
import ru.home.tourManagerBot.API.ObtainService;
import ru.home.tourManagerBot.BotImplementation;
import ru.home.tourManagerBot.DTO.request.CreateClientRequest;
import ru.home.tourManagerBot.DTO.request.ObtainClientRequest;
import ru.home.tourManagerBot.DTO.response.CreateClientResponse;
import ru.home.tourManagerBot.DTO.response.ObtainClientResponse;
import ru.home.tourManagerBot.DTO.response.UniqueResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class ObtainClient {
    static int flagOfObtaining = 0;
    static int sizeOfBD = 0;
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public SendMessage run(Update update) throws JsonProcessingException {
        //    sizeOfBD = BotImplementation.mainClientBD.size();

        if (update.getMessage().getText().equals("Получить пользователя") && (BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName()) == null)) {
            //создаем пустую запись для данного пользователя
            HashMap<String, String> tempClient = new HashMap<>();
            //размещаем эту запись в основную хэшмапу
            BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);
            BotImplementation.setObtain(true);
            flagOfObtaining = 1;

            //запрашивает почту
            return inputEmail(update);


        } else if ((flagOfObtaining == 1) && (update.getMessage().getText() != null)) {
            String inputMail = update.getMessage().getText();
            HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
            tempClient.put("email", inputMail);
            BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);


            return finish(update);


        } else {
            return bullshit(update);
        }
    }


    public SendMessage inputEmail(Update update) {
        setConfirmationKeyboardMarkup();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите Email пользователя, данные которого хотите получить");
        return sendMessage;
    }

    private SendMessage bullshit(Update update) {
        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("вылетел из цикла получить одного, косяк в логике");

        return sendMessage;
    }

    private SendMessage finish(Update update) throws JsonProcessingException {
        String textMessage;

        BotImplementation.setObtain(false);

        ObtainClientRequest obtainClientRequest = new ObtainClientRequest();

        HashMap<String, String> tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
        obtainClientRequest.setEmail(tempClient.get("email"));
        BotImplementation.managerAndClient.remove(update.getMessage().getFrom().getUserName());
        //передаем полученные данные в CreateService и получаем ответ от сервера
        UniqueResponse uniqueResponse = ObtainService.postJSon(obtainClientRequest);

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


    private void setConfirmationKeyboardMarkup() {

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardCorrect = new KeyboardRow();
        KeyboardRow keyboardInCorrect = new KeyboardRow();
        KeyboardRow keyboardCancel = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);//видно всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true);//подгоняет клавиатуру под высоту кнопок
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрывать клаву после использования?

        keyboard.clear();
        keyboardCorrect.add("Да, далее");
        keyboardInCorrect.add("Нет, назад");
        keyboardCancel.add("в главное меню");

        keyboard.add(keyboardCorrect);
        keyboard.add(keyboardInCorrect);
        keyboard.add(keyboardCancel);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }
}

