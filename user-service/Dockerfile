FROM openjdk:17-oracle
EXPOSE 8004
ARG JAR_FILE="./target/user-service-1.0.0-SNAPSHOT.jar"
ADD ${JAR_FILE} user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]