FROM openjdk:8-jdk-alpine
COPY target/sbrest-1.0.0.jar sbrest.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/sbrest.jar"]