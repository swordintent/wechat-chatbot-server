package com.swordintent.wx.mp.config.ai;

import cn.xsshome.taip.nlp.TAipNlp;
import com.swordintent.wx.mp.dependency.NlpTextChatService;
import com.swordintent.wx.mp.dependency.impl.baiduai.BaiduAiNlpTextChatImpl;
import com.swordintent.wx.mp.dependency.impl.tencentai.TencentAiNlpTextChatImpl;
import com.swordintent.wx.mp.dependency.util.BaiduAipClient;
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

    private final TAipNlp tAipNlp;

    private final BaiduAipClient baiduAipClient;

    @Bean
    @ConditionalOnProperty(name = "ai.cfg.text-chat", havingValue = "baidu")
    public NlpTextChatService baiduAiNlpTextChatImpl() {
        return new BaiduAiNlpTextChatImpl(baiduAipClient);
    }

    @Bean
    @ConditionalOnProperty(name = "ai.cfg.text-chat", havingValue = "tencent")
    public NlpTextChatService tencentAiNlpTextChatImpl() {
        return new TencentAiNlpTextChatImpl(tAipNlp);
    }

}
