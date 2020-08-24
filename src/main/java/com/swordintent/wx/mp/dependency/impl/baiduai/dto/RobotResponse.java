package com.swordintent.wx.mp.dependency.impl.baiduai.dto;

import lombok.Data;

import java.util.List;

/**
 * @author liuhe
 */
@Data
public class RobotResponse {
    private RobotResponseInner result;
    private int error_code;

    @Data
    public static class RobotResponseInner {
        private String session_id;
        private List<ResponseItem> response_list;

        @Data
        public static class ResponseItem {
            private String msg;
            private List<ActionItem> action_list;

            @Data
            public static class ActionItem {
                private double confidence;
                private String say;

            }
        }
    }
}