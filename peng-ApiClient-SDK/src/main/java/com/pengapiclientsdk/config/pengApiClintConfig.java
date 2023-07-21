package com.pengapiclientsdk.config;

import com.pengapiclientsdk.clint.PengApiClint;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("pengapi.clint")
@ComponentScan
public class pengApiClintConfig {
    private String accessKey;
    private String secretKey;
    @Bean
    public PengApiClint pengApiClint() {
       return new PengApiClint(accessKey,secretKey);
    }
}