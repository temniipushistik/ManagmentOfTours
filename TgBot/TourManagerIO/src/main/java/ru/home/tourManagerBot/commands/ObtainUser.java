package ru.home.tourManagerBot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.BotImplementation;

import java.util.ArrayList;

public class ObtainUser {
    static int flagOfObtaining = 0;
    static int sizeOfBD = 0;
    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public SendMessage run(Update update) {
        sizeOfBD = BotImplementation.mainClientBD.size();

        if (update.getMessage().getText().equals("Получить пользователя") && (sizeOfBD > 0)) {
            BotImplementation.setObtain(true);
            flagOfObtaining = 1;

            //запрашивает почту
            return inputEmail(update);

        } else if (update.getMessage().getText().equals("Получить пользователя") && (sizeOfBD == 0)) {
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new Start().run(update);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("В базе ничего нет. Чтобы что-то отредактировать нужно сначала что-то создать");
            return sendMessage;


        } else if ((flagOfObtaining == 1) && (update.getMessage().getText() != null)) {
            String inputMail = update.getMessage().getText();
            String baseMail = BotImplementation.mainClientBD.get("email");
            //проверяем на наличие в базе
            if (inputMail.equals(baseMail)) {
                return finish(update);

            } else {
                setConfirmationKeyboardMarkup();
                SendMessage sendMessage = new Start().run(update);
                sendMessage.setChatId(update.getMessage().getChatId() + "");
                sendMessage.setText("Такой почты нет. Попробуйте снова");
                return sendMessage;
            }


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
        sendMessage.setText("вылетел из цикла, косяк в логике");

        return sendMessage;
    }

    private SendMessage finish(Update update) {
        String textMessage = "Клиент найден:\n";// = client.get("sourceOfTraffic") + "";
        for (String name : BotImplementation.mainClientBD.keySet()) {
            textMessage += (name + " : " + BotImplementation.mainClientBD.get(name) + "\n");
        }
        BotImplementation.setObtain(false);

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

