package com.swordintent.wx.mp.config.baiduai;

import com.baidu.aip.speech.AipSpeech;
import com.swordintent.wx.mp.dependency.util.BaiduAipClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 百度AI相关实体构造器
 *
 * @author liuhe
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({BaiduAiProperties.class})
public class BaiduAiConfiguration {

    private final BaiduAiProperties properties;

    @Bean
    public BaiduAipClient baiduUnitClient() {
        return new BaiduAipClient(properties.getAppid(), properties.getAppkey(), properties.getSecretKey(), properties.getRobotId());
    }

    @Bean
    public AipSpeech baiduSpeechClient() {
        return new AipSpeech(properties.getAppid(), properties.getAppkey(), properties.getSecretKey());
    }
}
