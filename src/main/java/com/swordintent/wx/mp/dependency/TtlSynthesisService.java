package com.swordintent.wx.mp.dependency;

/**
 * 语音合成服务接口
 * @author liuhe
 */
public interface TtlSynthesisService {

    /**
     * 语音合成，返回base64编码格式语音
     * @param content
     * @return
     * @throws Exception
     */
    byte[] synthesis(String content) throws Exception;
}
