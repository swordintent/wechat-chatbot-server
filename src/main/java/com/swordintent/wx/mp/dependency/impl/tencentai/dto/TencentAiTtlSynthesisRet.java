package com.swordintent.wx.mp.dependency.impl.tencentai.dto;

import com.swordintent.wx.mp.dependency.impl.tencentai.dto.BaseTencentAiRet;
import lombok.Data;

@Data
public class TencentAiTtlSynthesisRet extends BaseTencentAiRet {

    private TencentAiTtlSynthesisInner data;

    @Data
    public class TencentAiTtlSynthesisInner {
        private String speech;
    }
}
