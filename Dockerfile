FROM openjdk:8
EXPOSE 8080
ADD target/api-pags.jar api-pags.jar
ENTRYPOINT ["java","-jar","/api-pags.jar"]