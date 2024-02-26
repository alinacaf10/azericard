FROM openjdk:17-oracle
EXPOSE 8010
ARG JAR_FILE="./target/api-gateway-1.0.0-SNAPSHOT.jar"
ADD ${JAR_FILE} api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]