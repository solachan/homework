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
    - 打开浏览器，访问 `http://localhost:8080/api/incidents`。

## API 文档

### 创建事件
- **URL**: `/api/incidents`
- **Method**: `POST`
- **Request Body**:

```
{ "type": "Error", "title": "Database Connection Failed", "description": "The database connection failed due to incorrect credentials.", "status": "Open" }
```
- **Response**:
```
{ "id": 1, "type": "Error", "title": "Database Connection Failed", "description": "The database connection failed due to incorrect credentials.", "status": "Open", "fingerprint": "generated_fingerprint" }
```

### 查询事件
- **URL**: `/api/incidents`
- **Method**: `GET`
- **Response**:
```
[ { "id": 1, "type": "Error", "title": "Database Connection Failed", "description": "The database connection failed due to incorrect credentials.", "status": "Open", "fingerprint": "generated_fingerprint" } ]
```
### 更新事件
- **URL**: `/api/incidents/{id}`
- **Method**: `PUT`
- **Request Body**:
```
{ "type": "Error", "title": "Database Connection Failed", "description": "The database connection failed due to incorrect credentials.", "status": "Closed" }
```
- **Response**:
```
{ "id": 1, "type": "Error", "title": "Database Connection Failed", "description": "The database connection failed due to incorrect credentials.", "status": "Closed", "fingerprint": "generated_fingerprint" }
```
### 删除事件
- **URL**: `/api/incidents/{id}`
- **Method**: `DELETE`
- **Response**: No content (204)
- **DuplicateFingerprintException**：当尝试创建具有相同指纹的事件时，系统会抛出此异常。
- **Response**:
```
{ "timestamp": "2023-10-01T12:34:56.789+00:00", "status": 400, "error": "Bad Request", "message": "Incident with the same fingerprint already exists.", "path": "/api/incidents" }
```
## 测试
- **单元测试**：使用 JUnit 和 Mockito 进行单元测试。
- **集成测试**：使用 Spring Boot Test 进行集成测试。

## 部署指南
1. **打包项目**：
```
sh  
mvn clean package
```
2. **运行 JAR 文件**：
```
sh   
java -jar target/incident-manage-system-0.0.1-SNAPSHOT.jar
```
3. **Docker 部署**（可选）：
    - 创建 Dockerfile：
```
dockerfile FROM openjdk:17-jdk-slim COPY target/incident-manage-system-0.0.1-SNAPSHOT.jar /app/incident-manage-system.jar WORKDIR /app CMD ["java", "-jar", "incident-manage-system.jar"]
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
