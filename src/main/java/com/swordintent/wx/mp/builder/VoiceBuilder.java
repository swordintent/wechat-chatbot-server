package com.swordintent.wx.mp.builder;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;

/**
 * 微信公众号语音类消息生成，需要永久或临时mediaId
 * @author liuhe
 */
@AllArgsConstructor
public class VoiceBuilder implements WxMessageBuilder {

    private final String mediaId;

    @Override
    public WxMpXmlOutVoiceMessage build(WxMpXmlMessage wxMessage,
                                   WxMpService service) {
        WxMpXmlOutVoiceMessage msg = WxMpXmlOutMessage.VOICE().mediaId(mediaId)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return msg;
    }
}
