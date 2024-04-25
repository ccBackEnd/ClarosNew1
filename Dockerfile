FROM openjdk:8-jdk-alpine
COPY ./target/com.tempKafka-0.0.1-SNAPSHOT.jar clarosone.jar
CMD ["java","-jar","clarosone.jar"]
