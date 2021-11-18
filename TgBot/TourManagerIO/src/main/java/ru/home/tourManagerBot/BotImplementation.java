package ru.home.tourManagerBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class BotImplementation extends TelegramLongPollingBot {
    private static final String TOKEN = "2067787448:AAEsIUyOxkUGdB4SrRn0aJHprr3IsjzwUOk";
    private static final String USERNAME = "Tour_ManagerBot";
    //создаем клавиатуру:
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public String getBotUsername() {
        return USERNAME;
    }

    public String getBotToken() {
        return TOKEN;
    }


    public String getMessage(String msg) {
        //создаем 5 рядов клавиатуры
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRowFirstRow = new KeyboardRow();
        KeyboardRow keyboardRowSecondRow = new KeyboardRow();
        KeyboardRow keyboardRowThirdRow = new KeyboardRow();
        KeyboardRow keyboardRowFourthRow = new KeyboardRow();
        KeyboardRow keyboardRowFifthRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);//видно всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true);//подгоняет клавиатуру под высоту кнопок
        replyKeyboardMarkup.setOneTimeKeyboard(false);//скрывать клаву после использования?
        if (msg.equals("/start")) {
            keyboard.clear();
            keyboardRowFirstRow.add("Добавить пользователя");
            keyboardRowSecondRow.add("Редактировать пользователя");
            keyboardRowThirdRow.add("Удалить пользователя");
            keyboardRowFourthRow.add("Получить пользователя");
            keyboardRowFifthRow.add("Получить всех пользователей");

            keyboard.add(keyboardRowFirstRow);
            keyboard.add(keyboardRowSecondRow);
            keyboard.add(keyboardRowThirdRow);
            keyboard.add(keyboardRowFourthRow);
            keyboard.add(keyboardRowFifthRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
        }
            return "Привествую, менеджер, пожалуйста, выберете что хотите сделать";


        }



    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            long chat_id = update.getMessage().getChatId();
            //получаем ник пользователя
            String userName = update.getMessage().getFrom().getUserName();


            try {
                SendMessage sendMessage = new SendMessage();

                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setChatId(chat_id + "");
                sendMessage.setText(getMessage(update.getMessage().getText()));

                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
