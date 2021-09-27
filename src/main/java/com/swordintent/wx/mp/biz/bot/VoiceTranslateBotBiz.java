package com.swordintent.wx.mp.biz.bot;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.builder.VoiceBuilder;
import com.swordintent.wx.mp.dao.bot.ChatBotInfoRecorder;
import com.swordintent.wx.mp.service.TtlSynthesisVoiceMediaService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;
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
public class VoiceTranslateBotBiz implements BotBiz {

    private final TtlSynthesisVoiceMediaService wechatVoiceMessageService;

    private final ChatBotInfoRecorder chatBotInfoRecorder;

    private static final String ERROR_RESPONSE = "好像出错了";

    @Override
    public WxMpXmlOutMessage process(WxMpXmlMessage wxMessage,
                                     Map<String, Object> context, WxMpService weixinService,
                                     WxSessionManager sessionManager) throws Exception {
        String inputContent = getUserInputContent(wxMessage);
        String fromUser = getFromUser(wxMessage);
        recordChatInfo(inputContent, fromUser, "[语音回复]");
        if(StringUtils.length(inputContent) > 150){
            return new TextBuilder("字数太多了，请限制在150个字符以内哦（含标点符号）").build(wxMessage, weixinService);
        }
        return generateVoiceOutMessage(wxMessage, weixinService, inputContent);
    }

    private void recordChatInfo(String input, String fromUser, String botText) {
        chatBotInfoRecorder.record(fromUser, input, botText);
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

    private WxMpXmlOutVoiceMessage generateVoiceOutMessage(WxMpXmlMessage wxMessage, WxMpService weixinService, String content) {
        String fromUser = getFromUser(wxMessage);
        String mediaId = wechatVoiceMessageService.ttlSynthesis(fromUser, weixinService, content);
        return Optional.ofNullable(mediaId)
                .map(media -> new VoiceBuilder(media).build(wxMessage, weixinService))
                .orElse(null);
    }
}
