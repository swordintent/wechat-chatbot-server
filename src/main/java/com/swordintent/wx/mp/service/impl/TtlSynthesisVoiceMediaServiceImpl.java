package com.swordintent.wx.mp.service.impl;

import com.swordintent.wx.mp.dependency.TtlSynthesisService;
import com.swordintent.wx.mp.helper.WechatFileUploader;
import com.swordintent.wx.mp.service.TtlSynthesisVoiceMediaService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TtlSynthesisVoiceMediaServiceImpl implements TtlSynthesisVoiceMediaService {

    private static final Logger logger = LoggerFactory.getLogger(TtlSynthesisVoiceMediaServiceImpl.class);

    private final TtlSynthesisService ttlSynthesisService;

    private final WechatFileUploader wechatFileUploader;

    @Override
    public String ttlSynthesis(String fromUser, WxMpService weixinService, String content) {
        try {
            byte[] ttlSynthesisResult = ttlSynthesisService.synthesis(content);
            return Optional.ofNullable(ttlSynthesisResult)
                    .map(x -> wechatFileUploader.uploadVoice(fromUser, x, weixinService))
                    .orElse(null);
        } catch (Exception e) {
            logger.error("ttlSynthesis error.", e);
        }
        return null;
    }
}
