package com.swordintent.wx.mp.config.ai;

import com.baidu.aip.speech.AipSpeech;
import com.swordintent.wx.mp.dependency.NlpTextChatService;
import com.swordintent.wx.mp.dependency.TtlSynthesisService;
import com.swordintent.wx.mp.dependency.impl.baiduai.BaiduAiSynthesisImpl;
import com.swordintent.wx.mp.dependency.impl.baiduai.BaiduAiNlpTextChatImpl;
import com.swordintent.wx.mp.dependency.impl.tencentai.TencentAiNlpTextChatImpl;
import com.swordintent.wx.mp.dependency.impl.tencentai.TencentAiSynthesisImpl;
import com.swordintent.wx.mp.dependency.util.BaiduAiRobotClient;
import com.swordintent.wx.mp.dependency.util.TencentAiRobotClient;
import com.tencentcloudapi.tts.v20190823.TtsClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuhe
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({AiProperties.class})
public class AiConfiguration {

    private final TencentAiRobotClient tencentAiRobotClient;

    private final TtsClient ttsClient;

    private final BaiduAiRobotClient baiduAiRobotClient;

    private final AipSpeech aipSpeech;

    @Bean
    @ConditionalOnProperty(name = "ai.cfg.text-chat", havingValue = "baidu")
    public NlpTextChatService baiduAiNlpTextChatImpl() {
        return new BaiduAiNlpTextChatImpl(baiduAiRobotClient);
    }

    @Bean
    @ConditionalOnProperty(name = "ai.cfg.text-chat", havingValue = "tencent")
    public NlpTextChatService tencentAiNlpTextChatImpl() {
        return new TencentAiNlpTextChatImpl(tencentAiRobotClient);
    }

    @Bean
    @ConditionalOnProperty(name = "ai.cfg.ttl-synthesis", havingValue = "baidu")
    public TtlSynthesisService baiduAiSynthesisImpl() {
        return new BaiduAiSynthesisImpl(aipSpeech);
    }

    @Bean
    @ConditionalOnProperty(name = "ai.cfg.ttl-synthesis", havingValue = "tencent")
    public TtlSynthesisService tencentAiSynthesisImpl() {
        return new TencentAiSynthesisImpl(ttsClient);
    }

}
