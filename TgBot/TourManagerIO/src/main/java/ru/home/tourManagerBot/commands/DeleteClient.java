package ru.home.tourManagerBot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.API.ChangeService;
import ru.home.tourManagerBot.API.DeleteService;
import ru.home.tourManagerBot.BotImplementation;
import ru.home.tourManagerBot.DTO.request.ChangeClientRequest;
import ru.home.tourManagerBot.DTO.request.DeleteClientRequest;
import ru.home.tourManagerBot.DTO.response.ChangeClientResponse;

import ru.home.tourManagerBot.DTO.response.UniqueResponse;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteClient {
    static int flagOfDeleting = 0;
    static int sizeOfBD = 0;
    //поменять ветку, узнать, что изменилось в клиенте и репозитории
// changeUser
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    //получаем update из botImplementation
    public SendMessage run(Update update) throws JsonProcessingException {
        //sizeOfBD = BotImplementation.mainClientBD.size();



        if (update.getMessage().getText().equals("Удалить пользователя")&&(BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName())==null)) {
           //создаем пустую запись для данного пользователя
            HashMap<String, String> tempClient = new HashMap<>();
            //размещаем эту запись в основную хэшмапу
            BotImplementation.managerAndClient.put(update.getMessage().getFrom().getUserName(), tempClient);
            Integer flag = 3;//флаг говорит о том, что мы сейчас удалении пользователя
            //-1 - пустой флаг, 0 - create, 1- change, 2- obtain, 3 - delete
            BotImplementation.flags.put(update.getMessage().getFrom().getUserName(), flag);
            flagOfDeleting = 1;
            //запрашивает почту
            return deleteByEmail(update);
            //если коллекция пустая:

        } else if (flagOfDeleting == 1 && (update.getMessage().getText() != null)) {
            String inputMail = update.getMessage().getText();
            HashMap tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
            tempClient.put("email", inputMail);
            return finish(update);

        } else {

            return bullshit(update);
        }
    }

    public SendMessage deleteByEmail(Update update) {
        setConfirmationKeyboardMarkup();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите Email пользователя, данные которого хотите удалить. После ввода почты, пользователь будет удален безвозвратно!");
        return sendMessage;
    }

    private SendMessage bullshit(Update update) {
        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("вылетел из цикла удаления, косяк в логике");
        return sendMessage;
    }

    //завершаем и выводим данные
    private SendMessage finish(Update update) throws JsonProcessingException {
        String textMessage;

        DeleteClientRequest deleteClientRequest = new DeleteClientRequest();
        HashMap<String, String> tempClient = BotImplementation.managerAndClient.get(update.getMessage().getFrom().getUserName());
        deleteClientRequest.setEmail(tempClient.get("email"));
        BotImplementation.managerAndClient.remove(update.getMessage().getFrom().getUserName());
        Integer flag = -1;//флаг говорит о том, что мы сейчас мы всё сделали, можно возвращаться
        BotImplementation.flags.put(update.getMessage().getFrom().getUserName(), flag);

        //передаем полученные данные в DeleteService и получаем ответ от сервера
        UniqueResponse uniqueResponse = DeleteService.postJSon(deleteClientRequest);
        textMessage = uniqueResponse.getMessage();


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
