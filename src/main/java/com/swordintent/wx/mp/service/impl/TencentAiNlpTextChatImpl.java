package com.swordintent.wx.mp.service.impl;

import cn.xsshome.taip.nlp.TAipNlp;
import com.swordintent.wx.mp.pojo.bot.TencentAiNlpTextChatRet;
import com.swordintent.wx.mp.service.NlpTextChatService;
import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 腾讯AI平台聊天机器人实现
 * @author liuhe
 */
@Service
@AllArgsConstructor
public class TencentAiNlpTextChatImpl implements NlpTextChatService {
    private final TAipNlp tAipNlp;

    @Override
    public String chat(String session, String content) throws Exception {
        if(StringUtils.isEmpty(content)){
            return null;
        }
        String ret = tAipNlp.nlpTextchat(session, content);
        TencentAiNlpTextChatRet tencentAiNlpTextChatRet = JsonUtils.fromJson(ret, TencentAiNlpTextChatRet.class);
        if(tencentAiNlpTextChatRet.getRet() == 0){
            return tencentAiNlpTextChatRet.getData().getAnswer();
        }
        return null;
    }
}
