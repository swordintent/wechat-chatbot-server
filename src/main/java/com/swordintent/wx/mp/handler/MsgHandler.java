package com.swordintent.wx.mp.handler;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.ToString;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        //默认的消息处理
        String content = "收到你的信息了";
        logger.info("default log \001{}\001{}", ReflectionToStringBuilder.toString(wxMessage), content);
        return new TextBuilder().build(content, wxMessage, weixinService);
    }
}
