package com.swordintent.wx.mp.biz.bot;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.builder.VoiceBuilder;
import com.swordintent.wx.mp.dao.bot.ChatBotInfoRecorder;
import com.swordintent.wx.mp.dependency.NlpTextChatService;
import com.swordintent.wx.mp.service.TtlSynthesisVoiceMediaService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
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

    private final NlpTextChatService nlpTextChatService;

    private final TtlSynthesisVoiceMediaService wechatVoiceMessageService;

    private final ChatBotInfoRecorder chatBotInfoRecorder;

    private static final String ERROR_RESPONSE = "好像出错了";

    public WxMpXmlOutMessage process(WxMpXmlMessage wxMessage,
                                     Map<String, Object> context, WxMpService weixinService,
                                     WxSessionManager sessionManager) throws Exception {
        String input = getUserInputContent(wxMessage);
        String fromUser = getFromUser(wxMessage);
        String botText = chatWithbot(input, fromUser);
        recordChatInfo(input, fromUser, botText);
        return buildTextOrVoiceOutMessage(wxMessage, weixinService, botText);
    }

    private void recordChatInfo(String input, String fromUser, String botText) {
        chatBotInfoRecorder.record(fromUser, input, botText);
    }

    private WxMpXmlOutMessage buildTextOrVoiceOutMessage(WxMpXmlMessage wxMessage, WxMpService weixinService, String botText) {
        WxMpXmlOutMessage outMessage = null;
        if (needVoiceMessage(botText)) {
            outMessage = generateVoiceOutMessage(wxMessage, weixinService, botText);
        }
        outMessage = Optional.ofNullable(outMessage)
                .orElse(generateTextOutMessage(wxMessage, weixinService, botText));
        return outMessage;
    }

    private String chatWithbot(String userInputContent, String fromUser) throws Exception {
        String nlpResponse = nlpTextChatService.chat(fromUser, userInputContent);
        return Optional.ofNullable(nlpResponse).orElse(ERROR_RESPONSE);
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

    private boolean needVoiceMessage(String botText) {
        if(StringUtils.contains(botText, "语音")){
            return true;
        }
        return RandomUtils.nextInt(1, 10) == 1;
    }

    private WxMpXmlOutVoiceMessage generateVoiceOutMessage(WxMpXmlMessage wxMessage, WxMpService weixinService, String content) {
        String mediaId = wechatVoiceMessageService.ttlSynthesis(getFromUser(wxMessage), weixinService, content);
        return Optional.ofNullable(mediaId)
                .map(media -> new VoiceBuilder(media).build(wxMessage, weixinService))
                .orElse(null);
    }

    private WxMpXmlOutTextMessage generateTextOutMessage(WxMpXmlMessage wxMessage, WxMpService weixinService, String content) {
        return new TextBuilder(content).build(wxMessage, weixinService);
    }
}
