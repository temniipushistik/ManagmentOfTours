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

public class DeleteClient {
    static int flagOfDeleting = 0;
    static int sizeOfBD = 0;
    //поменять ветку, узнать, что изменилось в клиенте и репозитории
// changeUser
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    //получаем update из botImplementation
    public SendMessage run(Update update) throws JsonProcessingException {
        sizeOfBD = BotImplementation.mainClientBD.size();

        if (update.getMessage().getText().equals("Удалить пользователя") && (sizeOfBD > 0)) {
            BotImplementation.setDelete(true);
            flagOfDeleting = 1;
            //запрашивает почту
            return deleteByEmail(update);
            //если коллекция пустая:
        } else if (update.getMessage().getText().equals("Удалить пользователя") && (sizeOfBD == 0)) {
            /*setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new Start().run(update);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("В базе ничего нет. Чтобы что-то удалить нужно сначала что-то создать");
            return sendMessage;*/
            BotImplementation.setDelete(true);
            flagOfDeleting = 1;
            //запрашивает почту
            return deleteByEmail(update);


        } else if (flagOfDeleting == 1 && (update.getMessage().getText() != null)) {
            String inputMail = update.getMessage().getText();
            BotImplementation.mainClientBD.put("email", inputMail);
            return finish(update);

            /*String baseMail = BotImplementation.mainClientBD.get("email");
            //проверяем на наличие в базе
            if (inputMail.equals(baseMail)) {
                BotImplementation.mainClientBD.clear();

                return finish(update);
            } else {
                setConfirmationKeyboardMarkup();
                SendMessage sendMessage = new Start().run(update);
                sendMessage.setChatId(update.getMessage().getChatId() + "");
                sendMessage.setText("такой почты нет. Попробуйте снова");
                return sendMessage;
            } */
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
        BotImplementation.setDelete(false);
        DeleteClientRequest deleteClientRequest = new DeleteClientRequest();
        deleteClientRequest.setEmail(BotImplementation.mainClientBD.get("email"));

        BotImplementation.mainClientBD.clear();
        //передаем полученные данные в DeleteService и получаем ответ от сервера
        UniqueResponse uniqueResponse = DeleteService.postJSon(deleteClientRequest);
        textMessage = uniqueResponse.getMessage();

        /*if (uniqueResponse.getDto() == null) {
            //получаем из бэка ответа
            textMessage = uniqueResponse.getMessage();
        } else {
            textMessage = uniqueResponse.getMessage();

        }*/

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
