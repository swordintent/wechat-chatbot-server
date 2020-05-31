package com.swordintent.wx.mp.service;

public interface TtlSynthesisService {

    /**
     * 语音合成，返回base64编码格式语音
     * @return
     */
    String synthesis(String content) throws Exception;
}
