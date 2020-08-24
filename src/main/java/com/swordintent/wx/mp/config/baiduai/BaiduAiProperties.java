package com.swordintent.wx.mp.config.baiduai;

import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 百度AI配置属性
 * @author liuhe
 */
@Data
@ConfigurationProperties(prefix = "ai.baidu")
public class BaiduAiProperties {

    private String appid;

    private String appkey;

    private String secretKey;

    private String robotId;
    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
