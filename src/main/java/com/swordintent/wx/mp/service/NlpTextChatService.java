package com.swordintent.wx.mp.service;

public interface NlpTextChatService {

    /**
     * 聊天
     * @param session
     * @param content
     * @return
     * @throws Exception
     */
    String chat(String session, String content) throws Exception;
}
