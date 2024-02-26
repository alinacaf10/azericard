FROM openjdk:17-oracle
EXPOSE 8761
ARG JAR_FILE="./target/discovery-server-1.0.0-SNAPSHOT.jar"
ADD ${JAR_FILE} discovery-server.jar
ENTRYPOINT ["java", "-jar", "discovery-server.jar"]