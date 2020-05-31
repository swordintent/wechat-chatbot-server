package com.swordintent.wx.mp.handler.bot;

import com.swordintent.wx.mp.builder.TextBuilder;
import com.swordintent.wx.mp.builder.VoiceBuilder;
import com.swordintent.wx.mp.mapper.ChatInfoMapper;
import com.swordintent.wx.mp.pojo.bot.ChatInfoDo;
import com.swordintent.wx.mp.pojo.bot.TencentAiNlpTextChatRet;
import com.swordintent.wx.mp.handler.AbstractHandler;
import com.swordintent.wx.mp.service.NlpTextChatService;
import com.swordintent.wx.mp.service.TtlSynthesisService;
import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageMatcher;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import me.chanjar.weixin.mp.bean.material.WxMpMaterialUploadResult;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TextMsgChatBotHandler extends AbstractHandler implements WxMpMessageMatcher {

    private final NlpTextChatService nlpTextChatService;

    private final TtlSynthesisService ttlSynthesisService;

    private final ChatInfoMapper chatInfoMapper;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        String responseContent = "好像出错了";
        String userInputContent = "";
        String fromUser = "";
        try {
            userInputContent = getUserInputContent(wxMessage);
            fromUser = getFromUser(wxMessage);
            String nlpResponse = getNlpAutoResponse(userInputContent, fromUser);
            responseContent = Optional.ofNullable(nlpResponse).orElse(responseContent);
            saveLog(fromUser, userInputContent, responseContent);
        } catch (Exception e) {
            logger.error(String.format("user:%s request:%s response:%s", fromUser, userInputContent, responseContent), e);
        }
        return response(wxMessage, weixinService, responseContent);
    }

    private WxMpXmlOutMessage response(WxMpXmlMessage wxMessage, WxMpService weixinService, String responseContent) {
        //尽量利用语音回复，若语音暂时不可用，则返回文字消息
        WxMpXmlOutMessage wxMpXmlOutMessage = voiceResponse(wxMessage, weixinService, responseContent);
        return Optional.ofNullable(wxMpXmlOutMessage)
                .orElse(text(wxMessage, weixinService, responseContent));
    }

    private WxMpXmlOutMessage voiceResponse(WxMpXmlMessage wxMessage, WxMpService weixinService, String responseContent) {
        try {
            String synthesisData = ttlSynthesisService.synthesis(responseContent);
            if(synthesisData == null){
                return null;
            }
            String mediaId = getVoiceMediaId(wxMessage, weixinService, synthesisData);
            return voice(wxMessage, weixinService, mediaId);
        } catch (Exception e) {
            logger.warn("ttlSynthesisService error", e);
        }

        return null;
    }

    private String getVoiceMediaId(WxMpXmlMessage wxMessage, WxMpService weixinService, String synthesis) throws IOException, WxErrorException {
        File tempFile = File.createTempFile(getFromUser(wxMessage),".mp3");
        FileUtils.writeByteArrayToFile(tempFile, Base64Utils.decodeFromString(synthesis));
        WxMediaUploadResult voice = weixinService.getMaterialService().mediaUpload("voice", tempFile);
        return voice.getMediaId();
    }

    private String getFromUser(WxMpXmlMessage wxMessage) {
        return wxMessage.getFromUser();
    }

    private void saveLog(String fromUser, String userInputContent, String responseContent) {
        if(StringUtils.isAnyBlank(fromUser, userInputContent, responseContent)){
            return;
        }
        ChatInfoDo chatInfoDo = new ChatInfoDo();
        chatInfoDo.setOpenid(fromUser);
        chatInfoDo.setReqContent(userInputContent);
        chatInfoDo.setRespContent(responseContent);
        doSaveLog(chatInfoDo);
    }

    private void doSaveLog(ChatInfoDo chatInfoDo) {
        try{
            chatInfoMapper.insert(chatInfoDo);
        }catch (Exception e){
            logger.warn("save log to db error, please set db params correctly", e);
        }
    }

    private String getNlpAutoResponse(String queryContent, String fromUser) throws Exception {
        if(StringUtils.isEmpty(queryContent)){
            return null;
        }
        return nlpTextChatService.chat(fromUser, queryContent);
    }

    private String getUserInputContent(WxMpXmlMessage wxMessage) {
        String ct = wxMessage.getContent();
        if(StringUtils.isEmpty(ct)){
            //打开语音识别结果
            ct = wxMessage.getRecognition();
        }
        return ct;
    }

    private WxMpXmlOutMessage voice(WxMpXmlMessage wxMessage, WxMpService weixinService, String mediaId) {
        return new VoiceBuilder().build(mediaId, wxMessage, weixinService);
    }

    private WxMpXmlOutMessage text(WxMpXmlMessage wxMessage, WxMpService weixinService, String content) {
        return new TextBuilder().build(content, wxMessage, weixinService);
    }

    @Override
    public boolean match(WxMpXmlMessage wxMpXmlMessage) {
        return true;
    }
}
