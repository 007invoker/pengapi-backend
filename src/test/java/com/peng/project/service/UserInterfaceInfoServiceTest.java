package com.peng.project.service;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserInterfaceInfoServiceTest {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Test
    public void invokeCount() {
        boolean isTrue = userInterfaceInfoService.invokeCount(1, 1);
        System.out.println(isTrue);
    }
}