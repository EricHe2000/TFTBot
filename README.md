# TFTBot
A Team Fight Tactics bot script with customizable team comps, gold controls, and re-queue abilities. The bot includes the option of mission completion or surrender for tokens, with the ability to start a match and accept queue by itself. The bot is programmed to buy the selected champions of choosing and combine the items selected by the user. The placement of champions can also be pre-set based on choosen team comps. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for play and testing purposes.

### Installing

How to get the bot running  
Locate folder TFTbot and run command below in CMD

If running as source code

```
$ git clone https://github.com/EricHe2000/TFTBot/
$ mvn spring-boot:run
```

Build frontend
```
$ npm build
```

If running as jar

```
$ java -jar target/tftbot-0.0.1-SNAPSHOT.jar
```

## Changing Team Comps
Under resources/TeamComps create new text file for team comp

## Spring Boot Controls
http://localhost:8081/start?name= [Insert your team comp file name]  
http://localhost:8081/stop  
http://localhost:8081/monitor  
Buttons on home page also work

## Built With

* [Spring](https://spring.io/projects/spring-boot) - The configuration framework used
* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Eric He**
