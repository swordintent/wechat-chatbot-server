package com.swordintent.wx.mp.dependency.impl.tencentai;

import com.swordintent.wx.mp.dependency.TtlSynthesisService;
import com.tencentcloudapi.tts.v20190823.TtsClient;
import com.tencentcloudapi.tts.v20190823.models.TextToVoiceRequest;
import com.tencentcloudapi.tts.v20190823.models.TextToVoiceResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.util.Optional;
import java.util.UUID;

/**
 * 语音合成实现
 *
 * @author liuhe
 */
@AllArgsConstructor
public class TencentAiSynthesisImpl implements TtlSynthesisService {

    private final TtsClient ttsClient;

    @Override
    public byte[] synthesis(String content) throws Exception {
        // 实例化一个请求对象,每个接口都会对应一个request对象
        TextToVoiceRequest req = new TextToVoiceRequest();
        req.setText(content);
        req.setSessionId(UUID.randomUUID().toString());
        req.setModelType(1L);
//        req.setVoiceType(101001L);
        req.setVoiceType(4L);
        req.setCodec("mp3");

        // 返回的resp是一个TextToVoiceResponse的实例，与请求对象对应
        TextToVoiceResponse resp = ttsClient.TextToVoice(req);
        return Optional.ofNullable(resp).map(x -> x.getAudio()).map(x -> Base64Utils.decodeFromString(x)).orElseGet(null);
    }
}
