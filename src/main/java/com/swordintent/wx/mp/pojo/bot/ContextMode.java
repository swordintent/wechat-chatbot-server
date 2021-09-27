package com.swordintent.wx.mp.pojo.bot;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户所处的上下文模式
 */
public enum ContextMode {

    DEFAULT(0, "恢复默认", "默认"),
    TEXT_CHAT(1, "我要文字聊天", "文字聊天"),
    VOICE_CHAT(2, "我要语音聊天", "语音聊天"),
    VOICE_TRANSLATE(3, "我要转换文字", "文字语音转换"),
    IMAGE_QUERY(4, "我要图", "图片卡通化"),
    ;

    private int value;
    private String cmd;
    private String desc;

    ContextMode(int value, String cmd, String desc) {
        this.value = value;
        this.cmd = cmd;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getCmd() {
        return cmd;
    }

    public static ContextMode getMode(String text){
        for(ContextMode mode : ContextMode.values()){
            if(StringUtils.equals(mode.cmd, text)){
                return mode;
            }
        }
        return null;
    }
}
