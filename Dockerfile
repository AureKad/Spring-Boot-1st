FROM openjdk:22-jdk-alpine
MAINTAINER devitro.com
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]