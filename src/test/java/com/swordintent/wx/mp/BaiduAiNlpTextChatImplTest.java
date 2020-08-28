package com.swordintent.wx.mp;

import com.swordintent.wx.mp.dependency.impl.baiduai.BaiduAiNlpTextChatImpl;
import com.swordintent.wx.mp.dependency.util.BaiduAipClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaiduAiNlpTextChatImplTest {

    @Autowired
    private BaiduAipClient baiduAipClient;

    private BaiduAiNlpTextChatImpl baiduNlpTextChat;

    @Before
    public void before(){
        baiduNlpTextChat = new BaiduAiNlpTextChatImpl(baiduAipClient);
    }

    @Test
    public void chat() throws Exception {
        String ret = baiduNlpTextChat.chat("123456", "你好");
        ret = baiduNlpTextChat.chat("123456", "我不好");
        ret = baiduNlpTextChat.chat("123456", "不知道");
    }
}