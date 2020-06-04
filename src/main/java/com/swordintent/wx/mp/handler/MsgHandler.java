package com.swordintent.wx.mp.handler;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.ToString;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageMatcher;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * 微信消息处理器
 *
 * @author liuhe
 */
@AllArgsConstructor
@Component
public class MsgHandler extends AbstractHandler implements WxMpMessageMatcher {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        //默认的消息处理
        String content = "收到你的信息了";
        return new TextBuilder(content).build(wxMessage, weixinService);
    }

    @Override
    public boolean match(WxMpXmlMessage wxMpXmlMessage) {
        return true;
    }
}
