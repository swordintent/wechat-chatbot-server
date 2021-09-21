package com.swordintent.wx.mp.dependency.util;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tbp.v20190627.TbpClient;
import com.tencentcloudapi.tbp.v20190627.models.TextProcessRequest;
import com.tencentcloudapi.tbp.v20190627.models.TextProcessResponse;

import java.util.Optional;

/**
 * @author liuhe
 */
public class TencentAiRobotClient {

    private final String robotId;
    private final TbpClient client;

    public TencentAiRobotClient(String secretId, String secretKey, String robotId) {
        Credential cred = new Credential(secretId, secretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("tbp.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        this.client = new TbpClient(cred, "", clientProfile);
        this.robotId = robotId;
    }

    public String robotChat(String session, String content) throws TencentCloudSDKException {
        TextProcessRequest req = new TextProcessRequest();
        req.setBotId(robotId);
        req.setBotEnv("release");
        req.setTerminalId(session);
        req.setInputText(content);
        TextProcessResponse resp = client.TextProcess(req);
        return Optional.ofNullable(resp)
                .map(x -> x.getResponseMessage())
                .map(x -> x.getGroupList())
                .filter(x -> x.length > 0)
                .map(x -> x[0].getContent())
                .orElseGet(null);
    }
}
