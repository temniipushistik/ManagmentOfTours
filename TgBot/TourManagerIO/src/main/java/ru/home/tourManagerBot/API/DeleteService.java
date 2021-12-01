package ru.home.tourManagerBot.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import ru.home.tourManagerBot.DTO.request.DeleteClientRequest;
import ru.home.tourManagerBot.DTO.response.UniqueResponse;

public class DeleteService {
    public static UniqueResponse postJSon(DeleteClientRequest request) throws JsonProcessingException {
        UniqueResponse uniqueResponse;
        //создаем объект, содержит в себе API для запросов(POST|GET|DELETE) к адресу, который укажется ниже
        HttpClient client = HttpClientBuilder.create().build();
        //мне нужен пост запрос, который будет обращаться к адресу в конструкторе
        HttpPost delete = new HttpPost("http://localhost:8080/api/client/delete");
        //конвертируем DTO объект, полученный из фронта, в JSON-строку для пересылки на сервер:
        String requestToJSON = new ObjectMapper().writeValueAsString(request);

        try {
            //указываем кодировку  для JAVA строка будет в кодировке UTF-8
            StringEntity strInJSON = new StringEntity(requestToJSON, "UTF-8");
            //указываем формат данных, которые мы передаем в качестве хидера, что присылаем ему json, ключ хидера - setContentType
            strInJSON.setContentType("application/json;charset=utf-8");
            //добавляет в запрос мои данные:
            delete.setEntity(strInJSON);
            //выполняем запрос, в рес запишется значения, которые мне вернулись от сервера
            HttpResponse res = client.execute(delete);

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


