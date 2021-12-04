package ru.home.tourManagerBot.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import ru.home.tourManagerBot.DTO.response.UniqueResponse;

public class ObtainAllService {

    public static UniqueResponse postJSon() throws JsonProcessingException {

        UniqueResponse uniqueResponse;

        HttpClient client = HttpClientBuilder.create().build();
        //мне нужен пост запрос, который будет обращаться к адресу в конструкторе

        HttpGet get = new HttpGet("http://localhost:8080/api/client/obtainAll");
        //конвертируем DTO объект, полученный из фронта, в JSON-строку для пересылки на сервер:
        try {

            //выполняем запрос, в рес запишется значения, которые мне вернулись от сервера
            HttpResponse res = client.execute(get);

            System.out.println(res.getStatusLine().getStatusCode());
            //преобразуем ответ от сервера в строку формату ютф-8
            String result = EntityUtils.toString(res.getEntity(), "UTF-8");
            //создаем объект который нужно вернуть в аргументе указываем сам объект и в какой класс нужно его преобразовать
            uniqueResponse = new ObjectMapper().readValue(result, UniqueResponse.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return uniqueResponse;


    }

}

