FROM alpine:3.15.0

WORKDIR app

COPY src src
COPY pom.xml pom.xml

RUN apk update
RUN apk add openjdk8
RUN apk add maven

RUN mvn clean package


ENTRYPOINT ["java", "-jar", "jumia-task-0.0.1-SNAPSHOT.jar"]
