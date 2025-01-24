FROM eclipse-temurin:23-jre-alpine

ENV TZ=Asia/Shanghai \
    JAVA_OPTS="-XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

# 创建必要目录
WORKDIR /app
RUN mkdir -p config logs

# 声明端口和卷
EXPOSE 20200
VOLUME ["/app/logs", "/app/config"]

# 复制应用文件
COPY target/classes/*.yml config/
COPY target/land-*.jar land.jar

# 启动命令
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar land.jar"]