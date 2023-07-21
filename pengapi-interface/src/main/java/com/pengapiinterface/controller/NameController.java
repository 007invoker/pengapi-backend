package com.pengapiinterface.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.pengapiclientsdk.model.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 查询名称接口
 */
@RestController
@RequestMapping("/name")
public class NameController {
    @GetMapping("/")
    public String getNameByGet(String name){
        return "Get 你的名字是:"+name;
    }
    @PostMapping("/")
    public String getNameByPost(String name){
        return "Post 你的名字是:"+name;
    }
    @PostMapping("/json")
    public String getNameByJson(@RequestBody User user, HttpServletRequest request){
        return "Json 你的名字是:"+user.getName();
    }
    @GetMapping("/qq")
    public String getQQNameAndHeadImg(@RequestBody User user){
        System.out.println(user);
        String url = "https://dwz.btstu.cn/qqxt/api.php?qq=" + user.getAccount();
        HttpResponse response = HttpRequest.get(url).execute();
        if(StringUtils.isEmpty(user)){
            return "账号输入错误或该账号不存在";
        }
        return response.body();
    }

    @GetMapping("/zhiyin")
    public String getZhiYin(){
        String url = "https://dwz.btstu.cn/yan/api.php?charset=utf-8&encode=text";
        HttpResponse response = HttpRequest.get(url).execute();
        return response.body();
    }

}
