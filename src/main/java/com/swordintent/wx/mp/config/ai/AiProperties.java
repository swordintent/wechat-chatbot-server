package com.swordintent.wx.mp.config.ai;

import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 微信公众号配置属性
 * @author liuhe
 */
@Data
@ConfigurationProperties(prefix = "ai.cfg")
public class AiProperties {

    private String textchat;

    private String ttlsynthesis;

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
