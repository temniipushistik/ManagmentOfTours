package ru.home.tourManagerBot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.tourManagerBot.BotImplementation;

import java.util.ArrayList;

public class CreateUser {

    private ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    //получаем update из botImplementation
    public SendMessage run(Update update){

        //получаем имя менеджера
        String userName = update.getMessage().getFrom().getUserName();
//если инфа, которую мы получили добавить пользователя и у нас нет еще этого пользователя то
        if (update.getMessage().getText().equals("Добавить пользователя") && (BotImplementation.userState.get(userName) == null)){
            ArrayList<String> status = new ArrayList<>();
            //нулевое значение массива - действие:
            status.add("Добавить пользователя");
            //в нулевой элемент хэша кладем имя пользователя и статус
            BotImplementation.userState.put(userName,status);
            //возвращаем те же данные в добавить почту
            return addEmail(update);
            //если имя клиента есть и там в массиве уже один элемент - функция
        } else if ((BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 1))
        {
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("Email: "+ update.getMessage().getText());
            return sendMessage;
        } else if (update.getMessage().getText().equals("Далее") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 2)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            return addName(update);
        } else if (update.getMessage().getText().equals("Назад") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 2)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.remove(status.size()-1);
            BotImplementation.userState.put(userName,status);
            return addEmail(update);
        }
        else if ((BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 3))
        {
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("Name: "+ update.getMessage().getText());
            return sendMessage;
        }else if (update.getMessage().getText().equals("Далее") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 4)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            return addPhone(update);
        } else if (update.getMessage().getText().equals("Назад") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 4)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.remove(status.size()-1);
            BotImplementation.userState.put(userName,status);
            return addName(update);
        }
        else if ((BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 5))
        {
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("Phone: "+ update.getMessage().getText());
            return sendMessage;
        } else if (update.getMessage().getText().equals("Далее") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 6)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            return addTrafic(update);
        }else if (update.getMessage().getText().equals("Назад") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 6)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.remove(status.size()-1);
            BotImplementation.userState.put(userName,status);
            return addPhone(update);
        }
        else if ((BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 7))
        {
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            setConfirmationKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setChatId(update.getMessage().getChatId() + "");
            sendMessage.setText("Трафик: "+ update.getMessage().getText());
            return sendMessage;
        } else if (update.getMessage().getText().equals("Далее") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 8)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.add(update.getMessage().getText());
            BotImplementation.userState.put(userName,status);
            return finish(update);
        } else if (update.getMessage().getText().equals("Назад") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 8)){
            ArrayList<String> status = BotImplementation.userState.get(userName);
            status.remove(status.size()-1);
            BotImplementation.userState.put(userName,status);
            return addTrafic(update);
        } else if (update.getMessage().getText().equals("в главное меню") && (BotImplementation.userState.get(userName) != null) && (BotImplementation.userState.get(userName).size() == 8)){
            return new Start().run(update);
        }
        return new Start().run(update);
    }

    private SendMessage addEmail(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите пожалуйста корректный Email");
        return sendMessage;
    }

    private SendMessage addPhone(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите пожалуйста корректный номер телефона");
        return sendMessage;
    }

    private SendMessage addName(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите пожалуйста корректное имя");
        return sendMessage;
    }

    private SendMessage addTrafic(Update update){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId() + "");
        sendMessage.setText("Введите пожалуйста корректный источник трафика");
        return sendMessage;
    }

    private SendMessage finish(Update update){
        String textMessage = "Пользователь успешно добавлен "
                + BotImplementation.userState.get(update.getMessage().getFrom().getUserName()).get(1) + " "
                + BotImplementation.userState.get(update.getMessage().getFrom().getUserName()).get(3) + " "
                + BotImplementation.userState.get(update.getMessage().getFrom().getUserName()).get(5) + " "
                + BotImplementation.userState.get(update.getMessage().getFrom().getUserName()).get(7);
        SendMessage sendMessage = new Start().run(update);
        sendMessage.setText(textMessage);
        return sendMessage;
    }
    //создаем клавиатуру:
    private void setConfirmationKeyboardMarkup(){

        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardCorrect = new KeyboardRow();
        KeyboardRow keyboardInCorrect = new KeyboardRow();
        KeyboardRow keyboardCancel = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);//видно всем пользователям
        replyKeyboardMarkup.setResizeKeyboard(true);//подгоняет клавиатуру под высоту кнопок
        replyKeyboardMarkup.setOneTimeKeyboard(true);//скрывать клаву после использования?

        keyboard.clear();
        keyboardCorrect.add("Далее");
        keyboardInCorrect.add("Назад");
        keyboardCancel.add("в главное меню");

        keyboard.add(keyboardCorrect);
        keyboard.add(keyboardInCorrect);
        keyboard.add(keyboardCancel);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }


}
