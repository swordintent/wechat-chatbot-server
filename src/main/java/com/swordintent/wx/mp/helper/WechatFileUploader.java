package com.swordintent.wx.mp.helper;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author liuhe
 */
@Component
public class WechatFileUploader {

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
    public String uploadVoice(String fromUser, byte[] data, WxMpService weixinService) throws IOException, WxErrorException {
        File tempFile = File.createTempFile(fromUser, ".mp3");
        FileUtils.writeByteArrayToFile(tempFile, data);
        WxMediaUploadResult result = weixinService.getMaterialService().mediaUpload("voice", tempFile);
        return result.getMediaId();
    }
}
