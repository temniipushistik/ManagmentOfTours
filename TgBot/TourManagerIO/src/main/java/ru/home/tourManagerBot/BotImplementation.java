package ru.home.tourManagerBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotImplementation extends TelegramLongPollingBot {
    private static final String TOKEN = "2067787448:AAEsIUyOxkUGdB4SrRn0aJHprr3IsjzwUOk";
    private static final String USERNAME = "Tour_ManagerBot";

    public String getBotUsername() {
        return USERNAME;
    }

    public String getBotToken() {
        return TOKEN;
    }

    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            long chat_id = update.getMessage().getChatId();
            String userName = update.getMessage().getFrom().getUserName();
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chat_id+"");
                sendMessage.setText("hi "+userName+ "! How is it going?");
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
}
