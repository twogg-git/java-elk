FROM openjdk:8-jdk-alpine
# RUN apk update && apk add bash
COPY sbrest-0.1.jar sbrest.jar
VOLUME /logs
EXPOSE 8080
ENTRYPOINT ["java","-jar","/sbrest.jar"]