FROM eclipse-temurin:23-jre-alpine
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:+UseG1GC "

WORKDIR /opt/land
RUN mkdir -p /var/log/land

COPY target/land-*.jar land.jar

EXPOSE 20200
VOLUME /var/log/land

# 启动命令
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar land.jar"]