
## Table of Contents
- [Introduction](#introduction)
- [Functionality](#functionality)
- [Technologies](#technologies)
- [How to start](#setting-up-dev)

## Introduction
Test-assignment project that fetches data from https://www.arbeitnow.com/api/jobs, saves data to the database 
and then returns data as per REST API

## Technologies
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://openjdk.org/)
[![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)

## Functionality

Project is hosted on AWS EC2 instance. Jobs-data is fetched during initial startup, by using @PostConstruct annotation to add logic in bean creation lifecycle.
Then data is converted to DMO object and stored in H2 database sorted by create_at EPOCH long value.
Then with the help of Scheduler, new data is fetched from the https://www.arbeitnow.com/api/jobs and compared with 
created_at value. New data is stored, old data is discarded.
Stored data is saved to H2 database and then fetched in JobsApiController. Controller handles different get request 
as per assignment. Response returned with HATEOAS style, including page info, links and wrapped in DATA node.
Methods are documented with SWAGGER in order to get brief info.

### Setting up Dev

First you need to pull the latest code

```shell
git clone https://github.com/AndriiMartishyn/jobs-api-parsing
cd jobs-api-parsing/
```

To access database locally
```shell
http://localhost:8080/v1/jobs
https://localhost:8080/h2-console
```

To access Swagger UI or Api info
```shell
http://localhost:8080/v3/api-docs
http://localhost:8080/swagger-ui/index.htm
```

Project also runs on AWS EC2 Instance

```shell
ec2-18-193-71-244.eu-central-1.compute.amazonaws.com:8080
```