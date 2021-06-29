FROM openjdk:15.0.2-jdk-oracle
ARG JAR_FILE=target/library-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/app.jar"]
