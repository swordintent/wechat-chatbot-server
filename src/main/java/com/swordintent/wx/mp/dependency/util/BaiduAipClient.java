package com.swordintent.wx.mp.dependency.util;

import com.baidu.aip.http.*;
import com.baidu.aip.nlp.AipNlp;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.swordintent.wx.mp.utils.JsonUtils;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author liuhe
 */
public class BaiduAipClient extends AipNlp {

    private static final String UNIT_CHAT_URI = "https://unit-api.baidu.com/rpc/2.0/unit/service/chat";
    private final String robotId;

    public BaiduAipClient(String appId, String apiKey, String secretKey, String robotId) {
        super(appId, apiKey, secretKey);
        this.robotId = robotId;
    }

    public JSONObject robotChat(String uniqId, String sessionId, String userId, String content, HashMap options) {
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
        return requestServer(request);
    }

    private static class UnitRobotRequest {
        private final String version;
        private final String service_id;
        private final String log_id;
        private final String session_id;
        private final InnerRequest request;

        public UnitRobotRequest(String version, String service_id, String log_id, String session_id, InnerRequest request) {
            this.version = version;
            this.service_id = service_id;
            this.log_id = log_id;
            this.session_id = session_id;
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
                return new UnitRobotRequest(version, service_id, log_id, session_id, new InnerRequest(user_id, query));
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
