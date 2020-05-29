package com.swordintent.wx.mp.config;

import com.swordintent.wx.mp.handler.LogHandler;
import com.swordintent.wx.mp.handler.MsgHandler;
import com.swordintent.wx.mp.handler.SubscribeHandler;
import com.swordintent.wx.mp.handler.UnsubscribeHandler;
import com.swordintent.wx.mp.handler.bot.TextMsgChatBotHandler;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

import static me.chanjar.weixin.common.api.WxConsts.EventType.SUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.EventType.UNSUBSCRIBE;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.*;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({WxMpProperties.class})
public class WxMpConfiguration {

    private final LogHandler logHandler;

    private final MsgHandler msgHandler;

    private final UnsubscribeHandler unsubscribeHandler;
    private final SubscribeHandler subscribeHandler;

    private final WxMpProperties properties;

    private final TextMsgChatBotHandler textMsgChatBotHandler;

    @Bean
    public WxMpService wxMpService() {
        final List<WxMpProperties.MpConfig> configs = this.properties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("configs is null");
        }

        WxMpService service = new WxMpServiceImpl();
        service.setMultiConfigStorages(configs
                .stream().map(a -> {
                    WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
                    configStorage.setAppId(a.getAppId());
                    configStorage.setSecret(a.getSecret());
                    configStorage.setToken(a.getToken());
                    configStorage.setAesKey(a.getAesKey());
                    return configStorage;
                }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
        return service;
    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志 （异步执行）
        router.rule().handler(this.logHandler).next();

        // 关注事件
        router.rule().async(false).msgType(EVENT).event(SUBSCRIBE).handler(this.subscribeHandler).end();

        // 取消关注事件
        router.rule().async(false).msgType(EVENT).event(UNSUBSCRIBE).handler(this.unsubscribeHandler).end();

        //文本消息-聊天
        router.rule().async(false).msgType(TEXT).matcher(this.textMsgChatBotHandler).handler(this.textMsgChatBotHandler).end();

        //语音消息-聊天
        router.rule().async(false).msgType(VOICE).matcher(this.textMsgChatBotHandler).handler(this.textMsgChatBotHandler).end();

        // 默认
        router.rule().async(false).handler(this.msgHandler).end();

        return router;
    }

}
