FROM maven:3.8.5-openjdk-17-slim AS mvnbuild

WORKDIR /opt/workspace

COPY pom.xml .
RUN mvn dependency:resolve-plugins dependency:go-offline -B -T 1C

COPY src/ src/
COPY openapi.yaml src/main/resources/static
RUN mvn install -B -T 1C

FROM eclipse-temurin:17-jdk-alpine as jdk17-jre-base

RUN jlink \
  --verbose \
  --compress 2 \
  --strip-java-debug-attributes \
  --no-header-files \
  --no-man-pages \
  --add-modules \
  java.base,java.xml,java.prefs,java.desktop,java.naming,java.management,java.security.jgss,java.instrument,jdk.crypto.ec,jdk.httpserver,java.rmi,jdk.jdwp.agent \
  --output /opt/jvm_small

FROM alpine:3.10.1

ENV JAVA_HOME=/opt/jvm_small
ENV PATH="$PATH:$JAVA_HOME/bin"
COPY --from=jdk17-jre-base /opt/jvm_small /opt/jvm_small

RUN mkdir /opt/inventory-manager/
COPY --from="mvnbuild" /opt/workspace/target/inventory-manager.jar /opt/inventory-manager/

EXPOSE 8080

CMD ["java","-Djava.awt.headless=true","-jar","/opt/inventory-manager/inventory-manager.jar"]