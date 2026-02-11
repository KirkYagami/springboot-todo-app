# Task Manager API

A simple Spring Boot REST API for managing tasks.
This project demonstrates a clean layered architecture using Controller, Service, and Repository patterns with Spring Data JPA and MySQL.

## Tech Stack

* Java 21
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* MySQL
* Spring Boot Actuator
* Maven

## Features

* Create a task
* Retrieve all tasks
* Retrieve task by ID
* Update a task
* Delete a task
* Mark task as completed
* Filter tasks by status
* Search tasks by title
* Basic health endpoints
* Actuator monitoring support

## Project Structure

* `controller` – Handles HTTP requests and responses
* `service` – Contains business logic
* `repository` – Data access layer using Spring Data JPA
* `model` – JPA entity definitions

The application follows a clean separation of concerns to keep logic organized and maintainable.

## Database Configuration

The application uses MySQL. Update your `application.properties` with your local database credentials if needed.

Example:

```
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=taskuser
spring.datasource.password=taskpass
```

Make sure the database exists before starting the application.

## Running the Application

Clone the project and run:

```
mvn spring-boot:run
```

Or build and run:

```
mvn clean install
java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```

The application runs on:

```
http://localhost:8080
```

## API Endpoints

Base URL: `/api/tasks`

* `POST /api/tasks` – Create a new task
* `GET /api/tasks` – Get all tasks
* `GET /api/tasks/{id}` – Get task by ID
* `PUT /api/tasks/{id}` – Update task
* `DELETE /api/tasks/{id}` – Delete task
* `PATCH /api/tasks/{id}/complete` – Mark task as completed
* `GET /api/tasks/status/{completed}` – Filter by completion status
* `GET /api/tasks/search?keyword=value` – Search by title

Health endpoints:

* `GET /`
* `GET /health`

Actuator endpoints:

* `/actuator/health`
* `/actuator/info`
* `/actuator/metrics`

## Sample Request Body

```
{
  "title": "Finish documentation",
  "description": "Write README and finalize API details",
  "completed": false
}
```

## Notes

* JPA auto-generates and updates tables using `ddl-auto=update`.
* Timestamps are automatically handled using `@PrePersist` and `@PreUpdate`.
* Repository methods use Spring Data JPA query method naming conventions.

This project can serve as a foundation for expanding into authentication, pagination, validation, and production-ready features.
