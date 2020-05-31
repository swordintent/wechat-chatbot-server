# wechat-chatbot-server
目前支持文本聊天，同时利用微信公众号的语音识别能力，可实现接收用户语音消息进行文字聊天

![maven compile and publish to docker hub](https://github.com/liuhe36/wechat-chatbot-server/workflows/maven%20compile%20and%20publish%20to%20docker%20hub/badge.svg)

# 使用方法

##注册一个公众号
https://mp.weixin.qq.com/

##注册腾讯AI平台
https://ai.qq.com/product/nlpchat.shtml

##初始化数据库（可选）
执行`src/main/resource/scripts/init-db.sql`创建相关表

##启动服务
本项目镜像已同步推送至dockerhub，可直接采用docker方式启动，相关配置参数通过docker的环境变量方式传入，参考命令如下
```
docker run --net=host -e SERVER_PORT=9090 \
    -e WX_APPID=替换 -e WX_SECRET=替换 \
    -e WX_TOKEN=替换 -e WX_AESKEY=替换 \
    -e DB_HOST=127.0.0.1 -e  DB_PORT=3306 -e DB_NAME=yourdbname -e  DB_USER=yourusername -e  DB_PASSWORD=yourpassword \
    -e AI_APPID=替换 -e AI_APPKEY=替换 \
    shadowdk/wechat-chatbot-server
```

# 微信公众号平台

![微信公众平台配置](https://raw.githubusercontent.com/liuhe36/resource/master/wechat-chatbot-server/images/wechat-config.png)

# 腾信AI聊天机器人

![腾讯AI配置](https://raw.githubusercontent.com/liuhe36/resource/master/wechat-chatbot-server/images/tencent-ai-config.png)

