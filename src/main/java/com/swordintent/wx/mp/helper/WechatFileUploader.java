package com.swordintent.wx.mp.helper;

import com.swordintent.wx.mp.biz.bot.TextMsgChatBotBiz;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author liuhe
 */
@Component
public class WechatFileUploader {

    private static final Logger logger = LoggerFactory.getLogger(WechatFileUploader.class);

    /**
     * 上传音频至微信
     *
     * @param fromUser
     * @param data
     * @param weixinService
     * @return
     * @throws IOException
     * @throws WxErrorException
     */
    public String uploadVoice(String fromUser, byte[] data, WxMpService weixinService) {
        try{
            File tempFile = writeVoiceDataToFile(fromUser, data);
            WxMediaUploadResult result = weixinService.getMaterialService().mediaUpload("voice", tempFile);
            return result.getMediaId();
        }catch (Exception e){
            logger.error("upload voice error.", e);
        }
        return null;
    }

    private File writeVoiceDataToFile(String prefix, byte[] data) throws IOException {
        File tempFile = File.createTempFile(prefix, ".mp3");
        FileUtils.writeByteArrayToFile(tempFile, data);
        return tempFile;
    }
}
