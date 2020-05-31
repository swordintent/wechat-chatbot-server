package com.swordintent.wx.mp.pojo.bot;

import lombok.Data;

@Data
public class TencentAiTtlSynthesisRet extends BaseTencentAiRet {

    private TencentAiTtlSynthesisInner data;

    @Data
    public class TencentAiTtlSynthesisInner {
        private String speech;
    }
}
