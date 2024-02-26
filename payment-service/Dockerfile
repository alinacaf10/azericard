FROM openjdk:17-oracle
EXPOSE 8001
ARG JAR_FILE="./target/payment-service-1.0.0-SNAPSHOT.jar"
ADD ${JAR_FILE} payment-service.jar
ENTRYPOINT ["java", "-jar", "payment-service.jar"]