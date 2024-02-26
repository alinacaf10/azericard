FROM openjdk:17-oracle
EXPOSE 8002
ARG JAR_FILE="./target/product-service-1.0.0-SNAPSHOT.jar"
ADD ${JAR_FILE} product-service.jar
ENTRYPOINT ["java", "-jar", "product-service.jar"]