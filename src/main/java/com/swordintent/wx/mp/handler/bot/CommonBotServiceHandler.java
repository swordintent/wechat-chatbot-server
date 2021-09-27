package com.swordintent.wx.mp.handler.bot;

import com.swordintent.wx.mp.biz.bot.BotBiz;
import com.swordintent.wx.mp.biz.bot.BotBizFactory;
import com.swordintent.wx.mp.dao.bot.ContextModeDao;
import com.swordintent.wx.mp.handler.MsgHandler;
import com.swordintent.wx.mp.pojo.bot.ContextMode;
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
public class CommonBotServiceHandler extends MsgHandler {

    private final BotBizFactory botBizFactory;
    private final ContextModeDao contextModeDao;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        try {
            //获取用户所在模式
            String fromUser = wxMessage.getFromUser();
            ContextMode contextMode = contextModeDao.getContextMode(fromUser);
            //基于所在模式进行逻辑处理
            BotBiz bot = botBizFactory.getBot(contextMode);
            logger.info("user is:{}, context mode is:{}, bot is:{}", fromUser, contextMode, bot);
            return bot.process(wxMessage, context, weixinService, sessionManager);
        } catch (Exception e) {
            logger.error(String.format("wxMessage:%s", wxMessage), e);
        }
        return super.handle(wxMessage, context, weixinService, sessionManager);
    }

    @Override
    public boolean match(WxMpXmlMessage wxMpXmlMessage) {
        return true;
    }
}
