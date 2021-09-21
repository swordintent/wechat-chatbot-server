package com.swordintent.wx.mp;

import com.baidu.aip.speech.AipSpeech;
import com.swordintent.wx.mp.dependency.impl.baiduai.BaiduAiSynthesisImpl;
import com.swordintent.wx.mp.dependency.impl.baiduai.BaiduAiNlpTextChatImpl;
import com.swordintent.wx.mp.dependency.util.BaiduAiRobotClient;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaiduAiTest {

    @Autowired
    private BaiduAiRobotClient baiduAiRobotClient;

    @Autowired
    private AipSpeech aipSpeech;

    private BaiduAiNlpTextChatImpl baiduNlpTextChat;

    private BaiduAiSynthesisImpl baiduAiNlpSynthesis;

    @Before
    public void before(){
        baiduNlpTextChat = new BaiduAiNlpTextChatImpl(baiduAiRobotClient);
        baiduAiNlpSynthesis = new BaiduAiSynthesisImpl(aipSpeech);
    }

    @Test
    public void chat() throws Exception {
        String ret = baiduNlpTextChat.chat("123456", "你好");
        System.out.println(ret);
        Thread.sleep(1000);
        ret = baiduNlpTextChat.chat("123456", "我不好");
        Thread.sleep(1000);
        System.out.println(ret);
        ret = baiduNlpTextChat.chat("123456", "不知道");
        Thread.sleep(1000);
        System.out.println(ret);
    }

    @Test
    public void synthesis() throws Exception {
        byte[] synthesis = baiduAiNlpSynthesis.synthesis("灯火通明的内殿之中，金碧辉煌，气势威严，殿内有着长明灯燃烧，其中燃烧着一颗青石，袅袅的青烟升腾而起，盘绕在殿内");
        File tempFile = File.createTempFile("test", ".mp3");
        FileUtils.writeByteArrayToFile(tempFile, synthesis);
    }
}