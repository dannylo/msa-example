FROM openjdk:8-jdk-alpine
VOLUME /tmp

RUN mkdir -p /api-inventory/
RUN mkdir -p /api-inventory/logs/

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /api-inventory/app-inventory.jar

ADD target/inventory-0.0.1-SNAPSHOT.jar /api-inventory/api-inventory.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/api-inventory/api-inventory.jar"]