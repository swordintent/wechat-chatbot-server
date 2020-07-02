package com.swordintent.wx.mp.dependency;

/**
 * 文本聊天机器人
 */
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
