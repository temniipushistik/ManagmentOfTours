package ru.home.tourManagerBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class createUser {
    private String email;
    private String name;
    private String phone;
    private String sourceOfTraffic;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSourceOfTraffic() {
        return sourceOfTraffic;
    }

    public void setSourceOfTraffic(String sourceOfTraffic) {
        this.sourceOfTraffic = sourceOfTraffic;
    }

    public String responseCreateUser(String msg) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboardCreateUser = new ArrayList<>();
        KeyboardRow keyboardRowFirstRowCreateUser = new KeyboardRow();
        KeyboardRow keyboardRowSecondRowCreateUser = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);//видно всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true);//подгоняет клавиатуру под высоту кнопок
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        keyboardRowFirstRowCreateUser.add("Далее");
        keyboardRowSecondRowCreateUser.add("Отменить действие и сделать шаг назад");
        replyKeyboardMarkup.setKeyboard(keyboardCreateUser);

        if (msg.contains("@")&msg.contains(".")) {
            setEmail(msg);

            return "введите имя пользователя:";

        }else
            return "почта введена неверно";


    }
}
