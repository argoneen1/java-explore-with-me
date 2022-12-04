FROM amazoncorretto:11-alpine-jdk
COPY statistics-*/*.jar stats.jar
ENTRYPOINT ["java","-jar","/stats.jar"]