package com.swordintent.wx.mp.service;

import me.chanjar.weixin.mp.api.WxMpService;

/**
 * 语音合成微信voice媒体id
 */
public interface TtlSynthesisVoiceMediaService {

    /**
     * 合成
     * @param fromUser
     * @param weixinService
     * @param content
     * @return
     */
    String ttlSynthesis(String fromUser, WxMpService weixinService, String content);
}
