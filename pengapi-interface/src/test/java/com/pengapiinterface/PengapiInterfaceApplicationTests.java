package com.pengapiinterface;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import com.pengapiclientsdk.clint.PengApiClint;

@SpringBootTest
class PengapiInterfaceApplicationTests {

    @Resource
    private  PengApiClint pengApiClint;
    @Test
    void contextLoads() {
        String result1 = pengApiClint.getNameByJson("invoker");
        pengApiClint.getNameByGet("invoker");
        pengApiClint.getNameByPost("invoker");
    }
}
