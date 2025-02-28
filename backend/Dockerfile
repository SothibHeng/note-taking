FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/note-0.0.1-SNAPSHOT.jar note-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","note-0.0.1-SNAPSHOT.jar"]
