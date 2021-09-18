package com.swordintent.wx.mp.dependency.impl.baiduai;

import com.baidu.aip.speech.AipSpeech;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.swordintent.wx.mp.dependency.TtlSynthesisService;
import com.swordintent.wx.mp.dependency.impl.baiduai.dto.RobotResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯AI平台聊天机器人实现
 *
 * @author liuhe
 */
@AllArgsConstructor
public class BaiduAiNlpSynthesisImpl implements TtlSynthesisService {

    private final AipSpeech aipSpeech;

    @Override
    public byte[] synthesis(String content) throws Exception {
//        aipSpeech.synthesis(content, )
        return new byte[0];
    }
}