package com.swordintent.wx.mp.handler.bot;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.dao.bot.ContextModeDao;
import com.swordintent.wx.mp.handler.MsgHandler;
import com.swordintent.wx.mp.pojo.bot.ContextMode;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 切换工作模式
 *
 * @author liuhe
 */
@Component
@AllArgsConstructor
public class SwitchContextModeHandler extends MsgHandler {

    private final ContextModeDao contextModeDao;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        try {
            ContextMode mode = getContextMode(wxMessage);
            setContextMode(wxMessage, mode);
            return new TextBuilder(String.format("切换至[%s]模式成功", mode.getDesc())).build(wxMessage, weixinService);
        } catch (Exception e) {
            logger.error(String.format("wxMessage:%s", wxMessage), e);
        }
        return super.handle(wxMessage, context, weixinService, sessionManager);
    }

    private void setContextMode(WxMpXmlMessage wxMessage, ContextMode mode) {
        if(mode != null){
            contextModeDao.setContextMode(wxMessage.getFromUser(), mode);
        }
    }

    @Override
    public boolean match(WxMpXmlMessage wxMpXmlMessage) {
        ContextMode mode = getContextMode(wxMpXmlMessage);
        return mode != null;
    }

    private ContextMode getContextMode(WxMpXmlMessage wxMpXmlMessage) {
        String content = wxMpXmlMessage.getContent();
        return ContextMode.getMode(content);
    }
}
