package com.swordintent.wx.mp.biz.bot;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.builder.VoiceBuilder;
import com.swordintent.wx.mp.helper.WechatFileUploader;
import com.swordintent.wx.mp.service.NlpTextChatService;
import com.swordintent.wx.mp.service.TtlSynthesisService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * 文本消息-聊天机器人处理
 *
 * @author liuhe
 */
@Component
@AllArgsConstructor
public class TextMsgChatBotBiz {

    private static final Logger logger = LoggerFactory.getLogger(TextMsgChatBotBiz.class);

    private final NlpTextChatService nlpTextChatService;

    private final TtlSynthesisService ttlSynthesisService;

    private final WechatFileUploader wechatFileUploader;

    private final ChatBotInfoRecorder chatBotInfoRecorder;

    private static final String CHAT_BOT_ERROR_RESPONSE = "好像出错了";

    public WxMpXmlOutMessage process(WxMpXmlMessage wxMessage,
                                     Map<String, Object> context, WxMpService weixinService,
                                     WxSessionManager sessionManager) throws Exception {
        String input = getUserInputContent(wxMessage);
        String fromUser = getFromUser(wxMessage);
        //聊天，返回文本
        String botText = chatWithbot(input, fromUser);
        WxMpXmlOutMessage outMessage = null;
        if (useTtlSynthesis()) {
            //需要返回语音，调用语音合成
            outMessage = ttlSynthesis(wxMessage, weixinService, botText);
        }
        outMessage = Optional.ofNullable(outMessage).orElse(text(wxMessage, weixinService, botText));
        chatBotInfoRecorder.record(fromUser, input, botText);
        return outMessage;
    }

    private String chatWithbot(String userInputContent, String fromUser) throws Exception {
        String nlpResponse = nlpTextChatService.chat(fromUser, userInputContent);
        return Optional.ofNullable(nlpResponse).orElse(CHAT_BOT_ERROR_RESPONSE);
    }

    private String getFromUser(WxMpXmlMessage wxMessage) {
        return wxMessage.getFromUser();
    }

    private String getUserInputContent(WxMpXmlMessage wxMessage) {
        String content = wxMessage.getContent();
        if (content == null) {
            content = wxMessage.getRecognition();
        }
        return content;
    }

    private WxMpXmlOutVoiceMessage ttlSynthesis(WxMpXmlMessage wxMessage, WxMpService weixinService, String responseContent) {
        try {
            byte[] ttlSynthesisResult = ttlSynthesisService.synthesis(responseContent);
            if (ttlSynthesisResult == null) {
                return null;
            }
            String mediaId = wechatFileUploader.uploadVoice(getFromUser(wxMessage), ttlSynthesisResult, weixinService);
            return voice(wxMessage, weixinService, mediaId);
        } catch (Exception e) {
            logger.warn("ttlSynthesisService error", e);
        }

        return null;
    }

    private boolean useTtlSynthesis() {
        //随机返回语音数据
        return RandomUtils.nextInt(1, 10) == 1;
    }

    private WxMpXmlOutVoiceMessage voice(WxMpXmlMessage wxMessage, WxMpService weixinService, String mediaId) {
        return new VoiceBuilder(mediaId).build(wxMessage, weixinService);
    }

    private WxMpXmlOutTextMessage text(WxMpXmlMessage wxMessage, WxMpService weixinService, String content) {
        return new TextBuilder(content).build(wxMessage, weixinService);
    }
}
