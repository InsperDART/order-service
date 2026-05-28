FROM openjdk:23-slim
COPY target/*.jar /app.jar
ENTRYPOINT [ "java", "-jar", "/app.jar" ]