# Java + ELK 

[![Build Status](https://cloud.drone.io/api/badges/twogg-git/java-elk/status.svg)](https://cloud.drone.io/twogg-git/java-elk)

## Running the services...

```sh
docker-compose up --build
````

### Interactive
- spring: http://localhost:8081/api/user/   
<img height="300" width="500" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_sbrest.png">

- kibana: http://localhost:5601   
<img height="300" width="500" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_kibana.png">

### Background (No WebUI access)
- elastic search 
- logstah
- filebeat
- heartbeat



## Java SpringBoot Code Highlights  

### Loggin setup
[/pom.xml](https://github.com/twogg-git/java-elk/blob/master/pom.xml)
```sh
...
	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
        ...
		<dependency>
            <groupId>net.logstash.logback</groupId>
            <artifactId>logstash-logback-encoder</artifactId>
            <version>5.2</version>
            <scope>runtime</scope>
        </dependency>
	</dependencies>
...
```





## Run locally: SpringBoot App

```sh

docker build -t sbrest .

docker run --name sbrest --rm -v $PWD/logs:/logs -p 8081:8080 sbrest
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
java -jar target/sbrest-0.1.jar

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




