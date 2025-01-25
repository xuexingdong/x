FROM eclipse-temurin:23-jre-alpine

ENV TZ=Asia/Shanghai \
    JAVA_OPTS="-XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

# 创建非 root 用户
RUN addgroup -S appuser && \
    adduser -S -G appuser appuser \

RUN mkdir -p /app/config /app/logs

WORKDIR /app

# 声明端口和卷
EXPOSE 20200
VOLUME ["/app/logs", "/app/config"]

# 复制应用文件
COPY target/classes/*.yml config/
COPY target/land-*.jar land.jar

RUN chown -R appuser:appuser /app

USER appuser
ENTRYPOINT ["java","${JAVA_OPTS} -jar land.jar"]