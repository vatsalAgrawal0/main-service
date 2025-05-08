FROM eclipse-temurin:23-jre-alpine
EXPOSE 8085

WORKDIR /app
COPY target/main-service-*.jar ./main-service.jar

ENTRYPOINT ["java", "-jar", "main-service.jar"]
