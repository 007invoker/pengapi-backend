package com.peng.gateway;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.stereotype.Service;

//取消数据库的自动注入
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
@EnableDubbo
@Service
public class PengGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PengGatewayApplication.class, args);
//        ConfigurableApplicationContext context = SpringApplication.run(PengGatewayApplication.class, args);
//        PengGatewayApplication application = context.getBean(PengGatewayApplication.class);
//        String result = application.doSayHello("world");
//        String result2 = application.doSayHello2("world");
//        System.out.println("result: " + result);
//        System.out.println("result: " + result2);
    }
//    public String doSayHello(String name) {
//        return demoService.sayHello(name);
//    }
//
//    public String doSayHello2(String name) {
//        return demoService.sayHello2(name);
//    }
}
