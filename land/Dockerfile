FROM eclipse-temurin:23-jre-alpine

ENV TZ=Asia/Shanghai \
    JAVA_OPTS="-XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

RUN addgroup -S appuser && \
    adduser -S -G appuser appuser

WORKDIR /app

RUN mkdir -p config logs

# 声明端口和卷
EXPOSE 20200
VOLUME ["/app/logs", "/app/config"]

COPY --chown=appuser:appuser target/classes/application*.yml config/
COPY --chown=appuser:appuser target/land*.jar land.jar

USER appuser
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar land.jar"]