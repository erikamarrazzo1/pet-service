
# Pet Service

## Overview

The project contains a CRUD apis for managing pets.

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
  docker build -t pet-service:v1.0.0 -f docker/Dockerfile . && docker run -d --name pet-service -p 8080:8080 pet-service:v1.0.0
```




## Test APIs

With the app running, all APIs can be tested here: http://localhost:8080/swagger-ui/index.html
