package com.kai.demo.provider;

import com.alibaba.fastjson.JSON;
import com.kai.demo.dto.AccessTokenDTO;
import com.kai.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;


@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String content = JSON.toJSONString(accessTokenDTO);
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token/")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/user?access_token=" + accessToken;
        Request request = new Request.Builder()
                .header("Authorization",  "token " + accessToken)
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
