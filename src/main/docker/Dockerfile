FROM --platform=linux/amd64 openjdk:8-jdk-alpine as stage-amd64
FROM --platform=linux/arm64 arm64v8/openjdk:8-jdk-alpine as stage-arm64

ARG TARGETARCH
FROM stage-${TARGETARCH} as final

VOLUME /opt/logs/
ADD wechat-chatbot-server-1.0.0-SNAPSHOT.jar app.jar

ENV SERVER_PORT=9090 \
	WX_APPID=$WX_APPID \
	WX_SECRET=$WX_SECRET \
	WX_TOKEN=$WX_TOKEN \
	WX_AESKEY=$WX_AESKEY

ENV DB_HOST=127.0.0.1 \
	DB_PORT=3306 \
	DB_NAME=$DB_NAME \
	DB_USER=$DB_USER \
	DB_PASSWORD=$DB_PASSWORD

ENV AI_APPID=$AI_APPID \
	AI_APPKEY=$AI_APPKEY
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
