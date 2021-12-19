package ru.home.tourManagerBot;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.tourManagerBot.commands.*;

import java.io.IOException;
import java.util.HashMap;

public class BotImplementation extends TelegramLongPollingBot {
    // old private static final String TOKEN = "2067787448:AAEsIUyOxkUGdB4SrRn0aJHprr3IsjzwUOk";
    private static final String TOKEN = "";
    private static final String USERNAME = "Tour_ManagerBot";
    //private String userName;
    private String managerName;

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    //создаем мапу для хранения полученных данных. В стринг - @имя, массив эррея - данные об этом
    //пользователе
    public static HashMap<String, HashMap<String, String>> managerAndClient = new HashMap<>();
    //создаем хэшмапу с флагами для каждого менеджера тура
    public static HashMap<String, Integer> flags = new HashMap<>();//-1 - пустой флаг, 0 - create, 1- change, 2- obtain, 3 - delete

    public static HashMap<String, Integer> steps = new HashMap<>();//номера шагов внутри флагов создать, редактировать и пр

    public String getBotUsername() {
        return USERNAME;
    }

    public String getBotToken() {
        return TOKEN;
    }

    //этот метод вызывается !!!каждый раз!!! когда пользователь отправляет сообщение и всё сначала
    //жопа с тем, что каждый раз мы заново проверяем
    public void onUpdateReceived(Update update) {

        if (update.getMessage() != null && update.getMessage().hasText()) {
            setManagerName(update.getMessage().getFrom().getUserName());
            //получаем ник пользователя
            String text = update.getMessage().getText();
            try {
                if (text.equals("/start")) {
                    execute(new Start().run(update));
                } else if (text.equals("Добавить пользователя") || (flags.get(update.getMessage().getFrom().getUserName()) == 0)) {
                    //выводит сообщение введите нужный емейл:
                    execute(new CreateClient().run(update));
                } else if ((text.equals("Редактировать пользователя") && (flags.get(update.getMessage().getFrom().getUserName()) == null)) || (flags.get(update.getMessage().getFrom().getUserName()) == 1)) {
                    execute(new ChangeClient().run(update));
                } else if (text.equals("Получить пользователя") || (flags.get(update.getMessage().getFrom().getUserName()) == 2)) {
                    execute((new ObtainClient().run(update)));
                } else if (text.equals("Удалить пользователя") || (flags.get(update.getMessage().getFrom().getUserName()) == 3)) {
                    execute(new DeleteClient().run(update));
                } else if (text.equals("Получить всех пользователей")) {
                    execute((new ObtainAllClients().run(update)));


                }

//если нажато добавить пользователя или в хэшмапе есть значение и в этом значении(эррейлисте) первое значение "добавить пользователя"

            } catch (TelegramApiException | JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}