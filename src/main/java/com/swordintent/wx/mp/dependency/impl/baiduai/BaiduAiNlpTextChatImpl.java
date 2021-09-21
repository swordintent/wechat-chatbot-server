package com.swordintent.wx.mp.dependency.impl.baiduai;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.swordintent.wx.mp.dependency.NlpTextChatService;
import com.swordintent.wx.mp.dependency.impl.baiduai.dto.RobotResponse;
import com.swordintent.wx.mp.dependency.util.BaiduAiRobotClient;
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
public class BaiduAiNlpTextChatImpl implements NlpTextChatService {

    private final BaiduAiRobotClient baiduAiRobotClient;

    private static Cache<String, Optional<String>> SESSION_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.MINUTES).maximumSize(10000).build();

    @Override
    public String chat(String session, String content) throws Exception {
        if (StringUtils.isEmpty(content)) {
            return null;
        }
        RobotResponse robotResponse = baiduAiRobotClient.robotChat(UUID.randomUUID().toString(),
                SESSION_CACHE.get(session, () -> Optional.empty()).orElse(""),
                session,
                content);
        if (robotResponse.getError_code() == 0) {
            return Optional.ofNullable(robotResponse.getResult()).map(e -> {
                SESSION_CACHE.put(session, Optional.ofNullable(e.getSession_id()));
                return Optional.ofNullable(e.getResponse_list())
                        .filter(list -> list.size() > 0)
                        .map(list -> list.get(0))
                        .map(x -> x.getAction_list())
                        .filter(actionItems -> actionItems.size() > 0)
                        .map(actionItems -> actionItems.get(0))
                        .map(actionItem -> actionItem.getSay())
                        .orElseGet(null);
            }).orElseGet(null);
        }
        return null;
    }
}