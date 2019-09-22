FROM openjdk:8-jdk-alpine

#ARG JAR_FILE=target/sbrest-1.0.0.jar
#ADD ${JAR_FILE} sbrest.jar

ADD target/sbrest-1.0.0.jar sbrest.jar

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sbrest.jar"]