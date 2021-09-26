package com.swordintent.wx.mp.pojo.bot;

public class WechatUserInputContent {

    /**
     * 用户所处的上下文模式
     */
    private ContextMode contextMode;

    /**
     * 原始文本
     */
    private String rawText;

    /**
     * 识别出的指令
     */
    private String cmd;

    /**
     * 去除指令部分的内容
     */
    private String text;

}
