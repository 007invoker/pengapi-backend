package com.pengapiclientsdk.clint;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pengapiclientsdk.model.User;
import com.pengapiclientsdk.utils.SignUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


/**
 * 三方接口调用客户端
 */
@Data
@NoArgsConstructor
public class PengApiClint {
    private String accessKey;//签名
    private String secretKey;//签名密匙
//    private String GATEWAY_HOST = "http://localhost:8090";

    private String GATEWAY_HOST="http://175.178.181.126:8090";
    public PengApiClint(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", map);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", map);
        System.out.println(result);
        return result;
    }

    private Map<String, String> getHeaders(String body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);
        //密匙,一定不能发个客户端发送
//        headers.put("secretKey",secretKey);
        headers.put("nonce", RandomUtil.randomNumbers(4));//随机数
        headers.put("body", body);
        headers.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));//时间戳
        headers.put("sign", SignUtil.getSign(body, secretKey));
        return headers;
    }

    public String getNameByJson(String name) {
        User user = new User();
        user.setName(name);
        String json = JSONUtil.toJsonStr(user);
        HttpResponse execute = HttpRequest.post(GATEWAY_HOST + "/api/name/json")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();
        System.out.println(execute.getStatus());
        String result = execute.body();
        System.out.println(result);
        return result;
    }

    public String getQQNameAndHeadImg(String account) {
        JSONObject object = JSONUtil.parseObj(account);
        String user_account = (String)object.get("qq");
        User user = new User();

        user.setName("");
        user.setAccount(user_account);
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.get(GATEWAY_HOST + "/api/name/qq")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();

        return response.body();
    }

    public String getZhiYin() {
        User user = new User();
        user.setName("");
        user.setAccount("");
        String json = JSONUtil.toJsonStr(user);
        HttpResponse response = HttpRequest.get(GATEWAY_HOST + "/api/name/zhiyin")
                .addHeaders(getHeaders(json))
                .body(json)
                .execute();
        return response.body();
    }
}
