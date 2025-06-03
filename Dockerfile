FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true
#RUN ls target  # Debugging step to list the contents of the target directory

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/aws-qna-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]