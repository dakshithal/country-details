# Build jar file
FROM maven:3.5-jdk-8-alpine AS maven
RUN mkdir /app
WORKDIR /app
COPY src /app/src
COPY pom.xml /app
RUN mvn clean package

# Run jar file
FROM openjdk:8-jre
COPY --from=maven /app/target/assignment-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
