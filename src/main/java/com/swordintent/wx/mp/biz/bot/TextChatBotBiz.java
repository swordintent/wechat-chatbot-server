package com.swordintent.wx.mp.biz.bot;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.dao.bot.ChatBotInfoRecorder;
import com.swordintent.wx.mp.dependency.NlpTextChatService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
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
public class TextChatBotBiz implements BotBiz {

    private final NlpTextChatService nlpTextChatService;

    private final ChatBotInfoRecorder chatBotInfoRecorder;

    private static final String ERROR_RESPONSE = "好像出错了";

    @Override
    public WxMpXmlOutMessage process(WxMpXmlMessage wxMessage,
                                     Map<String, Object> context, WxMpService weixinService,
                                     WxSessionManager sessionManager) throws Exception {
        String inputContent = getUserInputContent(wxMessage);
        String fromUser = getFromUser(wxMessage);
        String retContent = chatWithbot(inputContent, fromUser);
        recordChatInfo(inputContent, fromUser, retContent);
        return generateTextOutMessage(wxMessage, weixinService, retContent);
    }

    private void recordChatInfo(String input, String fromUser, String botText) {
        chatBotInfoRecorder.record(fromUser, input, botText);
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

    private WxMpXmlOutTextMessage generateTextOutMessage(WxMpXmlMessage wxMessage, WxMpService weixinService, String content) {
        return new TextBuilder(content).build(wxMessage, weixinService);
    }
}
