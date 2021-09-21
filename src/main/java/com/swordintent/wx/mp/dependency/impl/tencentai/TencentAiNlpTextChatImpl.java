package com.swordintent.wx.mp.dependency.impl.tencentai;

import com.swordintent.wx.mp.dependency.NlpTextChatService;
import com.swordintent.wx.mp.dependency.util.TencentAiRobotClient;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 腾讯AI平台聊天机器人实现
 * @author liuhe
 */
@AllArgsConstructor
public class TencentAiNlpTextChatImpl implements NlpTextChatService {

    private final TencentAiRobotClient client;

    @Override
    public String chat(String session, String content) throws Exception {
        if(StringUtils.isEmpty(content)){
            return null;
        }

        return client.robotChat(session, content);
    }
}
