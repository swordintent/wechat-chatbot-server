package com.swordintent.wx.mp.pojo.bot;

import lombok.Data;

@Data
public class TencentAiNlpTextChatRet extends BaseTencentAiRet {

    private TencentAiNlpTextChatInner data;

    @Data
    public class TencentAiNlpTextChatInner {
        private String session;

        private String answer;
    }
}
