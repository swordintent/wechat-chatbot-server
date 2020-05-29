package com.swordintent.wx.mp.config;

import cn.xsshome.taip.nlp.TAipNlp;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({AiQQProperties.class})
public class AiQQConfiguration {

    private final AiQQProperties properties;

    @Bean
    public TAipNlp tAipNlp() {
        return new TAipNlp(properties.getAppid(), properties.getAppkey());
    }
}
