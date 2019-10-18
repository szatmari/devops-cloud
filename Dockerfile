FROM openjdk:8-jdk-alpine
RUN mkdir /app
ADD build/libs/gs-rest-service-0.1.0.jar /app
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/gs-rest-service-0.1.0.jar"]
