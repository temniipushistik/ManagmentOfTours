package ru.home.tourManagerBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.tourManagerBot.commands.*;

import java.util.HashMap;

public class BotImplementation extends TelegramLongPollingBot {
    private static final String TOKEN = "2067787448:AAEsIUyOxkUGdB4SrRn0aJHprr3IsjzwUOk";
    private static final String USERNAME = "Tour_ManagerBot";
    //private String userName;
    private String managerName;

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
//static HashMap<String, String> client = new HashMap<>();


    public static void setCreate(boolean create) {
        BotImplementation.create = create;
    }

    //статусы для операций
    private static boolean create = false;
    private static boolean change = false;
    private static boolean obtain = false;
    private static boolean delete = false;

    public static boolean isDelete() {
        return delete;
    }

    public static void setDelete(boolean delete) {
        BotImplementation.delete = delete;
    }

    public static boolean isObtain() {
        return obtain;
    }

    public static void setObtain(boolean obtain) {
        BotImplementation.obtain = obtain;
    }

    public static boolean isChange() {
        return change;
    }

    public static void setChange(boolean change) {
        BotImplementation.change = change;
    }

    //создаем клавиатуру:
    // ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    private long chat_id;


    //создаем мапу для хранения полученных данных. В стринг - @имя, массив эррея - данные об этом
    //пользователе
    public static HashMap<String, HashMap<String, String>> userState = new HashMap<>();
    public static HashMap<String,String> mainClientBD = new HashMap<>();


    public long getChat_id() {
        return chat_id;
    }

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
            chat_id = update.getMessage().getChatId();
            setManagerName(update.getMessage().getFrom().getUserName());
            //получаем ник пользователя


            String text = update.getMessage().getText();
            try {
                if (text.equals("/start")) {
                    execute(new Start().run(update));
//если нажато добавить пользователя или в хэшмапе есть значение и в этом значении(эррейлисте) первое значение "добавить пользователя"
                } else if (text.equals("Добавить пользователя") || (create == true)) {
                    //выводит сообщение введите нужный емейл:
                    execute(new CreateUser2().run(update));

                } else if (text.equals("Редактировать пользователя") || (change ==true)) {
                    execute(new ChangeUser().run(update));

                }else if (text.equals("Получить пользователя")|| (obtain ==true)){
                    execute((new ObtainUser().run(update)));
                }
            else if (text.equals("Получить всех пользователей")){
                execute((new ObtainAll().run(update)));
            }
                else if (text.equals("Удалить пользователя")||delete==true){
                    execute((new DeleteUser().run(update)));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}