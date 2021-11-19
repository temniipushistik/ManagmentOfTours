package ru.home.tourManagerBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.tourManagerBot.commands.CreateUser;
import ru.home.tourManagerBot.commands.Start;

import java.util.ArrayList;
import java.util.HashMap;

public class BotImplementation extends TelegramLongPollingBot {
    private static final String TOKEN = "2067787448:AAEsIUyOxkUGdB4SrRn0aJHprr3IsjzwUOk";
    private static final String USERNAME = "Tour_ManagerBot";
    private String userName;
    //создаем клавиатуру:
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private long chat_id;
    //создаем мапу для хранения полученных данных. В стринг - @имя, массив эррея - данные об этом
    //пользователе
    public static HashMap<String, ArrayList<String>> userState = new HashMap<>();

    public long getChat_id() {
        return chat_id;
    }

    public String getBotUsername() {
        return USERNAME;
    }


    public String getBotToken() {
        return TOKEN;
    }






    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            chat_id = update.getMessage().getChatId();
            //получаем ник пользователя
            userName = update.getMessage().getFrom().getUserName();

            String text = update.getMessage().getText();
            try {
                if (text.equals("/start")) {
                    execute(new Start().run(update));
//нужны пояснения в логике сравнения.
                } else if (text.equals("Добавить пользователя") || (userState.get(userName) != null && userState.get(userName).get(0).equals("Добавить пользователя"))) {
                    execute(new CreateUser().run(update));
                } else {

                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}