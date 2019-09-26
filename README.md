# Java + ELK

[![Build Status](https://cloud.drone.io/api/badges/twogg-git/java-elk/status.svg)](https://cloud.drone.io/twogg-git/java-elk)

## Endpoints 

- http://localhost:8080/health   
- http://localhost:8080/version 
- http://localhost:8080/api/user/ 

## Run locally: SpringBoot App

```sh
docker build -t sbrest .

docker run --rm -p 8080:8080 --name sbrest sbrest
```

## Run locally: Elastic Stack 

```sh
docker-compose up --build
```

## ELK Stack 

http://localhost:5601/

#### Configure an index pattern
- Index pattern: heartbeat-*    
- Time Filter field name: @timestamp  
- Click *create*

## Compile java code

```sh
mvn dependency:tree

mvn -Dtest=SpringBootRestTestClient test

mvn package
```

## Run App locally

```sh
java -jar target/sbrest-1.0.0.jar

mvn spring-boot:run
```

## Maven-Docker pluging  

*From this point you need a DockerHub account* (https://hub.docker.com)

```sh
mvn package dockerfile:build

docker login

docker push twogghub/java-elk:1.0.0
```

https://hub.docker.com/r/twogghub/java-elk


## DroneCI

https://cloud.drone.io/twogg-git/java-elk

## Clean-Up 

```sh
docker-compose down --volumes
```
