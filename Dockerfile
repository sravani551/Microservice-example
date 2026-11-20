# One image definition for every service: docker build --build-arg MODULE=user-service .
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src
COPY pom.xml .
COPY eureka-server/pom.xml eureka-server/
COPY api-gateway/pom.xml api-gateway/
COPY user-service/pom.xml user-service/
COPY post-service/pom.xml post-service/
COPY notification-service/pom.xml notification-service/
ARG MODULE
# Cache dependencies in their own layer so code changes don't re-download them
RUN mvn -B -q -pl ${MODULE} -am dependency:go-offline
COPY . .
RUN mvn -B -q -pl ${MODULE} -am package -DskipTests

FROM eclipse-temurin:17-jre
ARG MODULE
WORKDIR /app
COPY --from=build /src/${MODULE}/target/${MODULE}-*.jar app.jar
RUN useradd --system app
USER app
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
