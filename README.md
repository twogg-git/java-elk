# Playing with Java + ELK Stack 
<img height="200" width="600" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_stack.png">

[![Build Status](https://cloud.drone.io/api/badges/twogg-git/java-elk/status.svg)](https://cloud.drone.io/twogg-git/java-elk)

This repo aims to practice with ELK Stack thanks to a simple SpringBoot application. 
Following this repo, you are going to:  
- Monitor a SpringBoot application log and API beats with the ELK Stack.​
- Practice with Kibana Dashboards and indexing tools. ​
- Follow each ELK setup and understand connections and events setup.​

Click here to get more info on [ELK Monitoring](https://github.com/twogg-git/talks/blob/master/ELK_Monitoring_Endava.pdf).

## Running the services...
```sh
docker-compose up -d --build
```

## Kibana initial setup and basic use. 
1. Go to http://localhost:5601/
<img height="300" width="550" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_1create_index.png">

2. Configure all matching index pattern: *
<img height="300" width="480" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_2index_match.png">

3. Set Time Filter field name: @timestamp  
<img height="300" width="480" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_3timestamp.png">

4. Save and click *Discover* to visualize the current logs saved in ElasticSearch database.   
<img height="350" width="700" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_4discover.png">

5. Add some useful fields like *tags* to order the results.  
<img height="350" width="250" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_5tags.png">

6. Now enjoy logging with the ELK Stack.
<img height="400" width="800" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_6tags_selected.png">

## Docker-Compose services

#### Interactive
- spring: http://localhost:8081/api/user/   
<img height="400" width="800" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_sbrest.png">

- kibana: http://localhost:5601   
<img height="400" width="800" src="https://raw.githubusercontent.com/twogg-git/talks/master/resources/elk_repo/elk_kibana.png">

#### Background (No WebUI access)
- elastic search 
- logstah
- filebeat
- heartbeat

## Java SpringBoot Loggin   
Logback configuration to send spring log file to elasticsearch through logstash.   
[/pom.xml](https://github.com/twogg-git/java-elk/blob/master/pom.xml)
```sh
    ...
	<properties>
		<java.version>1.8</java.version>
	</properties>
    ...
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

[/src/main/resources/application.yml)](https://github.com/twogg-git/java-elk/blob/master/src/main/resources/application.yml)
```sh
server:
  port: 8080
  contextPath: /sbrest

logging:
  file: logs/application.log
```

## Filebeat Setup  
Logs from Java will be send to logstash the to elasticsearch indexing.     
[/elk-filebeat/filebeat.yml)](https://github.com/twogg-git/java-elk/blob/master/elk-filebeat/filebeat.yml)
```sh
filebeat.inputs:
   - type: log
    paths:
      - /logs/application.log  
    tags: ["filebeat", "log_file"]    
    ...
output.elasticsearch:
  hosts: ["elasticsearch:9200"]
...
# X-pack optional module
xpack.monitoring.enabled: true
xpack.monitoring.elasticsearch.hosts: ["host.docker.internal:9200"]
```

## Heartbeat Setup  
Setting up services and endpoints to monitor HTTP status.   
[/elk-heartbeat/heartbeat.yml)](https://github.com/twogg-git/java-elk/blob/master/elk-heartbeat/heartbeat.yml)
```sh
heartbeat.monitors:
# ELK monitors
...
- type: http
  schedule: '*/3 * * * * *'
  urls: ["http://elasticsearch:9200"]   
  name: "monitor_elasticsearch"
  tags: ["elk", "elasticsearch_index", "status"]
...
# APP monitors
- type: http
  schedule: '*/1 * * * * *'
  urls: ["http://sbrest:8080/health"]
  check.response.status: 200
  name: "monitor_app_health"
  tags: ["app_info", "health", "status"]
...
# STACK setup
heartbeat.scheduler:
  limit: 1
output.elasticsearch:
  hosts: ["elasticsearch:9200"]
setup.kibana:
  host: "kibana:5601"
```

## Logstash Setup  
The input will come filebeat, then the events will filtered and send to elasticsearch.     
[/elk-logstash/logstash.conf)](https://github.com/twogg-git/java-elk/blob/master/elk-logstash/logstash.conf)
```sh
input {

    file {
        path => "/logs/application.log"
        tags => ['sbrest', 'application.log']
        type => "logback"
    }
}

output {
    elasticsearch {
        hosts => ["elasticsearch:9200"]
        manage_template => false
        index => "logback-%{+YYYY.MM.dd}"
        document_type => "application.log"
   }
}
```

## Run locally ONLY the SpringBoot Rest App
Go to the folder **/java-sbrest** then run the following Docker commands.
```sh
docker build -t sbrest_only .
docker run --name sbrest_only --rm -v $PWD/logs:/logs -p 8082:8080 sbrest_only
```

#### Updating the source code
If you are going to make changes into the source code and test those into the ELK enviroment. Remember to build the **sbrest-0.1.jar.jar** file, then copy it from /target into the /java-sbrest folder, or the changes wont be updated into the compose deployment.
```sh
    mvn dependency:tree
    mvn -Dtest=SpringBootRestTestClient test
    mvn package
    mvn spring-boot:run

    cp target/sbrest-0.1.jar java-sbrest/sbrest-0.1.jar
     
    # If the compose environment is running.
    docker-compose up --detach --build sbrest
```

#### Testing logs outputs
To try endpoints outputs in the logstash and filebeat monitoring apps, run any of this curl requests.
```sh
curl http://localhost:8081/api/user/  
curl http://localhost:8081/api/user/2  
curl http://localhost:8081/api/user/a  
curl http://localhost:8081/api/user/99  
curl http://localhost:8081/api/dummy 
curl http://localhost:8081/api/debug 

# This example will output a full java exception
curl http://localhost:8081/api/exception 
```