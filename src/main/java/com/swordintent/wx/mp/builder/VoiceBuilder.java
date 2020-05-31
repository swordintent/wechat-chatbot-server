package com.swordintent.wx.mp.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;

public class VoiceBuilder extends AbstractBuilder {

    @Override
    public WxMpXmlOutMessage build(String mediaId, WxMpXmlMessage wxMessage,
                                   WxMpService service) {
        WxMpXmlOutVoiceMessage m = WxMpXmlOutMessage.VOICE().mediaId(mediaId)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return m;
    }
}
