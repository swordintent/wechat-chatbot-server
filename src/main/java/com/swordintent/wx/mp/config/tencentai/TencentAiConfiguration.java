package com.swordintent.wx.mp.config.tencentai;

import com.swordintent.wx.mp.dependency.util.TencentAiRobotClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tts.v20190823.TtsClient;
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
    public TencentAiRobotClient tbpClient() {

        return new TencentAiRobotClient(properties.getAppid(), properties.getAppkey(), properties.getRobotId());
    }

    @Bean
    public TtsClient ttsClient() {
        Credential cred = new Credential(properties.getAppid(), properties.getAppkey());
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("tts.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        TtsClient client = new TtsClient(cred, "ap-beijing", clientProfile);
        return client;
    }
}
