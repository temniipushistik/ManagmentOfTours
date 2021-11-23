package ru.home.tourManagerBot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.BotImplementation;

import java.util.ArrayList;

public class Start {
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public SendMessage run(Update update){

        SendMessage sendMessage = new SendMessage();
        setReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Выберите нужно действие, "+ update.getMessage().getFrom().getUserName()+"!");
        return sendMessage;
    }
    //создаем клавиатуру:
    private void setReplyKeyboardMarkup(){

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardAddClient = new KeyboardRow();
        KeyboardRow keyboardEditClient = new KeyboardRow();
        KeyboardRow keyboardDeleteClient = new KeyboardRow();
        KeyboardRow keyboardGetClient = new KeyboardRow();
        KeyboardRow keyboardGetAllClients = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);//видно всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true);//подгоняет клавиатуру под высоту кнопок
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрывать клаву после использования?

        keyboard.clear();
        keyboardAddClient.add("Добавить пользователя");
        keyboardEditClient.add("Редактировать пользователя");
        keyboardDeleteClient.add("Удалить пользователя");
        keyboardGetClient.add("Получить пользователя");
        keyboardGetAllClients.add("Получить всех пользователей");

        keyboard.add(keyboardAddClient);
        keyboard.add(keyboardEditClient);
        keyboard.add(keyboardDeleteClient);
        keyboard.add(keyboardGetClient);
        keyboard.add(keyboardGetAllClients);

        replyKeyboardMarkup.setKeyboard(keyboard);

    }


}
