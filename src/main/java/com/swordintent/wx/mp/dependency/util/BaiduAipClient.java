package com.swordintent.wx.mp.dependency.util;

import com.baidu.aip.http.*;
import com.baidu.aip.nlp.AipNlp;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.swordintent.wx.mp.dependency.impl.baiduai.dto.RobotResponse;
import com.swordintent.wx.mp.utils.JsonUtils;
import lombok.Data;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author liuhe
 */
public class BaiduAipClient extends AipNlp {

    private static final String UNIT_CHAT_URI = "https://aip.baidubce.com/rpc/2.0/unit/service/chat";
    private final String robotId;

    public BaiduAipClient(String appId, String apiKey, String secretKey, String robotId) {
        super(appId, apiKey, secretKey);
        this.robotId = robotId;
    }

    /**
     *
     * @param uniqId 开发者需要在客户端生成的唯一id，用来定位请求，响应中会返回该字段。对话中每轮请求都需要一个log_id
     * @param sessionId session保存机器人的历史会话信息，由机器人创建，客户端从上轮应答中取出并直接传递，不需要了解其内容。如果为空，
     *                  则表示清空session（开发者判断用户意图已经切换且下一轮会话不需要继承上一轮会话中的词槽信息时可以把session置空，从而进行新一轮的会话）
     * @param userId 与技能对话的用户id（如果客户端是用户未登录状态情况下对话的，也需要尽量通过其他标识（比如设备id）来唯一区分用户），
     *               方便今后在平台的日志分析模块定位分析问题、从用户维度统计分析相关对话情况。
     * @param content 内容
     * @return 机器人响应
     */
    public RobotResponse robotChat(String uniqId, String sessionId, String userId, String content) {
        AipRequest request = new AipRequest();
        preOperation(request);
        UnitRobotRequest unitRobotRequest = UnitRobotRequest.builder()
                .log_id(uniqId)
                .service_id(robotId)
                .session_id(sessionId)
                .user_id(userId)
                .query(content).build();
        request.addBody("body", JsonUtils.toJson(unitRobotRequest));
        request.setUri(UNIT_CHAT_URI);
        request.addHeader(Headers.CONTENT_ENCODING, HttpCharacterEncoding.ENCODE_UTF8);
        request.addHeader(Headers.CONTENT_TYPE, HttpContentType.JSON_DATA);
        request.setBodyFormat(EBodyFormat.RAW_JSON_ARRAY);
        postOperation(request);
        JSONObject jsonObject = requestServer(request);
        return JsonUtils.fromJson(jsonObject.toString(), RobotResponse.class);
    }

    private static class UnitRobotRequest {
        private final String version;
        private final String service_id;
        private final String log_id;
        private final String session_id;
        private final List<String> skill_ids;
        private final InnerRequest request;

        public UnitRobotRequest(String version, String service_id, String log_id, String session_id, List<String> skill_ids, InnerRequest request) {
            this.version = version;
            this.service_id = service_id;
            this.log_id = log_id;
            this.session_id = session_id;
            this.skill_ids = skill_ids;
            this.request = request;
        }

        public static Builder builder(){
            return new UnitRobotRequest.Builder();
        }
        private static class Builder {
            private String version;
            private String service_id;
            private String log_id;
            private String session_id;
            private String user_id;
            private String query;
            private List<String> skill_ids;

            public Builder() {
                this.version = "2.0";
            }

            private Builder service_id(String service_id) {
                this.service_id = service_id;
                return this;
            }

            private Builder log_id(String log_id) {
                this.log_id = log_id;
                return this;
            }

            private Builder session_id(String session_id) {
                this.session_id = session_id;
                return this;
            }

            private Builder user_id(String user_id) {
                this.user_id = user_id;
                return this;
            }

            private Builder query(String query) {
                this.query = query;
                return this;
            }

            private UnitRobotRequest build() {
                return new UnitRobotRequest(version, service_id, log_id, session_id, skill_ids, new InnerRequest(user_id, query));
            }

        }

        private static class InnerRequest {
            private final String user_id;
            private final String query;

            public InnerRequest(String user_id, String query) {
                this.user_id = user_id;
                this.query = query;
            }
        }
    }

}
