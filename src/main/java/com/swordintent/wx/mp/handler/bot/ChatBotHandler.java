package com.swordintent.wx.mp.handler.bot;

import com.swordintent.wx.mp.biz.bot.TextMsgChatBotBiz;
import com.swordintent.wx.mp.handler.MsgHandler;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 文本消息-聊天机器人处理
 *
 * @author liuhe
 */
@Component
@AllArgsConstructor
public class ChatBotHandler extends MsgHandler {

    private final TextMsgChatBotBiz textMsgChatBotBiz;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        try {
            String msgType = wxMessage.getMsgType();
            if(isTextMsg(msgType)){
                return textMsgChatBotBiz.process(wxMessage, context, weixinService, sessionManager);
            }
            return textMsgChatBotBiz.process(wxMessage, context, weixinService, sessionManager);
        } catch (Exception e) {
            logger.error(String.format("wxMessage:%s", wxMessage), e);
        }
        return super.handle(wxMessage, context, weixinService, sessionManager);
    }

    private boolean isTextMsg(String msgType) {
        return StringUtils.equals(WxConsts.XmlMsgType.TEXT, msgType);
    }

    @Override
    public boolean match(WxMpXmlMessage wxMpXmlMessage) {
        return true;
    }
}
