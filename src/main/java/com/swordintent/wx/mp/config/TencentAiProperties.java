package com.swordintent.wx.mp.config;

import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "ai.tencent")
public class TencentAiProperties {

    private String appid;

    private String appkey;
    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
