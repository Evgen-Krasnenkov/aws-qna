FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y maven && rm -rf /var/lib/apt/lists/*
WORKDIR /app

COPY pom.xml ./
COPY src ./src

RUN mvn clean package -Dmaven.test.skip=true

COPY target/aws-qna-0.0.1-SNAPSHOT.jar app.jar

ENV JAVA_TOOL_OPTIONS \
-Dorg.testcontainers.DEBUG=true

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]