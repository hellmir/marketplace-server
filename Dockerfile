ARG BASE_IMAGE=public.ecr.aws/docker/library/eclipse-temurin:21-jre
FROM ${BASE_IMAGE}
EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
