package com.swordintent.wx.mp.pojo.bot;

import lombok.Data;

@Data
public class ChatRet extends BaseBotRet{

    private ChatRetInner data;

    @Data
    public class ChatRetInner{
        private String session;

        private String answer;
    }
}
