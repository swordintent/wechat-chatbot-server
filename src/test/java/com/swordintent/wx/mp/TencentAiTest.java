package com.swordintent.wx.mp;

import com.swordintent.wx.mp.dependency.impl.tencentai.TencentAiNlpTextChatImpl;
import com.swordintent.wx.mp.dependency.impl.tencentai.TencentAiSynthesisImpl;
import com.swordintent.wx.mp.dependency.util.TencentAiRobotClient;
import com.tencentcloudapi.tts.v20190823.TtsClient;
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
public class TencentAiTest {

    @Autowired
    private TtsClient ttsClient;
    @Autowired
    private TencentAiRobotClient tencentAiRobotClient;

    private TencentAiNlpTextChatImpl textClient;

    private TencentAiSynthesisImpl synthesisClient;

    @Before
    public void before(){
        textClient = new TencentAiNlpTextChatImpl(tencentAiRobotClient);
        synthesisClient = new TencentAiSynthesisImpl(ttsClient);
    }

    @Test
    public void chat() throws Exception {
        String ret = textClient.chat("123456", "你好");
        System.out.println(ret);
        Thread.sleep(1000);
        ret = textClient.chat("123456", "我不好");
        Thread.sleep(1000);
        System.out.println(ret);
        ret = textClient.chat("123456", "不知道");
        Thread.sleep(1000);
        System.out.println(ret);
    }

    @Test
    public void synthesis() throws Exception {
        byte[] synthesis = synthesisClient.synthesis("今年中秋晚会沿袭篇章式结构。除了序《明月升》，尾声《海上明月》外，晚会还设置上篇《月到中秋》、中篇《月之故乡》、下篇《人月两圆》三个篇章。舞台视觉的主题意象以“北斗七星”为载体，与三个篇章交相呼应。北京时间9月21日晚8点让我们相约CCTV春晚频道共度佳节！");
        File tempFile = File.createTempFile("test", ".mp3");
        FileUtils.writeByteArrayToFile(tempFile, synthesis);
    }
}