FROM openjdk:8-jre-alpine3.9

WORKDIR app

COPY src ./
COPY pom.xml ./

RUN ls

RUN apk update
RUN apk add maven

RUN mvn clean install
RUN mvn clean package


ENTRYPOINT ["java", "-jar", "jumia-task-0.0.1-SNAPSHOT.jar"]
