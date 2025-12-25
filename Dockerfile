ARG BASE_IMAGE=public.ecr.aws/amazoncorretto/amazoncorretto:21-alpine3.19
FROM ${BASE_IMAGE}
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
