package com.swordintent.wx.mp.pojo.bot;

import lombok.Data;

@Data
public class ChatInfoDo {
    private String openid;

    private String reqContent;

    private String respContent;

    private String extral;

    private String userContext;
}
