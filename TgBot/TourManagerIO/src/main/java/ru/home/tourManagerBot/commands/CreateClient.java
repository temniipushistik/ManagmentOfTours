package ru.home.tourManagerBot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.API.CreateService;
import ru.home.tourManagerBot.BotImplementation;
import ru.home.tourManagerBot.DTO.request.CreateClientRequest;
import ru.home.tourManagerBot.DTO.response.CreateClientResponse;
import ru.home.tourManagerBot.DTO.response.UniqueResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class CreateClient {
    //засчет флага двигаемся по циклу добавления нового пользователя
    static int flagOfCreating;
    static HashMap<String, String> client = new HashMap<>();


    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public SendMessage run(Update update) throws JsonProcessingException {

        if (update.getMessage().getText().equals("Добавить пользователя")) {
            BotImplementation.setCreate(true);
            flagOfCreating = 1;
            //запрашивает почту
            return addEmail(update);
            //вводим почту и сохраняем в коллекции
        } else if (flagOfCreating == 1 && update.getMessage().getText() != null) {
            client.put("email", update.getMessage().getText());
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("почта корректна?");
            flagOfCreating = 2;
            return sendMessage;

        } else if (update.getMessage().getText() == null) {
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new Start().run(update);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("вы ввели пустое поле. Не знаю, как так получилось,но самое время начать ввод сначала");
            return sendMessage;


            //почта введена корректно?
        } else if ((flagOfCreating == 2) && (update.getMessage().getText().equals("Да, далее"))) {
            flagOfCreating = 3;
            return addName(update);
        } else if ((flagOfCreating == 2) && (update.getMessage().getText().equals("Нет, назад"))) {
            client.remove("email");
            flagOfCreating--;
            return addEmail(update);

        } else if (flagOfCreating == 3 && update.getMessage().getText() != null) {
            client.put("userName", update.getMessage().getText());
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("Имя корректно?");
            flagOfCreating = 4;
            return sendMessage;

            //имя пользователя корректно?

        } else if (flagOfCreating == 4 && (update.getMessage().getText().equals("Да, далее"))) {
            flagOfCreating = 5;
            return addPhone(update);

        } else if (flagOfCreating == 4 && (update.getMessage().getText().equals("Нет, назад"))) {
            flagOfCreating--;
            client.remove("userName");
            return addName(update);

        } else if (flagOfCreating == 5 && update.getMessage().getText() != null) {
            client.put("phoneNumber", update.getMessage().getText());
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("номер телефона корректен?");
            flagOfCreating = 6;
            return sendMessage;
        } else if (flagOfCreating == 6 && update.getMessage().getText().equals("Да, далее")) {
            flagOfCreating = 7;
            return sourceOfTraffic(update);
        } else if (flagOfCreating == 6 && update.getMessage().getText().equals("Нет, назад")) {
            flagOfCreating--;
            client.remove("phoneNumber");

            return addPhone(update);

        } else if (flagOfCreating == 7 && update.getMessage().getText() != null) {
            client.put("sourceOfTraffic", update.getMessage().getText());
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("источник траффика корректен?");
            flagOfCreating = 8;
            return sendMessage;
            //если источник траффика корректен?
        } else if (flagOfCreating == 8 && update.getMessage().getText().equals("Да, далее")) {
            flagOfCreating = 9;
            return finish(update);
        } else if (flagOfCreating == 8 && update.getMessage().getText().equals("Нет, назад")) {
            client.remove("sourceOfTraffic");
            flagOfCreating--;
            return finish(update);

        } else if (update.getMessage().getText().equals("в главное меню")) {
            client.clear();
            //удалить пользователя
            SendMessage sendMessage = new Start().run(update);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("Данные вводимого пользователя удалены");
            BotImplementation.setCreate(false);

            return sendMessage;

        }


        return bullshit(update);
    }


    private SendMessage addEmail(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите пожалуйста корректный Email");
        return sendMessage;//вернет результат команды run();
    }

    private SendMessage addName(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите имя клиента");

        return sendMessage;

    }

    private SendMessage addPhone(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите номер телефона");

        return sendMessage;

    }

    private SendMessage sourceOfTraffic(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Откуда пришел клиент?");

        return sendMessage;

    }


    private SendMessage bullshit(Update update) {
        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("вылетел из цикла создать пользователя, косяк в логике");

        return sendMessage;

    }

    private SendMessage finish(Update update) throws JsonProcessingException {
        String textMessage;


        //создаем DTO и заполняем его
        CreateClientRequest createClientRequest = new CreateClientRequest();
        createClientRequest.setEmail(client.get("email"));
        createClientRequest.setUserName(client.get("userName"));
        createClientRequest.setSourceOfTraffic(client.get("sourceOfTraffic"));
        createClientRequest.setPhoneNumber(client.get("phoneNumber"));
        //передаем полученные данные в CreateService и получаем ответ от сервера
        UniqueResponse uniqueResponse = CreateService.postJSon(createClientRequest);

        BotImplementation.setCreate(false);


        if (uniqueResponse.getDto() == null) {
            //получаем из бэка ответа
            textMessage = uniqueResponse.getMessage();
        } else {
            textMessage = uniqueResponse.getMessage();
            //получаю объект, который записался в БД из бэка( т.е. часть DTO)
            CreateClientResponse response = new ObjectMapper().convertValue(uniqueResponse.getDto(), CreateClientResponse.class);
            //мапим в стринг и добавляем к тексту ответа
            textMessage +="\n"+new ObjectMapper().writeValueAsString(response);

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
