FROM openjdk:8-jdk-alpine
VOLUME /tmp

RUN mkdir -p /api-credit/
RUN mkdir -p /api-credit/logs/

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /api-credit/app-credit.jar

ADD target/credit-custommer-0.0.1-SNAPSHOT.jar /api-credit/api-credit.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/api-credit/api-credit.jar"]