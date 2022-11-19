FROM openjdk:11-jdk-slim
MAINTAINER ilya-papruga

COPY target/meetup-api-1.0.0.jar meetup.jar

ENTRYPOINT ["java", "-jar", "/meetup.jar"]