FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src /app/src

RUN mvn clean package

FROM openjdk:17

WORKDIR /app

COPY --from=build /app/target/pet-service-0.0.1-SNAPSHOT.jar pet-service.jar

ENTRYPOINT ["java", "-jar", "pet-service.jar"]

EXPOSE 8080