package ru.home.tourManagerBot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.BotImplementation;

import java.util.ArrayList;

public class ObtainAllClients {

    static int sizeOfBD = 0;
    // private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public SendMessage run(Update update) {
        sizeOfBD = BotImplementation.mainClientBD.size();

        if (update.getMessage().getText().equals("Получить всех пользователей") && (sizeOfBD != 0)) {

            return finish(update);

        } else if (update.getMessage().getText().equals("Получить всех пользователей") && (sizeOfBD == 0)) {
            //  setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new Start().run(update);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("В базе ничего нет. Чтобы что-то получить нужно сначала что-то создать");
            return sendMessage;

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

    private SendMessage finish(Update update) {
        String textMessage = "Данные по всем клиентам:\n";// = client.get("sourceOfTraffic") + "";
        for (String name : BotImplementation.mainClientBD.keySet()) {
            textMessage += (name + " : " + BotImplementation.mainClientBD.get(name) + "\n");
        }
        SendMessage sendMessage = new Start().run(update);
        sendMessage.setChatId(update.getMessage().getChatId() + "");

        sendMessage.setText(textMessage);
        return sendMessage;
    }


       /* private void setConfirmationKeyboardMarkup() {

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
      */
}


