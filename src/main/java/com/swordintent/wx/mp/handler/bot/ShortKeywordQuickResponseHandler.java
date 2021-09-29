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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 切换工作模式
 *
 * @author liuhe
 */
@Component
@AllArgsConstructor
public class ShortKeywordQuickResponseHandler extends MsgHandler {

    private static final Map<String, String> RESPONSE_MAP = new HashMap<>();

    static {
        RESPONSE_MAP.put("查询指令", getQueryCommand());
    }

    private static String getQueryCommand() {
        ContextMode[] values = ContextMode.values();
        StringBuilder sb = new StringBuilder();
        sb.append("请回复：\n");
        String collect = Arrays.stream(values).map((x) -> String.format("%s：切换至[%s]模式\n\n", x.getCmd(), x.getDesc()))
                .collect(Collectors.joining());
        sb.append(collect);
        String queryCommand = sb.toString();
        return queryCommand;
    }

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        try {
            String commandRet = RESPONSE_MAP.get(wxMessage.getContent());
            if(Objects.nonNull(commandRet)){
                return new TextBuilder(commandRet).build(wxMessage, weixinService);
            }
        } catch (Exception e) {
            logger.error(String.format("wxMessage:%s", wxMessage), e);
        }
        return super.handle(wxMessage, context, weixinService, sessionManager);
    }

    @Override
    public boolean match(WxMpXmlMessage wxMpXmlMessage) {
        return RESPONSE_MAP.containsKey(wxMpXmlMessage.getContent());
    }
}
