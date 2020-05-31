CREATE TABLE `chat_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255)  NOT NULL,
  `req_content` varchar(255)  NOT NULL,
  `resp_content` varchar(255)  NOT NULL,
  `extral` varchar(255)  NULL DEFAULT NULL,
  `user_context` varchar(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;