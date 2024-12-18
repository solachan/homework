## 项目名称
事件管理系统 (Incident manage System)

## 项目概述
事件管理系统是一个用于管理和跟踪各种类型事件的应用程序。系统支持事件的创建、查询、更新和删除操作，并确保每个事件都有一个唯一的指纹标识，以避免重复事件的创建。

## 主要功能
- **事件创建**：用户可以创建新的事件，系统会自动生成一个唯一的指纹标识。
- **事件查询**：用户可以根据不同的条件查询事件，包括分页查询。
- **事件更新**：用户可以更新已有的事件信息。
- **事件删除**：用户可以删除不再需要的事件。
- **唯一性检查**：在创建事件时，系统会检查指纹是否已经存在，如果存在则抛出异常。

## 技术栈
- **编程语言**：Java
- **框架**：Spring Boot
- **持久层**：Spring Data JPA
- **数据库**：H2内存数据库
- **构建工具**：Maven
- **版本控制**：Git

## 项目结构
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

## 开发环境配置
1. **安装 Java Development Kit (JDK)**：确保安装了 JDK 8 或更高版本。
2. **安装 Maven**：确保安装了 Maven 3.6 或更高版本。
3. **配置数据库连接**：
    - 打开 `src/main/resources/application.properties` 文件。
    - 配置数据库连接信息，例如：

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

## 项目启动
1. **克隆项目**：  
   sh   
   git clone https://github.com/solachan/homework.git
   cd incident

2. **编译项目**：
   sh  
   mvn clean install

3. **运行项目**：
   sh  
   mvn spring-boot:run

4. **访问应用**：
    - 打开浏览器，访问 `http://localhost:8080/incident`。

## API 文档

### 创建事件
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

### 查询事件
- **URL**: `/incident`
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
### 更新事件
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
### 删除事件
- **URL**: `/incident/{id}`
- **Method**: `DELETE`
- **Response**: No content (204)
- **DuplicateFingerprintException**：当尝试创建具有相同指纹的事件时，系统会抛出此异常。
- **Response**:
```
{
    "code": 200,
    "msg": "操作成功",
    "data": null,
    "traceId": null
}
```
## 测试
- **单元测试**：使用 JUnit 和 Mockito 进行单元测试。
- **集成测试**：使用 Spring Boot Test 进行集成测试。

## 部署指南
1. **打包项目**：
```
sh  
mvn clean package -Dmaven.test.skip=true
```
2. **运行 JAR 文件**：
```
sh   
java -jar target/incident-manage-system-0.0.1-SNAPSHOT.jar
```
3. **Docker 部署**（可选）：
    - 创建 Dockerfile：
```
dockerfile  
FROM openjdk:17-jdk-slim  
COPY target/incident-manage-system-0.0.1-SNAPSHOT.jar /app/incident-manage-system.jar WORKDIR /app  
CMD ["java", "-jar", "incident-manage-system.jar"]
```
- 构建 Docker 镜像：
```
sh  
docker build -t incident-manage-system .
```
- 运行 Docker 容器：
```
sh  
docker run -p 8080:8080 incident-manage-system
```
