package com.swordintent.wx.mp.handler.bot;

import cn.xsshome.taip.nlp.TAipNlp;
import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.mapper.ChatInfoMapper;
import com.swordintent.wx.mp.pojo.bot.ChatInfoDo;
import com.swordintent.wx.mp.pojo.bot.ChatRet;
import com.swordintent.wx.mp.handler.AbstractHandler;
import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageMatcher;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TextMsgChatBotHandler extends AbstractHandler implements WxMpMessageMatcher {

    private final TAipNlp tAipNlp;

    private final ChatInfoMapper chatInfoMapper;
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        String responseContent = "好像出错了";
        try {
            String userInputContent = getTextContent(wxMessage);
            String fromUser = wxMessage.getFromUser();
            String nlpResponse = getAutoResponse(userInputContent, fromUser);
            responseContent = Optional.ofNullable(nlpResponse).orElse(responseContent);
            savelog(fromUser, userInputContent, responseContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text(wxMessage, weixinService, responseContent);
    }

    private void savelog(String fromUser, String userInputContent, String responseContent) {
        if(StringUtils.isAnyBlank(fromUser, userInputContent, responseContent)){
            return;
        }
        ChatInfoDo chatInfoDo = new ChatInfoDo();
        chatInfoDo.setOpenid(fromUser);
        chatInfoDo.setReqContent(userInputContent);
        chatInfoDo.setRespContent(responseContent);
        chatInfoMapper.insert(chatInfoDo);
    }

    private String getAutoResponse(String queryContent, String fromUser) throws Exception {
        if(StringUtils.isEmpty(queryContent)){
            return null;
        }
        String response = tAipNlp.nlpTextchat(fromUser, queryContent);
        ChatRet chatRet = JsonUtils.fromJson(response, ChatRet.class);
        String answer;
        if(chatRet.getRet() == 0){
            answer = chatRet.getData().getAnswer();
            return answer;
        }
        return null;
    }

    private String getTextContent(WxMpXmlMessage wxMessage) {
        String ct = wxMessage.getContent();
        if(StringUtils.isEmpty(ct)){
            ct = wxMessage.getRecognition();
        }
        return ct;
    }

    private WxMpXmlOutMessage text(WxMpXmlMessage wxMessage, WxMpService weixinService, String content) {
        return new TextBuilder().build(content, wxMessage, weixinService);
    }

    @Override
    public boolean match(WxMpXmlMessage wxMpXmlMessage) {
        return true;
    }
}
