package com.swordintent.wx.mp.builder;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * 微信公众号文本类消息生成
 *
 * @author liuhe
 */
@AllArgsConstructor
public class TextBuilder implements WxMessageBuilder {

    private final String content;

    @Override
    public WxMpXmlOutTextMessage build(WxMpXmlMessage wxMessage, WxMpService service) {
        WxMpXmlOutTextMessage msg = WxMpXmlOutMessage.TEXT().content(content)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return msg;
    }
}
