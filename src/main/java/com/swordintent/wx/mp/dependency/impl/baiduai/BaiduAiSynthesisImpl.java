package com.swordintent.wx.mp.dependency.impl.baiduai;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.swordintent.wx.mp.dependency.TtlSynthesisService;
import com.swordintent.wx.mp.dependency.impl.baiduai.dto.RobotResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 腾讯AI平台聊天机器人实现
 *
 * @author liuhe
 */
@AllArgsConstructor
public class BaiduAiSynthesisImpl implements TtlSynthesisService {

    private final AipSpeech aipSpeech;

    @Override
    public byte[] synthesis(String content) throws Exception {
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "5");
        options.put("pit", "5");
        options.put("per", "0");
        TtsResponse response = aipSpeech.synthesis(content, "zh", 1, options);
        return Optional.ofNullable(response).map(x -> x.getData()).orElseGet(null);
    }
}