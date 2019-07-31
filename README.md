# TFTBot
A Team Fight Tactics bot script with customizable team comps and 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for play and testing purposes.

### Installing

A step by step series of examples that tell you how to get the bot running

If running as source code

```
$ mvn spring-boot:run
```

If running as jar

```
java -jar target/tftbot-0.0.1-SNAPSHOT.jar
```

## Changing Team Comps
Under resources/TeamComps create new text file for team comp

## Spring Boot controls
http://localhost:8081/start?name=[Insert your team comp file name]
http://localhost:8081/stop
http://localhost:8081/monitor

## Built With

* [Spring](https://spring.io/projects/spring-boot) - The configuration framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Eric He**
