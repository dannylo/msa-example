FROM openjdk:8-jdk-alpine

VOLUME /tmp

# The application's jar file
ARG JAR_FILE=target/product-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]