FROM eclipse-temurin:23-jre-alpine

ENV TZ=Asia/Shanghai \
    JAVA_OPTS="-XX:+UseG1GC -Djava.security.egd=file:/dev/./urandom"

RUN addgroup -S appuser && \
    adduser -S -G appuser appuser

WORKDIR /app

RUN mkdir -p logs && \
    chown -R appuser:appuser logs

EXPOSE 20200

COPY --chown=appuser:appuser target/land*.jar land.jar

USER appuser
ENTRYPOINT exec java $JAVA_OPTS -jar land.jar