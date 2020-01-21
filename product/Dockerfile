FROM openjdk:8-jdk-alpine
VOLUME /tmp

RUN mkdir -p /api/
RUN mkdir -p /api/logs/

ADD target/product-0.0.1-SNAPSHOT.jar /api/api.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/api/api.jar"]