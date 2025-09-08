
# Pet Service

## Overview

Pet Service is a Spring Boot microservice exposing REST APIs to manage pets with full CRUD operations.
The current implementation uses an in-memory H2 database for simplicity, so the service can run ready to use without any external setup.

The codebase is structured to keep the domain layer independent of persistence, 
making it easy to replace H2 with a NoSQL database (for example MongoDB) in the future.

## Tech Stack

🪶 **Maven** 3.9.9 \
☕ **Java** openjdk17  \
🍃 **Spring** 3.5.5  \
🛢️ **H2** 2.3.232 


## Run Locally

Clone the project

```bash
git clone https://github.com/erikamarrazzo1/pet-service.git
```

Go to the project directory

```bash
cd pet-service
```

Build & Run application

```bash
docker build -t pet-service:v1.0.0 -f docker/Dockerfile . && \
docker run -d --name pet-service -p 8080:8080 pet-service:v1.0.0
```

## Test APIs

Swagger: http://localhost:8080/swagger-ui/index.html

## Technical Details

The project is structured with a clear separation between the domain layer and the persistence layer.
This makes the code easier to maintain, test, and change in the future.

The domain layer contains the main business model, `Pet`, along with enums and domain-specific 
exceptions like InvalidPetAgeException.
These classes are completely independent of the database and do not include any 
database-specific annotations.

The `PetRepository` defines the contract between the application logic and any persistence system.
The `PetService` depends only on this interface, not on a specific database implementation.
This design allows switching from one database to another (for example, from H2 to a non-relational database) 
without changing the domain or service code.

The persistence layer implements this repository interface.
Right now, it uses **Spring Data JPA** with H2 and handles all database-specific logic, 
including mapping between entities and domain objects.
Adding a new database in the future would simply mean creating a new `PetRepository` implementation 
that implements the same interfaces, leaving the rest of the application untouched.
