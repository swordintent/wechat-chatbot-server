package com.swordintent.wx.mp.dao.bot;

import com.swordintent.wx.mp.helper.ExecutorHelper;
import com.swordintent.wx.mp.mapper.ChatInfoMapper;
import com.swordintent.wx.mp.pojo.bot.ChatInfoDo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 机器人聊天记录器
 *
 * @author liuhe
 */
@AllArgsConstructor
@Component
public class ChatBotInfoRecorder {

    protected static Logger logger = LoggerFactory.getLogger(ChatBotInfoRecorder.class);

    private final ChatInfoMapper chatInfoMapper;

    private final ExecutorHelper executor;

    public void record(String fromUser, String userInputContent, String responseContent) {
        if (StringUtils.isAnyBlank(fromUser, userInputContent, responseContent)) {
            return;
        }
        ChatInfoDo chatInfoDo = new ChatInfoDo();
        chatInfoDo.setOpenid(fromUser);
        chatInfoDo.setReqContent(userInputContent);
        chatInfoDo.setRespContent(responseContent);
        executor.runAsync(() -> doSaveLog(chatInfoDo));
    }

    private void doSaveLog(ChatInfoDo chatInfoDo) {
        try {
            chatInfoMapper.insert(chatInfoDo);
        } catch (Exception e) {
            logger.warn("save log to db error, please set db params correctly.", e);
        }
    }
}
