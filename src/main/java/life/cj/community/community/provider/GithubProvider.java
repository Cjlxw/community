package life.cj.community.community.provider;

import com.alibaba.fastjson2.JSON;
import life.cj.community.community.dto.AccessTokenDTO;
import life.cj.community.community.dto.GithubUser;
import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author cj
 * @apiNote TODO
 * @date 2022/9/20 15:13
 */
@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient().newBuilder().readTimeout(60, TimeUnit.SECONDS).build();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String tokenStr = split[0];
            String token = tokenStr.split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
