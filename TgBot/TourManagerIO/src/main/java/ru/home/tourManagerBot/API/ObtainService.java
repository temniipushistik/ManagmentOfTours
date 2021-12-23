package ru.home.tourManagerBot.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import ru.home.tourManagerBot.DTO.request.ObtainClientRequest;
import ru.home.tourManagerBot.DTO.response.UniqueResponse;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

public class ObtainService {
    public static UniqueResponse postJSon(ObtainClientRequest request) throws JsonProcessingException {
        UniqueResponse uniqueResponse;

        //HttpClient client = HttpClientBuilder.create().build();
        //мне нужен пост запрос, который будет обращаться к адресу в конструкторе
        HttpClient client=null;
        try {
            client = HttpClients
                    .custom()
                    .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        HttpGet get = new HttpGet("https://localhost:8443/api/client/obtain/"+request.getEmail());

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
