# Template App - Surgery Patients

Use this repository as a starting point for a database homework. It contains one generic entity, `Item`, and a simple server-rendered CRUD flow.

If you want to see a more complete reference application, use [flights-db-hw-3](https://github.com/stpavliuk/flights-db-hw-3).

Use it to:
- run the app quickly
- inspect one complete MVC flow
- rename `Item` to your own domain model
- replace the schema and build your own application from the same structure

## Stack

- Java 21+
- Gradle
- Spring Boot
- Spring Data JDBC
- Thymeleaf
- Liquibase
- MySQL

## Project Structure

- [TemplateApplication.java](src/main/java/org/example/app/TemplateApplication.java): Spring Boot entry point
- [IndexController.java](src/main/java/org/example/app/IndexController.java): redirects `/` to `/item`
- [ItemController.java](src/main/java/org/example/app/patient/ItemController.java): CRUD controller
- [Item.java](src/main/java/org/example/app/patient/Item.java): entity
- [ItemRepository.java](src/main/java/org/example/app/patient/ItemRepository.java): repository
- [0_schema.sql](src/main/resources/db/changelog/0_schema.sql): minimal schema
- [1_data.sql](src/main/resources/db/changelog/1_data.sql): small seed dataset
- [templates/item](src/main/resources/templates/item): Thymeleaf pages

## Local Setup

The `docker-compose.yml` is already configured for the `surgery-patients-hw3` database. 

Start the database:

```bash
docker compose up -d
```

Verify your src/main/resources/application.properties has the matching values:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3307/surgery-patients-hw3}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:secret}
```

Or set environment variables:

```bash
export DB_URL=jdbc:mysql://localhost:3307/surgery-patients-hw3
export DB_USERNAME=root
export DB_PASSWORD=secret
```

Run the project:

```bash
./gradlew bootRun
```

Open:

```text
http://localhost:8080
```

The screens of working web application:
!(Picture1.png)
!(Picture2.png)
!(Picture3.png)
!(Picture4.png)
