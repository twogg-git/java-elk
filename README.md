# Java + ELK

## Compile java code
mvn dependency:tree

mvn package

## Run App locally

java -jar target/sbrest-1.0.0.jar

mvn spring-boot:run

## Endpoints 

http://localhost:8080/sbrest/api/user/


## Docker  

docker build -t sbrest .
docker run --rm -p 8080:8080 --name sbrest sbrest

## Maven-Docker pluging () 

- Use your DockerHub account (https://hub.docker.com)

mvn package dockerfile:build

docker login

docker push twogghub/java-elk:1.0.0

https://hub.docker.com/r/twogghub/java-elk

