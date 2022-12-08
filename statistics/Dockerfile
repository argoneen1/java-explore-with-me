FROM amazoncorretto:11-alpine-jdk
COPY statistics-server/target/statistics-server-0.0.1-SNAPSHOT.jar stats.jar
ENTRYPOINT ["java","-jar","/stats.jar"]