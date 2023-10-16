FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:18
WORKDIR /app
COPY --from=build /app/target/FinancialHelper-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "FinancialHelper-0.0.1-SNAPSHOT.jar"]