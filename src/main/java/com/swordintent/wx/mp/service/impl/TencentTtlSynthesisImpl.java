package com.swordintent.wx.mp.service.impl;

import cn.xsshome.taip.speech.TAipSpeech;
import com.swordintent.wx.mp.pojo.bot.TencentAiTtlSynthesisRet;
import com.swordintent.wx.mp.service.TtlSynthesisService;
import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

/**
 * 语音合成实现
 *
 * @author liuhe
 */
@Service
@AllArgsConstructor
public class TencentTtlSynthesisImpl implements TtlSynthesisService {

    private final TAipSpeech tAipSpeech;

    /**
     * 语音发音人编码 普通话男声 1 静琪女声 5 欢馨女声 6 碧萱女声 7
     */
    private static final int SPEAKER = 7;

    /**
     * 合成语音格式编码 PCM 1 WAV 2 MP3 3
     */
    private static final int FORMAT = 3;

    /**
     * 合成语音音量 取值范围[-10, 10]，如-10表示音量相对默认值小10dB，0表示默认音量，10表示音量相对默认值大10dB合成语音音量 取值范围[-10, 10]，如-10表示音量相对默认值小10dB，0表示默认音量，10表示音量相对默认值大10dB
     */
    private static final int VOLUMN = 0;

    /**
     * 合成语音语速，默认100
     */
    private static final int SPEED = 100;

    /**
     * 合成语音降低/升高半音个数，即改变音高，默认0
     */
    private static final int AHT = 7;

    /**
     * 控制频谱翘曲的程度，改变说话人的音色，默认58
     */
    private static final int APC = 53;

    @Override
    public byte[] synthesis(String content) throws Exception {
        String ret = tAipSpeech.TtsSynthesis(content, SPEAKER, FORMAT, VOLUMN, SPEED, AHT, APC);
        TencentAiTtlSynthesisRet tencentAiTtlSynthesisRet = JsonUtils.fromJson(ret, TencentAiTtlSynthesisRet.class);
        if (tencentAiTtlSynthesisRet.getRet() == 0) {
            return Base64Utils.decodeFromString(tencentAiTtlSynthesisRet.getData().getSpeech());
        }
        return null;
    }
}
