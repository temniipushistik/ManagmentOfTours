package ru.home.tourManagerBot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.API.ChangeService;

import ru.home.tourManagerBot.BotImplementation;
import ru.home.tourManagerBot.DTO.request.ChangeClientRequest;
import ru.home.tourManagerBot.DTO.response.ChangeClientResponse;
import ru.home.tourManagerBot.DTO.response.UniqueResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ChangeClient {
    static int flagOfChanging = 0;
    //static int sizeOfBD = 0;


    //поменять ветку, узнать, что изменилось в клиенте и репозитории
// changeUser
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    //получаем update из botImplementation
    public SendMessage run(Update update) throws JsonProcessingException {

     //   sizeOfBD = (BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName())).size();


        //if (update.getMessage().getText().equals("Редактировать пользователя") && (sizeOfBD > 0)) {
        //    BotImplementation.setChange(true);
        //    flagOfChanging = 1;
        //запрашивает почту
        //  return requestEmail(update);
        //если коллекция пустая:
        if (update.getMessage().getText().equals("Редактировать пользователя") && (BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName())==null)) {
            //внутреняя хэшмапа для данных каждого менеджера
            HashMap<String, String> tempClient = new HashMap<>();

            BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);
            BotImplementation.setChange(true);
            flagOfChanging = 1;
            // setConfirmationKeyboardMarkup();
            // SendMessage sendMessage = new Start().run(update);
            //sendMessage.setChatId(update.getMessage().getChatId() + "");
            //sendMessage.setText("В базе ничего нет. Чтобы что-то отредактировать нужно сначала что-то создать");
            return requestEmail(update);


        } else if (flagOfChanging == 1 && (update.getMessage().getText() != null)) {
            String inputMail = update.getMessage().getText();
            if (BotImplementation.managerAndClient.containsKey(update.getMessage().getFrom().getUserName())) {

                HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
                tempClient.put("email", inputMail);
                BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);

            } else {
                return bullshit(update);

            }
            //BotImplementation.mainClientBD.put("email", inputMail);

            //   String baseMail = BotImplementation.mainClientBD.get("email");
            //проверяем на наличие в базе
            flagOfChanging = 2;
            return changeName(update);
            /*} else {
                setConfirmationKeyboardMarkup();
                SendMessage sendMessage = new Start().run(update);
                sendMessage.setChatId(update.getMessage().getChatId() + "");
                sendMessage.setText("такой почты нет. Попробуйте снова");
                return sendMessage;
            }*/
        }
        //имя новое записываем
        else if (flagOfChanging == 2 && (update.getMessage().getText() != null)) {
            // BotImplementation.mainClientBD.put("userName", update.getMessage().getText());
            //получаю хэшмапу с одним значением(почта)
            HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
            //кладем еще одно значение - юзернейм
            tempClient.put("userName", update.getMessage().getText());
            //возвращаем
            BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);

            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("откорректированное имя корректно?");
            flagOfChanging = 3;
            return sendMessage;
        } else if (flagOfChanging == 3 && (update.getMessage().getText() != "Да, далее")) {
            flagOfChanging = 4;
            return changePhone(update);
        } else if (flagOfChanging == 3 && (update.getMessage().getText() != "Нет, назад")) {
            flagOfChanging--;

            HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
            //удаляем  одно значение - юзернейм
            tempClient.remove("userName", update.getMessage().getText());
            //возвращаем с удаленным полем юзернейм
            BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);
            return changeName(update);
        }

        //добавляем номер телефона в коллекцию
        else if (flagOfChanging == 4 && (update.getMessage().getText() != null)) {

            if (BotImplementation.managerAndClient.containsKey(update.getMessage().getFrom().getUserName())) {

                HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
                tempClient.put("phoneNumber", update.getMessage().getText());
                BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);
            } else {
                return bullshit(update);
            }

            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("откорректированный телефон корректен?");
            flagOfChanging = 5;
            return sendMessage;
        } else if (flagOfChanging == 5 && (update.getMessage().getText() != "Да, далее")) {
            flagOfChanging = 6;
            return finish(update);
        } else if (flagOfChanging == 5 && (update.getMessage().getText() != "Нет, назад")) {
            flagOfChanging--;
            HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
            tempClient.remove("phoneNumber", update.getMessage().getText());
            BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);
            return changePhone(update);
        } else {
            return bullshit(update);
        }


    }


    public SendMessage requestEmail(Update update) {
        setConfirmationKeyboardMarkup();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите Email пользователя, данные которого хотите изменить");
        return sendMessage;
    }

    public SendMessage changeName(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        String textMessage = "Введена почта: \n";
        HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
        textMessage += (tempClient.get("email") + "\n");
        textMessage += "Введите новое имя клиента:";

        sendMessage.setText(textMessage);
        return sendMessage;
    }

    SendMessage changePhone(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите новый номер телефона");
        return sendMessage;
    }

    private SendMessage bullshit(Update update) {
        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("вылетел из цикла изменить пользователя, косяк в логике");
        return sendMessage;
    }

    //завершаем и выводим данные
    private SendMessage finish(Update update) throws JsonProcessingException {
        String textMessage;

        BotImplementation.setChange(false);


        //создаем DTO и заполняем его
        ChangeClientRequest changeClientRequest = new ChangeClientRequest();
        HashMap<String, String> tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
        changeClientRequest.setEmail(tempClient.get("email"));
        changeClientRequest.setUserName(tempClient.get("userName"));
        changeClientRequest.setPhoneNumber(tempClient.get("phoneNumber"));
        BotImplementation.managerAndClient.remove(update.getMessage().getFrom().getUserName());
        //передаем полученные данные в CreateService и получаем ответ от сервера
        UniqueResponse uniqueResponse = ChangeService.postJSon(changeClientRequest);

        if (uniqueResponse.getDto() == null) {
            //получаем из бэка ответа
            textMessage = uniqueResponse.getMessage();
        } else {
            textMessage = uniqueResponse.getMessage();
            //получаю объект, который записался в БД из бэка( т.е. часть DTO)
            ChangeClientResponse response = new ObjectMapper().convertValue(uniqueResponse.getDto(), ChangeClientResponse.class);
            //мапим в стринг и добавляем к тексту ответа
            textMessage += "\n" + new ObjectMapper().writeValueAsString(response);

        }

        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText(textMessage);
        return sendMessage;

    }

    //создаем клавиатуру:
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
