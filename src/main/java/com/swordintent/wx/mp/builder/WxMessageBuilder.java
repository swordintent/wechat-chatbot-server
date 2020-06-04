package com.swordintent.wx.mp.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信消息生成器
 * @author liuhe
 */
public interface WxMessageBuilder {

    /**
     * generateReponse out message
     *
     * @param wxMessage
     * @param service
     * @return
     */
    WxMpXmlOutMessage build(WxMpXmlMessage wxMessage, WxMpService service);
}
