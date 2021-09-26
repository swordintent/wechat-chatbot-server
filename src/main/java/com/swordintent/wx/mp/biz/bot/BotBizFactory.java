package com.swordintent.wx.mp.biz.bot;

import com.swordintent.wx.mp.pojo.bot.ContextMode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BotBizFactory {

    private final VoiceChatBotBiz voiceChatBotBiz;

    private final VoiceTranslateBotBiz voiceTranslateBotBiz;

    private final TextChatBotBiz textChatBotBiz;

    private final DefaultChatBotBiz defaultChatBotBiz;

    public BotBiz getBot(ContextMode mode){
        switch (mode){
            case TEXT_CHAT:
                return textChatBotBiz;
            case VOICE_CHAT:
                return voiceChatBotBiz;
            case VOICE_TRANSLATE:
                return voiceTranslateBotBiz;
            case IMAGE_QUERY:
            default:
                return defaultChatBotBiz;
        }
    }
}
