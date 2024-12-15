# Project Name
Incident Management System

## 项目概述
The Incident Management System is an application designed to manage and track various types of incidents. The system supports the creation, querying, updating, and deletion of incidents, ensuring that each incident has a unique fingerprint identifier to prevent duplicate incidents.

## Main Features
- **Incident Creation**: Users can create new incidents, and the system will automatically generate a unique fingerprint.
- **Incident Querying**: Users can query incidents based on different criteria, including paginated queries.
- **Incident Updating**: Users can update existing incident information.
- **Incident Deletion**: Users can delete incidents that are no longer needed.

## Technology Stack
- **Programming Language**: Java
- **Framework**: Spring Boot
- **Persistence Layer**: Spring Data JPA
- **Database**: H2 In-Memory Database
- **Build Tool**: Maven
- **Version Control**: Git


## Project Structure
``` 
│  .gitattributes
│  .gitignore
│  HELP.md
│  incident.iml
│  mvnw
│  mvnw.cmd
│  pom.xml
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─xiepuxin
    │  │          └─incident
    │  │              │  IncidentApplication.java
    │  │              │
    │  │              ├─config
    │  │              ├─controller
    │  │              │      IncidentController.java
    │  │              │
    │  │              ├─dao
    │  │              │  │  IncidentDao.java
    │  │              │  │
    │  │              │  └─impl
    │  │              ├─dto
    │  │              │      IncidentDTO.java
    │  │              │
    │  │              ├─entity
    │  │              │      Incident.java
    │  │              │
    │  │              ├─exception
    │  │              │      DuplicateFingerprintException.java
    │  │              │      ResourceNotFoundException.java
    │  │              │
    │  │              ├─handler
    │  │              │      GlobalExceptionHandler.java
    │  │              │
    │  │              ├─model
    │  │              │      R.java
    │  │              │
    │  │              ├─service
    │  │              │  │  IncidentService.java
    │  │              │  │
    │  │              │  └─impl
    │  │              │          IncidentServiceImpl.java
    │  │              │
    │  │              └─validate
    │  │                      AddGroup.java
    │  │                      EditGroup.java
    │  │
    │  └─resources
    │      │  application.properties
    │      │
    │      ├─static
    │      └─templates
    └─test
        └─java
            └─com
                └─xiepuxin
                    └─incident
                            IncidentApplicationTest.java
```

## Development Environment Setup
1. **Install Java Development Kit (JDK)**: Ensure JDK 8 or higher is installed.
2. **Install Maven**: Ensure Maven 3.6 or higher is installed.
3. **Configure Database Connection**:
   - Open the `src/main/resources/application.properties` file.
   - Configure the database connection details, for example:

### properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=500,expireAfterAccess=60s
logging.level.org.springframework=DEBUG 

## Project Startup
1. **Clone the Project**:
```
sh   
git clone https://github.com/solachan/homework.git
cd incident
```

2. **Compile the Project**:
```
sh  
mvn clean install  
```

3. **Run the Project**:
```
sh  
mvn spring-boot:run  
```

4. **Access the Application**:
   - Open a browser and navigate to `http://localhost:8080/incident`.

## API Documentation

### Create Incident
- **URL**: `/incident`
- **Method**: `POST`
- **Request Body**:

```
{
     "type": "Error",
     "title": "Database Connection Failed",
     "description": "The database connection failed due to incorrect credentials.",
     "status": 0,
}
```
- **Response**:
```
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "id": 1,
        "type": "Error",
        "title": "Database Connection Failed",
        "description": "The database connection failed due to incorrect credentials.",
        "status": 0,
        "time": "2024-12-15 16:21:13",
        "fingerprint": "fdff58e8bd76198ba0b67a0815dd4718"
    },
    "traceId": null
}
```

### Query incident
- **URL**: `/incident?page=0&size=10`
- **Method**: `GET`
- **Response**:
```
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/incident?pageNum=1&pageSize=10&page=0&size=20&sort=id,desc"
            }
        ],
        "content": [
            {
                "id": 1,
                "type": "dolor do non est",
                "title": "生你干系走化",
                "description": "表任国容养可员军成单江她青近样置完十。必状以花元术同同山律场类界常而太海。济据引间想社织示能数作容查。快属果处些证果科员色响京府代。",
                "status": 0,
                "time": "2024-12-15 16:21:13",
                "fingerprint": "fdff58e8bd76198ba0b67a0815dd4718",
                "links": []
            }
        ],
        "page": {
            "size": 20,
            "totalElements": 1,
            "totalPages": 1,
            "number": 0
        }
    },
    "traceId": null
}
```
### Update Incident
- **URL**: `/incident/{id}`
- **Method**: `PUT`
- **Request Body**:
```
{
    "type": "et do occaecat mollit cillum",
    "title": "引龙员意置",
    "description": "容院更号头步细红育技队还花论那立团严。外到组即些如听治集不教群知运己质。政到油油美直龙清打大取选常放红。无油象很技深眼动别今育调生化石才。",
    "status": 1
}
```
- **Response**:
```
{
    "code": 200,
    "msg": "操作成功",
    "data": {
        "id": 1,
        "type": "et do occaecat mollit cillum",
        "title": "引龙员意置",
        "description": "容院更号头步细红育技队还花论那立团严。外到组即些如听治集不教群知运己质。政到油油美直龙清打大取选常放红。无油象很技深眼动别今育调生化石才。",
        "status": 1,
        "time": "2024-12-15 16:24:18",
        "fingerprint": "58b949986b2ff0eec533c515e86ccbbc"
    },
    "traceId": null
}
```
### Delete Incident
- **URL**: `/incident/{id}`
- **Method**: `DELETE`
- **Response**: No content (204)
- **DuplicateFingerprintException**: Thrown when attempting to create an incident with the same fingerprint.
- **Response**:
```
{
    "code": 200,
    "msg": "操作成功",
    "data": null,
    "traceId": null
}
```
## Testing
- **Unit Tests**: Use JUnit and Mockito for unit testing.
- **Integration Tests**: Use Spring Boot Test for integration testing.

## Deployment Guide
1. **Package the Project**:
```
sh  
mvn clean package -Dmaven.test.skip=true
```
2. **Run the JAR File**:
```
sh   
java -jar target/incident-manage-system-0.0.1-SNAPSHOT.jar
```
3. **Docker Deployment** (Optional):
   - Create a Dockerfile:
```
dockerfile  
FROM openjdk:17-jdk-slim  
COPY target/incident-manage-system-0.0.1-SNAPSHOT.jar /app/incident-manage-system.jar  
WORKDIR /app 
CMD ["java", "-jar", "incident-manage-system.jar"]
```
- Build the Docker Image:
```
sh  
docker build -t incident-manage-system .
```
- Run the Docker Container:
```
sh  
docker run -p 8080:8080 incident-manage-system
```
