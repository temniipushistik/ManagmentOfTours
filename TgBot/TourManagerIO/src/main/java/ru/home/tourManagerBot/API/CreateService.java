package ru.home.tourManagerBot.API;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class CreateService {

    public static String postJSon(String strJson) {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://localhost:8080/api/client/create");
        String result = null;
        try {
            StringEntity s = new StringEntity(strJson, "UTF-8");
            s.setContentType("application/json;charset=utf-8");
            post.setEntity(s);
            HttpResponse res = client.execute(post);

            System.out.println(res.getStatusLine().getStatusCode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;


    }
}
