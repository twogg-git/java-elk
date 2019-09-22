FROM openjdk:8-jdk-alpine

EXPOSE 8080

ARG JAR_FILE=target/sbrest-1.0.0.jar

ADD ${JAR_FILE} sbrest.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sbrest.jar"]