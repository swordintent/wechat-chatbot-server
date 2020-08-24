package com.swordintent.wx.mp.config.tencentai;

import cn.xsshome.taip.nlp.TAipNlp;
import cn.xsshome.taip.speech.TAipSpeech;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯AI相关实体构造器
 * @author liuhe
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({TencentAiProperties.class})
public class TencentAiConfiguration {

    private final TencentAiProperties properties;

    @Bean
    public TAipNlp tAipNlp() {
        return new TAipNlp(properties.getAppid(), properties.getAppkey());
    }

    @Bean
    public TAipSpeech tAipSpeech() {
        return new TAipSpeech(properties.getAppid(), properties.getAppkey());
    }

}
