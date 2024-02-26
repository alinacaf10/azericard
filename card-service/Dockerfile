FROM openjdk:17-oracle
EXPOSE 8000
ARG JAR_FILE="./target/card-service-1.0.0-SNAPSHOT.jar"
ADD ${JAR_FILE} card-service.jar
ENTRYPOINT ["java", "-jar", "card-service.jar"]