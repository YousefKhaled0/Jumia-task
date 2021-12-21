FROM openjdk:8-jre-alpine3.9

WORKDIR app

COPY src ./
COPY pom.xml ./

RUN apk update
RUN apk add maven
RUN mvn clean package 

WORKDIR target
RUN ls

ENTRYPOINT ["java", "-jar", "jumia-task-0.0.1-SNAPSHOT.jar"]
